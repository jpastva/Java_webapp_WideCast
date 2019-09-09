import java.io.*;

import javax.servlet.http.*;
import javax.servlet.RequestDispatcher;
import java.util.*;
import java.text.*;

import java.sql.*;

import java.io.IOException;
import java.io.*;



public class AjaxUtility {
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
	
	public StringBuffer readData(String searchId)
	{	
		StringBuffer sb = new StringBuffer();
		HashMap<String,GenericProduct> data;
		data = getData();
		
 	    Iterator it = data.entrySet().iterator();	
        while (it.hasNext()) 
	    {
            Map.Entry pi = (Map.Entry)it.next();
			if(pi!=null)
			{
				GenericProduct p=(GenericProduct)pi.getValue();                   
                if (p.getName().toLowerCase().startsWith(searchId))
                {
                        sb.append("<product>");
                        sb.append("<id>" + p.getId() + "</id>");
                        sb.append("<productName>" + p.getName() + "</productName>");
                        sb.append("</product>");
                }
			}
       }
	   
	   return sb;
	}
	
	public static HashMap<String,GenericProduct> getData()
	{
		HashMap<String,GenericProduct> hm=new HashMap<String,GenericProduct>();
		try
		{
			getConnection();
			
		    String selectproduct="select * from  Streamingdetails";
		    PreparedStatement pst = conn.prepareStatement(selectproduct);
			ResultSet rs = pst.executeQuery();
			
			while(rs.next())
			{	
				GenericProduct p = new GenericProduct(rs.getString("id"),rs.getString("prodName"),rs.getDouble("price"),rs.getString("image"),rs.getString("distributor"),rs.getString("prodType"));
				hm.put(rs.getString("id"), p);
			}

			selectproduct="select * from  TVPlan";
		    pst = conn.prepareStatement(selectproduct);
			rs = pst.executeQuery();
			
			while(rs.next())
			{	
				GenericProduct p2 = new GenericProduct(rs.getString("id"),rs.getString("planName"),rs.getDouble("price"),rs.getString("image"),rs.getString("distributor"),"tvPlans");
				hm.put(rs.getString("id"), p2);
			}

			selectproduct="select * from  DataPlan";
		    pst = conn.prepareStatement(selectproduct);
			rs = pst.executeQuery();
			
			while(rs.next())
			{	
				GenericProduct p3 = new GenericProduct(rs.getString("id"),rs.getString("planName"),rs.getDouble("price"),rs.getString("image"),rs.getString("distributor"),"dataPlans");
				hm.put(rs.getString("id"), p3);
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return hm;			
	}
}