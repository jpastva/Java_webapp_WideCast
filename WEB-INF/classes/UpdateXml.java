import java.util.*;
import javax.xml.transform.*;
import javax.xml.transform.stream.*;
import javax.xml.transform.dom.*;
import org.w3c.dom.*;
import javax.xml.parsers.*;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathFactory;
import javax.xml.xpath.XPathConstants;
import org.xml.sax.SAXException;
import javax.xml.xpath.XPathExpressionException;
import java.io.IOException;

// This class accepts attributes from a web form to insert into an existing XML product catalog file.

public class UpdateXml {
		
	public static void addProdNode (String type, String id, String name, double price, String image, String distributor, 
		String genre, int runtime, String date) throws Exception {	
		String TOMCAT_HOME = System.getProperty("catalina.home");
			DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
       		DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
        	Document document = documentBuilder.parse(TOMCAT_HOME + "\\webapps\\WideCast\\ProductCatalog.xml");

        	Node catalog;
        	Element newProduct;

        	// Check for product category and create new XML node accordingly to insert into ProductCatalog.xml
	        switch (type) {
 		       	case "movie":
        			newProduct = document.createElement("movie");
        			catalog = document.getElementsByTagName("MovieCatalog").item(0);
        			break;

        		case "event":
        			newProduct = document.createElement("event");
        			catalog = document.getElementsByTagName("EventCatalog").item(0);
        			break;

        		default:
        			newProduct = document.createElement("movie");
        			catalog = document.getElementsByTagName("MovieCatalog").item(0);
        	}
        	
        	// Set product id attribute
        	newProduct.setAttribute("id", id);

        	// Create nodes for product attributes and insert data from web form
       		Element prodName = document.createElement("name");
        	prodName.appendChild(document.createTextNode(name));

        	Element prodPrice = document.createElement("price");
        	prodPrice.appendChild(document.createTextNode(String.valueOf(price)));

        	Element prodImage = document.createElement("image");
        	prodImage.appendChild(document.createTextNode(image));

        	Element prodDist = document.createElement("distributor");
        	prodDist.appendChild(document.createTextNode(distributor));

        	Element prodGenre = document.createElement("genre");
        	prodGenre.appendChild(document.createTextNode(genre));

        	Element prodTime = document.createElement("runtime");
        	prodTime.appendChild(document.createTextNode(String.valueOf(runtime)));

            Element prodDate = document.createElement("date");
            prodDate.appendChild(document.createTextNode(String.valueOf(date)));

        	// Append product attribute nodes to parent product node
        	newProduct.appendChild(prodName);
        	newProduct.appendChild(prodPrice);
        	newProduct.appendChild(prodImage);
        	newProduct.appendChild(prodDist);
        	newProduct.appendChild(prodGenre);
        	newProduct.appendChild(prodTime);
            newProduct.appendChild(prodDate);

        	// Append new product node to parent catalog node
        	catalog.appendChild(newProduct);

            document.normalize();

            try {
                writeFile(document);
            } catch (Exception e) 
            {
            }            
    }

    public static void removeNode(String id) throws SAXException, ParserConfigurationException, XPathExpressionException, IOException {
        String TOMCAT_HOME = System.getProperty("catalina.home");
        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
        Document doc = documentBuilder.parse(TOMCAT_HOME + "\\webapps\\WideCast\\ProductCatalog.xml");

        XPath xpath = XPathFactory.newInstance().newXPath();
        Node delNode = (Node)xpath.evaluate("//*[@id='" + id +"']", doc, XPathConstants.NODE);
        while (delNode.hasChildNodes()) {
            delNode.removeChild(delNode.getFirstChild());
        }
        delNode.getParentNode().removeChild(delNode);

        doc.normalize();

        try {
            writeFile(doc);
        } catch (Exception e) 
        {
        }  

    }

    private static void writeFile(Document document) throws Exception {
        String TOMCAT_HOME = System.getProperty("catalina.home");
        DOMSource source = new DOMSource(document);
            
        // Rewrite data to existing xml file
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        StreamResult result = new StreamResult(TOMCAT_HOME + "\\webapps\\WideCast\\ProductCatalog.xml");
        transformer.transform(source, result);   
    }

    public static void addTVNode (String id, String name, double price, String image, String channels, String distributor) throws Exception {  
        String TOMCAT_HOME = System.getProperty("catalina.home");
            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
            Document document = documentBuilder.parse(TOMCAT_HOME + "\\webapps\\WideCast\\ProductCatalog.xml");

            Node catalog;
            Element newProduct = document.createElement("tv");
            catalog = document.getElementsByTagName("TVCatalog").item(0);
            
            // Set product id attribute
            newProduct.setAttribute("id", id);

            // Create nodes for product attributes and insert data from web form
            Element prodName = document.createElement("name");
            prodName.appendChild(document.createTextNode(name));

            Element prodPrice = document.createElement("price");
            prodPrice.appendChild(document.createTextNode(String.valueOf(price)));

            Element prodImage = document.createElement("image");
            prodImage.appendChild(document.createTextNode(image));

            Element prodChannels = document.createElement("channels");
            prodChannels.appendChild(document.createTextNode(channels));

            Element prodDist = document.createElement("distributor");
            prodDist.appendChild(document.createTextNode(distributor));

            // Append product attribute nodes to parent product node
            newProduct.appendChild(prodName);
            newProduct.appendChild(prodPrice);
            newProduct.appendChild(prodImage);
            newProduct.appendChild(prodChannels);
            newProduct.appendChild(prodDist);

            // Append new product node to parent catalog node
            catalog.appendChild(newProduct);

            document.normalize();

            try {
                writeFile(document);
            } catch (Exception e) 
            {
            }            
    }

    public static void addDataNode (String id, String name, double price, String image, String speed, String distributor) throws Exception {  
        String TOMCAT_HOME = System.getProperty("catalina.home");
            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
            Document document = documentBuilder.parse(TOMCAT_HOME + "\\webapps\\WideCast\\ProductCatalog.xml");

            Node catalog;
            Element newProduct = document.createElement("data");
            catalog = document.getElementsByTagName("DataCatalog").item(0);
            
            // Set product id attribute
            newProduct.setAttribute("id", id);

            // Create nodes for product attributes and insert data from web form
            Element prodName = document.createElement("name");
            prodName.appendChild(document.createTextNode(name));

            Element prodPrice = document.createElement("price");
            prodPrice.appendChild(document.createTextNode(String.valueOf(price)));

            Element prodImage = document.createElement("image");
            prodImage.appendChild(document.createTextNode(image));

            Element prodSpeed = document.createElement("speed");
            prodSpeed.appendChild(document.createTextNode(speed));

            Element prodDist = document.createElement("distributor");
            prodDist.appendChild(document.createTextNode(distributor));

            // Append product attribute nodes to parent product node
            newProduct.appendChild(prodName);
            newProduct.appendChild(prodPrice);
            newProduct.appendChild(prodImage);
            newProduct.appendChild(prodSpeed);
            newProduct.appendChild(prodDist);

            // Append new product node to parent catalog node
            catalog.appendChild(newProduct);

            document.normalize();

            try {
                writeFile(document);
            } catch (Exception e) 
            {
            }            
    }
 }