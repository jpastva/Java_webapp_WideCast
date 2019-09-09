import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.LocalDate;

@WebServlet("/DataPlan")

/* 
	DataPlan class contains class variables id, name, price, image, channels, and distributor.

	DataPlan class has a constructor with arguments name, price, image, channels, and distributor.
	  
	DataPlan class contains getters and setters for name, price, image, channels, and distributor.
*/

public class DataPlan extends HttpServlet {
	private String id;
	private String name;
	private double price;
	private String image;
	private String speed;
	private String distributor;
	
	public DataPlan(String name, double price, String image, String speed, String distributor) {
		this.name = name;
		this.price = price;
		this.image = image;
		this.speed = speed;
		this.distributor = distributor;
	}
	
	public DataPlan() {	
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

	public String getSpeed() {
		return speed;
	}

	public void setSpeed(String speed) {
		this.speed = speed;
	}

	public String getDistributor() {
		return distributor;
	}
	
	public void setDistributor(String distributor) {
		this.distributor = distributor;
	}
}
