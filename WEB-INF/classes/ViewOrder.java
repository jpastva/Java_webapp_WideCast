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

@WebServlet("/ViewOrder")

public class ViewOrder extends HttpServlet {
	
protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		response.setContentType("text/html");
		PrintWriter pw = response.getWriter();

		Utilities utility = new Utilities(request, pw);
		//check if the user is logged in
		if(!utility.isLoggedin()){
			HttpSession session = request.getSession(true);				
			session.setAttribute("login_msg", "Please Login to View your Orders");
			response.sendRedirect("Login");
			return;
		}
		String username = utility.username();
		utility.printHtml("Header.html");
		utility.printHtml("LeftNavigationBar.html");
		pw.print("<form name ='ViewOrder' action='ViewOrder' method='get'>");
		pw.print("<div id='content'><div class='post'><h2 class='title meta'>");
		pw.print("<a style='font-size: 24px;'>Order</a>");
		pw.print("</h2><div class='entry'>");

		/*check if the order button is clicked 
		if order button is not clicked that means the view order page is visited freshly
		then user will get textbox to give order number by which they can view order 
		if order button is clicked user will be directed to this same servlet and user has given order number 
		then this page shows all the order details*/
	
		if(request.getParameter("Order") == null)
		{
			pw.print("<table align='center'><tr><td>Enter OrderNo &nbsp&nbsp<input name='orderId' type='text'></td>");
			pw.print("<td><input type='submit' name='Order' value='ViewOrder' class='btnbuy'></td></tr></table>");
		}

		/*if order button is clicked that is user provided a order number to view order 
		order details will be fetched and displayed in  a table 
		Also user will get an button to cancel the order */

		if(request.getParameter("Order") != null && request.getParameter("Order").equals("ViewOrder"))
		{
			if (request.getParameter("orderId") != null && request.getParameter("orderId") != "" )
			{	
				int orderId = Integer.parseInt(request.getParameter("orderId"));
				HashMap<Integer, ArrayList<OrderPayment>> orderPayments = new HashMap<>();
				pw.print("<input type='hidden' name='orderId' value='" + orderId + "'>");
				
				//get the order details from database	      
				orderPayments = MySqlDataStoreUtilities.selectSpecOrder(orderId);
				
				int size = 0;
			
				/*get the order size and check if there exists an order with given order number 
				if there is no order present give a message no order stored with this id */

				if(orderPayments.get(orderId) != null)
				{
					for(OrderPayment od:orderPayments.get(orderId))	
						if(od.getUserName().equals(username))
							size = orderPayments.get(orderId).size();
				}
				// display the orders if there exist order with order id
				if(size > 0)
				{	
					pw.print("<table  class='gridtable'>");
					pw.print("<tr><td></td>");
					pw.print("<td>OrderId:</td>");
					pw.print("<td>ItemId:</td>");
					pw.print("<td>User Name:</td>");
					pw.print("<td>Product Ordered:</td>");
					pw.print("<td>Product Price:</td>");
					for (OrderPayment oi : orderPayments.get(orderId)) 
					{
						pw.print("<tr>");			
						pw.print("<td><input type='radio' name='itemId' value='" + oi.getItemId() + "'></td>");			
						pw.print("<td>"+oi.getOrderId()+".</td><td>"+oi.getItemId()+".</td><td>"+oi.getUserName()+"</td><td>" + oi.getOrderName() 
							+ "</td><td>Price: " + oi.getOrderPrice()+"</td>");
						pw.print("<td><input type='submit' name='Order' value='CancelOrder' class='btnbuy'></td>");
						pw.print("</tr>");
					
					}
					pw.print("</table>");
				}
				else
				{
					pw.print("<h4 style='color:red'>You have not placed any order with this order id</h4>");
				}
			} 
			else	
			{
				pw.print("<h4 style='color:red'>Please enter the valid order number</h4>");	
			}
		}
		//if the user presses cancel order from order details shown then process to cancel the order
		if(request.getParameter("Order") != null && request.getParameter("Order").equals("CancelOrder"))
		{
			if(request.getParameter("orderId") != null && request.getParameter("itemId") != null)
			{
				int itemId = Integer.parseInt(request.getParameter("itemId"));
				int orderId = Integer.parseInt(request.getParameter("orderId"));

				OrderPayment order = new OrderPayment();
				order = MySqlDataStoreUtilities.selectOrderItem(orderId, itemId);

				// check if PPV event order can be deleted
				if (order.getItemType().equals("events")) {
						LocalDate startDate = order.getDate();
						LocalDate today = LocalDate.now();
						if (startDate.equals(today) || today.isAfter(startDate)) {
							pw.print("<h4 style='color:red'>You cannot cancel a PPV event order less than 24 hours before the even start date, or after it has occurred.</h4><br>");
							pw.print("<h4><a href='/WideCast/Account'>Return to account</a></h4>");
						}
						else {
							MySqlDataStoreUtilities.deleteOrder(orderId, itemId);
							pw.print("<h4 style='color:red'>Your Order is cancelled.</h4>");
						}
				}
				else {	
					//delete the order from the database
					MySqlDataStoreUtilities.deleteOrder(orderId, itemId);
					pw.print("<h4 style='color:red'>Your Order is cancelled.</h4>");								
				}

			}
			else
			{
				pw.print("<h4 style='color:red'>Please enter an order number.</h4>");
			}
		}
		else
		{
			pw.print("<h4 style='color:red'>Please enter an order number.</h4>");
		}
		pw.print("</form></div></div></div>");		
		utility.printHtml("Footer.html");
	}

}


