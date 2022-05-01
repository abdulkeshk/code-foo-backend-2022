# Code Foo 2022 Submission: Backend

Hi! My name is Abdulrahman Keshk. I am a Computer Science student at SUNY, with a deep passion for video games. I have been following IGN on YouTube to keep up with gaming content. I chose to participate in Code Foo on Apr. 29th as I was searching online for internship opportunities. Despite the late start, I decided to try my best and complete as much as possible. I learned a lot during this short time span, and any feedback would be highly appreciated.


# Project Details

I chose to write a **backend** service in **Java** to parse the provided CSV Data, clean the data, and push it into a local **PostgresSQL** database. Then have a **REST API in Spring Boot** to search for records in the database, with filtering options. The IDE used is **IntelliJ**, where all code was written and the local PostgresSQL db was created.

## File Structure: Two Java Modules
### 1 for CSV & DB Handling, 1 for REST API Service

1) [CODE-FOO-IGN-CSV](https://github.com/abdulkeshk/code-foo-backend-2022/tree/main/CODE-FOO-IGN-CSV "CODE-FOO-IGN-CSV") is a Java module, responsible for opening, reading, and cleaning data from the CSV file. It then connects to the database, and inserts each record one-by-one.

	a) [CSVUtils](https://github.com/abdulkeshk/code-foo-backend-2022/tree/main/CODE-FOO-IGN-CSV/src/com/keshk/CSVUtils "CSVUtils") is a package containing all Java files responsible for opening, reading, and storing each row in the CSV file. Within this package:
	- [CSVReader.java](https://github.com/abdulkeshk/code-foo-backend-2022/blob/main/CODE-FOO-IGN-CSV/src/com/keshk/CSVUtils/CSVReader.java "CSVReader.java") has functions to open, process and store each row of data.

	b) [DataCleaner](https://github.com/abdulkeshk/code-foo-backend-2022/tree/main/CODE-FOO-IGN-CSV/src/com/keshk/DataCleaner "DataCleaner") is a package containing all Java files responsible for cleaning all rows of data. Within this package:
	
	- [IGNDataCleaner.java](https://github.com/abdulkeshk/code-foo-backend-2022/blob/main/CODE-FOO-IGN-CSV/src/com/keshk/DataCleaner/IGNDataCleaner.java "IGNDataCleaner.java") has functions to clean individual rows. Without data cleaning, it is difficult to programmatically produce valid SQL queries to insert records. 

	c) [DatabaseUtils](https://github.com/abdulkeshk/code-foo-backend-2022/tree/main/CODE-FOO-IGN-CSV/src/com/keshk/DatabaseUtils "DatabaseUtils") is a package containing all Java files responsible for connecting and executing queries to the PostgresSQL db. Within this package:
	
	- [SQLConnector.java](https://github.com/abdulkeshk/code-foo-backend-2022/blob/main/CODE-FOO-IGN-CSV/src/com/keshk/DatabaseUtils/SQLConnector.java "SQLConnector.java") is a class that connects to the local database instance upon initialization of a new SQLConnector object. It also has a function to insert a single row. 

	d) **Main.java** is the main java class that runs reading the CSV file, cleaning the data, and finally inserting it into the db. It is the main driver that runs all the code in the above structure.

2. [IGN-REST-API](https://github.com/abdulkeshk/code-foo-backend-2022/tree/main/IGN-REST-API "IGN-REST-API") is a java module responsible for running a REST API Service, with 3 GET endpoints to retrieve records by id, genre, franchises, etc.
	- [IgnApplication.java](https://github.com/abdulkeshk/code-foo-backend-2022/blob/main/IGN-REST-API/src/main/java/com/keshk/ign/IgnApplication.java "IgnApplication.java") is the main  class running the API service to listen for requests.
	- [IGNRecord.java](https://github.com/abdulkeshk/code-foo-backend-2022/blob/main/IGN-REST-API/src/main/java/com/keshk/ign/IGNRecord.java "IGNRecord.java") is a model class representing what a single row is made up of in our service. It has internal variables for id, media_type, name, etc.
	- [RecordController.java](https://github.com/abdulkeshk/code-foo-backend-2022/blob/main/IGN-REST-API/src/main/java/com/keshk/ign/RecordController.java "RecordController.java") is the controller responsible for handling GET requisitions. It supports 3 GET endpoints: 
		- ``getRecordById, getRecordByReviewURL, getRecordByGenres``

## Challenges (Why was this so tough?)
- The first major challenge was reading the csv file and structuring the data properly in Java. I realized that manually trying to split each row by each column was tricky since commas were sprinkled within the data itself. To overcome this challenge, I imported the jackson csv library to handle this. Now each column could be separated for all rows.
- The next challenge was how to store/represent any single row in Java. After searching I noticed there was a way to store each column name to its value via a ``Map<String, String>``. The key would be a string for the column name, and the value would be the piece of data for that column. This is a data structure I have not used before, which required time to understand.
- Connecting to a database instance required the java sql library, and inserting records needed some string manipulation to produce a valid query. Producing queries correctly took some manual validation and debugging in Postgres. The major issue was all the special characters like curly braces {}, single quotes, and HTML tags that ruined the syntax of the queries I made.
- To handle the invalid queries due to special queries, I realized that I had to clean the data. Removing all the invalid characters resolved this, and required some more manual verification of the new queries.

## Setting Up PostgresSQL Database

To create the database, I used IntelliJ's built in Database explore to create a PostgresSQL DB instance called ``ign_records``, and ran a few queries to get started.
	- First step was to create a table. Given the limited time, here was the query run to create a table of records. The table will have the same structure as the CSV file, meaning each column in the file will be a column in the records table.
		``
create table Records(  
  id varchar PRIMARY KEY,  
  media_type varchar,  
  name varchar,  
  short_name varchar,  
  long_description varchar,  
  short_description varchar,  
  created_at varchar,  
  updated_at varchar,  
  review_url varchar,  
  review_score varchar,  
  slug varchar,  
  genres varchar,  
  created_by varchar,  
  published_by varchar,  
  franchises varchar,  
  regions varchar  
);``

After running the **Main.java** to read, clean, and insert data into the db I had to verify the insertions were successful by getting all the records and sorting them by id.

``
select * from records order by cast(id as numeric);``

IntelliJ provides an option to download the entire records table to a separate CSV file for comparison here: [ign_records_public_records.csv](https://github.com/abdulkeshk/code-foo-backend-2022/blob/main/CODE-FOO-IGN-CSV/out/production/CodeFooIGN/com/keshk/ign_records_public_records.csv "ign_records_public_records.csv")

## How To Download and Run The Code?
This project was built in **IntelliJ**, where we can go to ``File -> Open Project``. From here, either choose the 1st (CSV) or 2nd (REST API) Module. For the REST API, simply run the project as a SpringBoot application. For the CSV Application, simply run Main.java.

## Let's Watch the Code Run!

For Demo purposes, let's see the **Main.java** code run and validate the data is in our DB. Since the REST API is incomplete, no demo is provided but the code is explained in the project structure section.

## Incomplete Work

Since I worked on this for 1-2 days, I could only handle reading the CSV file, cleaning data, and pushing records into the database. Meanwhile the REST API was incomplete. The API runs locally, and has the 3 endpoints setup. But ultimately, the API does not return any real values to the user hitting those endpoints. I think it would be fun to continue working on this project just to show on my resume.

## Learnings

Prior to Code Foo, I was not familiar with Maps, PostgresSQL, Spring Boot. I learned a great deal from lots of searching and going through small tutorials. The major learnings were from understanding concepts:
	
	1) What is a database? How is PostgresSQL different? How do I create a db and add a table? How do I insert a record?
	2) How are CSV files read programmatically? Is there a library to make this easier?
	3) What is an API? Why use SOAP or REST? What frameworks can help me build one in Java?
