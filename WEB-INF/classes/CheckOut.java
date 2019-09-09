import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.sql.*;

@WebServlet("/CheckOut")

//once the user clicks buy now button page is redirected to checkout page where user has to give checkout information

public class CheckOut extends HttpServlet {
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		response.setContentType("text/html");
		PrintWriter pw = response.getWriter();
	    Utilities Utility = new Utilities(request, pw);
		storeOrders(request, response);
	}
	
	protected void storeOrders(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        response.setContentType("text/html");
		PrintWriter pw = response.getWriter();
        Utilities utility = new Utilities(request,pw);
        String userName = "";
        HttpSession session = request.getSession();

        if(!utility.isLoggedin())
		{
			session = request.getSession(true);				
			session.setAttribute("login_msg", "Please Login to add items to cart");
			response.sendRedirect("Login");
			return;
		}
		String cust = request.getParameter("custUsername");
       	if (cust != null)
	    	userName = request.getParameter("custUsername");
	    else
	    	userName = session.getAttribute("username").toString();

		try
		{
			//get the order product details	on clicking submit the form will be passed to submitorder page	
		
	    	Customer customer = MySqlDataStoreUtilities.selectSpecCustomer(userName);
		
	        String orderTotal = request.getParameter("orderTotal");
			utility.printHtml("Header.html");
			utility.printHtml("LeftNavigationBar.html");
			if(customer.getFirstName().equals("FirstName") || customer.getLastName().equals("LastName") || customer.getUserAddress().equals("Address")
	    	|| customer.getUserEmail().equals("Email") || customer.getCreditCardNo().equals("CreditCard")) {
		    	pw.print("<div id='content'><div class='post'><h2 class='title meta'>");
				pw.print("<a style='font-size: 24px;'>Please update your account information!</a>");
				pw.print("</h2><div class='entry'>");
				pw.print("<form action='ModifyCustomers' method='post'>");
		     	pw.print("<input type='hidden' name='username' value='" + customer.getUsername() + "'>");
		    	pw.print("<input type='submit' name='ModifyCustomers' value='Modify' class='btnbuy'></td>");
		    	pw.print("</form></div></div></div>");		
				utility.printHtml("Footer.html"); 
			}
			else {
				pw.print("<form name ='CheckOut' action='Payment' method='post'>");
		        pw.print("<div id='content'><div class='post'><h2 class='title meta'>");
				pw.print("<a style='font-size: 24px;'>Order</a>");
				pw.print("</h2><div class='entry'>");
				pw.print("<table class='gridtable'><tr><td>Customer Username:</td><td>");
				pw.print(userName);
				pw.print("</td></tr>");
				// for each order iterate and display the order name price
				for (OrderItem oi : utility.getCustomerOrders()) {
					pw.print("<tr><td> Product Purchased: </td><td>");
					pw.print(oi.getName()+"</td></tr><tr><td>");
					pw.print("Item ID: </td><td>");
					pw.print(oi.getItemId()+"</td></tr><tr><td>");
					pw.print("<input type='hidden' name='orderPrice' value='"+oi.getPrice()+"'>");
					pw.print("<input type='hidden' name='orderItem' value='"+oi.getItemId()+"'>");
					pw.print("<input type='hidden' name='orderName' value='"+oi.getName()+"'>");
					pw.print("<input type='hidden' name='date' value='"+oi.getDate()+"'>");
					pw.print("Product Price:</td><td>"+ oi.getPrice());
					if(oi.getType().equals("events"))
						pw.print("</td></tr><tr><td>Event air date:</td><td>"+ oi.getDate().toString());
					else if(oi.getType().equals("tvPlans") || oi.getType().equals("dataPlans"))
						pw.print("</td></tr><tr><td>Subscription start date:</td><td>"+ oi.getDate().toString());
					pw.print("</td></tr>");
				}

				pw.print("<tr><td>");
		        pw.print("Total Order Cost</td><td>"+orderTotal);
				pw.print("<input type='hidden' name='orderTotal' value='"+orderTotal+"'>");
				pw.print("</td></tr></table><table><tr></tr><tr></tr>");	
				pw.print("<tr><td>");
				pw.print("Customer Name:");
				pw.print("</td>");
				pw.print("<td>" + customer.getFirstName() + " " + customer.getLastName() + "</td>");
				pw.print("<input type='hidden' name='custName' value='"+customer.getUsername()+"'>");
				pw.print("</tr>");
				pw.print("<tr><td>");
		     	pw.print("Credit Card No</td>");
				pw.print("<td>" + customer.getCreditCardNo() + "</td>");
				pw.print("<input type='hidden' name='creditCardNo' value='"+customer.getCreditCardNo()+"'>");
				pw.print("</tr>");
				pw.print("<tr><td>");
			    pw.print("Customer Address</td>");
				pw.print("<td>" + customer.getUserAddress() + "</td>");
				pw.print("<input type='hidden' name='userAddress' value='"+customer.getUserAddress()+"'>");
		        pw.print("</tr>");
				pw.print("<tr><td colspan='2'>");
				pw.print("<input type='submit' name='submit' class='btnbuy'>");
		        pw.print("</td></tr></table></form>");
				pw.print("</div></div></div>");		
				utility.printHtml("Footer.html");
			} 					
		}
		catch(Exception e)
		{
         System.out.println(e.getMessage());
		}
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	    {
		response.setContentType("text/html");
		PrintWriter pw = response.getWriter();
	    }
}
