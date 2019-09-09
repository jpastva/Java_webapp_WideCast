import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.*;

@WebServlet("/AddCustomer")

public class AddCustomer extends HttpServlet {
	private String msg;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		PrintWriter pw = response.getWriter();
		displayCustomerForm(request, response, pw, false);
	}

	/*   Username, Password, Usertype information are Obtained from HttpServletRequest variable and validates whether
		 the User credential already exists or else User details are added to the Users hashMap */

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		PrintWriter pw = response.getWriter();

		String username = request.getParameter("username");
		String password = request.getParameter("password");
		String repassword = request.getParameter("repassword");
		String usertype = "customer";
		String firstName = request.getParameter("firstName");
		String lastName = request.getParameter("lastName");
		String userAddress = request.getParameter("userAddress");
		String userEmail = request.getParameter("userEmail");
		String creditCardNo = request.getParameter("creditCardNo"); 

		//if password and repassword do not match show error message

		if(!password.equals(repassword))
		{
			msg = "Passwords don't match!";
		}
		else
		{
			HashMap<String, User> hm = new HashMap<String, User>();

			//get the user details from file 	      
			hm = MySqlDataStoreUtilities.selectUser();

			// if the user already exists show error that already exists
			if(hm.containsKey(username)) {
				msg = "Username already exists.";
				displayCustomerForm(request, response, pw, true);
			}
			else
			{
				// create a User object and store details into Registration table in database
				// create a Customer object and store details into Customer table in database

				MySqlDataStoreUtilities.insertUser(username, password, repassword, usertype);
				MySqlDataStoreUtilities.insertCustomer(username, firstName, lastName, userAddress, userEmail, creditCardNo);
				
				msg = "Customer created.";
				displayCustomerForm(request, response, pw, true);
				response.sendRedirect("CustomerList");
			}
		}
	}

	/*  displayCustomerForm displays the Registration page for New Customer */
	
	protected void displayCustomerForm(HttpServletRequest request,
			HttpServletResponse response, PrintWriter pw, boolean error)
			throws ServletException, IOException {
		Utilities utility = new Utilities(request, pw);
		utility.printHtml("Header.html");
		pw.print("<div class='post' style='float: none; width: 100%'>");
		pw.print("<h2 class='title meta'><a style='font-size: 24px;'>Add Customer</a></h2>"
				+ "<div class='entry'>"
				+ "<div style='width:400px; margin:25px; margin-left: auto;margin-right: auto;'>");
		if (error)
			pw.print("<h4 style='color:red'>" + msg + "</h4>");
		pw.print("<form method='post' action='AddCustomer'>"
				+ "<table style='width:100%'><tr><td>"
				+ "<h3>Username</h3></td><td><input type='text' name='username' value='' class='input' required></input>"
				+ "</td></tr><tr><td>"
				+ "<h3>Password</h3></td><td><input type='password' name='password' value='' class='input' required></input>"
				+ "</td></tr><tr><td>"
				+ "<h3>Re-Password</h3></td><td><input type='password' name='repassword' value='' class='input' required></input>"
				+ "</td></tr><tr><td>"
				+ "<h3>First Name</h3></td><td><input type='text' name='firstName' value='' class='input' required></input>"
				+ "</td></tr><tr><td>"
				+ "<h3>Last Name</h3></td><td><input type='text' name='lastName' value='' class='input' required></input>"
				+ "</td></tr><tr><td>"
				+ "<h3>Customer Address</h3></td><td><input type='text' name='userAddress' value='' class='input' required></input>"
				+ "</td></tr><tr><td>"
				+ "<h3>Customer Credit Card no.</h3></td><td><input type='text' name='creditCardNo' value='' class='input' required></input>"
				+ "</td></tr><tr><td>"
				+ "<h3>Customer Email Address</h3></td><td><input type='text' name='userEmail' value='' class='input' required></input>"
				+ "</td></tr>"
				+ "</table>"
				+ "<input type='submit' class='btnbuy' name='AddCustomer' value='Create Customer' style='float: right;height: 20px margin: 20px; margin-right: 10px;'></input>"
				+ "</form>" + "</div></div></div>");
		utility.printHtml("Footer.html");
	}
}
