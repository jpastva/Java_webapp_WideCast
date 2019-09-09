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

@WebServlet("/TicketsList")

public class TicketsList extends HttpServlet {

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		response.setContentType("text/html");
		PrintWriter pw = response.getWriter();
		Utilities utility = new Utilities(request, pw);
		String username = utility.username();
		HashMap<Integer, ArrayList<Ticket>> tickets = new HashMap<>();
		User thisUser = MySqlDataStoreUtilities.getSpecUser(username);

		//check if the user is logged in
		if(!utility.isLoggedin()){
			HttpSession session = request.getSession(true);				
			session.setAttribute("login_msg", "Please Login to view your support tickets");
			response.sendRedirect("Login");
			return;
		}

		tickets = MySqlDataStoreUtilities.getTickets();

		utility.printHtml("Header.html");
		utility.printHtml("LeftNavigationBar.html");
		pw.print("<div id='content'><div class='post'><h2 class='title meta'>");
		pw.print("<a style='font-size: 24px;'>List of Tickets</a>");
		pw.print("</h2><div class='entry'>");
		if (request.getParameter("error") != null)
			pw.print("<h3>"+request.getParameter("error")+"</h3>");
		pw.print("<table class='gridtable'>");
		pw.print("<tr>");
		pw.print("<td>TicketID</td>");
		pw.print("<td>Customer username</td>");
		pw.print("<td>Technician username</td>");
		pw.print("<td>Description</td>");
		pw.print("<td>Ticket Active?</td>");
		pw.print("</tr>");
		for(Map.Entry<Integer, ArrayList<Ticket>> entry : tickets.entrySet()) {
			for (Ticket ti : entry.getValue()) {
				pw.print("<tr>");
				pw.print("<td> "+ti.getTicketId()+" </td><td> "+ti.getCustUsername()+" </td><td> "+ti.getTechnician()+" </td><td> "
					+ti.getDescription()+" </td><td> " + ti.getActiveStatus() +"</td>");
				pw.print("<input type='hidden' name='ticketId' value='"+ti.getTicketId()+"'>");
				pw.print("</tr>");	
			}		
		}
		pw.print("</table></form><br>");
		pw.print("<br><h3><a href='TicketForm.jsp'>Add Ticket</a></h3><br>");
		if (thisUser.getUsertype().equals("technician") || thisUser.getUsertype().equals("manager")) {
			pw.print("<h3>Enter TicketID to modify or cancel a ticket.</h3>");
			pw.print("<form action='ModifyTickets' method='post'>");
			pw.print("<table><tr><td><h4>Ticket ID: </h4></td>");
			pw.print("<td><input name='ticketId' type='text' value='' class='input' required></td></tr>");
			pw.print("<tr><td><input type='submit' name='ModifyTickets' value='Modify' class='btnbuy'></td>");
			pw.print("<td><input type='submit' name='ModifyTickets' value='Cancel' class='btnbuy'></td></tr></table></form>");
		}
		pw.print("</div></div></div>");
		pw.print("<br><br>");
		utility.printHtml("Footer.html");
	}

}