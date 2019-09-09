import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.LocalDate;

@WebServlet("/Movie")

/* 
	Movie class contains class variables id, name, price, image, distributor, runtime, and date.

	Movie class has a constructor with arguments name, price, image, distributor, runtime, and date.
	  
	Movie class contains getters and setters for id, name, price, image, distributor, runtime, and date.
*/

public class Movie extends HttpServlet implements Streaming {
	private String id;
	private String name;
	private double price;
	private String image;
	private String distributor;
	private String genre;
	private int runtime;
	private LocalDate date;
	
	public Movie(String name, double price, String image, String distributor, String genre, int runtime, String date) {
		this.name = name;
		this.price = price;
		this.image = image;
		this.distributor = distributor;
		this.genre = genre;
		this.runtime = runtime;
		this.date = LocalDate.parse(date);
	}
	
	public Movie() {	
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

	public String getDistributor() {
		return distributor;
	}
	
	public void setDistributor(String distributor) {
		this.distributor = distributor;
	}

	public String getGenre() {
		return genre;
	}

	public void setGenre(String genre) {
		this.genre = genre;
	}

	public int getRuntime() {
		return runtime;
	}

	public void setRuntime(int runtime) {
		this.runtime = runtime;
	}

	public LocalDate getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = LocalDate.parse(date);
	}
}
