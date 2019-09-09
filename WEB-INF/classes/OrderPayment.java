import java.io.IOException;
import java.io.*;
import java.time.LocalDate;



/* 
	OrderPayment class contains class variables username,ordername,price,image,address,creditcardno.

	OrderPayment  class has a constructor with Arguments username,ordername,price,image,address,creditcardno
	  
	OrderPayment  class contains getters and setters for username,ordername,price,image,address,creditcardno
*/

public class OrderPayment implements Serializable{
	private int orderId;
	private int itemId;
	private String itemType;
	private String userName;
	private String orderName;
	private double orderPrice;
	private String userAddress;
	private String creditCardNo;
	private LocalDate date;
	
	public OrderPayment() {
	}

	public OrderPayment(int orderId, int itemId, String itemType, String userName, String orderName, double orderPrice,
		String userAddress, String creditCardNo, LocalDate date) {
		this.orderId = orderId;
		this.itemId = itemId;
		this.itemType = itemType;
		this.userName = userName;
		this.orderName = orderName;
	 	this.orderPrice = orderPrice;
		this.userAddress = userAddress;
	 	this.creditCardNo = creditCardNo;
	 	this.date = date;
	}

	public OrderPayment(OrderPayment order) {
		this.orderId = order.orderId;
		this.itemId = order.itemId;
		this.itemType = order.itemType;
		this.userName = order.userName;
		this.orderName = order.orderName;
	 	this.orderPrice = order.orderPrice;
		this.userAddress = order.userAddress;
	 	this.creditCardNo = order.creditCardNo;
	 	this.date = order.date;
	}

	public String getUserAddress() {
		return userAddress;
	}

	public void setUserAddress(String userAddress) {
		this.userAddress = userAddress;
	}

	public String getCreditCardNo() {
		return creditCardNo;
	}

	public void setCreditCardNo(String creditCardNo) {
		this.creditCardNo = creditCardNo;
	}

	public String getOrderName() {
		return orderName;
	}

	public void setOrderName(String orderName) {
		this.orderName = orderName;
	}

	public String getItemType() {
		return itemType;
	}

	public void setItemType(String itemType) {
		this.itemType = itemType;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
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

	public double getOrderPrice() {
		return orderPrice;
	}

	public void setOrderPrice(double orderPrice) {
		this.orderPrice = orderPrice;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

	public LocalDate getDate() {
		return date;
	}
}
