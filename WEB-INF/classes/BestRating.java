import java.io.*;

public class BestRating {
	String productname ;
	String rating;

	public  BestRating(String productname,String rating) {
		this.productname = productname ;
	    this.rating = rating;
	}

	public String getProductname() {
		return productname;
	}

	public String getRating () {
		return rating;
	}
}