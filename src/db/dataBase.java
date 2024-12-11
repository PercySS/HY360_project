    package db;
    
    
    
    import javax.swing.*;
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
    
        public static int[] booked_availableTickets(int EventId) throws SQLException {
            ResultSet rs = get("SELECT * FROM events WHERE EventId = " + EventId);
            rs.next();
            int tReg = rs.getInt("tReg");
            int tVIP = rs.getInt("tVIP");
    
            rs = get("SELECT * FROM bookings WHERE EventId = " + EventId);
            while (rs.next()) {
                tReg -= rs.getInt("tReg");
                tVIP -= rs.getInt("tVIP");
            }
    
            return new int[]{tReg, tVIP};
        }
    
    
        public static float eventIncome(int EventId) throws SQLException {
            ResultSet rs = get("SELECT * FROM bookings WHERE EventId = " + EventId);
            float income = 0;
            while (rs.next()) {
                income += rs.getFloat("Cost");
            }
    
            return income;
        }
    
        public static String mostPopularEvent() throws SQLException {
            // most popular event based on bookings
            ResultSet rs = get("SELECT EventId, COUNT(EventId) AS count FROM bookings GROUP BY EventId ORDER BY count DESC LIMIT 1");
            rs.next();
            int eventId = rs.getInt("EventId");
    
            rs = get("SELECT * FROM events WHERE EventId = " + eventId);
            rs.next();
            return rs.getString("Name");
        }
    
        public static String mostProfitableEvent(Date d1, Date d2) throws SQLException {
            // most profitable event based in a certain time period
    
            // check if d1 pre dates d2
            if (d1.after(d2)) {
                JOptionPane.showMessageDialog(null, "Invalid date range", "Error", JOptionPane.ERROR_MESSAGE);
                return null;
            }
    
            ResultSet rs = get("SELECT EventId, SUM(Cost) AS sum FROM bookings WHERE BookingDate BETWEEN '" + d1 + "' AND '" + d2 + "' GROUP BY EventId ORDER BY sum DESC LIMIT 1");
            rs.next();
            int eventId = rs.getInt("EventId");
    
            rs = get("SELECT * FROM events WHERE EventId = " + eventId);
            rs.next();
            return rs.getString("Name");
        }
    
        public static float[] profitsTickets(int EventId) throws SQLException {
            float profitR = 0, profitV = 0;
    
            ResultSet rs;
            if (EventId == 0) {
                // all events
                rs = get("SELECT * FROM ticketsRegular WHERE AVAILABILITY = 0");
                while (rs.next()) {
                    profitR += rs.getFloat("Price");
                }
    
                rs = get("SELECT * FROM ticketsVIP WHERE AVAILABILITY = 0");
            } else {
                // one event
                rs = get("SELECT * FROM ticketsRegular WHERE EventId = " + EventId + " AND AVAILABILITY = 0");
                while (rs.next()) {
                    profitR += rs.getFloat("Price");
                }
    
                rs = get("SELECT * FROM ticketsVIP WHERE EventId = " + EventId + " AND AVAILABILITY = 0");
            }
    
            while (rs.next()) {
                profitV += rs.getFloat("Price");
            }
    
            return new float[]{profitR, profitV};
        }

        public static boolean bookingsInTimeSpan(Date d1, Date d2) throws SQLException {
            // check if dates not null
            if (d1 == null || d2 == null) {
                JOptionPane.showMessageDialog(null, "Invalid date range", "Error", JOptionPane.ERROR_MESSAGE);
                return false;
            }

            //check valid input
            if (d1.after(d2)) {
                JOptionPane.showMessageDialog(null, "Invalid date range", "Error", JOptionPane.ERROR_MESSAGE);
                return false;
            }

            ResultSet rs = get("SELECT * FROM bookings WHERE BookingDate BETWEEN '" + d1 + "' AND '" + d2 + "'");
            String[] bookings = new String[100];
            int i = 0, id;
            while (rs.next()) {
                bookings[i] = "Booking ID: " + rs.getInt("BookingId") + " | Customer ID: " + rs.getInt("CustomerId") + " | Event ID: " + rs.getInt("EventId") + " | Date: " + rs.getDate("BookingDate") + " | Cost: " + rs.getFloat("Cost") + "\n";
                i++;
            }

            if (i == 0) {
                JOptionPane.showMessageDialog(null, "No bookings in this time span", "Bookings", JOptionPane.INFORMATION_MESSAGE);
                return false;
            }

            JOptionPane.showMessageDialog(null, bookings, "Bookings", JOptionPane.INFORMATION_MESSAGE);
            return true;
        }
    
    
    }
