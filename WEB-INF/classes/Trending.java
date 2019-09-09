import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

@WebServlet("/Trending")

public class Trending extends HttpServlet {

	ArrayList<MostSold> mostSold = new ArrayList<>();
    ArrayList<MostSoldZip> mostSoldZip = new ArrayList<>();
	ArrayList<BestRating> bestRated = new ArrayList<>();

	protected void doGet(HttpServletRequest request,
		HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		PrintWriter pw = response.getWriter();
		mostSold = MongoDBDataStoreUtilities.mostSoldProducts();
		mostSoldZip = MongoDBDataStoreUtilities.mostSoldZip();
		bestRated = MongoDBDataStoreUtilities.topProducts();

		String name = "Trending";

		Utilities utility = new Utilities(request, pw);
		utility.printHtml("Header.html");
		utility.printHtml("LeftNavigationBar.html");
		pw.print("<div id='content'><div class='post'><h2 class='title meta'>");
		pw.print("<a style='font-size: 24px;'>Highest Rated Products</a>");
		pw.print("</h2><div class='entry'><table id='bestseller'>");
		Iterator itr2 = bestRated.iterator();
        while(itr2.hasNext()) {
	        BestRating best = (BestRating)itr2.next();
	 		pw.print("<tr>");
			pw.print("<td>");
			pw.print(best.getProductname());
			pw.print("</td>");
			pw.print("<td>");
			pw.print(best.getRating());
			pw.print("</td>");
			pw.print("</tr>");
        }
		pw.print("</table></div></div></div>");	
		
		pw.print("<div id='content'><div class='post'><h2 class='title meta'>");
		pw.print("<a style='font-size: 24px;'>Zip Codes Where Most Products Sold</a>");
		pw.print("</h2><div class='entry'><table id='bestseller'>");
		Iterator itr1 = mostSoldZip.iterator();
        	while(itr1.hasNext()) {
        	MostSoldZip mostZip = (MostSoldZip)itr1.next();
	 		pw.print("<tr>");
			pw.println("<td border: 1px >");
			pw.println(mostZip.getZipcode());
			pw.println("</td>");
			pw.println("<td border: 1px >");
			pw.println(mostZip.getCount());
			pw.println("</td>");
			pw.println("</tr>");
        }
		pw.print("</table></div></div></div>");	

		pw.print("<div id='content'><div class='post'><h2 class='title meta'>");
		pw.print("<a style='font-size: 24px;'>Most Popular Products by Sales</a>");
		pw.print("</h2><div class='entry'><table id='bestseller'>");
		
        Iterator itr = mostSold.iterator();
        while(itr.hasNext()) {
        	MostSold most = (MostSold)itr.next();
	 		pw.println("<tr>");
			pw.println("<td border: 1px >");
			pw.println(most.getProductname());
			pw.println("</td>");
			pw.println("<td border: 1px >");
			pw.println(most.getCount());
			pw.println("</td>");
			pw.println("</tr>");
        }
		pw.print("</table></div></div></div>");
		
	//	pw.print("</table></div></div></div>");	
		
		utility.printHtml("Footer.html");
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
	}

}