import java.io.PrintWriter;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/Inventory")

public class Inventory extends HttpServlet {

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		PrintWriter pw = response.getWriter();
		displayInvMenu(request, response);
	}

	/* Display Inventory menu */

	protected void displayInvMenu(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		PrintWriter pw = response.getWriter();
		Utilities utility = new Utilities(request, pw);
        response.setContentType("text/html");
		HttpSession session = request.getSession(); 

		utility.printHtml("Header.html");
		utility.printHtml("LeftNavigationBar.html");
		pw.print("<div id='content'><div class='post'><h2 class='title meta'>");
		pw.print("<a style='font-size: 24px;'>Inventory Menu</a>");
		pw.print("</h2><div class='entry'>");
		pw.print("<table class='gridtable'>");
		pw.print("<tr>");
		pw.print("<td><a href='MovieList'>PPV Movies</td>");
		pw.print("<td><a href='EventList'>PPV Events</a></td>");
		pw.print("</tr>");
		pw.print("<tr>");
		pw.print("<td><a href='TVPlanList'>Cable Plans</a></td>");
		pw.print("<td><a href='DataPlanList'>Internet Plans</a></td>");
		pw.print("</tr>");
		pw.print("<tr>");
		pw.print("<td><a href='AddProduct'>Add PPV product</a></td>");
		pw.print("<td><a href='AddTVPlan'>Add Cable Plan</a></td>");
		pw.print("</tr>");
		pw.print("<tr>");
		pw.print("<td><a href='AddDataPlan'>Add Internet Plan</a></td>");
		pw.print("<td><a href='UpdateProduct'>Update PPV Product</a></td>");
		pw.print("</tr><tr>");
		pw.print("<td><a href='UpdateTVPlan'>Update Cable Plan</a></td>");
		pw.print("<td><a href='UpdateDataPlan'>Update Internet Plan</a></td>");
		pw.print("</tr><tr>");
		pw.print("<td><a href='RemoveProduct'>Remove PPV Product</a></td>");
		pw.print("<td><a href='RemoveProduct'>Remove Cable Plan</a></td>");
		pw.print("</tr><tr>");
		pw.print("<td><a href='RemoveProduct'>Remove Internet Plan</a></td>");
		pw.print("<td></td>");
		pw.print("</tr></table>");
	}
}
