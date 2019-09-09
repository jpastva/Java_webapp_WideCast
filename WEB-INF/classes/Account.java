import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.*;
import java.io.*;
import java.sql.*;

@WebServlet("/Account")

public class Account extends HttpServlet {
	private String error_msg;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		PrintWriter pw = response.getWriter();
		displayAccount(request, response);
	}

	/* Display Account Details of the Customer (Username, first and last name, address, email, and credit card info) */

	protected void displayAccount(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		PrintWriter pw = response.getWriter();
		Utilities utility = new Utilities(request, pw);
		String username = utility.username();
		HashMap<Integer, ArrayList<OrderPayment>> orderPayments = new HashMap<Integer, ArrayList<OrderPayment>>();

		if(!utility.isLoggedin())
		{
			HttpSession session = request.getSession(true);				
			session.setAttribute("login_msg", "Please Login to add items to your cart.");
			response.sendRedirect("Login");
			return;
		}
		HttpSession session = request.getSession(); 	
		utility.printHtml("Header.html");
		utility.printHtml("LeftNavigationBar.html");
		pw.print("<div id='content'><div class='post'><h2 class='title meta'>");
		pw.print("<a style='font-size: 24px;'>Account</a>");
		pw.print("</h2><div class='entry'>");
		
		Customer customer = MySqlDataStoreUtilities.selectSpecCustomer(username);

		pw.print("<form method='post' action='ModifyCustomers'>");
		pw.print("<table class='gridtable'>");
		pw.print("<tr>");
		pw.print("<td> Username: </td>");
		pw.print("<td>" + customer.getUsername() + "</td>");
		pw.print("</tr>");
		pw.print("<tr>");
		pw.print("<td> Name: </td>");
		pw.print("<td>" + customer.getFirstName() + " " + customer.getLastName() + "</td>");
		pw.print("</tr><tr>");
		pw.print("<td> Mailing Address: </td>");
		pw.print("<td>" + customer.getUserAddress() + " </td>");
		pw.print("</tr><tr>");
		pw.print("<td> Email Address: </td>");
		pw.print("<td>" + customer.getUserEmail() + " </td>");
		pw.print("</tr><tr>");
		pw.print("<td> Credit Card Number: </td>");
		pw.print("<td>" + customer.getCreditCardNo() + " </td>");
		pw.print("</tr></table>");
		pw.print("<input type='hidden' name='username' value='" + customer.getUsername() + "'>");
		pw.print("<input type='submit' name='ModifyCustomers' value='Modify' class='btnbuy'>");
		pw.print("<br><br>");
		pw.print("</form>");

		User user = MySqlDataStoreUtilities.getSpecUser(username);
		orderPayments = MySqlDataStoreUtilities.selectOrder();

		int size = 0;
		for(Map.Entry<Integer, ArrayList<OrderPayment>> entry : orderPayments.entrySet())
			{
				for(OrderPayment od : entry.getValue())	
					if(od.getUserName().equals(user.getName()))
					size = size + 1;
			}
			
		if(size > 0)
		{	
			pw.print("<h3>Order history</h3><br>");
			pw.print("<h4>*Subscriptions can be paid or cancelled via the Subscriptions menu.</h4><br>");
			pw.print("<table class='gridtable'>");
			pw.print("<tr><td></td>");
			pw.print("<td>OrderId:</td>");
			pw.print("<td>ItemId:</td>");
			pw.print("<td>UserName:</td>");
			pw.print("<td>productOrdered:</td>");
			pw.print("<td>productPrice:</td>");
			for(Map.Entry<Integer, ArrayList<OrderPayment>> entry : orderPayments.entrySet())
			{
				for(OrderPayment oi : entry.getValue())	
				if(oi.getUserName().equals(user.getName())) 
				{
					pw.print("<form method='get' action='ViewOrder'>");
					pw.print("<tr>");			
					if (oi.getItemType().equals("events") || oi.getItemType().equals("movies"))
						pw.print("<td><input type='radio' name='itemId' value='" + oi.getItemId() + "'></td>");
					else
						pw.print("<td></td>");		
					pw.print("<td>" + oi.getOrderId() + ".</td><td>" + oi.getItemId() + "</td><td>" + oi.getUserName() + "</td><td>" 
						+ oi.getOrderName() + "</td><td>Price: "+oi.getOrderPrice()+ "</td>");
					pw.print("<td><input type='hidden' name='orderId' value='"+ oi.getOrderId() + "'></td>");
					if (oi.getItemType().equals("events") || oi.getItemType().equals("movies"))
						pw.print("<td><input type='submit' name='Order' value='CancelOrder' class='btnbuy'></td>");
					pw.print("</tr>");
					pw.print("</form>");
				}
			}
			pw.print("</table><br>");
		}
		else
		{
			pw.print("<h3>Order history</h3>");
			pw.print("<h4>You have not yet placed any orders.</h4>");
		}

		ArrayList<Subscription> subscriptions = new ArrayList<>();
		subscriptions = MySqlDataStoreUtilities.selectSubscriptions(username);
		if(!subscriptions.isEmpty()) {
			pw.print("<h3>Subscriptions</h3>");
			pw.print("<table class='gridtable'>");
			pw.print("<tr><td>OrderID</td><td>ItemId</td><td>Product</td>");
			pw.print("<td>Price</td>");
			pw.print("<td>Account balance</td>");
			pw.print("<td>Subscription start date</td>");
			pw.print("</tr>");
			for (Subscription sub : subscriptions) {
				pw.print("<tr><td>" + sub.getOrderId() + "</td>");
				pw.print("<td>" + sub.getItemId() + "</td>");
				pw.print("<td>" + sub.getProduct() + "</td>");
				pw.print("<td>" + sub.getPrice() + "</td>");
				pw.print("<td>" + sub.getBalance() + "</td>");
				pw.print("<td>" + sub.getStartDate().toString() + "</td></tr>");

				
			}
			pw.print("</table><br>");

			pw.print("<form method='post' action='UpdateSubscription'>");
			pw.print("<h3>Enter the subscription order id and item id to modify</h3>");
			pw.print("<table><tr><td><h4>Order ID: </h4></td>");
			pw.print("<td><input name='subProductOrder' type='text' value='' class='input' required></td></tr>");
			pw.print("<tr><td><h4>Item ID: </h4></td>");
			pw.print("<td><input name='subItem' type='text' value='' class='input' required></td></tr>");
			pw.print("<tr><td><input type='submit' name='UpdateSubscription' value='Cancel' class='btnbuy'></td>");
			pw.print("<td><input type='submit' name='UpdateSubscription' value='PayBill' class='btnbuy'></td></tr>");
			pw.print("</table></form>");
		}
		else {
			pw.print("<h3>Subscriptions</h3>");
			pw.print("<h4>You do not have any subscriptions.</h4>");
		}
		pw.print("</h2></div></div></div>");		
		utility.printHtml("Footer.html");	        	
	}
}
