import java.io.*;
import java.io.PrintWriter;
import java.util.*;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/AddAccessory")

public class AddAccessory extends HttpServlet {
	private String msg;
	boolean unique;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		PrintWriter pw = response.getWriter();
		addProductForm(request, response, pw, false);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		PrintWriter pw = response.getWriter();
		

		/* Accessory information(type, id, name, price, image, manufacturer, condition, and discount) is obtained from HttpServletRequest,
		Based on the type of product (phone, tv, tablet, laptop) a respective hashmap is called and the product ID 
		is validated and added to the ProductCatalog.xml and respective product list */

		String prodType = "accessory";
		String relId = request.getParameter("relId");
		String id = request.getParameter("id");
		String name = request.getParameter("productName");
		double price = Double.parseDouble(request.getParameter("price"));
		String image = request.getParameter("image");
		String retailer = request.getParameter("retailer");
		String condition = request.getParameter("condition");
		double discount = Double.parseDouble(request.getParameter("discount"));
		double rebate = Double.parseDouble(request.getParameter("rebate"));

		if (isUnique(id) && prodExists(relId)) {
			try {
			UpdateXml.addAccNode(relId, id, name, price, image, retailer, condition, discount, rebate);
			MySqlDataStoreUtilities.addProducts(prodType, id, name, price, image, retailer, condition, discount, rebate, relId);
			}
			catch(Exception e) {
			}
			SaxParserDataStore.addHashmap();
			msg = "This product has been added to the inventory.";
			addProductForm(request, response, pw, true);
		}
		else {
			msg = "This accessory already exists or its associated item cannot be found.";
			addProductForm(request, response, pw, true);
		}
	}

	// Check datastore to make sure inventory item has unique ID

	private boolean isUnique(String id) {
		unique = true;

		for(String accId : SaxParserDataStore.products.keySet()) {
			if(accId.equals(id))
				unique = false;
		}
		return unique;
	}

	private boolean prodExists(String relId) {
		boolean exists = false;
		ArrayList<String> productIDs = new ArrayList<>();

		for(Map.Entry<String, Laptop> laptop : SaxParserDataStore.laptops.entrySet()) {
			productIDs.add(laptop.getKey());
		}
		for(Map.Entry<String, Phone> phone : SaxParserDataStore.phones.entrySet()) {
			productIDs.add(phone.getKey());
		}
		for(Map.Entry<String, Tablet> tablet : SaxParserDataStore.tablets.entrySet()) {
			productIDs.add(tablet.getKey());
		}
		for(Map.Entry<String, TV> tv : SaxParserDataStore.tvs.entrySet()) {
			productIDs.add(tv.getKey());
		}
		if (productIDs.contains(relId))
			exists = true;

		return exists;
	}

	/*  AddProductForm is displayed, store manager enters new product information, including ID, name, price, image, manufacturer, 
		discount, and condition. */

	protected void addProductForm(HttpServletRequest request, HttpServletResponse response, PrintWriter pw, boolean error)
			throws ServletException, IOException {

		Utilities utility = new Utilities(request, pw);
		utility.printHtml("Header.html");
		pw.print("<div class='post' style='float: none; width: 100%'>");
		pw.print("<h2 class='title meta'><a style='font-size: 24px;'>Add Accessory</a></h2>"
				+ "<div class='entry'>"
				+ "<div style='width:400px; margin:25px; margin-left: auto;margin-right: auto;'>");
		if (error)
			pw.print("<h4 style='color:red'>" + msg + "</h4>");
		
		pw.print("<form method='post' action='AddAccessory'>"
				+ "<table style='width:100%'><tr><td>"
				+ "<h4>Related Product ID</h4></td><td><input type='text' name='relId' class='input' required></input>"
				+ "</td></tr><tr><td>"
				+ "<h4>Accessory ID</h4></td><td><input type='text' name='id' value='' class='input' required></input>"
				+ "</td></tr><tr><td>"
				+ "<h4>Accessory Name</h4></td><td><input type='text' name='productName' value='' class='input' required></input>"
				+ "</td></tr><tr><td>"
				+ "<h4>Accessory Price</h4></td><td><input type='text' name= 'price' class='input' required></input>"
				+ "</td></tr><tr><td>"
				+ "<h4>Accessory Image</h4></td><td><input type='text' name='image' value='' class='input' required></input>"
				+ "</td></tr><tr><td>"
				+ "<h4>Manufacturer</h4></td><td><select name='retailer' class='input' required><option value='Apple' selected>Apple</option>" 
					+ "<option value='Google'>Google</option><option value='LG'>LG</option><option value='Microsoft'>Microsoft</option>" 
					+ "<option value='Samsung'>Samsung</option><option value='Sony'>Sony</option></select>"
				+ "</td></tr><tr><td>"
				+ "<h4>Condition</h4></td><td><select name='condition' class='input' required><option value='new' selected>New</option><option value='used'>Used</option></select>"
				+ "</td></tr><tr><td>"
				+ "<h4>Discount</h4></td><td><input type ='text' name='discount' value='' class='input' required></input>"
				+ "</td></tr><tr><td>"
				+ "<h4>Rebate</h4></td><td><input type ='text' name='rebate' value='' class='input' required></input>"
				+ "</td></tr><tr><td>"
				+ "<input type='submit' class='btnbuy' value='Add Accessory' style='float: right;height: 20px margin: 20px; margin-right: 10px;'></input>"
				+ "</td></tr></table>"
				+ "</form>" + "</div></div></div>");
		utility.printHtml("Footer.html");
	}
}