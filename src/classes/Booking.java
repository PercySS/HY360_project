package classes;

import java.sql.*;
import java.util.Date;

import static db.dataBase.*;

public class Booking {
    private int BookingId;
    private Date BookingDate;
    private int CustomerId;
    private int EventId;

    // ==== Constructors ====
    public Booking(int BookingId, Date BookingDate, int CustomerId, int EventId) {
        this.BookingId = BookingId;
        this.BookingDate = BookingDate;
        this.CustomerId = CustomerId;
        this.EventId = EventId;
    }

    // ==== Setters ====

    public void setBookingId(int BookingId) {
        this.BookingId = BookingId;
    }

    public void setBookingDate(Date BookingDate) {
        this.BookingDate = BookingDate;
    }

    public void setCustomerId(int CustomerId) {
        this.CustomerId = CustomerId;
    }

    public void setEventId(int EventId) {
        this.EventId = EventId;
    }

    // ==== Getters ====
    public int getBookingId() {
        return BookingId;
    }

    public Date getBookingDate() {
        return BookingDate;
    }

    public int getCustomerId() {
        return CustomerId;
    }

    public int getEventId() {
        return EventId;
    }

    public static void addBooking(Connection conn, int BookingId, Date BookingDate, int CustomerId, int EventId) throws SQLException {
        String query = "INSERT INTO Booking VALUES (?, ?, ?, ?)";
        PreparedStatement stmt = conn.prepareStatement(query);
        stmt.setInt(1, BookingId);
        stmt.setDate(2, (java.sql.Date) BookingDate);
        stmt.setInt(3, CustomerId);
        stmt.setInt(4, EventId);
        stmt.executeUpdate();
    }

    public static void deleteBooking(Connection conn, int BookingId) throws SQLException {
        String query = "DELETE FROM Booking WHERE BookingId = ?";
        PreparedStatement stmt = conn.prepareStatement(query);
        stmt.setInt(1, BookingId);
        stmt.executeUpdate();
    }




}
