import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Random;
import java.time.LocalDate;

@WebServlet("/Payment")

public class Payment extends HttpServlet {
	
		protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		response.setContentType("text/html");
		PrintWriter pw = response.getWriter();
		

		Utilities utility = new Utilities(request, pw);
		if(!utility.isLoggedin())
		{
			HttpSession session = request.getSession(true);				
			session.setAttribute("login_msg", "Please log in to pay.");
			response.sendRedirect("Login");
			return;
		}

		// get the payment details from cart servlet	
		String custName = request.getParameter("custName");
		String userAddress = request.getParameter("userAddress");
		String creditCardNo = request.getParameter("creditCardNo");
		LocalDate date = LocalDate.parse(request.getParameter("date"));
		System.out.print("the user address is" + userAddress);
		System.out.print(creditCardNo);
		if(!userAddress.isEmpty() && !creditCardNo.isEmpty())
		{
			int orderId = MySqlDataStoreUtilities.getNewOrderId();

			//iterate through each order
			for (OrderItem oi : utility.getCustomerOrders())
			{
				// store orders in database
				MySqlDataStoreUtilities.insertOrder(orderId, oi.getItemId(), oi.getType(), custName, oi.getName(), oi.getPrice(),
					userAddress, creditCardNo, date);

				// check for cable or data plans and enroll customer
				if(oi.getType().equals("tvPlans") || oi.getType().equals("dataPlans"))
					MySqlDataStoreUtilities.insertSubscription(orderId, oi.getItemId(), custName, oi.getName(), oi.getPrice(), oi.getPrice(), date);
			}

			//remove the order details from cart after processing
			
			OrdersHashMap.orders.remove(utility.username());
			Utilities.count = 1;
			Utilities.itemIds.clear();	
			utility.printHtml("Header.html");
			utility.printHtml("LeftNavigationBar.html");
			pw.print("<div id='content'><div class='post'><h2 class='title meta'>");
			pw.print("<a style='font-size: 24px;'>Order</a>");
			pw.print("</h2><div class='entry'>");
			pw.print("<h3>Your Order");
			pw.print("&nbsp&nbsp");  
			pw.print("is stored. ");
			pw.print("<br>Your Order No is: " + (orderId) + ".");
			pw.print("<br>PPV orders may be canceled up to 24 hours before the date of the event.");
			pw.print("</h3></div></div></div>");		
			utility.printHtml("Footer.html");
		} 
		else
		{
			utility.printHtml("Header.html");
			utility.printHtml("LeftNavigationBar.html");
			pw.print("<div id='content'><div class='post'><h2 class='title meta'>");
			pw.print("<a style='font-size: 24px;'>Order</a>");
			pw.print("</h2><div class='entry'>");
		
			pw.print("<h4 style='color:red'>Please enter valid address and creditcard number</h4>");
			pw.print("</h2></div></div></div>");		
			utility.printHtml("Footer.html");
		}	
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		response.setContentType("text/html");
		PrintWriter pw = response.getWriter();
		Utilities utility = new Utilities(request, pw);		
	}
}