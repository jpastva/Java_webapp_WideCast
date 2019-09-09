import java.sql.*;
import java.util.*;
import java.time.LocalDate;
import java.sql.Date;
                	
public class MySqlDataStoreUtilities {
	static Connection conn = null;

	public static void getConnection()
	{
		try
		{
		Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
		conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/widecastdb","root","root");							
		}
		catch(Exception e)
		{
		}
	}

	public static void deleteOrder(int orderId,int itemId)
	{
		try
		{
			getConnection();
			String deleteOrderQuery ="DELETE FROM customerorders WHERE OrderId=? AND itemId=?";
			PreparedStatement pst = conn.prepareStatement(deleteOrderQuery);
			pst.setInt(1,orderId);
			pst.setInt(2,itemId);
			pst.executeUpdate();
		}
		catch(Exception e)
		{	
		}
	}

	public static void insertOrder(int orderId, int itemId, String itemType, String userName, String orderName, double orderPrice,
		String userAddress, String creditCardNo, LocalDate date) {
		try
		{
			getConnection();
			String insertIntoCustomerOrderQuery = "INSERT INTO CustomerOrders(OrderId,itemId,itemType,userName,orderName,orderPrice,userAddress,creditCardNo,itemDate)"
			+ "VALUES (?,?,?,?,?,?,?,?,?);";	
				
			PreparedStatement pst = conn.prepareStatement(insertIntoCustomerOrderQuery);
			//set the parameter for each column and execute the prepared statement
			pst.setInt(1,orderId);
			pst.setInt(2,itemId);
			pst.setString(3,itemType);
			pst.setString(4,userName);
			pst.setString(5,orderName);
			pst.setDouble(6,orderPrice);
			pst.setString(7,userAddress);
			pst.setString(8,creditCardNo);
			pst.setDate(9,java.sql.Date.valueOf(date));
			pst.execute();
		}
		catch(Exception e)
		{
		}		
	}

	public static void updateOrder(int orderId, int itemId, String userName, String orderName, double orderPrice,
		String userAddress,String creditCardNo, LocalDate date) {
		try
		{
			getConnection();
			String updateCustomerOrder = "UPDATE CustomerOrders SET OrderId = ?, itemId = ?, userName = ?, orderName = ?,"
			+"orderPrice = ?, userAddress = ?, creditCardNo = ?, itemDate = ?"
			+"WHERE orderId = ? AND itemId = ?";
				
			PreparedStatement pst = conn.prepareStatement(updateCustomerOrder);
			//set the parameter for each column and execute the prepared statement
			pst.setInt(1,orderId);
			pst.setInt(2,itemId);
			pst.setString(3,userName);
			pst.setString(4,orderName);
			pst.setDouble(5,orderPrice);
			pst.setString(6,userAddress);
			pst.setString(7,creditCardNo);
			pst.setDate(8,java.sql.Date.valueOf(date));
			pst.setInt(9,orderId);
			pst.setInt(10,itemId);
			pst.executeUpdate();
			conn.close();
		}
		catch(Exception e)
		{
		}		
	}

	public static int getNewOrderId() {	
		int maxId = 0;
		
		try
		{					
			getConnection();
	        //select the table 
			String selectMaxIdQuery ="SELECT MAX(OrderId) from CustomerOrders";			
			PreparedStatement pst = conn.prepareStatement(selectMaxIdQuery);
			ResultSet rs = pst.executeQuery();	
			while(rs.next())
			{
				maxId = rs.getInt(1);	
			}					
		}
		catch(Exception e)
		{	
		}
		maxId++;
		return maxId;
	}

	public static boolean userExists(String username) {
		boolean exists = false;
		try
		{
			getConnection();
			String selectUserQuery = "SELECT * FROM Registration WHERE username=?";			
			PreparedStatement pst = conn.prepareStatement(selectUserQuery);
			pst.setString(1,username);
			ResultSet rs = pst.executeQuery();
			if(rs.next()) {
				exists = true;
			}
		}
		catch(Exception e)
		{	
		}
		return exists;
	}

