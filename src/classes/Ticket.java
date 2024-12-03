package classes;

import java.sql.*;

public class Ticket {
    private int TicketId;
    private float Price;
    private int Availability;

    // ==== Setters ====
    public void setTicketId(int TicketId) {
        this.TicketId = TicketId;
    }
    public void setPrice(float Price) {
        this.Price = Price;
    }
    public void setAvailability(int Availability) {
        this.Availability = Availability;
    }

    // ==== Getters ====
    public int getTicketId() {
        return TicketId;
    }
    public float getPrice() {
        return Price;
    }
    public int getAvailability() { return Availability; }

    public static void addTicketVIP(Connection conn, int TicketId, float Price, int Availability, int EventId) throws SQLException {
        String query = "INSERT INTO ticketsVIP VALUES (?, ?, ?, ?)";
        PreparedStatement stmt = conn.prepareStatement(query);
        stmt.setInt(1, TicketId);
        stmt.setFloat(2, Price);
        stmt.setInt(3, Availability);
        stmt.setInt(4, EventId);
        stmt.executeUpdate();
    }

    public static void addTicketRegular(Connection conn, int TicketId, float Price, int Availability, int EventId) throws SQLException {
        String query = "INSERT INTO ticketsRegular VALUES (?, ?, ?, ?)";
        PreparedStatement stmt = conn.prepareStatement(query);
        stmt.setInt(1, TicketId);
        stmt.setFloat(2, Price);
        stmt.setInt(3, Availability);
        stmt.setInt(4, EventId);
        stmt.executeUpdate();
    }

    public static void deleteTicketVIP(Connection conn, int TicketId) throws SQLException {
        String query = "DELETE FROM ticketsVIP WHERE TicketId = ?";
        PreparedStatement stmt = conn.prepareStatement(query);
        stmt.setInt(1, TicketId);
        stmt.executeUpdate();
    }

    public static void deleteTicketRegular(Connection conn, int TicketId) throws SQLException {
        String query = "DELETE FROM ticketsRegular WHERE TicketId = ?";
        PreparedStatement stmt = conn.prepareStatement(query);
        stmt.setInt(1, TicketId);
        stmt.executeUpdate();
    }

    public static void updateTicketsPriceVIP(Connection con, int EventId, float Price) throws SQLException {
        String query = "UPDATE ticketsVIP SET Price = ? WHERE EventId = ?";
        PreparedStatement stmt = con.prepareStatement(query);
        stmt.setFloat(1, Price);
        stmt.setInt(2, EventId);
        stmt.executeUpdate();
    }

    public static void updateTicketsPriceRegular(Connection con, int EventId, float Price) throws SQLException {
        String query = "UPDATE ticketsRegular SET Price = ? WHERE EventId = ?";
        PreparedStatement stmt = con.prepareStatement(query);
        stmt.setFloat(1, Price);
        stmt.setInt(2, EventId);
        stmt.executeUpdate();
    }

    public static void updateTicketsAvailabilityVIP(Connection con, int EventId, int Availability) throws SQLException {
        String query = "UPDATE ticketsVIP SET Availability = ? WHERE EventId = ?";
        PreparedStatement stmt = con.prepareStatement(query);
        stmt.setInt(1, Availability);
        stmt.setInt(2, EventId);
        stmt.executeUpdate();
    }

    public static void updateTicketsAvailabilityRegular(Connection con, int EventId, int Availability) throws SQLException {
        String query = "UPDATE ticketsRegular SET Availability = ? WHERE EventId = ?";
        PreparedStatement stmt = con.prepareStatement(query);
        stmt.setInt(1, Availability);
        stmt.setInt(2, EventId);
        stmt.executeUpdate();
    }
}
