Application installation/run instructions:

1. Install MySQL and configure to work with Java by downloading the mysql-connector-java-8.0.1.7.jar file. Database username/password: root/root
2. Open MySQL Workbench.
3. Create widecastdb using the SQL supplied in the QueryForCreatingDatabase.sql file in the WideCast directory.
4. Install and configure MongoDB to work with Java, including adding necessary .jar files. Make sure that it is running and connected via default port 27017.
5. Make sure WideCast directory is saved in local tomcat\webapps directory.
6. Startup Tomcat.
7. Launch WideCast application in web browser at http://localhost/WideCast
8. Test MySQL connectivity by creating user accounts and customer orders, then verifying data is reflected in the Registration and CustomerOrders tables. Streamingdetails table should also be populated with PPV product catalog from ProductCatalog.xml file.
9. Test Mongo functionality on website by selecting a product and writing a review using the given fields. You should be able to read this review in using the "View Review" button on the product. Write several reviews to populate the Mongo database.
10. Test the Mongo query component of the application by visting the Trending page. There should be counts of the most popular products by category based on the content of the reviews stored.
11. Test autocomplete search feature by entering the first few letters of a product in the search box. A list of suggested products should be populated if a product exists using your supplied letters. Click on a link to a product, which will bring you to the product's page.
12. Test customer account creation and modification; ability to pay monthly bills.
13. Test Technician's ability to create, delete, and modify incident tickets.
14. Test account specialist's ability to create tickets, create/update customers, and place PPV orders for customers.
15. Change date of PPV event to today's date in ProductCatalog.xml file and test ability to cancel order (should not work).