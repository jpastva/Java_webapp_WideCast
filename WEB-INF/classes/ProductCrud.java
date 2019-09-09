import java.io.*;
import java.io.PrintWriter;
import java.util.HashMap;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/ProductCrud")

public class ProductCrud extends HttpServlet {
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		PrintWriter pw = response.getWriter();
		Utilities utility = new Utilities(request, pw);
		String action = request.getParameter("button");
		
		String msg = "good";
		String productType = "";
		String productId = "";
		String productName = "";
		String productImage = "";
		String productManufacturer = "";
		String productCondition = "";
		String prod = "";
		double productPrice = 0.0;
		double productDiscount = 0.0;
		double productRebate = 0.0;

		HashMap<String, Phone> allPhones = new HashMap<String, Phone>();
		HashMap<String,Tablet> allTablets = new HashMap<String, Tablet>();
		HashMap<String,Laptop> allLaptops = new HashMap<String, Laptop>();
		HashMap<String,TV> allTVs = new HashMap<String, TV>();
		HashMap<String, Accessory> allAccessories = new HashMap<String, Accessory>();
		if (action.equals("add") || action.equals("update"))
		{	
			 productType = request.getParameter("producttype");
			 productId   = request.getParameter("productId");
			 productName = request.getParameter("productName"); 
			 productPrice = Double.parseDouble(request.getParameter("productPrice"));
			 productImage = request.getParameter("productImage");
			 productManufacturer = request.getParameter("productManufacturer");
			 productCondition = request.getParameter("productCondition");
			 productDiscount = Double.parseDouble(request.getParameter("productDiscount"));
			 productRebate = Double.parseDouble(request.getParameter("productRebate"));
		}
		else{
			productId   = request.getParameter("productId");
		}	
		utility.printHtml("Header.html");
		utility.printHtml("LeftNavigationBar.html");

		if(action.equals("add"))
		{
		  	if(productType.equals("phones")) {
				allPhones = MySqlDataStoreUtilities.getPhones();
				if(allPhones.containsKey(productId)) {
					msg = "Product already available"; 
			    }
				  
		    } 
		    else if(productType.equals("laptops")) {
				allLaptops = MySqlDataStoreUtilities.getLaptops();
			    if(allLaptops.containsKey(productId)) {
					msg = "Product already available";
			    }
		    }
		    else if (productType.equals("tablets")) {
				allTablets = MySqlDataStoreUtilities.getTablets();
			    if(allTablets.containsKey(productId)) {
				    msg = "Product already available";
			    }
		    }
		    else if(productType.equals("tvs")) {
		    	allTVs = MySqlDataStoreUtilities.getTVs();
		    	if(allTVs.containsKey(productId)) {
		    		msg = "Product already available";
		    	}
		    }
		    else if (productType.equals("accessories")) {  
				if(!request.getParameter("product").isEmpty()) {
					prod = request.getParameter("product");
					allPhones = MySqlDataStoreUtilities.getPhones();
					if(allPhones.containsKey(prod)) {
						allAccessories = MySqlDataStoreUtilities.getAccessories();
						if(allAccessories.containsKey(productId)) {
							msg = "Product already available";
						}
					}
					else {
						msg = "The product related to accessories is not available";
					}
					
				}
				else {
					msg = "Please add the prodcut name";
				}
			  
		  }	

		  if (msg.equals("good"))
		  {  
			  try
			  {
				  msg = MySqlDataStoreUtilities.addProducts(productType,productId,productName,productPrice,productImage,productManufacturer,productCondition,productDiscount,productRebate,prod);
			  }
			  catch(Exception e)
			  { 
				msg = "Product cannot be inserted";
			  }
			  msg = "Product has been successfully added";
		  }					
		} else if(action.equals("update")) {
			
			if(productType.equals("phones")){
				allPhones = MySqlDataStoreUtilities.getPhones();
				if(!allPhones.containsKey(productId)) {
					msg = "Product not available";
				}
					  
			}
			else if(productType.equals("laptops")) {
				allLaptops = MySqlDataStoreUtilities.getLaptops();
				if(!allLaptops.containsKey(productId)) {
					msg = "Product not available";
				}
			}
			else if (productType.equals("tablets")) {
				allTablets = MySqlDataStoreUtilities.getTablets();
				if(!allTablets.containsKey(productId)){
					msg = "Product not available";
				}
			}
			else if (productType.equals("tvs")) {
				allTVs = MySqlDataStoreUtilities.getTVs();
				if(!allTVs.containsKey(productId)){
					msg = "Product not available";
				}
			}  
			else if (productType.equals("accessories")) {
				allAccessories = MySqlDataStoreUtilities.getAccessories();
				if(!allAccessories.containsKey(productId)) {
					msg = "Product not available";
				}
			}	
			
			if (msg.equals("good")) {		
				
				try {
					msg = MySqlDataStoreUtilities.updateProducts(productType,productId,productName,productPrice,productImage,productManufacturer,productCondition,productDiscount,productRebate);
				}
				catch(Exception e)
				{ 
					msg = "Product cannot be updated";
				}
				msg = "Product has been successfully updated";
			} 
		} 
		else if(action.equals("delete")) {
			msg = "bad";
			allPhones = MySqlDataStoreUtilities.getPhones();
			if(allPhones.containsKey(productId)) {
				msg = "good";
			}
				  
			allLaptops = MySqlDataStoreUtilities.getLaptops();
			if(allLaptops.containsKey(productId)) {
				msg = "good";
			}
		  
			allTablets = MySqlDataStoreUtilities.getTablets();
			if(allTablets.containsKey(productId)) {
				msg = "good";
			}

			allTVs = MySqlDataStoreUtilities.getTVs();
			if(allTVs.containsKey(productId)) {
				msg = "good";
			}
		  
			allAccessories = MySqlDataStoreUtilities.getAccessories();
			if(allAccessories.containsKey(productId)) {
				msg = "good";
			}
	       		
			if (msg.equals("good")) {		
				
				try {  	
					msg = MySqlDataStoreUtilities.deleteProducts(productId);
			    }
			    catch(Exception e)
			    { 
					msg = "Product cannot be deleted";
			    }
			    msg = "Product has been successfully deleted";
			}
			else {
				msg = "Product not available";
			}
		}	
			
		pw.print("<div id='content'><div class='post'><h2 class='title meta'>");
		pw.print("<a style='font-size: 24px;'>Order</a>");
		pw.print("</h2><div class='entry'>");
		pw.print("<h4 style='color:blue'>"+msg+"</h4>");
		pw.print("</div></div></div>");		
		utility.printHtml("Footer.html");
	}
}