	public static int getNewTicketId() {	
		int maxId = 0;
		
		try
		{					
			getConnection();
	        //select the table 
			String selectMaxIdQuery ="SELECT MAX(ticketId) from Ticket";			
			PreparedStatement pst = conn.prepareStatement(selectMaxIdQuery);
			ResultSet rs = pst.executeQuery();	
			while(rs.next())
			{
				maxId = rs.getInt(1);	
			}					
		}
		catch(Exception e)
		{	
		}
		maxId++;
		return maxId;
	}

	public static void insertTicket(int ticketId, String custUsername, String technician, String description, boolean activeStatus) {
		try
		{	
			getConnection();
			String insertIntoTicketQuery = "INSERT INTO Ticket(ticketId,custUsername,technician,description,activeStatus)"
			+ "VALUES (?,?,?,?,?);";	
					
			PreparedStatement pst = conn.prepareStatement(insertIntoTicketQuery);
			pst.setInt(1,ticketId);
			pst.setString(2,custUsername);
			pst.setString(3,technician);
			pst.setString(4,description);
			pst.setBoolean(5,activeStatus);
			pst.execute();
		}
		catch(Exception e)
		{	
		}	
	}

	public static void deleteTicket(int ticketId)
	{
		try
		{
			getConnection();
			String deleteTicketQuery ="DELETE FROM Ticket WHERE ticketId=?";
			PreparedStatement pst = conn.prepareStatement(deleteTicketQuery);
			pst.setInt(1,ticketId);
			pst.executeUpdate();
		}
		catch(Exception e)
		{	
		}
	}

	public static void updateTicket(int ticketId, String description, boolean activeStatus) {
		try
		{
			getConnection();
			String updateTicketQuery = "UPDATE Ticket SET description=?, activeStatus=? WHERE ticketId=?";
				
			PreparedStatement pst = conn.prepareStatement(updateTicketQuery);
			//set the parameter for each column and execute the prepared statement
			pst.setString(1,description);
			pst.setBoolean(2,activeStatus);
			pst.setInt(3,ticketId);
			pst.executeUpdate();
			conn.close();
		}
		catch(Exception e)
		{
		}		
	}

	public static Ticket getSpecTicket(int ticketId) {
		Ticket ticket = new Ticket();
		try
		{
			getConnection();
			Statement stmt = conn.createStatement();
			String selectSpecTicketQuery = "SELECT * FROM Ticket WHERE ticketId = ?";

			PreparedStatement pst = conn.prepareStatement(selectSpecTicketQuery);
			pst.setInt(1,ticketId);
			ResultSet rs = pst.executeQuery();
			while(rs.next())
			{
				ticket.setTicketId(rs.getInt("ticketId"));
				ticket.setCustUsername(rs.getString("custUsername"));
				ticket.setTechnician(rs.getString("technician"));
				ticket.setDescription(rs.getString("description"));
				ticket.setActiveStatus(rs.getBoolean("activeStatus"));
			}					
		}
		catch(Exception e)
		{	
		}
		return ticket;
	}

	public static HashMap<Integer, ArrayList<Ticket>> getTickets() {	
		HashMap<Integer, ArrayList<Ticket>> tickets = new HashMap<>();
			
		try
		{					
			getConnection();
	        //select the table 
			String selectTicketsQuery ="SELECT * FROM Ticket";			
			PreparedStatement pst = conn.prepareStatement(selectTicketsQuery);
			ResultSet rs = pst.executeQuery();	
			ArrayList<Ticket> ticketList = new ArrayList<Ticket>();
			while(rs.next())
			{
				if(!tickets.containsKey(rs.getInt("ticketId")))
				{	
					ArrayList<Ticket> arr = new ArrayList<Ticket>();
					tickets.put(rs.getInt("ticketId"), arr);
				}
				ArrayList<Ticket> listTicket = tickets.get(rs.getInt("ticketId"));		
				System.out.println("data is"+rs.getInt("ticketId")+tickets.get(rs.getInt("ticketId")));

				//add to ticket hashmap
				Ticket ticket = new Ticket(rs.getInt("ticketId"),rs.getString("custUsername"),rs.getString("technician"),rs.getString("description"),rs.getBoolean("activeStatus"));
				listTicket.add(ticket);	
			}					
		}
		catch(Exception e)
		{	
		}
		return tickets;
	}

