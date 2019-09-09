import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.*;
import java.io.*;

@WebServlet("/ViewReview")

public class ViewReview extends HttpServlet {
	// HashMap<String,ArrayList<Review>> review = null;  
	// MongoDBDataStoreUtilities s = null;
      
 //    public void init() {       
	// 	review = new HashMap<String, ArrayList<Review>>();
	// 	s  = new MongoDBDataStoreUtilities();
	// 	review = s.selectReview();
	// }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		PrintWriter pw = response.getWriter();		
	}
  
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HashMap<String,ArrayList<Review>> reviews = new HashMap<>();
		reviews = MongoDBDataStoreUtilities.selectReview();

		response.setContentType("text/html");
		PrintWriter pw = response.getWriter();
		Utilities utility = new Utilities(request, pw);
		String productName = request.getParameter("displayName");
      	ArrayList<Review> productReview = reviews.get(productName);
		
		utility.printHtml("Header.html");
		utility.printHtml("LeftNavigationBar.html");
		pw.print("<form name ='ViewReview' action='ViewReview' method='post'>");
		pw.print("<div id='content'><div class='post'><h2 class='title meta'>");
		pw.print("<a style='font-size: 24px;'>" + productName +" reviews</a>");
		pw.print("</h2><div class='entry'>");
		if(productReview != null) {				 
			for(Review review : reviews.get(productName)) {											 																							
			    if(review != null) {
					pw.println("<dl>");
					pw.println("<dt>" + review.getProductName() + "</dt>");							
					pw.println("<dd>- Product Category: " + review.getProductType() + "</dd>");
					pw.println("<dd>- Product Price: " + review.getProductPrice() + "</dd>");							
					pw.println("<dd>- Distributor Name: " + review.getDistributor() + "</dd>");							
					pw.println("<dd>- Customer Zip Code: " + review.getCustomerZip() + "</dd>");							
					pw.println("<dd>- Customer City: " + review.getCustomerCity() + "</dd>");							
					pw.println("<dd>- Customer State: " + review.getCustomerState() + "</dd>");							
					pw.println("<dd>- User ID: " + review.getUserId() + "</dd>");							
					pw.println("<dd>- User Age: " + review.getUserAge() + "</dd>");							
					pw.println("<dd>- User Gender: " + review.getUserGender() + "</dd>");							
					pw.println("<dd>- User Occupation: " + review.getUserOccupation() + "</dd>");							
					pw.println("<dd>- Review Rating: " + review.getRating() + "</dd>");							
					pw.println("<dd>- Review Date: " + review.getReviewDate() + "</dd>"); 
					pw.println("<dd>- Review Text: " + review.getReviewText() + "</dd>");
					pw.println("</dl>");
				} 
				else {
					pw.println("<h4>This product has not yet been reviewed.</h4>");
				}  
			}				 
		}
		else
			pw.println("This product has not yet been reviewed.");
		
		pw.print("</form></div></div></div>");		
		utility.printHtml("Footer.html");               
	}	
}