import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.*;
import java.io.*;
import java.time.LocalDate;

@WebServlet("/ModifyCustomers")

public class ModifyCustomers extends HttpServlet {

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		PrintWriter pw = response.getWriter();
		Utilities utility = new Utilities(request, pw);
		Customer customer = new Customer();
		String usertype = utility.session.getAttribute("usertype").toString();

		if(request.getParameter("ModifyCustomers") != null && request.getParameter("ModifyCustomers").equals("SubmitChange"))
		{
			String newFirstName;
			String newLastName;
			String newAddress;
			String newEmail;
			String newCreditCard;

			// Initialize fields to keep in DB record
			String username = request.getParameter("username");

			// Insert new values, if supplied, otherwise use existing values
			if (request.getParameter("newFirstName").equals(""))
				newFirstName = request.getParameter("oldFirstName");
			else
				newFirstName = request.getParameter("newFirstName");
			
			if (request.getParameter("newLastName").equals(""))
				newLastName = request.getParameter("oldLastName");
			else
				newLastName = request.getParameter("newLastName");
			
			if (request.getParameter("newAddress").equals(""))
				newAddress = request.getParameter("oldAddress");
			else
				newAddress = request.getParameter("newAddress");

			if (request.getParameter("newEmail").equals(""))
				newEmail = request.getParameter("oldEmail");
			else
				newEmail = request.getParameter("newEmail");

			if (request.getParameter("newCreditCard").equals(""))
				newCreditCard = request.getParameter("oldCreditCard");
			else
				newCreditCard = request.getParameter("newCreditCard");

			MySqlDataStoreUtilities.updateCustomer(username,newFirstName,newLastName,newAddress,newEmail,newCreditCard);
					
			if(usertype.equals("customer"))
				response.sendRedirect("Account");
			else
				response.sendRedirect("CustomerList");
		}

		if(request.getParameter("ModifyCustomers") != null && request.getParameter("ModifyCustomers").equals("Modify"))
		{
			String username = request.getParameter("username");

			customer = MySqlDataStoreUtilities.selectSpecCustomer(username);

			if(customer.getUsername() != null && !customer.getUsername().equals("")) {


				displayModForm(customer, request, response);
				// else if(request.getParameter("ModifyCustomers").equals("Delete"))
				// {
				// 	MySqlDataStoreUtilities.deleteCustomer(username);
				// 	response.sendRedirect("CustomerList");
				// }
			}
			else 
			{
				utility.printHtml("Header.html");
				utility.printHtml("LeftNavigationBar.html");
				pw.print("<div id='content'><div class='post'><h2 class='title meta'>");
				pw.print("<a style='font-size: 24px;'>Modify Customer</a>");
				pw.print("</h2><div class='entry'>");
				pw.print("<h4 style='color:red'>There are no customers in the database with this username.</h4><br>");
				pw.print("<h4><a href='/WideCast/CustomerList'>Return to Customers</a></h4>");
				pw.print("</div></div></div>");
				utility.printHtml("Footer.html");
			}
		}

	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {	
		response.setContentType("text/html");
		PrintWriter pw = response.getWriter();
		Utilities utility = new Utilities(request, pw);

		//check if the user is logged in
		if(!utility.isLoggedin()) {
			HttpSession session = request.getSession(true);				
			session.setAttribute("login_msg", "Please log in to view customers.");
			response.sendRedirect("Login");
			return;
		}
	}	

	public void displayModForm(Customer customer, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter pw = response.getWriter();
		Utilities utility = new Utilities(request, pw);

		utility.printHtml("Header.html");
		utility.printHtml("LeftNavigationBar.html");
		pw.print("<div id='content'><div class='post'><h2 class='title meta'>");
		pw.print("<a style='font-size: 24px;'>Update Customer Information</a>");
		pw.print("</h2><div class='entry'>");
		
		// Show existing customer info
		pw.print("<table style='width:100%'>");
		pw.print("<tr><td>");
		pw.print("Existing customer username: " + customer.getUsername() + "</td></tr>");
		pw.print("<tr><td>Existing Customer First Name: " + customer.getFirstName() + "</td></tr>");
		pw.print("<tr><td>");
		pw.print("Existing Customer Last Name: " + customer.getLastName() + "</td></tr>");
		pw.print("<tr><td>");
		pw.print("Existing Customer Address: " + customer.getUserAddress() + "</td></tr>");
		pw.print("<tr><td>");
		pw.print("Existing Customer Email Address: " + customer.getUserEmail() + "</td></tr>");
		pw.print("<tr><td>");
		pw.print("Existing Customer Credit Card No.: " + customer.getCreditCardNo() + "</td></tr>");
		pw.print("</table>");
		
		// Display form for new customer info
		pw.print("<form method='post' action='ModifyCustomers'><table style='width:100%'> ");
		pw.print("<tr><td>");
		pw.print("<h4>New First Name</h4></td><td><input type='text' name='newFirstName' value='' class='input'></input></td></tr>");
		pw.print("<tr><td>");
		pw.print("<h4>New Last Name</h4></td><td><input type='text' name='newLastName' value='' class='input'></input></td></tr>");
		pw.print("<tr><td>");
		pw.print("<h4>New Address</h4></td><td><input type='text' name='newAddress' value='' class='input'></input></td></tr>");
		pw.print("<tr><td>");
		pw.print("<h4>New Email Address</h4></td><td><input type='text' name='newEmail' value='' class='input'></input></td></tr>");
		pw.print("<tr><td>");
		pw.print("<h4>New Credit Card No.</h4></td><td><input type='text' name='newCreditCard' value='' class='input'></input></td></tr>");
		pw.print("<tr><td>");
		pw.print("<input type='hidden' name='username' value='" + customer.getUsername() + "'>");
		pw.print("<input type='hidden' name='oldFirstName' value='" + customer.getFirstName() + "'>");				
		pw.print("<input type='hidden' name='oldLastName' value='" + customer.getLastName() + "'>");
		pw.print("<input type='hidden' name='oldAddress' value='" + customer.getUserAddress() + "'>");
		pw.print("<input type='hidden' name='oldEmail' value='" + customer.getUserEmail() + "'>");
		pw.print("<input type='hidden' name='oldCreditCard' value='" + customer.getCreditCardNo() + "'>");
		pw.print("<input type='submit' name='ModifyCustomers' value='SubmitChange' class='btnbuy'>");
		pw.print("</td></tr></table>");			
		pw.print("</form></div></div></div>");
		pw.print("<br><br>");
		utility.printHtml("Footer.html");
	}
}