	public static HashMap<Integer, ArrayList<OrderPayment>> selectOrder() {	
		HashMap<Integer, ArrayList<OrderPayment>> orderPayments = new HashMap<>();
			
		try
		{					
			getConnection();
	        //select the table 
			String selectOrderQuery ="SELECT * from CustomerOrders";			
			PreparedStatement pst = conn.prepareStatement(selectOrderQuery);
			ResultSet rs = pst.executeQuery();	
			ArrayList<OrderPayment> orderList = new ArrayList<OrderPayment>();
			while(rs.next())
			{
				if(!orderPayments.containsKey(rs.getInt("OrderId")))
				{	
					ArrayList<OrderPayment> arr = new ArrayList<OrderPayment>();
					orderPayments.put(rs.getInt("orderId"), arr);
				}
				ArrayList<OrderPayment> listOrderPayment = orderPayments.get(rs.getInt("OrderId"));		
				System.out.println("data is"+rs.getInt("OrderId")+orderPayments.get(rs.getInt("OrderId")));

				//add to orderpayment hashmap
				OrderPayment order = new OrderPayment(rs.getInt("OrderId"),rs.getInt("itemId"),rs.getString("itemType"),rs.getString("userName"),rs.getString("orderName"),
					rs.getDouble("orderPrice"),rs.getString("userAddress"),rs.getString("creditCardNo"),rs.getDate("itemDate").toLocalDate());
				listOrderPayment.add(order);	
			}					
		}
		catch(Exception e)
		{	
		}
		return orderPayments;
	}

	public static HashMap<Integer, ArrayList<OrderPayment>> selectSpecOrder(int orderId) {	
		HashMap<Integer, ArrayList<OrderPayment>> orderPayments = new HashMap<>();
			
		try
		{					
			getConnection();
	        //select the table 
			String selectOrderQuery ="SELECT * FROM CustomerOrders WHERE OrderId=?";			
			PreparedStatement pst = conn.prepareStatement(selectOrderQuery);
			pst.setInt(1,orderId);
			ResultSet rs = pst.executeQuery();	
			ArrayList<OrderPayment> orderList = new ArrayList<OrderPayment>();
			while(rs.next())
			{
				if(!orderPayments.containsKey(rs.getInt("OrderId")))
				{	
					ArrayList<OrderPayment> arr = new ArrayList<OrderPayment>();
					orderPayments.put(rs.getInt("orderId"), arr);
				}
				ArrayList<OrderPayment> listOrderPayment = orderPayments.get(rs.getInt("OrderId"));		
				System.out.println("data is"+rs.getInt("OrderId")+orderPayments.get(rs.getInt("OrderId")));

				//add to orderpayment hashmap
				OrderPayment order = new OrderPayment(rs.getInt("OrderId"),rs.getInt("itemId"),rs.getString("itemType"),rs.getString("userName"),rs.getString("orderName"),
					rs.getDouble("orderPrice"),rs.getString("userAddress"),rs.getString("creditCardNo"),rs.getDate("itemDate").toLocalDate());
				listOrderPayment.add(order);	
			}					
		}
		catch(Exception e)
		{	
		}
		return orderPayments;
	}

	public static OrderPayment selectOrderItem(int orderId, int itemId) {	
		OrderPayment order = new OrderPayment();
		
		try
		{					
			getConnection();
	        //select the table 
			String selectOrderQuery ="SELECT * FROM CustomerOrders WHERE OrderId=? AND itemId=?";			
			PreparedStatement pst = conn.prepareStatement(selectOrderQuery);
			pst.setInt(1,orderId);
			pst.setInt(2,itemId);
			ResultSet rs = pst.executeQuery();
			while(rs.next())
			{
				//update OrderPayment object
				order.setOrderId(rs.getInt("OrderId"));
				order.setItemId(rs.getInt("itemId"));
				order.setItemType(rs.getString("itemType"));
				order.setUserName(rs.getString("userName"));
				order.setOrderName(rs.getString("orderName"));
				order.setOrderPrice(rs.getDouble("orderPrice"));
				order.setUserAddress(rs.getString("userAddress"));
				order.setCreditCardNo(rs.getString("creditCardNo"));
				order.setDate(rs.getDate("itemDate").toLocalDate());
			}					
		}
		catch(Exception e)
		{	
		}
		return order;
	}

