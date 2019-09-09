import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.LocalDate;

@WebServlet("/TVPlan")

/* 
	TVPlan class contains class variables id, name, price, image, channels, and distributor.

	TVPlan class has a constructor with arguments name, price, image, channels, and distributor.
	  
	TVPlan class contains getters and setters for name, price, image, channels, and distributor.
*/

public class TVPlan extends HttpServlet {
	private String id;
	private String name;
	private double price;
	private String image;
	private int channels;
	private String distributor;
	
	public TVPlan(String name, double price, String image, int channels, String distributor) {
		this.name = name;
		this.price = price;
		this.image = image;
		this.channels = channels;
		this.distributor = distributor;
	}
	
	public TVPlan() {	
	}
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public int getChannels() {
		return channels;
	}

	public void setChannels(int channels) {
		this.channels = channels;
	}

	public String getDistributor() {
		return distributor;
	}
	
	public void setDistributor(String distributor) {
		this.distributor = distributor;
	}
}
