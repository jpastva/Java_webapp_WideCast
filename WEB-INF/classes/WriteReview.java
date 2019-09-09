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

@WebServlet("/WriteReview")

public class WriteReview extends HttpServlet {
  HashMap<String, ArrayList<Review>> reviews = new HashMap<String, ArrayList<Review>>();
	MongoDBDataStoreUtilities s = null;   

  public void init() {              
    s  = new MongoDBDataStoreUtilities();
  }

  protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		PrintWriter pw = response.getWriter();
		displayReview(request, response, pw, false);
	}
  
  protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    response.setContentType("text/html");
		PrintWriter pw = response.getWriter();
		Utilities utility = new Utilities(request, pw);
		String username = request.getParameter("username");
		
    if(!utility.isLoggedin()) {
			HttpSession session = request.getSession(true);				
			session.setAttribute("login_msg", "Please log in to write a review.");
			response.sendRedirect("Login");
			return;
		}

    displayReview(request, response, pw, true);				
  }	
    	
  protected void displayReview(HttpServletRequest request, HttpServletResponse response, PrintWriter pw, boolean error)
  throws ServletException, IOException {
    Utilities utility = new Utilities(request, pw);
    String productName = request.getParameter("name");   
    String productType = request.getParameter("type");
    String productDist = request.getParameter("distributor");
    double productPrice = Double.parseDouble(request.getParameter("price"));
    String productDisplayName = request.getParameter("displayName");
    
    utility.printHtml("Header.html");
    utility.printHtml("LeftNavigationBar.html");
	pw.print("<form name ='WriteReview' action='SubmitReview' method='post'>");
	pw.print("<div id='content'><div class='post'><h2 class='title meta'>");
	pw.print("<a style='font-size: 24px;color: red;'>Write a review:</a>");
	pw.print("</h2><div class='entry'>");		
    pw.println("<form action='WriteReview'>");
  	pw.println("<h2>Please review your purchase.</h2>");
  	pw.println("<table>");
  	pw.println("<tr>");
  	pw.println("<td>Product: </td>");
    pw.println("<td>" + productDisplayName + "</td>");
  	pw.println("<input type='hidden' name='productName' value='" + productDisplayName + "'>");
  	pw.println("</tr><tr>");
  	pw.println("<td>Product Category: </td>");
  	pw.println("<td><input type='text' name='productType' value='" + productType + "'></td>");
  	pw.println("</tr><tr>");
  	pw.println("<td>Product Price: </td>");
    pw.println("<td><input type='text' name='productPrice' value='" + productPrice + "'></td>");
  	pw.println("</tr><tr>");
  	pw.println("<td>Distributor: </td>");
    pw.println("<td><input type='text' name='distributor' value='" + productDist + "'></td>");
  	pw.println("</tr><tr>");
    pw.println("<td>Customer Zip Code: </td>");
  	pw.println("<td><input type='text' name='customerZip' class='input'></td>");
  	pw.println("</tr><tr>");
  	pw.println("<td>Customer City: </td>");
  	pw.println("<td><input type='text' name='customerCity' class='input'></td>");
  	pw.println("</tr><tr>");
  	pw.println("<td>Customer State: </td>");
  	pw.println("<td><input type='text' name='customerState' class='input'></td>");
  	pw.println("</tr><tr>");  		
  	pw.println("<td>User ID: </td>");
  	pw.println("<td><input type='text' name='userId' class='input'></td>");
  	pw.println("</tr><tr>");
  	pw.println("<td>User Age: </td>");
  	pw.println("<td><input type='text' name='userAge' class='input'></td>");
  	pw.println("</tr><tr>");
  	pw.println("<td>User Gender: </td>");
  	pw.println("<td><select name='userGender' class='input'><option value='Male'>Male</option><option value='Female'>Female</option></select>");
  	pw.println("</td>");
  	pw.println("</tr><tr>");
  	pw.println("<td>User Occupation: </td>");
  	pw.println("<td><input type='text' name='userOccupation'></td>");
  	pw.println("</tr><tr>");
  	pw.println("<td>Review Rating (5 - highest): </td>");
  	pw.println("<td><select name='rating' class='input'><option value='1'>1</option><option value='2'>2</option><option value='3'>3</option>"
      +"<option value='4'>4</option><option value='5'>5</option></select></td>");
  	pw.println("</tr><tr>");
  	pw.println("<td>Review Date: </td>");
  	pw.println("<td><input type='text' name='reviewDate'></td>");
  	pw.println("</tr><tr>");
  	pw.println("<td>Review Text:</td>");
  	pw.println("<td><textarea rows='5' cols='50' name='reviewText'></textarea></td>");
  	pw.println("</tr>");
    pw.print("<tr><td></td><td></td><td><input type='submit' name='submit' value='Submit' class='btnbuy'></td>");			
  	pw.println("</table>");
  	pw.println("</form>");
    pw.print("</form></div></div></div>");		
    utility.printHtml("Footer.html");
	}  
}