	public static void insertCustomer(String username,String firstName,String lastName,String userAddress, String userEmail, String creditCardNo) {
		try
		{	
			getConnection();
			String insertCustomerQuery = "INSERT INTO Customer(username,firstName,lastName,userAddress,userEmail,creditCardNo) "
			+ "VALUES (?,?,?,?,?,?);";	
					
			PreparedStatement pst = conn.prepareStatement(insertCustomerQuery);
			pst.setString(1,username);
			pst.setString(2,firstName);
			pst.setString(3,lastName);
			pst.setString(4,userAddress);
			pst.setString(5,userEmail);
			pst.setString(6,creditCardNo);
			pst.execute();
		}
		catch(Exception e)
		{	
		}	
	}

	public static void deleteCustomer(String username) {
		try
		{
			getConnection();
			String deleteCustomerQuery ="DELETE FROM Customer WHERE username=?";
			PreparedStatement pst = conn.prepareStatement(deleteCustomerQuery);
			pst.setString(1,username);
			pst.executeUpdate();
		}
		catch(Exception e)
		{	
		}
	}

	public static void updateCustomer(String username, String firstName, String lastName, String userAddress, String userEmail,
		String creditCardNo) {
		try
		{
			getConnection();
			String updateCustomer = "UPDATE Customer SET username=?, firstName=?, lastName=?, userAddress=?,"
			+"userEmail=?, creditCardNo=?"
			+"WHERE username=?";
				
			PreparedStatement pst = conn.prepareStatement(updateCustomer);
			//set the parameter for each column and execute the prepared statement
			pst.setString(1,username);
			pst.setString(2,firstName);
			pst.setString(3,lastName);
			pst.setString(4,userAddress);
			pst.setString(5,userEmail);
			pst.setString(6,creditCardNo);
			pst.setString(7,username);
			pst.executeUpdate();
			conn.close();
		}
		catch(Exception e)
		{
		}		
	}


	// returns hashmap of all customers from Customer database
	public static HashMap<String,Customer> selectCustomers() {	
		HashMap<String,Customer> hm = new HashMap<>();
		try 
		{
			getConnection();
			Statement stmt = conn.createStatement();
			String selectCustomersQuery="SELECT * FROM Customer";
			ResultSet rs = stmt.executeQuery(selectCustomersQuery);
			while(rs.next())
			{	
				Customer customer = new Customer(rs.getString("username"),rs.getString("firstName"),rs.getString("lastName"),
					rs.getString("userAddress"),rs.getString("userEmail"),rs.getString("creditCardNo"));
					hm.put(rs.getString("username"), customer);
			}
		}
		catch(Exception e)
		{
		}
		return hm;			
	}

	public static Customer selectSpecCustomer(String username) {	
		Customer customer = new Customer();
		try 
		{
			getConnection();
			Statement stmt = conn.createStatement();
			String selectSpecCustomerQuery="SELECT * FROM Customer WHERE username=?";
			PreparedStatement pst = conn.prepareStatement(selectSpecCustomerQuery);

			pst.setString(1,username);
			ResultSet rs = pst.executeQuery();
			while(rs.next())
			{
				customer.setUsername(rs.getString("username"));
				customer.setFirstName(rs.getString("firstName"));
				customer.setLastName(rs.getString("lastName"));
				customer.setUserAddress(rs.getString("userAddress"));
				customer.setUserEmail(rs.getString("userEmail"));
				customer.setCreditCardNo(rs.getString("creditCardNo"));
			}					
		}
		catch(Exception e)
		{
		}
		return customer;			
	}

