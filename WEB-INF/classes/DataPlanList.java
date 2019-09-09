import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/DataPlanList")

public class DataPlanList extends HttpServlet {

	/* DataPlanList page displays all the data plans and their information */

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		PrintWriter pw = response.getWriter();

	/* Checks the data plan type */

		String pageTitle = null;
		String plan = request.getParameter("id");
		HashMap<String, DataPlan> hm = new HashMap<String, DataPlan>();

		if (plan == null)	
		{
			hm.putAll(SaxParserDataStore.dataPlans);
			pageTitle = "";
		} 
		else 
		{
			if(plan.equals("speed")) 
			{	
				for(Map.Entry<String, DataPlan> entry : SaxParserDataStore.dataPlans.entrySet())
				{
				  if(entry.getValue().getId().equals("speed"))
				  {
					 hm.put(entry.getValue().getId(),entry.getValue());
				  }
				}
				pageTitle ="SpeedLane";
			} 
			else if (plan.equals("light"))
			{
				for(Map.Entry<String, DataPlan> entry : SaxParserDataStore.dataPlans.entrySet())
				{
				  if(entry.getValue().getId().equals("light"))
				  {
					 hm.put(entry.getValue().getId(),entry.getValue());
				  }
				}
				pageTitle = "LightLane";
			} 
	    }

		/* Header, Left Navigation Bar are Printed.

		All the plan information is dispalyed in the Content section

		and then Footer is printed*/

		Utilities utility = new Utilities(request, pw);
		utility.printHtml("Header.html");
		utility.printHtml("LeftNavigationBar.html");
		pw.print("<div id='content'><div class='post'><h2 class='title meta'>");
		pw.print("<a style='font-size: 24px;'>" + pageTitle + " Internet Plans</a>");
		pw.print("</h2><div class='entry'><table id='bestseller'>");
		int i = 1;
		int size = hm.size();
		for (Map.Entry<String, DataPlan> entry : hm.entrySet()) {
			DataPlan data = entry.getValue();
			if (i % 3 == 1)
				pw.print("<tr>");
			pw.print("<td><div id='shop_item'>");
			pw.print("<h3>" + data.getName() + "</h3>");
			pw.print("<strong>$" + data.getPrice() + "</strong><ul>");
			pw.print("<li id='item'><img src='images/dataplans/"
					+ data.getImage() + "' alt='' /></li>");
			pw.print("<li><form method='post' action='Cart'>" +
					"<input type='hidden' name='name' value='" + entry.getKey() + "'>" +
					"<input type='hidden' name='type' value='dataPlans'>" +
					"<input type='hidden' name='displayName' value='" + data.getName() + "'>" +
					"<input type='submit' class='btnbuy' value='Sign Up Now'></form></li>");
			pw.print("<li><form method='post' action='WriteReview'>"+"<input type='hidden' name='name' value='"+entry.getKey()+"'>"+
					"<input type='hidden' name='type' value='dataPlans'>"+
					"<input type='hidden' name='displayName' value='" + data.getName() + "'>" +
					"<input type='hidden' name='distributor' value='"+data.getDistributor()+"'>" +
					"<input type='hidden' name='price' value='"+data.getPrice()+"'>" +
				    "<input type='submit' value='WriteReview' class='btnreview'></form></li>");
			pw.print("<li><form method='post' action='ViewReview'>"+"<input type='hidden' name='name' value='"+entry.getKey()+"'>"+
					"<input type='hidden' name='type' value='dataPlans'>"+
					"<input type='hidden' name='displayName' value='" + data.getName() + "'>" +
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
