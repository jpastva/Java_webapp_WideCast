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

@WebServlet("/ModifyCart")

public class ModifyCart extends HttpServlet {

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		PrintWriter pw = response.getWriter();
		Utilities utility = new Utilities(request, pw);
		String username = utility.username();

		//if the user presses cancel order from cart details, process to cancel the order
		if(request.getParameter("ModifyCart") != null && request.getParameter("ModifyCart").equals("CancelOrder"))
		{
			if(request.getParameter("orderName") != null)
		 	{
		 		String orderName = request.getParameter("orderName");
		 		boolean removed = utility.removeCustomerOrder(username, orderName);
		 		if(removed) { 
		 			response.sendRedirect("Cart");
		 		}
		 		else {
		 			response.sendRedirect("ModifyCart");
		 		}
		 	}
		}
	}

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		response.setContentType("text/html");
		PrintWriter pw = response.getWriter();
		Utilities utility = new Utilities(request, pw);
		String username = utility.username();
		//check if the user is logged in
		if(!utility.isLoggedin()){
			HttpSession session = request.getSession(true);				
			session.setAttribute("login_msg", "Please Login to View your Orders");
			response.sendRedirect("Login");
			return;
		}

		utility.printHtml("Header.html");
		utility.printHtml("LeftNavigationBar.html");
		pw.print("<div id='content'><div class='post'><h2 class='title meta'>");
		pw.print("<a style='font-size: 24px;'>Modify Cart</a>");
		pw.print("</h2><div class='entry'>");
		pw.print("<form method='post' action='ModifyCart'>");
		pw.print("<table class='gridtable'>");
		pw.print("<tr>");
		pw.print("<td>#</td>");
		pw.print("<td>Item</td>");
		pw.print("<td>Price</td>");
		pw.print("<td>Option</td>");
		int i = 1;
		for (OrderItem oi : utility.getCustomerOrders()) 
			{
				pw.print("<tr>");
				pw.print("<td>"+i+".</td><td>"+oi.getName()+"</td><td>: "+oi.getPrice()+"</td>");
				pw.print("<input type='hidden' name='orderName' value='"+oi.getName()+"'>");
				pw.print("<input type='hidden' name='orderPrice' value='"+oi.getPrice()+"'>");
				pw.print("<td><input type='submit' name='ModifyCart' value='CancelOrder' class='btnbuy'></td>");
				pw.print("</tr>");
				i++;
			}
		pw.print("</table></form></div></div></div>");
		pw.print("<br><br>");
		pw.print("<a href='Cart'>Return to Cart</a>");
		utility.printHtml("Footer.html");
	}

}