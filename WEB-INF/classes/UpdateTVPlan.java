import java.io.*;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.time.LocalDate;

@WebServlet("/UpdateTVPlan")

public class UpdateTVPlan extends HttpServlet {
	private String msg;
	boolean exists;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		PrintWriter pw = response.getWriter();
		updatePlanForm(request, response, pw, false);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		PrintWriter pw = response.getWriter();
		

		/* Streaming evet information is obtained from HttpServletRequest,
		Based on the type of product (event, movie) a respective hashmap is called and the product ID 
		is validated and updated in the ProductCatalog.xml and respective product list */

		String id = request.getParameter("id");
		String name = request.getParameter("productName");
		double price = 0;
		String image = request.getParameter("image");
		int channels = 0;

		if (!(request.getParameter("price").isEmpty()) || !(request.getParameter("price").equals(""))) { 
			price = Double.parseDouble(request.getParameter("price"));
		}

		if (!(request.getParameter("channels").isEmpty()) || !(request.getParameter("channels").equals(""))) { 
			channels = Integer.parseInt(request.getParameter("channels"));
		}


		if (!prodExists(id)) {
			msg = "This cable plan does not exist in the inventory.";
			updatePlanForm(request, response, pw, true);
		}
		else {
			for(Map.Entry<String, TVPlan> entry : SaxParserDataStore.tvPlans.entrySet()) {
				if (entry.getKey().equals(id)) {
					if (name.isEmpty() || name.equals("")) {
						name = entry.getValue().getName();
					}
					if (request.getParameter("price").isEmpty() || request.getParameter("price").equals("")) {
						price = entry.getValue().getPrice();
					}
					if (image.isEmpty() || image.equals("")) {
						image = entry.getValue().getImage();
					}

					if (request.getParameter("channels").isEmpty() || request.getParameter("channels").equals("")) {
						channels = entry.getValue().getChannels();
					}
				}
			}
			try {
				UpdateXml.removeNode(id);
				
				UpdateXml.addTVNode(id, name, price, image, String.valueOf(channels), "WideCast");
				MySqlDataStoreUtilities.updateTV(id, name, price, image, channels);
				
			}
			catch(Exception e) {
			}
			SaxParserDataStore.addHashmap();
			msg = "This cable plan has been updated in the inventory.";

			updatePlanForm(request, response, pw, true);
		}
	}	
	
	// Check datastore to make sure inventory item exists
	private boolean prodExists(String id) {
		exists = false;

		for(Map.Entry<String, TVPlan> tv : SaxParserDataStore.tvPlans.entrySet()) {
				if(tv.getKey().equals(id))
					exists = true;
			}
		return exists;
	}

	/*  AddProductForm is displayed, store manager enters new product information, including ID, name, price, image, manufacturer, 
		discount, and condition. */

	protected void updatePlanForm(HttpServletRequest request, HttpServletResponse response, PrintWriter pw, boolean error)
			throws ServletException, IOException {

		Utilities utility = new Utilities(request, pw);
		utility.printHtml("Header.html");
		utility.printHtml("LeftNavigationBar.html");
		pw.print("<div id='content'><div class='post'>");
		pw.print("<h2 class='title meta'><a style='font-size: 24px;'>Update Cable Plan</a></h2>"
				+ "<div class='entry'>");
		if (error)
			pw.print("<h4 style='color:red'>" + msg + "</h4>");
		
		pw.print("<form method='post' action='UpdateTVPlan'>"
				+ "<table><tr><td>"
				+ "<h4>Current ID</h4></td><td><input type='text' name='id' value='' class='input' required></input>"
				+ "</td></tr><tr><td>"
				+ "<h4>Updated Name</h4></td><td><input type='text' name='productName' value='' class='input'></input>"
				+ "</td></tr><tr><td>"
				+ "<h4>Updated Price</h4></td><td><input type='text' name= 'price' class='input'></input>"
				+ "</td></tr><tr><td>"
				+ "<h4>Updated Image</h4></td><td><input type='text' name='image' value='' class='input'></input>"
				+ "</td></tr><tr><td>"
				+ "<h4>Updated Channels</h4></td><td><input type ='text' name='channels' value='' class='input'></input></td>"
				+ "</tr><tr><td>"
				+ "<input type='submit' class='btnbuy' value='Update Plan' style='float: right;height: 20px margin: 20px; margin-right: 10px;'></input>"
				+ "</td></tr></table>"
				+ "</form>" + "</div></div></div>");
		utility.printHtml("Footer.html");
	}
}