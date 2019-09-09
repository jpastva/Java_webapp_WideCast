/*********


http://www.saxproject.org/

SAX is the Simple API for XML, originally a Java-only API. 
SAX was the first widely adopted API for XML in Java, and is a “de facto” standard. 
The current version is SAX 2.0.1, and there are versions for several programming language environments other than Java. 

The following URL from Oracle is the JAVA documentation for the API

https://docs.oracle.com/javase/7/docs/api/org/xml/sax/helpers/DefaultHandler.html


*********/
import org.xml.sax.InputSource;

import java.io.IOException;
import java.text.ParseException;
import java.util.*;
import java.io.StringReader;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;
import java.time.LocalDate;

////////////////////////////////////////////////////////////

/**************

SAX parser uses callback function to notify client object of the XML document structure. 
You should extend DefaultHandler and override the method when parsing the XML document

***************/

////////////////////////////////////////////////////////////

public class SaxParserDataStore extends DefaultHandler {
    TVPlan tv;
    DataPlan data;
    Movie movie;
    Event event;
    static HashMap<String, TVPlan> tvPlans;
    static HashMap<String, DataPlan> dataPlans;
    static HashMap<String, Movie> movies;
    static HashMap<String, Event> events;
    static HashMap<String, Streaming> streaming;
    String productXmlFileName;
    String elementValueRead;
	String currentElement = "";
    
    public SaxParserDataStore() {
	}

	public SaxParserDataStore(String productXmlFileName) {
    	this.productXmlFileName = productXmlFileName;
    	tvPlans = new HashMap<String, TVPlan>();
		dataPlans = new  HashMap<String, DataPlan>();
		movies = new HashMap<String, Movie>();
		events = new HashMap<String, Event>();
		streaming = new HashMap<String, Streaming>();
		parseDocument();
    }

   //parse the xml using sax parser to get the data
    private void parseDocument() {
        SAXParserFactory factory = SAXParserFactory.newInstance();
        try {
	    	SAXParser parser = factory.newSAXParser();
	    	parser.parse(productXmlFileName, this);
        } catch (ParserConfigurationException e) {
            System.out.println("ParserConfig error");
        } catch (SAXException e) {
            System.out.println("SAXException : xml not well formed");
        } catch (IOException e) {
            System.out.println("IO error");
        }
	}

   

////////////////////////////////////////////////////////////

/*************

There are a number of methods to override in SAX handler  when parsing your XML document :

    Group 1. startDocument() and endDocument() :  Methods that are called at the start and end of an XML document. 
    Group 2. startElement() and endElement() :  Methods that are called  at the start and end of a document element.  
    Group 3. characters() : Method that is called with the text content in between the start and end tags of an XML document element.


There are few other methods that you could use for notification for different purposes, check the API at the following URL:

https://docs.oracle.com/javase/7/docs/api/org/xml/sax/helpers/DefaultHandler.html

***************/

////////////////////////////////////////////////////////////
	
	// when xml start element is parsed store the id into respective hashmap for movie, event, etc. 
    @Override
    public void startElement(String str1, String str2, String elementName, Attributes attributes) throws SAXException {

        if (elementName.equalsIgnoreCase("movie")) 
		{
			currentElement = "movie";
			movie = new Movie();
            movie.setId(attributes.getValue("id"));
		}
        if (elementName.equalsIgnoreCase("event"))
		{
			currentElement="event";
			event = new Event();
            event.setId(attributes.getValue("id"));
        }
        if (elementName.equalsIgnoreCase("tv"))
		{
			currentElement="tv";
			tv = new TVPlan();
            tv.setId(attributes.getValue("id"));
        }
        if (elementName.equalsIgnoreCase("data"))
        {
        	currentElement = "data";
        	data = new DataPlan();
        	data.setId(attributes.getValue("id"));
        }
    }
	
