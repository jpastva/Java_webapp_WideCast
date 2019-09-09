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

@WebServlet("/RemoveProduct")

public class RemoveProduct extends HttpServlet {
	private String msg;
	boolean exists;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		PrintWriter pw = response.getWriter();
		removeProductForm(request, response, pw, false);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		PrintWriter pw = response.getWriter();
		

		/* Item information (type, id) is obtained from HttpServletRequest,
		Based on the type of product (phone, tv, tablet, laptop) a respective hashmap is called and the product ID 
		is validated before being removed from ProductCatalog.xml and respective product list */

		String prodType = request.getParameter("type");
		String id = request.getParameter("id");

		if (prodExists(prodType, id)) {
			try {
				UpdateXml.removeNode(id);

				if (prodType.equals("movie") || prodType.equals("event"))
					MySqlDataStoreUtilities.deleteStreaming(id);

				else if (prodType.equals("tvPlan"))
					MySqlDataStoreUtilities.deleteTV(id);

				else 
					MySqlDataStoreUtilities.deleteData(id);
			}

			catch(Exception e) {
			}
			SaxParserDataStore.addHashmap();
			msg = "This product has been removed from the inventory.";
			removeProductForm(request, response, pw, true);
		}
		else {
			msg = "This product does not exist in the inventory.";
			removeProductForm(request, response, pw, true);
		}
	}

	// Check datastore to make sure inventory item exists

	private boolean prodExists(String type, String id) {
		exists = false;

		switch (type) {
 		       	case "movie":
 		       		for(Map.Entry<String, Streaming> stream : SaxParserDataStore.streaming.entrySet()) {
						if(stream.getKey().equals(id))
						exists = true;
					}
        			break;

        		case "event":
        			for(Map.Entry<String, Streaming> stream : SaxParserDataStore.streaming.entrySet()) {
						if(stream.getKey().equals(id))
						exists = true;
					}
        			break;

        		case "tvPlan":
        			for(Map.Entry<String, TVPlan> tv : SaxParserDataStore.tvPlans.entrySet()) {
						if(tv.getKey().equals(id))
						exists = true;
					}
					break;

        		case "dataPlan":
        			for(Map.Entry<String, DataPlan> data : SaxParserDataStore.dataPlans.entrySet()) {
					if(data.getKey().equals(id))
					exists = true;
					}
					break;

        		default:
        			for(Map.Entry<String, Streaming> stream : SaxParserDataStore.streaming.entrySet()) {
						if(stream.getKey().equals(id))
						exists = true;
					}
        }
		
		return exists;
	}

	/*  RemoveProductForm is displayed, store manager enters product including ID and type */

	protected void removeProductForm(HttpServletRequest request, HttpServletResponse response, PrintWriter pw, boolean error)
			throws ServletException, IOException {

		Utilities utility = new Utilities(request, pw);
		utility.printHtml("Header.html");
		utility.printHtml("LeftNavigationBar.html");
		pw.print("<div id='content'><div class='post'>");
		pw.print("<h2 class='title meta'><a style='font-size: 24px;'>Remove Product</a></h2>"
				+ "<div class='entry'>");
		if (error)
			pw.print("<h4 style='color:red'>" + msg + "</h4>");
		
		pw.print("<form method='post' action='RemoveProduct'>"
				+ "<table style='width:100%'><tr><td>"
				+ "<h4>Product Type</h4></td><td><select name='type' class='input' required><option value='tvPlan' selected>Cable Plan</option><option value='event' >Event</option>"
				+ "<option value='dataPlan'>Internet Plan</option><option value='movie'>Movie</option></select>"
				+ "</td></tr><tr><td>"
				+ "<h4>Product, Movie, or Event ID</h4></td><td><input type='text' name='id' value='' class='input' required></input></td>"
				+ "</tr><tr><td>"
				+ "<input type='submit' class='btnbuy' value='Remove Product' style='float: right;height: 20px margin: 20px; margin-right: 10px;'></input>"
				+ "</td></tr></table>"
				+ "</form>" + "</div></div></div>");
		utility.printHtml("Footer.html");
	}
}