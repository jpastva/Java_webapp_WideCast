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

@WebServlet("/ModifyOrders")

public class ModifyOrders extends HttpServlet {

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		PrintWriter pw = response.getWriter();
		Utilities utility = new Utilities(request, pw);
		OrderPayment order = new OrderPayment();

		if(request.getParameter("ModifyOrders") != null && request.getParameter("ModifyOrders").equals("SubmitChange"))
		{
			double newPrice;
			String newAddress;
			String newCreditCard;

			// Initialize fields to keep in DB record
			String oldOrder = request.getParameter("oldOrder");
			String oldUser = request.getParameter("oldUser");
			LocalDate oldDate = LocalDate.parse(request.getParameter("oldDate"));
			int oldId = Integer.parseInt(request.getParameter("oldId"));
			int oldItemId = Integer.parseInt(request.getParameter("oldItemId"));

			// Insert new values, if supplied, otherwise use existing values
			if (request.getParameter("newOrderPrice").equals(""))
				newPrice = Double.parseDouble(request.getParameter("oldPrice"));
			else
				newPrice = Double.parseDouble(request.getParameter("newOrderPrice"));
			
			if (request.getParameter("newOrderAddress").equals(""))
				newAddress = request.getParameter("oldAddress");
			else
				newAddress = request.getParameter("newOrderAddress");
			
			if (request.getParameter("newOrderCredit").equals(""))
				newCreditCard = request.getParameter("oldCreditCard");
			else
				newCreditCard = request.getParameter("newOrderCredit");

			MySqlDataStoreUtilities.updateOrder(oldId, oldItemId, oldUser, oldOrder, newPrice, newAddress, newCreditCard, oldDate);
					
			response.sendRedirect("OrdersList");
		}

		if(request.getParameter("ModifyOrders") != null && !request.getParameter("ModifyOrders").equals("SubmitChange"))
		{
			int orderId = Integer.parseInt(request.getParameter("orderId"));
			int itemId = Integer.parseInt(request.getParameter("itemId"));

			order = MySqlDataStoreUtilities.selectOrderItem(orderId, itemId);

			if(order.getOrderName() != null) {

				if (request.getParameter("ModifyOrders").equals("Modify")) 
				{
					displayModForm(order, request, response);
				}
				else if(request.getParameter("ModifyOrders").equals("Cancel"))
				{
					if (order.getItemType().equals("events")) {
						LocalDate startDate = order.getDate();
						LocalDate today = LocalDate.now();
						if (startDate.equals(today) || today.isAfter(startDate)) {
							pw.print("<h4 style='color:red'>You cannot cancel a PPV event order less than 24 hours before the even start date, or after it has occurred.</h4><br>");
							pw.print("<h4><a href='/WiedCast/OrdersList'>Return to Orders</a></h4>");	
						}
						else {
							MySqlDataStoreUtilities.deleteOrder(order.getOrderId(), order.getItemId());
							response.sendRedirect("OrdersList");
						}
					}
					else {
						MySqlDataStoreUtilities.deleteOrder(order.getOrderId(), order.getItemId());
						response.sendRedirect("OrdersList");
					}
				}
			}
			else 
			{
				utility.printHtml("Header.html");
				utility.printHtml("LeftNavigationBar.html");
				pw.print("<div id='content'><div class='post'><h2 class='title meta'>");
				pw.print("<a style='font-size: 24px;'>Modify Orders</a>");
				pw.print("</h2><div class='entry'>");
				pw.print("<h4 style='color:red'>There are no records in the database for this order.</h4><br>");
				pw.print("<h4><a href='/WiedCast/OrdersList'>Return to Orders</a></h4>");
				pw.print("</div></div></div>");
				utility.printHtml("Footer.html");
			}
		}
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		OrderPayment order = new OrderPayment();
		
		response.setContentType("text/html");
		PrintWriter pw = response.getWriter();
		Utilities utility = new Utilities(request, pw);

		//check if the user is logged in
		if(!utility.isLoggedin()) {
			HttpSession session = request.getSession(true);				
			session.setAttribute("login_msg", "Please log in to view orders");
			response.sendRedirect("Login");
			return;
		}
	}	

	public void displayModForm(OrderPayment order, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter pw = response.getWriter();
		Utilities utility = new Utilities(request, pw);

		utility.printHtml("Header.html");
		utility.printHtml("LeftNavigationBar.html");
		pw.print("<div id='content'><div class='post'><h2 class='title meta'>");
		pw.print("<a style='font-size: 24px;'>Enter New Order Information</a>");
		pw.print("</h2><div class='entry'>");
		
		// Show existing order info
		pw.print("<table style='width:100%'>");
		pw.print("<tr><td>");
		pw.print("Existing Order ID: " + order.getOrderId() + "</td></tr>");
		pw.print("<tr><td>Existing Order Name: " + order.getOrderName() + "</td></tr>");
		pw.print("<tr><td>");
		pw.print("Existing Order Item ID: " + order.getItemId() + "</td></tr>");
		pw.print("<tr><td>");
		pw.print("Existing Order Price: " + order.getOrderPrice() + "</td></tr>");
		pw.print("<tr><td>");
		pw.print("Existing Order User: " + order.getUserName() + "</td></tr>");
		pw.print("<tr><td>");
		pw.print("Existing Order Address: " + order.getUserAddress() + "</td></tr>");
		pw.print("<tr><td>");
		pw.print("Existing Order Credit Card #: " + order.getCreditCardNo() + "</td></tr>");
		pw.print("<tr><td>");
		pw.print("Existing Order Date: " + order.getDate().toString() + "</td></tr>");
		pw.print("</table>");
		
		// Display form for new order info
		pw.print("<form method='post' action='ModifyOrders'><table style='width:100%'> ");
		pw.print("<tr><td>");
		pw.print("<h4>New Order Price</h4></td><td><input type='text' name='newOrderPrice' value='' class='input'></input></td></tr>");
		pw.print("<tr><td>");
		pw.print("<h4>New Order Address</h4></td><td><input type='text' name='newOrderAddress' value='' class='input'></input></td></tr>");
		pw.print("<tr><td>");
		pw.print("<h4>New Order CreditCard</h4></td><td><input type='text' name='newOrderCredit' value='' class='input'></input></td></tr>");
		pw.print("<input type='hidden' name='oldId' value='" + order.getOrderId() + "'>");
		pw.print("<input type='hidden' name='oldItemId' value='" + order.getItemId() + "'>");				
		pw.print("<input type='hidden' name='oldOrder' value='" + order.getOrderName() + "'>");
		pw.print("<input type='hidden' name='oldUser' value='" + order.getUserName() + "'>");
		pw.print("<input type='hidden' name='oldPrice' value='" + order.getOrderPrice() + "'>");
		pw.print("<input type='hidden' name='oldAddress' value='" + order.getUserAddress() + "'>");
		pw.print("<input type='hidden' name='oldCreditCard' value='" + order.getCreditCardNo() +"'>");
		pw.print("<input type='hidden' name='oldDate' value='"+ order.getDate().toString() + "'>");
		pw.print("<input type='submit' name='ModifyOrders' value='SubmitChange' class='btnbuy'>");
		pw.print("</td></tr></table>");			
		pw.print("</form></div></div></div>");
		pw.print("<br><br>");
		utility.printHtml("Footer.html");
	}
}