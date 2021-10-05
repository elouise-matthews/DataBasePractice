import org.postgresql.util.PSQLException;

import java.awt.*;
import java.sql.*;
import java.util.Properties;

public class DriverConnections {
    private static Connection conn;

    public void establishConnection(String userName, String password, String dbms, String serverName, int portNumber, String dbName) throws SQLException {
        Properties connectionProps = new Properties();
        connectionProps.put("user", userName);
        connectionProps.put("password", password);
        this.conn = DriverManager.getConnection(
                "jdbc:" + dbms + "://" +
                        serverName +
                        ":" + portNumber + "/"+dbName,
                connectionProps);
        this.conn.setAutoCommit(true);
        System.out.println("Connected to database");
    }

    public void applyCommand (String command ) throws SQLException {
        Statement stmt = null;
        if (conn != null) {
            try {
                stmt = conn.createStatement();
                stmt.execute(command);
            } catch (PSQLException e){
                System.out.println("operation not possible: "+ command.substring(0, 40));
            }
        }
        else {
            System.out.println("No connection");
        }
    }

    public void viewTable() throws SQLException {
        String query = "select COF_NAME, SUP_ID, PRICE, SALES, TOTAL from COFFEES";
        try (Statement stmt = this.conn.createStatement()) {
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                String coffeeName = rs.getString("COF_NAME");
                int supplierID = rs.getInt("SUP_ID");
                float price = rs.getFloat("PRICE");
                int sales = rs.getInt("SALES");
                int total = rs.getInt("TOTAL");
                System.out.println(coffeeName + ", " + supplierID + ", " + price +
                        ", " + sales + ", " + total);
            }
        } catch (PSQLException e) {
            System.out.println("operation not possible");
        }
    }


    public void preparedCommand (String command ) throws SQLException {

    }

}
