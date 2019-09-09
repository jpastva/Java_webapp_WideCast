import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.ArrayList;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.util.Iterator;
import java.time.LocalDate;
import java.time.DayOfWeek;

@WebServlet("/Utilities")

/* 
	Utilities class contains class variables of type HttpServletRequest, PrintWriter, String and HttpSession.

	Utilities class has a constructor with  HttpServletRequest, PrintWriter variables.
	  
*/

public class Utilities extends HttpServlet{
	HttpServletRequest req;
	PrintWriter pw;
	String url;
	HttpSession session;
	static ArrayList<Integer> itemIds = new ArrayList<>();
	static int count = 1;
	
	public Utilities(HttpServletRequest req, PrintWriter pw) {
		this.req = req;
		this.pw = pw;
		this.url = this.getFullURL();
		this.session = req.getSession(true);
	}

	/*  Printhtml Function gets the html file name as function Argument, 
		If the html file name is Header.html then It gets Username from session variables.
		Account, Cart Information and Logout Options are Displayed*/

	public void printHtml(String file) {
		String result = HtmlToString(file);
		//to print the right navigation in header of username cart and logout etc
		if (file == "Header.html") {
				result = result + "<div id='menu' style='float: right;'><ul>";
		if (session.getAttribute("username") != null) {
			String username = session.getAttribute("username").toString();
			String usertype = session.getAttribute("usertype").toString();
			username = Character.toUpperCase(username.charAt(0)) + username.substring(1);
			if (usertype.equals("customer")) {
				result = result + "<li><a><span class='glyphicon'>Hello " + username + "!</span></a></li>"
					+ "<li><a href='ViewOrder'><span class='glyphicon'>ViewOrder</span></a></li>"
					+ "<li><a href='Account'><span class='glyphicon'>Account</span></a></li>"
					+ "<li><a href='Cart'><span class='glyphicon'>Cart(" + CartCount() + ")</span></a></li>"
					+ "<li><a href='Logout'><span class='glyphicon'>Logout</span></a></li>";
			}
			else if (usertype.equals("support")) {
				result = result + "<li><a><span class='glyphicon'>Hello " + username + "!</span></a></li>" 
					+ "<li><a href='OrdersList'><span class='glyphicon'>Orders</span></a></li>" 
					+ "<li><a href='CustomerList'><span class='glyphicon'>Customers</span></a></li>"
					+ "<li><a href='TicketsList'><span class='glyphicon'>Tickets</span></a></li>"
					+ "<li><a href='Logout'><span class='glyphicon'>Logout</span></a></li>";
			}
			else if (usertype.equals("manager")) {
				result = result + "<li><a><span class='glyphicon'>Hello " + username + "!</span></a></li>" 
					+ "<li><a href='CustomerList'><span class='glyphicon'>Customers</span></a></li>"
					+ "<li><a href='Inventory'><span class= 'glyphicon'>Inventory</span></a></li>"
					+ "<li><a href='TicketsList'><span class='glyphicon'>Tickets</span></a></li>"
					+ "<li><a href='OrdersList'><span class='glyphicon'>Orders</span></a></li>" 
					+ "<li><a href='Logout'><span class='glyphicon'>Logout</span></a></li>";
			}
			else if (usertype.equals("technician")) {
				result = result + "<li><a><span class='glyphicon'>Hello " + username + "!</span></a></li>" 
					+ "<li><a href='OrdersList'><span class='glyphicon'>Orders</span></a></li>"
					+ "<li><a href='TicketsList'><span class='glyphicon'>Tickets</span></a></li>" 
					+ "<li><a href='Logout'><span class='glyphicon'>Logout</span></a></li>";
			}
		}
		else
			result = result + "<li><a href='ViewOrder'><span class='glyphicon'>View Order</span></a></li>"
				+ "<li><a href='Cart'><span class='glyphicon'>Cart(" + CartCount() + ")</span></a></li>"
				+ "<li><a href='Login'><span class='glyphicon'>Login</span></a></li>";
		result = result + "</ul></div></div><div id='page'>";
		pw.print(result);
		} else
				pw.print(result);
	}
	