	public static void insertUser(String username,String password,String repassword,String usertype) {
		try
		{	
			getConnection();
			String insertIntoCustomerRegisterQuery = "INSERT INTO Registration(username,password,repassword,usertype) "
			+ "VALUES (?,?,?,?);";	
					
			PreparedStatement pst = conn.prepareStatement(insertIntoCustomerRegisterQuery);
			pst.setString(1,username);
			pst.setString(2,password);
			pst.setString(3,repassword);
			pst.setString(4,usertype);
			pst.execute();
		}
		catch(Exception e)
		{	
		}	
	}

	public static void insertSubscription(int orderId, int itemId, String username, String product, double price, double balance, LocalDate startDate) {
		try
		{
			getConnection();
			String insertSubQuery = "INSERT INTO Subscription(OrderId,itemId,username,product,price,balance,startDate)"
			+ "VALUES (?,?,?,?,?,?,?);";	
				
			PreparedStatement pst = conn.prepareStatement(insertSubQuery);
			//set the parameter for each column and execute the prepared statement
			pst.setInt(1,orderId);
			pst.setInt(2,itemId);
			pst.setString(3,username);
			pst.setString(4,product);
			pst.setDouble(5,price);
			pst.setDouble(6,balance);
			pst.setDate(7,java.sql.Date.valueOf(startDate));
			pst.execute();
		}
		catch(Exception e)
		{
		}		
	}

	public static void deleteSubscription(int orderId, int itemId) {
		try
		{
			getConnection();
			String deleteSubscriptionQuery ="DELETE FROM Subscription WHERE OrderId=? AND itemId=?";
			PreparedStatement pst = conn.prepareStatement(deleteSubscriptionQuery);
			pst.setInt(1,orderId);
			pst.setInt(2,itemId);
			pst.executeUpdate();
		}
		catch(Exception e)
		{	
		}
		deleteOrder(orderId,itemId);
	}

	public static void paySubscription(int orderId, int itemId, double balance) {
		try
		{
			getConnection();
			String updateSub = "UPDATE Subscription SET balance=? WHERE OrderId=? AND itemId=?";
				
			PreparedStatement pst = conn.prepareStatement(updateSub);
			//set the parameter for each column and execute the prepared statement
			pst.setDouble(1,balance);
			pst.setInt(2,orderId);
			pst.setInt(3,itemId);
			pst.executeUpdate();
			conn.close();
		}
		catch(Exception e)
		{
		}		
	}

	public static ArrayList<Subscription> selectSubscriptions(String username) {	
		ArrayList<Subscription> subscriptions = new ArrayList<>();
			
		try
		{					
			getConnection();
	        //select the table 
			String selectSubQuery ="SELECT * FROM Subscription WHERE username=?";			
			PreparedStatement pst = conn.prepareStatement(selectSubQuery);
			pst.setString(1,username);
			ResultSet rs = pst.executeQuery();	
			while(rs.next())
			{
				//add to Subscription to list
				Subscription subscription = new Subscription(rs.getInt("OrderId"),rs.getInt("itemId"),rs.getString("username"),rs.getString("product"),rs.getDouble("price"),
					rs.getDouble("balance"),rs.getDate("startDate").toLocalDate());
				subscriptions.add(subscription);	
			}					
		}
		catch(Exception e)
		{	
		}
		return subscriptions;
	}

	public static Subscription getSpecSubscription(int orderId, int itemId) {
		Subscription sub = new Subscription();
		try
		{
			getConnection();
			Statement stmt = conn.createStatement();
			String selectSpecSubQuery = "SELECT * FROM Subscription WHERE OrderId=? AND itemId=?";

			PreparedStatement pst = conn.prepareStatement(selectSpecSubQuery);
			pst.setInt(1,orderId);
			pst.setInt(2,itemId);
			ResultSet rs = pst.executeQuery();
			while(rs.next())
			{
				sub.setOrderId(rs.getInt("OrderId"));
				sub.setItemId(rs.getInt("itemId"));
				sub.setUsername(rs.getString("username"));
				sub.setProduct(rs.getString("product"));
				sub.setPrice(rs.getDouble("price"));
				sub.setBalance(rs.getDouble("balance"));
				sub.setStartDate(rs.getDate("startDate").toLocalDate());
			}					
		}
		catch(Exception e)
		{	
		}
		return sub;
	}

