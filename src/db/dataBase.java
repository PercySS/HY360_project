package db;

import java.sql.*;


public class dataBase {
    public static void main(String[] args) throws Exception {
        // Load the driver 
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            System.out.println("MySQL JDBC Driver Registered!");
        } catch (ClassNotFoundException e) {
            System.out.println("MySQL JDBC Driver not found.");
            e.printStackTrace();
        }

        String url = new String("jdbc:mysql://localhost");
        String databaseName = new String("test");
        int port = 3306;
        
        String username = new String("root");
        String password = new String("");
        

        try (Connection conn = DriverManager.getConnection(url + ":" + port + "/" + databaseName + "?characterEncoding=UTF-8",username, password)) {
            System.out.println("Connected to database succesfully!");
        } catch (SQLException e) {
            e.printStackTrace();
        }


        //create an example table and add some values and query back
        try (Connection conn = DriverManager.getConnection(url + ":" + port + "/" + databaseName + "?characterEncoding=UTF-8",username, password)) {
            Statement stmt = conn.createStatement();
            stmt.executeUpdate("CREATE TABLE IF NOT EXISTS test_table (id INT PRIMARY KEY, name VARCHAR(255))");
            stmt.executeUpdate("INSERT INTO test_table (id, name) VALUES (3, 'Negros')");
            stmt.executeUpdate("INSERT INTO test_table (id, name) VALUES (4, 'Dimitris')");
            ResultSet rs = stmt.executeQuery("SELECT * FROM test_table");
            while (rs.next()) {
                System.out.println(rs.getInt("id") + " " + rs.getString("name"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
