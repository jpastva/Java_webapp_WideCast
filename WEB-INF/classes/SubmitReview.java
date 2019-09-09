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

@WebServlet("/SubmitReview")

public class SubmitReview extends HttpServlet {
	Review review;
	HashMap<String, ArrayList<Review>> reviews = new HashMap<String, ArrayList<Review>>();
		
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(true);
        response.setContentType("text/html");
        PrintWriter pw = response.getWriter();
		Utilities utility = new Utilities(request, pw);
	   
		utility.printHtml("Header.html");
		utility.printHtml("LeftNavigationBar.html");
		pw.print("<form name ='SubmitReview' action='SubmitReview' method='post'>");
		pw.print("<div id='content'><div class='post'><h2 class='title meta'>");
		pw.print("<a style='font-size: 24px;color: red;'>Review Status:</a>");
		pw.print("</h2><div class='entry'>");		
		String productName = request.getParameter("productName");
		String productType = request.getParameter("productType");
		String productPrice = request.getParameter("productPrice");
		String distributor = request.getParameter("distributor");
		String customerZip = request.getParameter("customerZip");
		String customerCity = request.getParameter("customerCity");
		String customerState = request.getParameter("customerState");
		String userId = request.getParameter("userId");
		String userAge = request.getParameter("userAge");
		String userGender = request.getParameter("userGender");
		String userOccupation = request.getParameter("userOccupation");
		String rating = request.getParameter("rating");
		String reviewDate = request.getParameter("reviewDate");
		String reviewText = request.getParameter("reviewText");

		// create Review object from fields submitted
		Review review = new Review(productName, productType, String.valueOf(productPrice), distributor, customerZip, customerCity,
			customerState, userId, userAge, userGender, userOccupation, rating,
			reviewDate, reviewText);

		ArrayList<Review> arr = new ArrayList<Review>();
        reviews.put(productName, arr);		
		// add Review to DB
		String saved = "";
		saved = MongoDBDataStoreUtilities.insertReview(review);
		if (saved.equals("Successful")) {
			pw.print("<br>Your review for the " + productName + " has been submitted.");
		}
		else
			pw.print("<br>Mongo DB is not up and running.");
	   
		pw.print("</form></div></div></div>");		
		utility.printHtml("Footer.html");
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		PrintWriter pw = response.getWriter();
		Utilities utility = new Utilities(request, pw);
	}
}