	// returns hashmap of all users from Registration database
	public static HashMap<String,User> selectUser() {	
		HashMap<String,User> hm = new HashMap<String,User>();
		try 
		{
			getConnection();
			Statement stmt=conn.createStatement();
			String selectCustomerQuery="select * from  Registration";
			ResultSet rs = stmt.executeQuery(selectCustomerQuery);
			while(rs.next())
			{	
				User user = new User(rs.getString("username"),rs.getString("password"),rs.getString("usertype"));
					hm.put(rs.getString("username"), user);
			}
		}
		catch(Exception e)
		{
		}
		return hm;			
	}

	// returns specific user based on username from Registration database
	public static User getSpecUser(String username) {
		User user = new User("","","");
		try
		{
			getConnection();
			Statement stmt = conn.createStatement();
			String selectSpecUserQuery = "SELECT * FROM REGISTRATION WHERE username = ?";

			PreparedStatement pst = conn.prepareStatement(selectSpecUserQuery);
			pst.setString(1,username);
			ResultSet rs = pst.executeQuery();
			while(rs.next())
			{
				user.setName(rs.getString("username"));
				user.setPassword(rs.getString("password"));
				user.setUsertype(rs.getString("usertype"));
			}					
		}
		catch(Exception e)
		{	
		}
		return user;
	}

	public static void insertStreaming() {
		try {
			getConnection();
			
			String truncatetableprod = "delete from Streamingdetails;";
			PreparedStatement psttprod = conn.prepareStatement(truncatetableprod);
			psttprod.executeUpdate();
			
			String insertStreamingQuery = "INSERT INTO Streamingdetails(prodType,id,prodName,price,image,distributor,genre,runtime,eventDate)" +
			"VALUES (?,?,?,?,?,?,?,?,?);";
			for(Map.Entry<String, Movie> entry : SaxParserDataStore.movies.entrySet())
			{   
				String name = "movies";
		        Movie movie = entry.getValue();
				
				PreparedStatement pst = conn.prepareStatement(insertStreamingQuery);
				pst.setString(1,name);
				pst.setString(2,movie.getId());
				pst.setString(3,movie.getName());
				pst.setDouble(4,movie.getPrice());
				pst.setString(5,movie.getImage());
				pst.setString(6,movie.getDistributor());
				pst.setString(7,movie.getGenre());
				pst.setInt(8,movie.getRuntime());
				pst.setDate(9,java.sql.Date.valueOf(movie.getDate()));
				pst.executeUpdate();
			}
			
			for(Map.Entry<String, Event> entry : SaxParserDataStore.events.entrySet())
			{   
				String name = "events";
		        Event event = entry.getValue();
				
				PreparedStatement pst = conn.prepareStatement(insertStreamingQuery);
				pst.setString(1,name);
				pst.setString(2,event.getId());
				pst.setString(3,event.getName());
				pst.setDouble(4,event.getPrice());
				pst.setString(5,event.getImage());
				pst.setString(6,event.getDistributor());
				pst.setString(7,event.getGenre());
				pst.setInt(8,event.getRuntime());
				pst.setDate(9,java.sql.Date.valueOf(event.getDate()));
				
				pst.executeUpdate();	
			}
			
		} catch(Exception e)
		{
	  		e.printStackTrace();
		}
	}

	public static void addStreaming(String productType, String id, String name, double price, String image, String distributor, String genre, int runtime, LocalDate eventDate) {
		try
		{	
			getConnection();
			String addProductQuery = "INSERT INTO Streamingdetails(prodType,id,prodName,price,image,distributor,genre,runtime,eventDate) "
			+ "VALUES (?,?,?,?,?,?,?,?,?);";	
					
			PreparedStatement pst = conn.prepareStatement(addProductQuery);
			pst.setString(1,productType);
			pst.setString(2,id);
			pst.setString(3,name);
			pst.setDouble(4,price);
			pst.setString(5,image);
			pst.setString(6,distributor);
			pst.setString(7,genre);
			pst.setInt(8,runtime);
			pst.setDate(9,java.sql.Date.valueOf(eventDate));

			pst.executeUpdate();

		}
		catch(Exception e)
		{	
		}	
	}

