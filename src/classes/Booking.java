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

    public static void addBooking(int BookingId, Date BookingDate, int CustomerId, int EventId, int tReg, int tVIP) throws SQLException {
        update("INSERT INTO bookings VALUES (" + BookingId + ", '" + BookingDate + "', " + CustomerId + ", " + EventId + ", " + tReg + ", " + tVIP + ")");
    }

    public static void deleteBooking(Connection conn, int BookingId) throws SQLException {
        update("DELETE FROM bookings WHERE BookingId = " + BookingId);
    }




}
