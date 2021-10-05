

import obsolete.DataSourceConnector;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.*;
// 1.
// no multivalued attributes.
// 2.
// every attribute is dependent on entire key, not just a part of it
// 3.
// every attribute is only dependent on the key, not on non-UID attributes

// Explain Analyse
// indexes
// squirl sql
// github with readme
//


public class Main {

    public static void main(String[] args) throws IOException, SQLException {
        String userName = "root";
        String password = "password";
        String dbms="postgresql";
        String serverName="localhost";
        int portNumber=5432;
        String dbName="postgres";
        //createCoffeeExample(userName, password, dbms, serverName, portNumber, dbName);

        DownloadIMDB downloadIMDB = new DownloadIMDB();
        //DownloadIMDB.read("title.basics.tsv.gz");
        //DownloadIMDB.read("title.episode.tsv.gz");
        //DownloadIMDB.read("title.crew.tsv.gz");
        //DownloadIMDB.read("name.basics.tsv.gz");
        //DownloadIMDB.read("title.principals.tsv.gz");
        JdbiConnector connector = new JdbiConnector();
        //createCoffeeExample(userName, password, dbms, serverName, portNumber, dbName);
    }


    public static void createCoffeeExample(String userName, String password, String dbms, String serverName, int portNumber, String dbName) throws SQLException, IOException {
        DriverConnections newConnector = new DriverConnections();
        newConnector.establishConnection(userName, password, dbms, serverName, portNumber, dbName);

        try (BufferedReader br = new BufferedReader(new FileReader("src/main/java/SQLcommands.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                newConnector.applyCommand(line);
            }
        }
        newConnector.viewTable();

    }





}