	public static void updateStreaming(String id, String name, double price, String image, String distributor, String genre, int runtime, LocalDate eventDate) {
		try
		{
			getConnection();
			String updateStreamingQuery = "UPDATE Streamingdetails SET prodName=?, price=?, image=?, distributor=?, genre=?, runtime=?, eventDate=?"
			+"WHERE id=?";
				
			PreparedStatement pst = conn.prepareStatement(updateStreamingQuery);
			//set the parameter for each column and execute the prepared statement
			pst.setString(1,name);
			pst.setDouble(2,price);
			pst.setString(3,image);
			pst.setString(4,distributor);
			pst.setString(5,genre);
			pst.setInt(6,runtime);
			pst.setDate(7,java.sql.Date.valueOf(eventDate));
			pst.setString(8,id);
			pst.executeUpdate();
			conn.close();
		}
		catch(Exception e)
		{
		}		
	}

	public static void deleteStreaming(String id) {
		try
		{
			getConnection();
			String deleteStreamingQuery ="DELETE FROM Streamingdetails WHERE Id=?";
			PreparedStatement pst = conn.prepareStatement(deleteStreamingQuery);
			pst.setString(1,id);

			pst.executeUpdate();
		}
		catch(Exception e)
		{	
		}
	}

	public static void insertTVPlans() {
		try {
			getConnection();
			
			String truncatetableprod = "delete from TVPlan;";
			PreparedStatement psttprod = conn.prepareStatement(truncatetableprod);
			psttprod.executeUpdate();
			
			String insertTVQuery = "INSERT INTO TVPlan(id,planName,price,image,channels,distributor)" +
			"VALUES (?,?,?,?,?,?);";
			for(Map.Entry<String, TVPlan> entry : SaxParserDataStore.tvPlans.entrySet())
			{   
		        TVPlan tv = entry.getValue();
				
				PreparedStatement pst = conn.prepareStatement(insertTVQuery);
				pst.setString(1,tv.getId());
				pst.setString(2,tv.getName());
				pst.setDouble(3,tv.getPrice());
				pst.setString(4,tv.getImage());
				pst.setInt(5,tv.getChannels());
				pst.setString(6,tv.getDistributor());

				pst.executeUpdate();
			}
			
		} catch(Exception e)
		{
	  		e.printStackTrace();
		}
	}

	public static void addTV(String id, String name, double price, String image, int channels, String distributor) {
		try
		{	
			getConnection();
			String addTVQuery = "INSERT INTO TVPlan(id,planName,price,image,channels,distributor) "
			+ "VALUES (?,?,?,?,?,?);";	
					
			PreparedStatement pst = conn.prepareStatement(addTVQuery);
			pst.setString(1,id);
			pst.setString(2,name);
			pst.setDouble(3,price);
			pst.setString(4,image);
			pst.setInt(5,channels);
			pst.setString(6,distributor);

			pst.executeUpdate();

		}
		catch(Exception e)
		{	
		}	
	}

	public static void updateTV(String id, String name, double price, String image, int channels) {
		try
		{
			getConnection();
			String updateTVQuery = "UPDATE TVPlan SET planName=?, price=?, image=?, channels=? "
			+"WHERE id=?";
				
			PreparedStatement pst = conn.prepareStatement(updateTVQuery);
			//set the parameter for each column and execute the prepared statement
			pst.setString(1,name);
			pst.setDouble(2,price);
			pst.setString(3,image);
			pst.setInt(4,channels);
			pst.setString(5,id);
			pst.executeUpdate();
			conn.close();
		}
		catch(Exception e)
		{
		}		
	}

	public static void deleteTV(String id) {
		try
		{
			getConnection();
			String deleteTVQuery ="DELETE FROM TVPlan WHERE Id=?";
			PreparedStatement pst = conn.prepareStatement(deleteTVQuery);
			pst.setString(1,id);

			pst.executeUpdate();
		}
		catch(Exception e)
		{	
		}
	}

