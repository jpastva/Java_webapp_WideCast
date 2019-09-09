import java.io.Serializable;
import java.time.LocalDate;

/* 
	OrderItem class contains class variables name, price, image, retailer.

	OrderItem  class has a constructor with Arguments name, price, image, retailer, discount, and rebate.
	  
	OrderItem  class contains getters and setters for name, price, image, retailer, discount, and rebate.
*/

public class OrderItem implements Serializable {
	private int itemId;
	private String name;
	private String type;
	private double price;
	private String image;
	private String distributor;
	private LocalDate date;
	
	public OrderItem(int itemId, String name, String type, double price, String image, String distributor, LocalDate date) {
		this.itemId = itemId;
		this.name = name;
		this.type = type;
		this.price = price;
		this.image = image;
		this.distributor = distributor;
		this.date = date;
	}

	public OrderItem() {
		itemId = 0;
		name = "";
		type = "";
		image = "";
		distributor = "";
		date = null;
	}

	public int getItemId() {
		return itemId;
	}

	public void setItemId() {
		this.itemId = itemId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
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

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

}
