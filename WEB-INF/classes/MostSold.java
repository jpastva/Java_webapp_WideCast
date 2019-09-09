import java.io.*;

public class MostSold {
	String productname ;
	String count;

	public  MostSold(String productname, String count) {
		this.productname = productname;
	    this.count = count;
	}


	public String getProductname() {
		return productname;
	}

	public String getCount () {
		return count;
	}
}