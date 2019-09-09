public class Review implements java.io.Serializable {

  String productName;
  String productType;
  String productPrice;
  String distributor;
  String customerName;
  String customerZip;
  String customerCity;
  String customerState;
  String userId;
  String userAge;
  String userGender;
  String userOccupation;
  String rating;
  String reviewDate;
  String reviewText;

  Review(String productName, String productType, String productPrice, String distributor,
    String customerZip, String customerCity, String customerState, 
    String userId, String userAge, String userGender, String userOccupation, String rating,
    String reviewDate, String reviewText) { 

    this.productName = productName;
    this.productType = productType;
    this.productPrice = productPrice;
    this.distributor = distributor;
    this.customerZip = customerZip;
    this.customerCity = customerCity;
    this.customerState = customerState;
    this.userId = userId;
    this.userAge = userAge;
    this.userGender = userGender;
    this.userOccupation = userOccupation;
    this.rating = rating;
    this.reviewDate = reviewDate;
    this.reviewText = reviewText;
  }

  public void setProductName(String productName) {
    this.productName = productName;
  }

  public String getProductName() {
    return productName;
  }

  public void setProductType(String productType) {
    this.productType = productType;
  }

  public String getProductType() {
    return productType;
  }

  public void setProductPrice(String productPrice) {
    this.productPrice = productPrice;
  }

  public String getProductPrice() {
    return productPrice;
  }

  public void setDistributor(String distributor) {
    this.distributor = distributor;
  }
  public String getDistributor() {
    return distributor;
  }

  public void setCustomerZip(String customerZip) {
    this.customerZip = customerZip;
  }

  public String getCustomerZip() {
    return customerZip;
  }
  public void setCustomerCity(String customerCity) {
    this.customerCity = customerCity;
  }

  public String getCustomerCity() {
    return customerCity;
  }

  public void setCustomerState(String customerState) {
    this.customerState = customerState;
  }

  public String getCustomerState() {
    return customerState;
  }

  public void setUserId(String userId ) {
  	this.userId = userId;
  }

  public String getUserId() {
  	return userId;
  }

  public void setUserAge(String userAge) {
  	this.userAge = userAge;
  }

  public String getUserAge() {
  	return userAge;
  }

  public void setUserGender(String userGender) {
  	this.userGender = userGender;
  }

  public String getUserGender() {
  	return userGender;
  }

  public void setUserOccupation(String userOccupation) {
  	this.userOccupation = userOccupation;
  }

  public String getUserOccupation() {
  	return userOccupation;
  }

  public void setRating(String rating) {
  	this.rating = rating;
  }

  public String getRating() {
  	return rating;
  }

  public void setReviewDate(String reviewDate) {
  	this.reviewDate = reviewDate;
  }

  public String getReviewDate() {
  	return reviewDate;
  }

  public void setReviewText(String reviewText) {
  	this.reviewText = reviewText;
  }

  public String getReviewText() {
  	return reviewText;
  }
}
