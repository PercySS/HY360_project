package classes;

import java.sql.*;
import java.util.Date;

import static db.dataBase.*;

public class Booking {

    public static void addBooking(Date BookingDate, int CustomerId, int EventId, int tReg, int tVIP) throws SQLException {
        for (int i = 0; i < tReg; i++) {
            // check if there are tReg tickets available for event EventId
            ResultSet rs = get("SELECT * FROM ticketsRegular WHERE EventId = " + EventId + " AND Availability = 1");

        }
        update("INSERT INTO bookings (BookingDate, CustomerId, EventId, tReg, tVIP) VALUES ('" + BookingDate + "', " + CustomerId + ", " + EventId + ", " + tReg + ", " + tVIP + ")");
    }

    public static void deleteBooking(Connection conn, int BookingId) throws SQLException {
        // make all tickets included in this booking available again
        ResultSet rs = get("SELECT * FROM ticketsRegular WHERE BookingId = " + BookingId);
        while (rs.next()) {
            update("UPDATE ticketsRegular SET Availability = 1, BookingId = NULL WHERE TicketId = " + rs.getInt("TicketId"));
        }

        rs = get("SELECT * FROM ticketsVIP WHERE BookingId = " + BookingId);
        while (rs.next()) {
            update("UPDATE ticketsVIP SET Availability = 1, BookingId = NULL WHERE TicketId = " + rs.getInt("TicketId"));
        }

        update("DELETE FROM bookings WHERE BookingId = " + BookingId);
    }




}
