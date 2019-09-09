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

@WebServlet("/AddProduct")

public class AddProduct extends HttpServlet {
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
		

		/* Item information is obtained from HttpServletRequest,
		Based on the type of product a respective hashmap is called and the product ID 
		is validated and added to the ProductCatalog.xml and respective product list */

		String prodType = request.getParameter("type");
		String id = request.getParameter("id");
		String name = request.getParameter("productName");
		double price = Double.parseDouble(request.getParameter("price"));
		String image = request.getParameter("image");
		String distributor = request.getParameter("distributor");
		String genre = request.getParameter("genre");
		int runtime = Integer.parseInt(request.getParameter("runtime"));
		String date = request.getParameter("date");

		if (isUnique(prodType, id)) {
			try {
			UpdateXml.addProdNode(prodType, id, name, price, image, distributor, genre, runtime, date);
			MySqlDataStoreUtilities.addStreaming(prodType, id, name, price, image, distributor, genre, runtime, LocalDate.parse(date));
			}
			catch(Exception e) {
			}
			SaxParserDataStore.addHashmap();
			msg = "This event/movie has been added to the inventory.";
			addProductForm(request, response, pw, true);
		}
		else {
			msg = "This event/movie already exists in the inventory.";
			addProductForm(request, response, pw, true);
		}
	}

	// Check datastore to make sure inventory item has unique ID

	private boolean isUnique(String type, String id) {
		unique = true;

		for(Map.Entry<String, Streaming> stream : SaxParserDataStore.streaming.entrySet()) {
			if(stream.getKey().equals(id))
				unique = false;
		}
		return unique;
	}

	/*  AddProductForm is displayed, store manager enters new product information, including ID, name, price, image, manufacturer, 
		discount, and condition. */

	protected void addProductForm(HttpServletRequest request, HttpServletResponse response, PrintWriter pw, boolean error)
			throws ServletException, IOException {

		Utilities utility = new Utilities(request, pw);
		utility.printHtml("Header.html");
		utility.printHtml("LeftNavigationBar.html");
		pw.print("<div id='content'><div class='post'>");
		pw.print("<h2 class='title meta'><a style='font-size: 24px;'>Add Movie/Event</a></h2>"
				+ "<div class='entry'>");
		if (error)
			pw.print("<h4 style='color:red'>" + msg + "</h4>");
		
		pw.print("<form method='post' action='AddProduct'>"
				+ "<table style='width:100%'><tr><td>"
				+ "<h4>Streaming Event Type</h4></td><td><select name='type' class='input' required>"
				+ "<option value='movie' selected>Movie</option>"
				+ "<option value='event'>Event</option></select>"
				+ "</td></tr><tr><td>"
				+ "<h4>Movie/Event ID</h4></td><td><input type='text' name='id' value='' class='input' required></input>"
				+ "</td></tr><tr><td>"
				+ "<h4>Movie/Event Name</h4></td><td><input type='text' name='productName' value='' class='input' required></input>"
				+ "</td></tr><tr><td>"
				+ "<h4>Movie/Event Price</h4></td><td><input type='text' name= 'price' class='input' required></input>"
				+ "</td></tr><tr><td>"
				+ "<h4>Movie/Event Image</h4></td><td><input type='text' name='image' value='' class='input' required></input>"
				+ "</td></tr><tr><td>"
				+ "<h4>Movie/Event Distributor</h4></td><td><select name='distributor' class='input'><option value='ESPN' selected>ESPN</option>" 
					+ "<option value='Lionsgate'>Lionsgate</option><option value='MLB'>MLB</option><option value='paramount'>Paramount</option>" 
					+ "<option value='Sony'>Sony</option><option value='Universal'>Universal</option><option value='Walt Disney'>Walt Disney</option>"
					+ "<option value='Warner Brothers'>Warner Brothers</option><option value='WBA'>WBA</option><option value='WWE'>WWE</option></select>"
				+ "</td></tr><tr><td>"
				+ "<h4>Movie/Event Genre</h4></td><td><select name='genre' class='input'><option value='Action' selected>Action</option><option value='Baseball'>Baseball</option>"
					+ "<option value='Boxing'>Boxing</option><option value='Children'>Children</option><option value='Comedy'>Comedy</option><option value='Drama'>Drama</option>"
					+ "<option value='Horror'>Horror</option><option value='Wrestling'>Wrestling</option></select>"
				+ "</td></tr><tr><td>"
				+ "<h4>Movie/Event Runtime</h4></td><td><input type ='text' name='runtime' value='' class='input' required></input></td>"
				+ "</tr><tr><td>"
				+ "<h4>Movie release/Event Date (YYYY-MM-DD)</h4></td><td><input type ='text' name='date' value='' class='input' required></input></td>"
				+ "</tr><tr><td>"
				+ "<input type='submit' class='btnbuy' value='Add Product' style='float: right;height: 20px margin: 20px; margin-right: 10px;'></input>"
				+ "</td></tr></table>"
				+ "</form>" + "</div></div></div>");
		utility.printHtml("Footer.html");
	}
}