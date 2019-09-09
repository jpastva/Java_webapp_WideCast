import java.io.Serializable;
import java.time.LocalDate;

/* 
	Subscription class contains variables username, product, price, and startDate.
*/

public class Subscription implements Serializable {
	private int orderId;
	private int itemId;
	private String username;
	private String product;
	private double price;
	private double balance;
	private LocalDate startDate;

	
	public Subscription(int orderId, int itemId, String username, String product, double price, LocalDate startDate) {
		this.orderId = orderId;
		this.itemId = itemId;
		this.username = username;
		this.product = product;
		this.price = price;
		this.balance = price;
		this.startDate = startDate;
	}

	public Subscription(int orderId, int itemId, String username, String product, double price, double balance, LocalDate startDate) {
		this.orderId = orderId;
		this.itemId = itemId;
		this.username = username;
		this.product = product;
		this.price = price;
		this.balance = balance;
		this.startDate = startDate;
	}
	
	public Subscription() {
		orderId = 0;
		itemId = 0;
		username = "";
		product = "";
		price = 0;
		balance = 0;
		startDate = null;
	}
	
	public int getOrderId() {
		return orderId;
	}

	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}

	public int getItemId() {
		return itemId;
	}

	public void setItemId(int itemId) {
		this.itemId = itemId;
	}
	
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
	
	public String getProduct() {
		return product;
	}

	public void setProduct(String product) {
		this.product = product;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public double getBalance() {
		return balance;
	}

	public void setBalance(double balance) {
		this.balance = balance;
	}

	public LocalDate getStartDate() {
		return startDate;
	}

	public void setStartDate(LocalDate startDate) {
		this.startDate = startDate;
	}
}
