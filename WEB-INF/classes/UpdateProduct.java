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

@WebServlet("/UpdateProduct")

public class UpdateProduct extends HttpServlet {
	private String msg;
	boolean exists;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		PrintWriter pw = response.getWriter();
		updateProductForm(request, response, pw, false);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		PrintWriter pw = response.getWriter();
		

		/* Streaming evet information is obtained from HttpServletRequest,
		Based on the type of product (event, movie) a respective hashmap is called and the product ID 
		is validated and updated in the ProductCatalog.xml and respective product list */

		String prodType = request.getParameter("type");
		String id = request.getParameter("id");
		String name = request.getParameter("productName");
		double price=0;
		String image = request.getParameter("image");
		String distributor = request.getParameter("distributor");
		String genre = request.getParameter("genre");
		int runtime=0;
		String date = request.getParameter("date");

		if (!(request.getParameter("price").isEmpty()) || !(request.getParameter("price").equals(""))) { 
			price = Double.parseDouble(request.getParameter("price"));
		}

		if (!(request.getParameter("runtime").isEmpty()) || !(request.getParameter("runtime").equals(""))) { 
			runtime = Integer.parseInt(request.getParameter("runtime"));
		}

		if (!prodExists(id)) {
			msg = "This movie/event does not exist in the inventory.";
			updateProductForm(request, response, pw, true);
		}
		else {
			for(Map.Entry<String, Streaming> entry : SaxParserDataStore.streaming.entrySet()) {
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
					if (distributor.isEmpty() || distributor.equals("")) {
						distributor = entry.getValue().getDistributor();
					}
					if (genre.isEmpty() || genre.equals("")) {
						genre = entry.getValue().getGenre();
					}
					if (request.getParameter("runtime").isEmpty() || request.getParameter("runtime").equals("")) {
						runtime = entry.getValue().getRuntime();
					}
					if (date.isEmpty() || date.equals("")) {
						date = entry.getValue().getDate().toString();
					}
				}
			}
			try {
				UpdateXml.removeNode(id);
				
				UpdateXml.addProdNode(prodType, id, name, price, image, distributor, genre, runtime, date);
				MySqlDataStoreUtilities.updateStreaming(id, name, price, image, distributor, genre, runtime, LocalDate.parse(date));
				
			}
			catch(Exception e) {
			}
			SaxParserDataStore.addHashmap();
			msg = "This movie/event has been updated in the inventory.";

			updateProductForm(request, response, pw, true);
		}
	}	
	
	// Check datastore to make sure inventory item exists
	private boolean prodExists(String id) {
		exists = false;

		for(Map.Entry<String, Streaming> stream : SaxParserDataStore.streaming.entrySet()) {
				if(stream.getKey().equals(id))
					exists = true;
			}
		return exists;
	}

	/*  AddProductForm is displayed, store manager enters new product information, including ID, name, price, image, manufacturer, 
		discount, and condition. */

	protected void updateProductForm(HttpServletRequest request, HttpServletResponse response, PrintWriter pw, boolean error)
			throws ServletException, IOException {

		Utilities utility = new Utilities(request, pw);
		utility.printHtml("Header.html");
		utility.printHtml("LeftNavigationBar.html");
		pw.print("<div id='content'><div class='post'>");
		pw.print("<h2 class='title meta'><a style='font-size: 24px;'>Update Movie/Event</a></h2>"
				+ "<div class='entry'>");
		if (error)
			pw.print("<h4 style='color:red'>" + msg + "</h4>");
		
		pw.print("<form method='post' action='UpdateProduct'>"
				+ "<table style='width:100%'><tr><td>"
				+ "<h4>Streaming Event Type</h4></td><td><select name='type' class='input' required><option value='movie' selected>Movie</option>"
				+ "<option value='event'>Event</option></select>"
				+ "</td></tr><tr><td>"
				+ "<h4>Current ID</h4></td><td><input type='text' name='id' value='' class='input' required></input>"
				+ "</td></tr><tr><td>"
				+ "<h4>Updated Name</h4></td><td><input type='text' name='productName' value='' class='input'></input>"
				+ "</td></tr><tr><td>"
				+ "<h4>Updated Price</h4></td><td><input type='text' name= 'price' class='input'></input>"
				+ "</td></tr><tr><td>"
				+ "<h4>Updated Image</h4></td><td><input type='text' name='image' value='' class='input'></input>"
				+ "</td></tr><tr><td>"
				+ "<h4>Updated Distributor</h4></td><td><select name='distributor' class='input'><option value='ESPN' selected>ESPN</option>" 
					+ "<option value='Lionsgate'>Lionsgate</option><option value='MLB'>MLB</option><option value='paramount'>Paramount</option>" 
					+ "<option value='Sony'>Sony</option><option value='Universal'>Universal</option><option value='Walt Disney'>Walt Disney</option>"
					+ "<option value='Warner Brothers'>Warner Brothers</option><option value='WBA'>WBA</option><option value='WWE'>WWE</option></select>"
				+ "</td></tr><tr><td>"
				+ "<h4>Updated Genre</h4></td><td><select name='genre' class='input'><option value='Action' selected>Action</option><option value='Baseball'>Baseball</option>"
					+ "<option value='Boxing'>Boxing</option><option value='Children'>Children</option><option value='Comedy'>Comedy</option><option value='Drama'>Drama</option>"
					+ "<option value='Horror'>Horror</option><option value='Wrestling'>Wrestling</option></select>"
				+ "</td></tr><tr><td>"
				+ "<h4>Updated Runtime</h4></td><td><input type ='text' name='runtime' value='' class='input'></input></td>"
				+ "</tr><tr><td>"
				+ "<h4>Updated Date</h4></td><td><input type ='text' name='date' value='' class='input'></input></td>"
				+ "</tr><tr><td></td></tr>"
				+ "<tr><td>"
				+ "<input type='submit' class='btnbuy' value='Update Product' style='float: right;height: 20px margin: 20px; margin-right: 10px;'></input>"
				+ "</td></tr></table>"
				+ "</form>" + "</div></div></div>");
		utility.printHtml("Footer.html");
	}
}