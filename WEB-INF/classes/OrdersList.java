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

@WebServlet("/OrdersList")

public class OrdersList extends HttpServlet {

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		response.setContentType("text/html");
		PrintWriter pw = response.getWriter();
		Utilities utility = new Utilities(request, pw);
		String username = utility.username();
		HashMap<Integer, ArrayList<OrderPayment>> orderPayments = new HashMap<>();

		//check if the user is logged in
		if(!utility.isLoggedin()){
			HttpSession session = request.getSession(true);				
			session.setAttribute("login_msg", "Please Login to View your Orders");
			response.sendRedirect("Login");
			return;
		}

		orderPayments = MySqlDataStoreUtilities.selectOrder();

		utility.printHtml("Header.html");
		utility.printHtml("LeftNavigationBar.html");
		pw.print("<div id='content'><div class='post'><h2 class='title meta'>");
		pw.print("<a style='font-size: 24px;'>List of Orders</a>");
		pw.print("</h2><div class='entry'>");
		if (request.getParameter("error") != null)
			pw.print("<h3>"+request.getParameter("error")+"</h3>");
		pw.print("<table class='gridtable'>");
		pw.print("<tr>");
		pw.print("<td>OrderID</td>");
		pw.print("<td>ItemID</td>");
		pw.print("<td>Name</td>");
		pw.print("<td>User</td>");
		pw.print("<td>Price</td>");
		pw.print("</tr>");
		for(Map.Entry<Integer, ArrayList<OrderPayment>> entry : orderPayments.entrySet()) {
			for (OrderPayment oi : entry.getValue()) {
				pw.print("<tr>");
				pw.print("<td> "+oi.getOrderId()+" </td><td> "+oi.getItemId()+" </td><td> "+oi.getOrderName()+" </td><td> "+oi.getUserName()+" </td><td> "
					+oi.getOrderPrice()+" </td>");
				pw.print("</tr>");	
			}		
		}	
		pw.print("</table><br><br>");
		pw.print("<form action='ModifyOrders' method='post'");
		pw.print("<h4>Enter OrderID and ItemID to modify or delete the order.</h4>");
		pw.print("<table><tr><td>Order ID: </td>");
		pw.print("<td><input name='orderId' type='text' value='' class='input' required></td></tr>");
		pw.print("<tr><td>Item ID: </td>");
		pw.print("<td><input name='itemId' type='text' value='' class='input' required></td></tr></table><br>");
		pw.print("<input type='submit' name='ModifyOrders' value='Modify' class='btnbuy'><br>");
		pw.print("<input type='submit' name='ModifyOrders' value='Cancel' class='btnbuy'></form>");
		pw.print("</div></div></div>");
		pw.print("<br><br>");

		utility.printHtml("Footer.html");
	}

}