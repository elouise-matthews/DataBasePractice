# DataBasePractice
How to setup and connect to databases using Java, sql, dbeaver, docker, squirrel sql, postgresql

## 1. Docker
No Installation for postgresql is needed if docker containers are used. </br>
copy the DBPractice_DockerDir folder to local computer and start the docker-compose file. This will start a postgres Database management system (dbms) at the address localhost:5432. By default, this also starts one database named postgres. </br> 

## 2. DBeaver
DBeaver is probably the easiest way to access a database. </br>
Follow [instructions](https://computingforgeeks.com/install-and-configure-dbeaver-on-ubuntu-debian/) to download DBeaver community edition (free) and follow [instructions](https://www.devart.com/odbc/postgresql/docs/dbeaver.htm) to create a connection to your local database. </br>
Keep in mind that `jdbc:postgresql` identifies the type of system you are trying to locate, while `localhost:5432` is the location of this system, and `postgres` is the database itself. </br>
In the docker-compose file, the user is set to `root` and the password to `password`, but you can change this. </br>
Once the connection established, you can use the built in sql console to write sql commands to the database. 

## 3. Squirrel SQL
Squirrel SQL is an alternative sql client to dbeaver, which works on any java complient databases. </br>
If you are are on a unix operating system, install it using `sudo apt update` `sudo apt install snapd` `sudo snap install squirrelsql`. Otherwise, search for instructions and install it. </br>
Once installed, follow [instructions](https://www.cdata.com/kb/tech/postgresql-jdbc-squirrel-sql.rst) to create a connection. In the process of creating this connection, you will be asked to locate the Driver jar file. You will have to download this jar file from the database service's homepage first. For postgres it is located [here](https://jdbc.postgresql.org/download.html). Download the latest version and store it somewhere you won't forget. There is no need to unzip it. <br/> 
Once the driver is connected and an alias is created, you can use the built in sql console to write commands. 

## 3. Java Program
The directory DBPractice contains the pom.xml file and java class files to compile a maven project. IT contains 4 revelant classes. 
#### DownloadIMDB 
This class downloads movie data from the imdb website and saves them to files under resources. 
If you run it more than once, it will simply overwrite the existing files with the newest data. 
#### DriverConnections 
This class is not use now, but is there to demonstrate one method of connecting to a database using jdbc Driver connections. 
#### JdbiConnector
This class is a more convenient method of connecting. </br>
It contains three methods besides the main void. One to send an sql to create each table, one to call up an arbitrary Select statement to check if it is working, and one to read the tsv files and upload their content to the database. </br>
It is important not to open and close handles too frequently as this is time consuming. </br>
Keep in mind that, when using atomic expressions as is done in this example, none of the commands will execute if any one fails. This is because the handle will rollback to the last savepoint or last commit if it encounters an exception. </br>

## 4. Explain analyze
explain analyze is a postgresql command used like this: `explain analyze select * from table where condition` and it 
experiment with [indeces](https://www.postgresql.org/docs/9.3/indexes.html) and use explain analyze to compare the preformance of sql statements. 


