import java.util.*;
import java.util.Map;

public class GenericProduct {
	private String id;
	private String name;
	private double price;
	private String image;
	private String distributor;
	private String type;
	
	public GenericProduct(String id, String name, double price, String image, String distributor, String type) {
		this.id = id;
		this.name = name;
		this.price = price;
		this.image = image;
		this.distributor = distributor;
		this.type = type;
	}
	
	public GenericProduct() {
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
}
