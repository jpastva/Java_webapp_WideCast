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

@WebServlet("/AddDataPlan")

public class AddDataPlan extends HttpServlet {
	private String msg;
	boolean unique;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		PrintWriter pw = response.getWriter();
		addPlanForm(request, response, pw, false);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		PrintWriter pw = response.getWriter();
		

		/* Item information is obtained from HttpServletRequest,
		The cable plan hashmap is called and the product ID 
		is validated and added to the ProductCatalog.xml and respective product list */

		String id = request.getParameter("id");
		String name = request.getParameter("productName");
		double price = Double.parseDouble(request.getParameter("price"));
		String image = request.getParameter("image");
		String speed = request.getParameter("speed");
		String distributor = request.getParameter("distributor");

		if (isUnique(id)) {
			try {
			UpdateXml.addDataNode(id, name, price, image, speed, distributor);
			MySqlDataStoreUtilities.addData(id, name, price, image, speed, distributor);
			}
			catch(Exception e) {
			}
			SaxParserDataStore.addHashmap();
			msg = "This internet plan has been added to the inventory.";
			addPlanForm(request, response, pw, true);
		}
		else {
			msg = "This internet plan already exists in the inventory.";
			addPlanForm(request, response, pw, true);
		}
	}

	// Check datastore to make sure inventory item has unique ID

	private boolean isUnique(String id) {
		unique = true;

		for(Map.Entry<String, DataPlan> plan : SaxParserDataStore.dataPlans.entrySet()) {
			if(plan.getKey().equals(id))
				unique = false;
		}
		return unique;
	}

	/*  AddPlanForm is displayed, store manager enters new cable plan information */

	protected void addPlanForm(HttpServletRequest request, HttpServletResponse response, PrintWriter pw, boolean error)
			throws ServletException, IOException {

		Utilities utility = new Utilities(request, pw);
		utility.printHtml("Header.html");
		utility.printHtml("LeftNavigationBar.html");
		pw.print("<div id='content'><div class='post'>");
		pw.print("<h2 class='title meta'><a style='font-size: 24px;'>Add Internet Plan</a></h2>"
				+ "<div class='entry'>");
		if (error)
			pw.print("<h4 style='color:red'>" + msg + "</h4>");
		
		pw.print("<form method='post' action='AddDataPlan'>"
				+ "<table style='width:100%'><tr><td>"
				+ "<h4>Plan ID</h4></td><td><input type='text' name='id' value='' class='input' required></input>"
				+ "</td></tr><tr><td>"
				+ "<h4>Plan Name</h4></td><td><input type='text' name='productName' value='' class='input' required></input>"
				+ "</td></tr><tr><td>"
				+ "<h4>Plan Price</h4></td><td><input type='text' name= 'price' class='input' required></input>"
				+ "</td></tr><tr><td>"
				+ "<h4>Plan Image</h4></td><td><input type='text' name='image' value='' class='input' required></input>"
				+ "</td></tr><tr><td>"
				+ "<h4>Plan Speed</h4></td><td><input type ='text' name='speed' value='' class='input' required></input></td>"
				+ "</tr><tr><td>"
				+ "<h4>Plan Distributor</h4></td><td><select name='distributor' class='input'><option value='WideCast' selected>WideCast</option></select></td>"
				+ "</tr><tr><td>"
				+ "<input type='submit' class='btnbuy' value='Add Internet Plan' style='float: right;height: 20px margin: 20px; margin-right: 10px;'></input>"
				+ "</td></tr></table>"
				+ "</form>" + "</div></div></div>");
		utility.printHtml("Footer.html");
	}
}