	public static void insertDataPlans() {
		try {
			getConnection();
			
			String truncatetableprod = "delete from DataPlan;";
			PreparedStatement psttprod = conn.prepareStatement(truncatetableprod);
			psttprod.executeUpdate();
			
			String insertDataQuery = "INSERT INTO DataPlan(id,planName,price,image,speed,distributor)" +
			"VALUES (?,?,?,?,?,?);";
			for(Map.Entry<String, DataPlan> entry : SaxParserDataStore.dataPlans.entrySet())
			{   
		        DataPlan data = entry.getValue();
				
				PreparedStatement pst = conn.prepareStatement(insertDataQuery);
				pst.setString(1,data.getId());
				pst.setString(2,data.getName());
				pst.setDouble(3,data.getPrice());
				pst.setString(4,data.getImage());
				pst.setString(5,data.getSpeed());
				pst.setString(6,data.getDistributor());

				pst.executeUpdate();
			}
			
		} catch(Exception e)
		{
	  		e.printStackTrace();
		}
	}

	public static void addData(String id, String name, double price, String image, String speed, String distributor) {
		try
		{	
			getConnection();
			String addDataQuery = "INSERT INTO DataPlan(id,planName,price,image,speed,distributor) "
			+ "VALUES (?,?,?,?,?,?);";	
					
			PreparedStatement pst = conn.prepareStatement(addDataQuery);
			pst.setString(1,id);
			pst.setString(2,name);
			pst.setDouble(3,price);
			pst.setString(4,image);
			pst.setString(5,speed);
			pst.setString(6,distributor);

			pst.executeUpdate();

		}
		catch(Exception e)
		{	
		}	
	}

	public static void deleteData(String id) {
		try
		{
			getConnection();
			String deleteDataQuery ="DELETE FROM DataPlan WHERE Id=?";
			PreparedStatement pst = conn.prepareStatement(deleteDataQuery);
			pst.setString(1,id);

			pst.executeUpdate();
		}
		catch(Exception e)
		{	
		}
	}

	public static void updateData(String id, String name, double price, String image, String speed) {
		try
		{
			getConnection();
			String updateDataQuery = "UPDATE DataPlan SET planName=?, price=?, image=?, speed=? "
			+"WHERE id=?";
				
			PreparedStatement pst = conn.prepareStatement(updateDataQuery);
			//set the parameter for each column and execute the prepared statement
			pst.setString(1,name);
			pst.setDouble(2,price);
			pst.setString(3,image);
			pst.setString(4,speed);
			pst.setString(5,id);
			pst.executeUpdate();
			conn.close();
		}
		catch(Exception e)
		{
		}		
	}

	public static HashMap<String, Movie> getMovies() {	
		HashMap<String, Movie> hm = new HashMap<String, Movie>();
		try 
		{
			getConnection();
			
			String selectMovie = "SELECT * FROM Streamingdetails WHERE ProductType = ?";
			PreparedStatement pst = conn.prepareStatement(selectMovie);
			pst.setString(1,"movies");
			ResultSet rs = pst.executeQuery();
		
			while(rs.next()) {	
				Movie movie = new Movie(rs.getString("name"),rs.getDouble("price"),rs.getString("image"),rs.getString("distributor"),rs.getString("genre"),rs.getInt("runtime"),rs.getDate("eventDate").toString());
					hm.put(rs.getString("id"), movie);
					movie.setId(rs.getString("id"));
			}
		}
		catch(Exception e) {
		}
		return hm;			
	}

	public static HashMap<String, Event> getEvents() {	
		HashMap<String, Event> hm = new HashMap<String, Event>();
		try 
		{
			getConnection();
			
			String selectEvent = "SELECT * FROM Streamingdetails WHERE ProductType = ?";
			PreparedStatement pst = conn.prepareStatement(selectEvent);
			pst.setString(1,"events");
			ResultSet rs = pst.executeQuery();
		
			while(rs.next()) {	
				Event event = new Event(rs.getString("name"),rs.getDouble("price"),rs.getString("image"),rs.getString("distributor"),rs.getString("genre"),rs.getInt("runtime"),rs.getDate("eventDate").toString());
					hm.put(rs.getString("id"), event);
					event.setId(rs.getString("id"));
			}
		}
		catch(Exception e) {
		}
		return hm;			
	}

}	