	/*  getFullURL Function - Reconstructs the URL user request  */

	public String getFullURL() {
		String scheme = req.getScheme();
		String serverName = req.getServerName();
		int serverPort = req.getServerPort();
		String contextPath = req.getContextPath();
		StringBuffer url = new StringBuffer();
		url.append(scheme).append("://").append(serverName);

		if ((serverPort != 80) && (serverPort != 443)) {
			url.append(":").append(serverPort);
		}
		url.append(contextPath);
		url.append("/");
		return url.toString();
	}

	/*  HtmlToString - Gets the Html file and Converts into String and returns the String.*/
	public String HtmlToString(String file) {
		String result = null;

		try {
			String webPage = url + file;
			URL url = new URL(webPage);
			URLConnection urlConnection = url.openConnection();
			InputStream is = urlConnection.getInputStream();
			InputStreamReader isr = new InputStreamReader(is);

			int numCharsRead;
			char[] charArray = new char[1024];
			StringBuffer sb = new StringBuffer();
			while ((numCharsRead = isr.read(charArray)) > 0) {
				sb.append(charArray, 0, numCharsRead);
			}
			result = sb.toString();
		} 
		catch (Exception e) {
		}
		return result;
	} 

	/*  logout Function removes the username , usertype attributes from the session variable*/

	public void logout(){
		session.removeAttribute("username");
		session.removeAttribute("usertype");
	}
	
	/*  logout Function checks whether the user is loggedIn or Not*/

	public boolean isLoggedin(){
		if (session.getAttribute("username") == null)
			return false;
		return true;
	}

	/*  username Function returns the username from the session variable.*/
	
	public String username(){
		if (session.getAttribute("username") != null)
			return session.getAttribute("username").toString();
		return null;
	}
	
	/*  usertype Function returns the usertype from the session variable.*/
	public String usertype(){
		if (session.getAttribute("usertype") != null)
			return session.getAttribute("usertype").toString();
		return null;
	}
		
	/*  getCustomerOrders Function gets  the Orders for the user*/
	public ArrayList<OrderItem> getCustomerOrders() {
		ArrayList<OrderItem> order = new ArrayList<OrderItem>(); 
		if(OrdersHashMap.orders.containsKey(username()))
			order = OrdersHashMap.orders.get(username());
		return order;
	}

	public boolean removeCustomerOrder (String username, String orderName) {
		boolean success = false;
		ArrayList<OrderItem> order = new ArrayList<OrderItem>(); 
		order = OrdersHashMap.orders.get(username());
		Iterator iter = order.iterator();
		int i = 1;
		while (iter.hasNext() && i > 0) {
			OrderItem item = (OrderItem)iter.next();
			if (item.getName().equals(orderName)) {
				iter.remove();
				i--;
			}
		}

		ArrayList<OrderItem> replaced = OrdersHashMap.orders.replace(username, order);
		if (replaced != null) {
			success = true; 
		}
		return success;
	}

	/*  CartCount Function gets  the size of User Orders*/
	public int CartCount(){
		if(isLoggedin())
		return getCustomerOrders().size();
		return 0;
	}
	
	public int getItemId() {
		if (itemIds.contains(count)) {
			count++;
		}
		itemIds.add(count);
		return count;
	}

