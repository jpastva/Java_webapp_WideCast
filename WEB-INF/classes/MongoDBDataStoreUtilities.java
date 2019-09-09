import java.util.*;
import java.io.*;
import java.sql.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.sql.Date;
import com.mongodb.BasicDBObject;
import com.mongodb.BulkWriteOperation;
import com.mongodb.BulkWriteResult;
import com.mongodb.Cursor;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.ParallelScanOptions;
import com.mongodb.ServerAddress;
import com.mongodb.MongoException;
import com.mongodb.WriteConcern;
import com.mongodb.*;

public class MongoDBDataStoreUtilities{

static DBCollection reviews;

    public static void getConnection() {
		MongoClient mongo = new MongoClient("localhost", 27017);
		
		DB db = mongo.getDB("CustomerReviewsWC");
		reviews = db.getCollection("reviews");
	}

	
	public static HashMap<String, ArrayList<Review>> selectReview() {
		getConnection();
	    HashMap<String, ArrayList<Review>> reviewMap = new HashMap<String, ArrayList<Review>>();
	    DBCursor cursor = reviews.find();
	    while (cursor.hasNext()) {  
	   		BasicDBObject obj = (BasicDBObject) cursor.next();
		  	if(! reviewMap.containsKey(obj.getString("productName"))) {
		    	ArrayList<Review> arr = new ArrayList<Review>();
		    	reviewMap.put(obj.getString("productName"), arr);
		    }
			
			ArrayList<Review> listReview = reviewMap.get(obj.getString("productName"));
		 	Review review = new Review(obj.getString("productName"), obj.getString("productType"),
		 		obj.getString("productPrice"), obj.getString("distributor"),obj.getString("customerZip"), obj.getString("customerCity"),
		 		obj.getString("customerState"), obj.getString("userId"), obj.getString("userAge"), obj.getString("userGender"),
		 		obj.getString("userOccupation"), obj.getString("rating"), obj.getString("reviewDate"), obj.getString("reviewText"));

		 	listReview.add(review);
	 	}
		return reviewMap;
	}

	public static String insertReview(Review review) {
		int result = 0;
		
		String productName = review.getProductName();			
		String productType = review.getProductType();
		String productPrice = review.getProductPrice();
		String distributor = review.getDistributor();
		String customerZip = review.getCustomerZip();
		String customerCity = review.getCustomerCity();
		String customerState = review.getCustomerState();
		String userId = review.getUserId();
		String userAge = review.getUserAge();
		String userGender = review.getUserGender();
		String userOccupation = review.getUserOccupation();
		String rating = review.getRating(); 
		String reviewDate = review.getReviewDate();
		String reviewText = review.getReviewText();				
	
   		try{			
			getConnection();
			BasicDBObject doc  = new BasicDBObject("title","reviews");
			doc.append("productName", productName);
			doc.append("productType", productType);
			doc.append("productPrice", productPrice);
			doc.append("distributor", distributor);
			doc.append("customerZip", customerZip);
			doc.append("customerCity", customerCity);
			doc.append("customerState", customerState);
			doc.append("userId",userId);
			doc.append("userAge",userAge);
			doc.append("userGender", userGender);
			doc.append("userOccupation", userOccupation);
			doc.append("rating", rating);
			doc.append("reviewDate", reviewDate);
			doc.append("reviewText", reviewText);				
			reviews.insert(doc);
			return "Successful";
			 
		}
		catch(Exception e) {
			return "Unsuccessful";
		}
	}

	public static ArrayList<BestRating> topProducts() {
		ArrayList<BestRating> bestRate = new ArrayList<BestRating>();
	  	try {
			getConnection();
		  	int retlimit = 5;
		  	DBObject sort = new BasicDBObject();
		  	sort.put("rating",-1);
		  	DBCursor cursor = reviews.find().limit(retlimit).sort(sort);
		  	while(cursor.hasNext()) {
				BasicDBObject obj = (BasicDBObject) cursor.next();   
			  	String productnm = obj.get("productName").toString();
			  	String rating = obj.get("rating").toString();
		      	BestRating best = new BestRating(productnm,rating);

			  	bestRate.add(best);
	  		}
	
		}catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return bestRate;
	}
   

   // Get the top five most sold products regardless of rating
	public static ArrayList<MostSold> mostSoldProducts() {
		ArrayList <MostSold> mostSold = new ArrayList<>();
	  	
	  	try {
    		getConnection();
	        DBObject groupProducts = new BasicDBObject("_id","$productName"); 
		    groupProducts.put("count", new BasicDBObject("$sum",1));
		    DBObject group = new BasicDBObject("$group", groupProducts);
		    DBObject limit = new BasicDBObject();
	        limit = new BasicDBObject("$limit",5);
		  
		    DBObject sortFields = new BasicDBObject("count",-1);
		    DBObject sort = new BasicDBObject("$sort", sortFields);
		    AggregationOutput output = reviews.aggregate(group, sort, limit);
		  
	        for (DBObject res : output.results()) {       
				String prodcutname =(res.get("_id")).toString();
		        String count = (res.get("count")).toString();	
		        MostSold mostsld = new MostSold(prodcutname, count);
				mostSold.add(mostsld);
			}
		}catch (Exception e) { 
			System.out.println(e.getMessage());
		}
        return mostSold;
    }

    // Get the most popular zip codes where items are sold
	public static ArrayList<MostSoldZip> mostSoldZip() {
		ArrayList <MostSoldZip> mostSoldZip = new ArrayList<>();
		
		try{
		    getConnection();
	        DBObject groupProducts = new BasicDBObject("_id","$customerZip"); 
		    groupProducts.put("count", new BasicDBObject("$sum", 1));
		    DBObject group = new BasicDBObject("$group", groupProducts);
		    DBObject limit = new BasicDBObject();
	        limit = new BasicDBObject("$limit", 5); 
		    DBObject sortFields = new BasicDBObject("count", -1);
		    DBObject sort = new BasicDBObject("$sort", sortFields);
		    AggregationOutput output = reviews.aggregate(group, sort, limit);
	    	for (DBObject res : output.results()) {
				String zipcode = (res.get("_id")).toString();
		        String count = (res.get("count")).toString();	
		        MostSoldZip mostsldzip = new MostSoldZip(zipcode, count);
				mostSoldZip.add(mostsldzip);
	  		}
		}catch (Exception e) { 
			System.out.println(e.getMessage());
		}
     	return mostSoldZip;
  	}

}
