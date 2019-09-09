import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/TVPlanList")

public class TVPlanList extends HttpServlet {

	/* TVPlanList page displays all the TV plans and their information */

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		PrintWriter pw = response.getWriter();

	/* Checks the TV plan type */

		String pageTitle = null;
		String plan = request.getParameter("id");
		HashMap<String, TVPlan> hm = new HashMap<String, TVPlan>();

		if (plan == null)	
		{
			hm.putAll(SaxParserDataStore.tvPlans);
			pageTitle = "";
		} 
		else 
		{
			if(plan.equals("basic")) 
			{	
				for(Map.Entry<String, TVPlan> entry : SaxParserDataStore.tvPlans.entrySet())
				{
				  if(entry.getValue().getId().equals("basic"))
				  {
					 hm.put(entry.getValue().getId(),entry.getValue());
				  }
				}
				pageTitle ="Basic";
			} 
			else if (plan.equals("plus"))
			{
				for(Map.Entry<String, TVPlan> entry : SaxParserDataStore.tvPlans.entrySet())
				{
				  if(entry.getValue().getId().equals("plus"))
				  {
					 hm.put(entry.getValue().getId(),entry.getValue());
				  }
				}
				pageTitle = "Basic Plus";
			} 
			else if (plan.equals("ultimate")) 
			{
				for(Map.Entry<String, TVPlan> entry : SaxParserDataStore.tvPlans.entrySet())
				{
				  if(entry.getValue().getId().equals("ultimate"))
				 {
					 hm.put(entry.getValue().getId(),entry.getValue());
				 }
				}	
				pageTitle = "Ultimate";
			}
	    }

		/* Header, Left Navigation Bar are Printed.

		All the plan information is dispalyed in the Content section

		and then Footer is printed*/

		Utilities utility = new Utilities(request, pw);
		utility.printHtml("Header.html");
		utility.printHtml("LeftNavigationBar.html");
		pw.print("<div id='content'><div class='post'><h2 class='title meta'>");
		pw.print("<a style='font-size: 24px;'>" + pageTitle + " TV Plans</a>");
		pw.print("</h2><div class='entry'><table id='bestseller'>");
		int i = 1;
		int size = hm.size();
		for (Map.Entry<String, TVPlan> entry : hm.entrySet()) {
			TVPlan tv = entry.getValue();
			if (i % 3 == 1)
				pw.print("<tr>");
			pw.print("<td><div id='shop_item'>");
			pw.print("<h3>" + tv.getName() + "</h3>");
			pw.print("<strong>$" + tv.getPrice() + "</strong><ul>");
			pw.print("<li id='item'><img src='images/tvplans/"
					+ tv.getImage() + "' alt='' /></li>");
			pw.print("<li><form method='post' action='Cart'>" +
					"<input type='hidden' name='name' value='" + entry.getKey() + "'>" +
					"<input type='hidden' name='type' value='tvPlans'>" +
					"<input type='hidden' name='displayName' value='" + tv.getName() + "'>" +
					"<input type='submit' class='btnbuy' value='Sign Up Now'></form></li>");
			pw.print("<li><form method='post' action='WriteReview'>"+"<input type='hidden' name='name' value='"+entry.getKey()+"'>"+
					"<input type='hidden' name='type' value='tvPlans'>"+
					"<input type='hidden' name='distributor' value='"+tv.getDistributor()+"'>" +
					"<input type='hidden' name='price' value='"+tv.getPrice()+"'>" +
					"<input type='hidden' name='displayName' value='" + tv.getName() + "'>" +
				    "<input type='submit' value='WriteReview' class='btnreview'></form></li>");
			pw.print("<li><form method='post' action='ViewReview'>"+"<input type='hidden' name='name' value='"+entry.getKey()+"'>"+
					"<input type='hidden' name='type' value='tvPlans'>"+
					"<input type='hidden' name='displayName' value='" + tv.getName() + "'>" +
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
