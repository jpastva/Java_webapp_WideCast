import java.io.*;

public class MostSoldZip {
	String zipcode ;
	String count;

	public  MostSoldZip(String zipcode,String count) {	
		this.zipcode = zipcode ;
	    this.count = count;
	}

	public String getZipcode() {
		return zipcode;
	}

	public String getCount () {
		return count;
	}
}