twitterwall
===========

A very Simple Twitterwall based on Java, Spring Frameworks and JavaScript. 

* Some experimental Work with Spring Integration and STS. 
* Runs on cloudfoundry.com or on an local tomcat servlet container. 
* Uses MySQL and MongoDB as Service.

Project Setup
=============

The Basics:
-----------
* Eclipse: Download and install Spring Tool Suite (STS) from: http://spring.io/tools
* java: Download jdk from: http://www.oracle.com/technetwork/java/javase/downloads/index.html
* Maven: Dowload and install maven3 from: http://maven.apache.org/download.cgi
* Install MySQL-Database from http://dev.mysql.com/downloads/ or from: http://www.apachefriends.org/de/xampp-windows.html
* Install MongoDb NoSQL Database from http://www.mongodb.org/downloads

import Project via shell
------------------------ 
* git clone https://github.com/phasenraum2010/twitterwall.git
* this creates a directory twitterwall
* open STS
* import -> Git -> Projects from Git -> next
* existing local repository -> next
* add the twitterall directory to the list of local repositories
* choose the twitterwall directory
* check: import as general project
* next -> finish
* wait a minute 
* right click on the Project in STS Package Explorer:
* configure -> convert to maven project

or import Project via STS only
------------------------------
* File -> import -> Git -> Projects from Git -> next 
* enter: "twitterwall" and search
* choose "phasenraum2010/twitterwall" (or your fork with your username instead of mine)
* next -> next -> choose Directory for downloading the project
* check: import as general project
* next -> finish
* wait a minute
* right click on the Project in STS Package Explorer:
* configure -> convert to maven project

Setup MySQL Database
--------------------

open MySQL-Client and enter: 

CREATE USER 'twitterwall'@'localhost' IDENTIFIED BY 'twitterwallpwd';

GRANT SELECT, INSERT, UPDATE, DELETE,CREATE, DROP,FILE,INDEX,ALTER, CREATE TEMPORARY TABLES, CREATE VIEW, EVENT, TRIGGER, SHOW VIEW, CREATE ROUTINE, ALTER ROUTINE, EXECUTE ON * . * TO 'twitterwall'@'localhost' IDENTIFIED BY 'twitterwallpwd' WITH MAX_QUERIES_PER_HOUR 0 MAX_CONNECTIONS_PER_HOUR 0 MAX_UPDATES_PER_HOUR 0 MAX_USER_CONNECTIONS 0 ;

CREATE DATABASE IF NOT EXISTS twitterwall;

GRANT ALL PRIVILEGES ON twitterwall.* TO 'twitterwall'@'localhost';

GRANT ALL PRIVILEGES ON twitterwall\_%.* TO 'twitterwall'@'localhost';


Setup MongoDb
-------------
Needs no setup, only Server up and running, uses the default test db.

Setup project Parameters
------------------------

* Open STS and in Package Explorer go to: src/main/resources
* copy twitterwall_sample.properties to twitterwall.properties

Setup Twitter App
-----------------
* Open "Manage your Apps" on Twitter's dev pages: https://dev.twitter.com/apps with your twitter account.
* Setup an App
* Insert the Credentials to twitterwall.properties:
* twitter.consumerKey=
* twitter.consumerSecret=
* twitter.accessToken=
* twitter.accessTokenSecret=
* Setup your searchterm for twitterwall in twitterwall.properties: twitter.searchterm=

Run the Twitterwall:
--------------------

* in the shell cd into project directory twittwewall
* mvn clean install tomcat7:run
* open browser: http://localhost:8080/twitterwall/
* please submit Bugs or Feature Requests to https://github.com/phasenraum2010/logfileloader/issues?state=open






