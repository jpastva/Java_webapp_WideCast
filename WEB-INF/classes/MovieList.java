import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/MovieList")

public class MovieList extends HttpServlet {

	/* Movie page displays all the movies and their information */

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		PrintWriter pw = response.getWriter();

	/* Checks the Movie genre */

		String pageTitle = null;
		String genre = request.getParameter("genre");
		HashMap<String, Movie> hm = new HashMap<String, Movie>();

		if (genre == null)	
		{
			hm.putAll(SaxParserDataStore.movies);
			pageTitle = "";
		} 
		else 
		{
			if(genre.equals("Children")) 
			{	
				for(Map.Entry<String, Movie> entry : SaxParserDataStore.movies.entrySet())
				{
				  if(entry.getValue().getGenre().equals("Children"))
				  {
					 hm.put(entry.getValue().getId(),entry.getValue());
				  }
				}
				pageTitle ="Children's";
			} 
			else if (genre.equals("Drama"))
			{
				for(Map.Entry<String, Movie> entry : SaxParserDataStore.movies.entrySet())
				{
				  if(entry.getValue().getGenre().equals("Drama"))
				  {
					 hm.put(entry.getValue().getId(),entry.getValue());
				  }
				}
				pageTitle = "Drama";
			} 
			else if (genre.equals("Comedy")) 
			{
				for(Map.Entry<String, Movie> entry : SaxParserDataStore.movies.entrySet())
				{
				  if(entry.getValue().getGenre().equals("Comedy"))
				 {
					 hm.put(entry.getValue().getId(),entry.getValue());
				 }
				}	
				pageTitle = "Comedy";
			}
			else if (genre.equals("Horror")) 
			{
				for(Map.Entry<String, Movie> entry : SaxParserDataStore.movies.entrySet())
				{
				  if(entry.getValue().getGenre().equals("Horror"))
				 {
					 hm.put(entry.getValue().getId(),entry.getValue());
				 }
				}	
				pageTitle = "Horror";
			}
			else if (genre.equals("Action")) 
			{
				for(Map.Entry<String, Movie> entry : SaxParserDataStore.movies.entrySet())
				{
				  if(entry.getValue().getGenre().equals("Action"))
				 {
					 hm.put(entry.getValue().getId(),entry.getValue());
				 }
				}	
				pageTitle = "Action";
			}
	    }

		/* Header, Left Navigation Bar are Printed.

		All the movie information is dispalyed in the Content section

		and then Footer is printed*/

		Utilities utility = new Utilities(request, pw);
		utility.printHtml("Header.html");
		utility.printHtml("LeftNavigationBar.html");
		pw.print("<div id='content'><div class='post'><h2 class='title meta'>");
		pw.print("<a style='font-size: 24px;'>" + pageTitle + " Movies</a>");
		pw.print("</h2><div class='entry'><table id='bestseller'>");
		int i = 1;
		int size = hm.size();
		for (Map.Entry<String, Movie> entry : hm.entrySet()) {
			Movie movie = entry.getValue();
			if (i % 3 == 1)
				pw.print("<tr>");
			pw.print("<td><div id='shop_item'>");
			pw.print("<h3>" + movie.getName() + "</h3>");
			pw.print("<strong>$" + movie.getPrice() + "</strong><ul>");
			pw.print("<li id='item'><img src='images/movies/"
					+ movie.getImage() + "' alt='' /></li>");
			pw.print("<li><form method='post' action='Cart'>" +
					"<input type='hidden' name='name' value='" + entry.getKey() + "'>" +
					"<input type='hidden' name='type' value='movies'>" +
					"<input type='hidden' name='displayName' value='" + movie.getName() + "'>" +
					"<input type='hidden' name='genre' value='" + genre + "'>" +
					"<input type='submit' class='btnbuy' value='Buy Now'></form></li>");
			pw.print("<li><form method='post' action='WriteReview'>"+"<input type='hidden' name='name' value='"+entry.getKey()+"'>"+
					"<input type='hidden' name='type' value='movies'>"+
					"<input type='hidden' name='displayName' value='" + movie.getName() + "'>" +
					"<input type='hidden' name='distributor' value='"+movie.getDistributor()+"'>" +
					"<input type='hidden' name='price' value='"+movie.getPrice()+"'>" +
					"<input type='hidden' name='genre' value='" + genre + "'>" +
				    "<input type='submit' value='WriteReview' class='btnreview'></form></li>");
			pw.print("<li><form method='post' action='ViewReview'>"+"<input type='hidden' name='name' value='"+entry.getKey()+"'>"+
					"<input type='hidden' name='type' value='movies'>"+
					"<input type='hidden' name='displayName' value='" + movie.getName() + "'>" +
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