	// when xml end element is parsed store the data into respective hashmap for event, movie, etc.
    @Override
    public void endElement(String str1, String str2, String element) throws SAXException {
 
        if (element.equals("movie")) {
			movies.put(movie.getId(), movie);
			return;
        }
 
        if (element.equals("event")) {	
			events.put(event.getId(), event);
			return;
        }
        if (element.equals("tv")) {	  
			tvPlans.put(tv.getId(), tv);
			return;
        }
        if (element.equals("data")) {	 
			dataPlans.put(data.getId(),data);
			return;
        }

        if (element.equalsIgnoreCase("name")) {
            if(currentElement.equals("movie"))
				movie.setName(elementValueRead);
        	if(currentElement.equals("event"))
				event.setName(elementValueRead);
			if(currentElement.equals("tv"))
				tv.setName(elementValueRead);
            if(currentElement.equals("data"))
				data.setName(elementValueRead);          
			return;
	    }

	    if(element.equalsIgnoreCase("price")){
			if(currentElement.equals("movie"))
				movie.setPrice(Double.parseDouble(elementValueRead));
        	if(currentElement.equals("event"))
				event.setPrice(Double.parseDouble(elementValueRead));
			if(currentElement.equals("tv"))
				tv.setPrice(Double.parseDouble(elementValueRead));
            if(currentElement.equals("data"))
				data.setPrice(Double.parseDouble(elementValueRead));        
			return;
        }
        
        if (element.equalsIgnoreCase("image")) {
		    if(currentElement.equals("movie"))
				movie.setImage(elementValueRead);
        	if(currentElement.equals("event"))
				event.setImage(elementValueRead);
			if(currentElement.equals("tv"))
				tv.setImage(elementValueRead);
            if(currentElement.equals("data"))
				data.setImage(elementValueRead);         
			return;
        }

        if (element.equalsIgnoreCase("distributor")) {
            if(currentElement.equals("movie"))
				movie.setDistributor(elementValueRead);
        	if(currentElement.equals("event"))
				event.setDistributor(elementValueRead);
			if(currentElement.equals("tv"))
				tv.setDistributor(elementValueRead);
            if(currentElement.equals("data"))
				data.setDistributor(elementValueRead);         
			return;
		}

		if (element.equalsIgnoreCase("genre")) {
            if(currentElement.equals("movie"))
				movie.setGenre(elementValueRead);
        	if(currentElement.equals("event"))
				event.setGenre(elementValueRead);          
			return;
	    }
        
		if (element.equalsIgnoreCase("runtime")) {
            if(currentElement.equals("movie"))
				movie.setRuntime(Integer.parseInt(elementValueRead));
        	if(currentElement.equals("event"))
				event.setRuntime(Integer.parseInt(elementValueRead));          
			return;
	    }

	    if (element.equalsIgnoreCase("date")) {
            if(currentElement.equals("movie"))
				movie.setDate(elementValueRead);
        	if(currentElement.equals("event"))
				event.setDate(elementValueRead);          
			return;
	    }

	    if (element.equalsIgnoreCase("channels")) {
            if(currentElement.equals("tv"))
				tv.setChannels(Integer.parseInt(elementValueRead));         
			return;
	    }

	    if (element.equalsIgnoreCase("speed")) {
            if(currentElement.equals("data"))
				data.setSpeed(elementValueRead);         
			return;
	    }
	
        streaming.putAll(movies);
        streaming.putAll(events);
	}
	//get each element in xml tag
    @Override
    public void characters(char[] content, int begin, int end) throws SAXException {
        elementValueRead = new String(content, begin, end);
    }


    /////////////////////////////////////////
    // 	     Kick-Start SAX in main       //
    ////////////////////////////////////////
	
//call the constructor to parse the xml and get product details
public static void addHashmap() {
		String TOMCAT_HOME = System.getProperty("catalina.home");	
		new SaxParserDataStore(TOMCAT_HOME + "\\webapps\\WideCast\\ProductCatalog.xml");
    } 
}
