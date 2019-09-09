import java.io.Serializable;
/* 
	Customer class contains variables username, firstName,lastName, userAddress, userEmail, and creditCardNo.
*/

public class Customer implements Serializable {
	private String username;
	private String firstName;
	private String lastName;
	private String userAddress;
	private String userEmail;
	private String creditCardNo;
	
	public Customer(String username, String firstName, String lastName, String userAddress, String userEmail, String creditCardNo) {
		this.username = username;
		this.firstName = firstName;
		this.lastName = lastName;
		this.userAddress = userAddress;
		this.userEmail = userEmail;
		this.creditCardNo = creditCardNo;
	}
	
	public Customer() {
		username = "";
		firstName = "";
		lastName = "";
		userAddress = "";
		userEmail = "";	
		creditCardNo = "";
	}
	
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
	
	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getUserAddress() {
		return userAddress;
	}

	public void setUserAddress(String userAddress) {
		this.userAddress = userAddress;
	}

	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}

	public String getUserEmail() {
		return userEmail;
	}

	public void setCreditCardNo(String creditCardNo) {
		this.creditCardNo = creditCardNo;
	}

	public String getCreditCardNo() {
		return creditCardNo;
	}
}
