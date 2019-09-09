CREATE DATABASE widecastdb;

USE widecastdb;

CREATE TABLE Registration
(
username varchar(40),
password varchar(40),
repassword varchar(40),
usertype varchar(40),
PRIMARY KEY(userName)
);

CREATE TABLE CustomerOrders
(
OrderId int,
itemId int,
itemType varchar(40),
userName varchar(40),
orderName varchar(40),
orderPrice double,
userAddress varchar(40),
creditCardNo varchar(40),
itemDate date,
PRIMARY KEY(OrderId,itemId),
FOREIGN KEY(userName) REFERENCES Registration(username)
);

CREATE TABLE Customer
(
userName varchar(40),
firstName varchar(40),
lastName varchar(40),
userAddress varchar(40),
userEmail varchar(40),
creditCardNo varchar(40),
PRIMARY KEY(userName),
FOREIGN KEY(userName) REFERENCES Registration(username)
);

CREATE TABLE Streamingdetails
(
prodType varchar(20),
id varchar(20),
prodName varchar(40),
price double,
image varchar(40),
distributor varchar(40),
genre varchar(40),
runtime int,
eventDate date,
PRIMARY KEY(id)
);

CREATE TABLE TVPlan
(
id varchar(20),
planName varchar(40),
price double,
image varchar(40),
channels int,
distributor varchar(40),
PRIMARY KEY(id)
);

CREATE TABLE DataPlan
(
id varchar(20),
planName varchar(40),
price double,
image varchar(40),
speed varchar(20),
distributor varchar(40),
PRIMARY KEY(id)
);

CREATE TABLE Subscription
(
OrderId int,
itemId int,
username varchar(40),
product varchar(40),
price double,
balance double,
startDate date,
PRIMARY KEY(OrderId, itemId),
FOREIGN KEY(username) REFERENCES Registration(username)
);

CREATE TABLE Ticket
(
ticketId integer,
custUsername varchar(40),
technician varchar(40),
description varchar(200),
activeStatus boolean,
PRIMARY KEY(ticketId),
FOREIGN KEY(custUsername) REFERENCES Registration(username),
FOREIGN KEY(technician) REFERENCES Registration(username)
);



