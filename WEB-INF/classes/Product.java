public interface Product {
	
	public String getId();

	public void setId(String id);
	
	public String getName();

	public void setName(String name);

	public double getPrice();

	public void setPrice(double price);

	public String getImage();

	public void setImage(String image);

	public String getRetailer();

	public void setRetailer(String retailer);

	public String getCondition();

	public void setCondition(String condition);

	public double getDiscount();

	public void setDiscount(double discount);

	public void setRebate(double rebate);

	public double getRebate();

}