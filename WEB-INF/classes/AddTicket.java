import java.io.*;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/AddTicket")

public class AddTicket extends HttpServlet {
	private String msg;
	boolean unique;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		PrintWriter pw = response.getWriter();
		displayResult("",request,response,pw);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		PrintWriter pw = response.getWriter();
		

		/* Ticket information(username, technician, description) is obtained from HttpServletRequest,
		  user and technician are validated, and a ticket is created */

		String custUsername = request.getParameter("custUsername");
		String technician = request.getParameter("technician");
		String description = request.getParameter("description");
		boolean activeStatus = Boolean.valueOf(request.getParameter("activeStatus"));
		int ticketId = 0;

		if (MySqlDataStoreUtilities.userExists(custUsername) && MySqlDataStoreUtilities.userExists(technician)) {
			try {
				ticketId = MySqlDataStoreUtilities.getNewTicketId();
				MySqlDataStoreUtilities.insertTicket(ticketId, custUsername, technician, description, activeStatus);
			}
			catch(Exception e) {
			}
			msg = "This ticket has been created." 
				+ "<br><h3><a href='TicketsList'>Tickets</a></h3><br>";
			displayResult(msg, request, response, pw);
		}
		else {
			msg = "Ticket not created. The customer or technician entered do not exist." 
				+ "<br><h3><a href='TicketsList'>Tickets</a></h3><br>";
			displayResult(msg, request, response, pw);
		}
	}

	protected void displayResult(String msg, HttpServletRequest request, HttpServletResponse response, PrintWriter pw) {

		Utilities utility = new Utilities(request, pw);
		utility.printHtml("Header.html");
		pw.print("<div class='post' style='float: none; width: 100%'>");
		pw.print("<h2 class='title meta'><a style='font-size: 24px;'>Ticket Status</a></h2>"
				+ "<div class='entry'>"
				+ "<div style='width:400px; margin:25px; margin-left: auto;margin-right: auto;'>");
		pw.print("<h4 style='color:red'>" + msg + "</h4>");
		pw.print("<br><br>");
		pw.print("<h4><a href='TicketForm.jsp'>Create a ticket</a></h4>");
		pw.print("</div></div></div>");
		utility.printHtml("Footer.html");
	}
}