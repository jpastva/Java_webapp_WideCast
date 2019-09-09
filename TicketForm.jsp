<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@include file="Header.html" %>

<div id='menu' style='float: right;'>
<ul>
	<li><a><span class='glyphicon'>Hello Account Specialist!</span></a></li>
	<li><a href='OrdersList'><span class='glyphicon'>Orders</span></a></li> 
	<li><a href='CustomerList'><span class='glyphicon'>Customers</span></a></li>
	<li><a href='TicketsList'><span class='glyphicon'>Tickets</span></a></li>
	<li><a href='Logout'><span class='glyphicon'>Logout</span></a></li>
</ul>
</div></div><div id='page'>

<%@include file="LeftNavigationBar.html" %>

<div id='content'><div class='post'>
<h2 class='title meta'><a style='font-size: 24px;'>Enter New Ticket Information</a></h2>
<div class='entry'>

<form method='post' action='AddTicket'><table style='width:100%'>
<tr><td>
<h4>Customer username: </h4></td><td><input type='text' name='custUsername' value='' class='input'></input></td></tr>
<tr><td>
<h4>Technician username: </h4></td><td><input type='text' name='technician' value='' class='input'></input></td></tr>
<tr><td>
<h4>Description: </h4></td><td><input type='text' name='description' value='' class='input'></input></td></tr>
<tr><td>
<h4>Active Status: </h4></td><td><select name='activeStatus' class='input'><option value='true' selected>Active</option><option value='false'>Complete</option></select>
<tr><td></td><td>
<input type='submit' value='Add Ticket' class='btnbuy'>
</td></tr></table>			
</form></div></div></div>
<br><br>
</div>

<%@include file="Footer.html" %>