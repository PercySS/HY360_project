package db;



import java.sql.*;


public class dataBase {
    static Connection connection = null;
    private final static String driver = "com.mysql.cj.jdbc.Driver";
    private final static String url = "jdbc:mysql://localhost";
    private final static String user = "root";
    private final static String password = "";
    private final static String dbName = "agency_db";
    private final static int port = 3306;


    // ======== DataBase Methods ========
    public static void createDb() {
        try {
            Class.forName(driver);
            Connection con = DriverManager.getConnection(url + ":" + port + "/", user, password);
            Statement stm = con.createStatement();
            stm.executeUpdate("DROP DATABASE IF EXISTS agency_db");
            stm.executeUpdate("CREATE DATABASE IF NOT EXISTS agency_db");
            System.out.println("Database created");
        } catch (Exception e) {
            System.out.println("Error: " + e);
        }
    }

    public static Connection update(String query) {
        try {
            Class.forName(driver);
            if (connection == null) {
                connection = DriverManager.getConnection(url + ":" + port + "/" + dbName + "?characterEncoding=UTF-8", user, password);
            }
            Statement stm = connection.createStatement();
            stm.executeUpdate(query);
            System.out.println("Database updated");
            return connection;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static ResultSet get(String query) {
        try {
            Class.forName(driver);
            if (connection == null) {
                connection = DriverManager.getConnection(url + ":" + port + "/" + dbName + "?characterEncoding=UTF-8", user, password);
            }
            Statement stm = connection.createStatement();
            ResultSet rs = stm.executeQuery(query);
            System.out.println("Got from DB");
            return rs;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static void dropDb() {
        update("DROP DATABASE IF EXISTS agency_db");
    }

    public void close() {
        try {
            connection.close();
        } catch (Exception e) {
            System.out.println("Error: " + e);
        }
    }

    public static void initDb() {
        update("CREATE TABLE IF NOT EXISTS events (" +
                "EventId INT AUTO_INCREMENT PRIMARY KEY," +
                "Name VARCHAR(255)," +
                "Date DATE," +
                "Time TIME," +
                "Type VARCHAR(255)," +
                "Capacity INT," +
                "tReg INT," +
                "tVIP INT," +
                "Price DEC(10, 2)" +
                ")");

        update("CREATE TABLE IF NOT EXISTS customers (" +
                "CustomerId INT AUTO_INCREMENT PRIMARY KEY," +
                "FullName VARCHAR(255)," +
                "Email VARCHAR(255)," +
                "CreditCardInfo VARCHAR(255)" +
                ")");

        update("CREATE TABLE IF NOT EXISTS bookings (" +
                "BookingId INT AUTO_INCREMENT PRIMARY KEY," +
                "BookingDate DATE," +
                "CustomerId INT," +
                "EventId INT," +
                "tReg INT," +
                "tVIP INT," +
                "Cost DEC(10, 2)," +
                "FOREIGN KEY (CustomerId) REFERENCES customers(CustomerId) ON DELETE CASCADE," +
                "FOREIGN KEY (EventId) REFERENCES events(EventId) ON DELETE CASCADE" +
                ")");

        update("CREATE TABLE IF NOT EXISTS ticketsVIP (" +
                "TicketId INT AUTO_INCREMENT PRIMARY KEY," +
                "Type VARCHAR(255)," +
                "Price DEC(10, 2)," +
                "Availability INT," +
                "EventId INT," +
                "BookingId INT," +
                "FOREIGN KEY (EventId) REFERENCES events(EventId) ON DELETE CASCADE," +
                "FOREIGN KEY (BookingId) REFERENCES bookings(BookingId) ON DELETE CASCADE" +
                ")");

        update("CREATE TABLE IF NOT EXISTS ticketsRegular (" +
                "TicketId INT AUTO_INCREMENT PRIMARY KEY," +
                "Type VARCHAR(255)," +
                "Price DEC(10, 2)," +
                "Availability INT," +
                "EventId INT," +
                "BookingId INT," +
                "FOREIGN KEY (EventId) REFERENCES events(EventId) ON DELETE CASCADE," +
                "FOREIGN KEY (BookingId) REFERENCES bookings(BookingId) ON DELETE CASCADE" +
                ")");
    }

    public static void dropTable(String tableName) {
        update("DROP TABLE IF EXISTS " + tableName);
    }

    public static void cleanTable(String tableName) {
        update("DELETE * FROM " + tableName);
    }



}
