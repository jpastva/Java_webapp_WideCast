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
import java.time.temporal.ChronoUnit;


@WebServlet("/UpdateSubscription")

public class UpdateSubscription extends HttpServlet {

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		PrintWriter pw = response.getWriter();
		Utilities utility = new Utilities(request, pw);

		if(request.getParameter("UpdateSubscription") != null && request.getParameter("UpdateSubscription").equals("Cancel"))
		{
			int subOrderId = Integer.parseInt(request.getParameter("subProductOrder"));
			int subItemId = Integer.parseInt(request.getParameter("subItem"));

			MySqlDataStoreUtilities.deleteSubscription(subOrderId, subItemId);
			response.sendRedirect("Account");
		}

		if(request.getParameter("UpdateSubscription") != null && request.getParameter("UpdateSubscription").equals("PayBill"))
		{
			
			int subOrderId = Integer.parseInt(request.getParameter("subProductOrder"));
			int subItemId = Integer.parseInt(request.getParameter("subItem"));
			
			Subscription sub = new Subscription();
			sub = MySqlDataStoreUtilities.getSpecSubscription(subOrderId, subItemId);

			if(sub.getUsername() != null && !sub.getUsername().equals("")) {
				if(sub.getBalance() > 0) {
					// get starting balance owed
					double due = sub.getBalance();
					// find out if payment is overdue, and how much more $$ is owed to add to balance
					LocalDate subStart = sub.getStartDate();
					LocalDate paymentDue = subStart.plusDays(30);
					LocalDate today = LocalDate.now();
					if(today.isAfter(paymentDue)) {
						long dayDiff = subStart.until(today, ChronoUnit.DAYS);
						int multiplier = (int)dayDiff / 30;
						double newCharge = sub.getPrice() * multiplier;
						due += newCharge; 
					}

					Customer customer = new Customer();
					customer = MySqlDataStoreUtilities.selectSpecCustomer(sub.getUsername());

					utility.printHtml("Header.html");
					utility.printHtml("LeftNavigationBar.html");
					pw.print("<div id='content'><div class='post'><h2 class='title meta'>");
					pw.print("<a style='font-size: 24px;'>Subscription Bill</a>");
					pw.print("</h2><div class='entry'>");
					pw.print("<form method='post' action='UpdateSubscription'>");
					pw.print("<h3>Your balance for " + sub.getProduct() + " is $" + due + ".</h3>");
					pw.print("<h4>Pay bill using your stored credit card number " + customer.getCreditCardNo() + "?</h4>");
					pw.print("<input type='hidden' name='paidProductOrder' value='"+ sub.getOrderId() + "'>");
					pw.print("<input type='hidden' name='paidItem' value='"+ sub.getItemId() + "'>");
					pw.print("<input type='submit' name='UpdateSubscription' value='SubmitPayment' class='btnbuy'>");
					pw.print("</form>");
					pw.print("</div></div></div>");
					pw.print("<br><br>");
					utility.printHtml("Footer.html");
				}
			
				else {
					utility.printHtml("Header.html");
					utility.printHtml("LeftNavigationBar.html");
					pw.print("<div id='content'><div class='post'><h2 class='title meta'>");
					pw.print("<a style='font-size: 24px;'>Subscription Bill</a>");
					pw.print("</h2><div class='entry'>");
					pw.print("<h3>Your bill for " + sub.getProduct() + " is already paid.</h3>");
					pw.print("</div></div></div>");
					pw.print("<br><br>");
					utility.printHtml("Footer.html");
				}
			}
			else {
				utility.printHtml("Header.html");
				utility.printHtml("LeftNavigationBar.html");
				pw.print("<div id='content'><div class='post'><h2 class='title meta'>");
				pw.print("<a style='font-size: 24px;'>Subscription Bill</a>");
				pw.print("</h2><div class='entry'>");
				pw.print("<h3>Subscription does not exist.</h3>");
				pw.print("</div></div></div>");
				pw.print("<br><br>");
				utility.printHtml("Footer.html");
			}

		}

		if(request.getParameter("UpdateSubscription") != null && request.getParameter("UpdateSubscription").equals("SubmitPayment"))
		{
			int paidOrder = Integer.parseInt(request.getParameter("paidProductOrder"));
			int paidItem = Integer.parseInt(request.getParameter("paidItem"));

			MySqlDataStoreUtilities.paySubscription(paidOrder,paidItem,0);
			response.sendRedirect("Account");
		}
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter pw = response.getWriter();
		response.setContentType("text/html");
		Utilities utility = new Utilities(request, pw);

		//check if the user is logged in
		if(!utility.isLoggedin()) {
			HttpSession session = request.getSession(true);				
			session.setAttribute("login_msg", "Please log in to view your subscriptions.");
			response.sendRedirect("Login");
			return;
		}
	}	
}