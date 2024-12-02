package classes;

import java.sql.*;


public class dataBase {
    Statement stm;
    Connection con = null;

    public dataBase() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/restaurant", "root", "");
            stm = con.createStatement();
        } catch (Exception e) {
            System.out.println("Error: " + e);
        }
    }

    public ResultSet query(String sql) {
        try {
            return stm.executeQuery(sql);
        } catch (Exception e) {
            System.out.println("Error: " + e);
            return null;
        }
    }

    public int update(String sql) {
        try {
            return stm.executeUpdate(sql);
        } catch (Exception e) {
            System.out.println("Error: " + e);
            return 0;
        }
    }

    public void close() {
        try {
            con.close();
        } catch (Exception e) {
            System.out.println("Error: " + e);
        }
    }
}
