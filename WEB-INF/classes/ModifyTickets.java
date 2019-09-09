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

@WebServlet("/ModifyTickets")

public class ModifyTickets extends HttpServlet {

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		PrintWriter pw = response.getWriter();
		Utilities utility = new Utilities(request, pw);
		Ticket ticket = new Ticket();

		if(request.getParameter("ModifyTickets") != null && request.getParameter("ModifyTickets").equals("SubmitChange"))
		{
			String newDescription;
			boolean newActiveStatus = Boolean.valueOf(request.getParameter("newActiveStatus"));

			// Initialize fields to keep in DB record
			int ticketId = Integer.parseInt(request.getParameter("ticketId"));

			// Insert new desc value, if supplied, otherwise use existing value
			if (request.getParameter("newDesc").equals(""))
				newDescription = request.getParameter("oldDesc");
			else
				newDescription = request.getParameter("newDesc");

			MySqlDataStoreUtilities.updateTicket(ticketId, newDescription, newActiveStatus);
					
			response.sendRedirect("TicketsList");
		}

		if(request.getParameter("ModifyTickets") != null && !request.getParameter("ModifyTickets").equals("SubmitChange"))
		{
			int ticketId = Integer.parseInt(request.getParameter("ticketId"));

			ticket = MySqlDataStoreUtilities.getSpecTicket(ticketId);

			if(ticket.getCustUsername() != null) {

				if (request.getParameter("ModifyTickets").equals("Modify")) 
				{
					displayModForm(ticket, request, response);
				}
				else if(request.getParameter("ModifyTickets").equals("Cancel"))
				{
					MySqlDataStoreUtilities.deleteTicket(ticketId);
					response.sendRedirect("TicketsList");
				}
			}
			else 
			{
				utility.printHtml("Header.html");
				utility.printHtml("LeftNavigationBar.html");
				pw.print("<div id='content'><div class='post'><h2 class='title meta'>");
				pw.print("<a style='font-size: 24px;'>Modify Tickets</a>");
				pw.print("</h2><div class='entry'>");
				pw.print("<h4 style='color:red'>There are no records in the database for this ticket.</h4><br>");
				pw.print("<h4><a href='/WideCast/TicketsList'>Return to Tickets</a></h4>");
				pw.print("</div></div></div>");
				utility.printHtml("Footer.html");
			}
		}
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {		
		response.setContentType("text/html");
		PrintWriter pw = response.getWriter();
		Utilities utility = new Utilities(request, pw);

		//check if the user is logged in
		if(!utility.isLoggedin()) {
			HttpSession session = request.getSession(true);				
			session.setAttribute("login_msg", "Please log in to modify tickets");
			response.sendRedirect("Login");
			return;
		}
	}	

	public void displayModForm(Ticket ticket, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter pw = response.getWriter();
		Utilities utility = new Utilities(request, pw);

		utility.printHtml("Header.html");
		utility.printHtml("LeftNavigationBar.html");
		pw.print("<div id='content'><div class='post'><h2 class='title meta'>");
		pw.print("<a style='font-size: 24px;'>Enter New Ticket Information</a>");
		pw.print("</h2><div class='entry'>");
		
		// Show existing ticket info
		pw.print("<table style='width:100%'>");
		pw.print("<tr><td>");
		pw.print("Existing Ticket ID: " + ticket.getTicketId() + "</td></tr>");
		pw.print("<tr><td>Existing Ticket Customer ID: " + ticket.getCustUsername() + "</td></tr>");
		pw.print("<tr><td>");
		pw.print("Existing Ticket Technician ID: " + ticket.getTechnician() + "</td></tr>");
		pw.print("<tr><td>");
		pw.print("Existing Ticket Description: " + ticket.getDescription() + "</td></tr>");
		pw.print("<tr><td>");
		pw.print("Ticket active? " + ticket.getActiveStatus() + "</td></tr>");
		pw.print("</table>");
		
		// Display form for new ticket info
		pw.print("<form method='post' action='ModifyTickets'><table style='width:100%'> ");
		pw.print("<tr><td>");
		pw.print("<h4>Updated ticket description: </h4></td><td><input type='text' name='newDesc' value='' class='input'></input></td></tr>");
		pw.print("<tr><td>");
		pw.print("<h4>Ticket active? </h4></td><td><select name='newActiveStatus' class='input'><option value='true' selected>Yes</option><option value='false'>No</option></select></td></tr>");
		pw.print("<input type='hidden' name='ticketId' value='" + ticket.getTicketId() + "'>");
		pw.print("<input type='hidden' name='custUsername' value='" + ticket.getCustUsername() + "'>");				
		pw.print("<input type='hidden' name='technician' value='" + ticket.getTechnician() + "'>");
		pw.print("<input type='hidden' name='oldDesc' value='" + ticket.getDescription() + "'>");
		pw.print("<tr><td></td><td><input type='submit' name='ModifyTickets' value='SubmitChange' class='btnbuy'></td></tr>");
		pw.print("</table>");			
		pw.print("</form></div></div></div>");
		pw.print("<br><br>");
		utility.printHtml("Footer.html");
	}
}