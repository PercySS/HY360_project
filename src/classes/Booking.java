package classes;

import java.sql.*;
import java.time.LocalDate;

import static db.dataBase.*;

public class Booking {

    public static boolean addBooking(int CustomerId, Date Date, int EventId, int tReg, int tVIP) throws SQLException {
        ResultSet rs;
        StringBuilder made = new StringBuilder();
        int availableTickets = 0;
        float cost = 0;
        made.append("Booking not created: ");

        // check if the customer exists
        rs = get("SELECT * FROM customers WHERE CustomerId = " + CustomerId);
        if (!rs.next()) {
            System.out.println(made.append("Customer does not exist"));
            return false;
        }

        // check if the event exists
        rs = get("SELECT * FROM events WHERE EventId = " + EventId);
        if (!rs.next()) {
            System.out.println(made.append("Event does not exist"));
            return false;
        }

        // check if there are enough tickets available
        // go into the tickets vip/regular and based on the eventid check for null bookingid if i have enough ticketts make the booking
        rs = get("SELECT * FROM ticketsRegular WHERE EventId = " + EventId + " AND BookingId IS NULL AND Availability = 1");
        if (rs.last()) {
            if (rs.getRow() < tReg) {
                System.out.println(made.append("Not enough Regular tickets available"));
                return false;
            }
        }

        rs = get("SELECT * FROM ticketsVIP WHERE EventId = " + EventId + " AND BookingId IS NULL AND Availability = 1");
        if (rs.last()) {
            if (rs.getRow() < tVIP) {
                System.out.println(made.append("Not enough VIP tickets available"));
                return false;
            }
        }
        // make the booking
        rs = get("SELECT * FROM ticketsRegular WHERE EventId = " + EventId + " AND BookingId IS NULL AND Availability = 1");
        for (int i = 0; i < tReg; i++) {
            rs.next();
            update("UPDATE ticketsRegular SET Availability = 0, BookingId = " + rs.getInt("TicketId") + " WHERE TicketId = " + rs.getInt("TicketId"));
            cost += rs.getFloat("Price");
        }

        rs = get("SELECT * FROM ticketsVIP WHERE EventId = " + EventId + " AND BookingId IS NULL AND Availability = 1");
        for (int i = 0; i < tVIP; i++) {
            rs.next();
            update("UPDATE ticketsVIP SET Availability = 0, BookingId = " + rs.getInt("TicketId") + " WHERE TicketId = " + rs.getInt("TicketId"));
            cost += rs.getFloat("Price");
        }

        // calculate the cost based on the tickets price column

        made.setLength(0);
        made.append("Booking created: ");



        update("INSERT INTO bookings (BookingDate, CustomerId, EventId, tReg, tVIP, Cost) VALUES ('" + Date + "', " + CustomerId + ", " + EventId + ", " + tReg + ", " + tVIP + ", " + cost + ")");
        return true;
    }

    public static boolean deleteBooking(int BookingId) throws SQLException {
        // check if the booking exists
        ResultSet rs = get("SELECT * FROM bookings WHERE BookingId = " + BookingId);
        if (!rs.next()) {
            System.out.println("Booking does not exist");
            return false;
        }

        // make all tickets included in this booking available again
        rs = get("SELECT * FROM ticketsRegular WHERE BookingId = " + BookingId);
        while (rs.next()) {
            update("UPDATE ticketsRegular SET Availability = 1, BookingId = NULL WHERE TicketId = " + rs.getInt("TicketId"));
        }

        rs = get("SELECT * FROM ticketsVIP WHERE BookingId = " + BookingId);
        while (rs.next()) {
            update("UPDATE ticketsVIP SET Availability = 1, BookingId = NULL WHERE TicketId = " + rs.getInt("TicketId"));
        }

        update("DELETE FROM bookings WHERE BookingId = " + BookingId);
        return true;
    }

    public static void availableTickets(int EventId, int Type) throws SQLException {
        ResultSet rs;
        if (Type == 1) {
            rs = get("SELECT * FROM ticketsRegular WHERE EventId = " + EventId + " AND BookingId IS NULL AND Availability = 1");
        } else {
            rs = get("SELECT * FROM ticketsVIP WHERE EventId = " + EventId + " AND BookingId IS NULL AND Availability = 1");
        }

        // store the total available tickets
        int total = rs.getFetchSize();
    }




}
