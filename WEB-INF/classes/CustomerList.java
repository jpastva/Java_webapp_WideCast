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

@WebServlet("/CustomerList")

public class CustomerList extends HttpServlet {
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		PrintWriter pw = response.getWriter();
		HashMap<String, Customer> customers = new HashMap<>();
	      
	// get HashMap of all customer from Customer database
	customers = MySqlDataStoreUtilities.selectCustomers();
	
	Utilities utility = new Utilities(request, pw);
	utility.printHtml("Header.html");
	utility.printHtml("LeftNavigationBar.html");
	pw.print("<div id='content'><div class='post'><h2 class='title meta'>");
	pw.print("<a style='font-size: 24px;'>Customer List</a>");
	pw.print("</h2><div class='entry'>");
	pw.print("<table class='gridtable'>");
	int i = 1;
	for (Map.Entry<String, Customer> customer : customers.entrySet()) {;
		pw.print("<tr><td>" + i + "</td><td>" + customer.getValue().getUsername() + "</td>");
		pw.print("<td>" + customer.getValue().getFirstName() + "</td><td>" + customer.getValue().getLastName() + "</td>");
		pw.print("<td>" + customer.getValue().getUserAddress() + "</td><td>" + customer.getValue().getUserEmail() + "</td>");
	  	//pw.print("<td><input type='submit' name='ModifyCustomers' value='Delete' class='btnbuy'></td>");
		pw.print("</tr>");
		i++;
	}
	pw.print("</table><br>");
	pw.print("<h4>Enter a customer username to modify:</h4>");
	pw.print("<form method='post' action='ModifyCustomers'>");
	pw.print("<table><tr><td><h4>Username: </h4></td>");
	pw.print("<td><input name='username' type='text' value='' class='input' required></td></tr>");
	pw.print("<tr><td></td><td><input type='submit' name='ModifyCustomers' value='Modify' class='btnbuy'></td></tr>");
	pw.print("</table></form");
	pw.print("<br><br>");
	pw.print("<h3><a href='AddCustomer'>Add Customer</a></h3>");
	pw.print("</div></div></div>");		
	utility.printHtml("Footer.html");

	}
}