	/* StoreProduct Function stores the Purchased product in Orders HashMap according to the User Names.*/
	public void storeProduct(String name, String type) {
		LocalDate today = LocalDate.now();

		if(!OrdersHashMap.orders.containsKey(username())) {	
			ArrayList<OrderItem> arr = new ArrayList<OrderItem>();
			OrdersHashMap.orders.put(username(), arr);
		}

		ArrayList<OrderItem> orderItems = OrdersHashMap.orders.get(username());
		if(type.equals("movies")) {
			Movie movie;
			movie = SaxParserDataStore.movies.get(name);
			OrderItem orderitem = new OrderItem(getItemId(),movie.getName(),type,movie.getPrice(),movie.getImage(),movie.getDistributor(),movie.getDate());
			orderItems.add(orderitem);
		}
		if(type.equals("events")) {
			Event event = null;
			event = SaxParserDataStore.events.get(name);
			OrderItem orderitem = new OrderItem(getItemId(),event.getName(),type,event.getPrice(),event.getImage(),event.getDistributor(),event.getDate());
			orderItems.add(orderitem);
		}
		if(type.equals("tvPlans")) {
			TVPlan tv = null;
			tv = SaxParserDataStore.tvPlans.get(name);
			OrderItem orderitem = new OrderItem(getItemId(),tv.getName(),type,tv.getPrice(),tv.getImage(),tv.getDistributor(),today);
			orderItems.add(orderitem);
		}
		if(type.equals("dataPlans")) {
			DataPlan data = null;
			data = SaxParserDataStore.dataPlans.get(name);
			OrderItem orderitem = new OrderItem(getItemId(),data.getName(),type,data.getPrice(),data.getImage(),data.getDistributor(),today);
			orderItems.add(orderitem);
		}
	}
	
	public LocalDate getDeliveryDate() {
		int busDays = 14;
		LocalDate arrival = LocalDate.now();
    	int addDays = 0;
    	
    	while (addDays < busDays) {
        	arrival = arrival.plusDays(1);
        	if (!(arrival.getDayOfWeek() == DayOfWeek.SATURDAY || arrival.getDayOfWeek() == DayOfWeek.SUNDAY)) {
            	++addDays;
        	}
    	}

    	return arrival;
	}
	
	/* getMovies function returns the Hashmap with all Movies in the store.*/

	public HashMap<String, Movie> getMovies() {
			HashMap<String, Movie> hm = new HashMap<String, Movie>();
			hm.putAll(SaxParserDataStore.movies);
			return hm;
	}
	
	/* getEvents function returns the  Hashmap with all Events in the store.*/

	public HashMap<String, Event> getEvents() {
			HashMap<String, Event> hm = new HashMap<String, Event>();
			hm.putAll(SaxParserDataStore.events);
			return hm;
	}
	
	/* getTVPlans function returns the Hashmap with all TVPlans in the store.*/

	public HashMap<String, TVPlan> getTVPlans() {
			HashMap<String, TVPlan> hm = new HashMap<String, TVPlan>();
			hm.putAll(SaxParserDataStore.tvPlans);
			return hm;
	}

	/* getDataPlans function returns the Hashmap with all DataPlans in the store.*/

	public HashMap<String, DataPlan> getDataPlans() {
			HashMap<String, DataPlan> hm = new HashMap<String, DataPlan>();
			hm.putAll(SaxParserDataStore.dataPlans);
			return hm;
	}
	
	/* getProducts Functions returns the Arraylist of phones in the store.*/

	public ArrayList<String> getProductsMovies(){
		ArrayList<String> ar = new ArrayList<String>();
		for(Map.Entry<String, Movie> entry : getMovies().entrySet()){			
			ar.add(entry.getValue().getName());
		}
		return ar;
	}
	
	/* getProducts Functions returns the Arraylist of tvs in the store.*/

	public ArrayList<String> getProductsEvents(){		
		ArrayList<String> ar = new ArrayList<String>();
		for(Map.Entry<String, Event> entry : getEvents().entrySet()){
			ar.add(entry.getValue().getName());
		}
		return ar;
	}
	
	/* getProducts Functions returns the Arraylist of Tablets in the store.*/

	public ArrayList<String> getProductsTVPlans(){		
		ArrayList<String> ar = new ArrayList<String>();
		for(Map.Entry<String, TVPlan> entry : getTVPlans().entrySet()){
			ar.add(entry.getValue().getName());
		}
		return ar;
	}
	/* getLaptops Functions returns the Arraylist of Laptops in the store.*/

	public ArrayList<String> getProductsDataPlans(){		
		ArrayList<String> ar = new ArrayList<String>();
		for(Map.Entry<String, DataPlan> entry : getDataPlans().entrySet()){
			ar.add(entry.getValue().getName());
		}
		return ar;
	}
}
