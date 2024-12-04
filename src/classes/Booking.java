package classes;

import java.sql.*;
import java.util.Date;

import static db.dataBase.*;

public class Booking {

    public static void addBooking(Date BookingDate, int CustomerId, int EventId, int tReg, int tVIP) throws SQLException {
        // check if there are enough tickets available
        float cost = 0;
        // TODO: check if the event is in the past
        // TODO: check if there are enough tickets available

        update("INSERT INTO bookings (BookingDate, CustomerId, EventId, tReg, tVIP, Cost) VALUES ('" + BookingDate + "', " + CustomerId + ", " + EventId + ", " + tReg + ", " + tVIP + ", " + cost + ")");
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
