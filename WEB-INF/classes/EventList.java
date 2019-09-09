import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/EventList")

public class EventList extends HttpServlet {

	/* Event page displays all the events and their information */

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		PrintWriter pw = response.getWriter();

	/* Checks the Event genre */

		String pageTitle = null;
		String genre = request.getParameter("genre");
		HashMap<String, Event> hm = new HashMap<String, Event>();

		if (genre == null)	
		{
			hm.putAll(SaxParserDataStore.events);
			pageTitle = "";
		} 
		else 
		{
			if(genre.equals("Baseball")) 
			{	
				for(Map.Entry<String, Event> entry : SaxParserDataStore.events.entrySet())
				{
				  if(entry.getValue().getGenre().equals("Baseball"))
				  {
					 hm.put(entry.getValue().getId(),entry.getValue());
				  }
				}
				pageTitle ="Baseball";
			} 
			else if (genre.equals("Boxing"))
			{
				for(Map.Entry<String, Event> entry : SaxParserDataStore.events.entrySet())
				{
				  if(entry.getValue().getGenre().equals("Boxing"))
				  {
					 hm.put(entry.getValue().getId(),entry.getValue());
				  }
				}
				pageTitle = "Boxing";
			} 
			else if (genre.equals("Wrestling")) 
			{
				for(Map.Entry<String, Event> entry : SaxParserDataStore.events.entrySet())
				{
				  if(entry.getValue().getGenre().equals("Wrestling"))
				 {
					 hm.put(entry.getValue().getId(),entry.getValue());
				 }
				}	
				pageTitle = "Wrestling";
			}
	    }

		/* Header, Left Navigation Bar are Printed.

		All the event information is dispalyed in the Content section

		and then Footer is printed*/

		Utilities utility = new Utilities(request, pw);
		utility.printHtml("Header.html");
		utility.printHtml("LeftNavigationBar.html");
		pw.print("<div id='content'><div class='post'><h2 class='title meta'>");
		pw.print("<a style='font-size: 24px;'>" + pageTitle + " Pay Per View Events</a>");
		pw.print("</h2><div class='entry'><table id='bestseller'>");
		int i = 1;
		int size = hm.size();
		for (Map.Entry<String, Event> entry : hm.entrySet()) {
			Event event = entry.getValue();
			if (i % 3 == 1)
				pw.print("<tr>");
			pw.print("<td><div id='shop_item'>");
			pw.print("<h3>" + event.getName() + "</h3>");
			pw.print("<strong>$" + event.getPrice() + "</strong><ul>");
			pw.print("<li id='item'><img src='images/events/"
					+ event.getImage() + "' alt='' /></li>");
			pw.print("<li><form method='post' action='Cart'>" +
					"<input type='hidden' name='name' value='" + entry.getKey() + "'>" +
					"<input type='hidden' name='type' value='events'>" +
					"<input type='hidden' name='displayName' value='" + event.getName() + "'>" +
					"<input type='hidden' name='genre' value='" + genre + "'>" +
					"<input type='submit' class='btnbuy' value='Buy Now'></form></li>");
			pw.print("<li><form method='post' action='WriteReview'>"+"<input type='hidden' name='name' value='"+entry.getKey()+"'>"+
					"<input type='hidden' name='type' value='events'>"+
					"<input type='hidden' name='displayName' value='" + event.getName() + "'>" +
					"<input type='hidden' name='distributor' value='"+event.getDistributor()+"'>" +
					"<input type='hidden' name='price' value='"+event.getPrice()+"'>" +
					"<input type='hidden' name='genre' value='" + genre + "'>" +
				    "<input type='submit' value='WriteReview' class='btnreview'></form></li>");
			pw.print("<li><form method='post' action='ViewReview'>"+"<input type='hidden' name='name' value='"+entry.getKey()+"'>"+
					"<input type='hidden' name='type' value='events'>"+
					"<input type='hidden' name='displayName' value='" + event.getName() + "'>" +
					"<input type='hidden' name='genre' value='" + genre + "'>"+
				    "<input type='submit' value='ViewReview' class='btnreview'></form></li>");
			pw.print("</ul></div></td>");
			if (i % 3 == 0 || i == size)
				pw.print("</tr>");
			i++;
		}
		pw.print("</table></div></div></div>");
		utility.printHtml("Footer.html");
	}
}
