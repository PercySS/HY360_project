package classes;

import jdk.nashorn.internal.scripts.JO;

import javax.swing.*;
import java.sql.*;
import java.time.LocalDate;

import static db.dataBase.*;

public class Booking {

    public static boolean addBooking(int CustomerId, Date Date, int EventId, int tReg, int tVIP) throws SQLException {
        ResultSet rs;
        StringBuilder made = new StringBuilder();
        int availableRegular = 0, availableVIP = 0;
        float cost = 0;
        made.append("Booking not created: ");

        // check if the customer exists
        rs = get("SELECT * FROM customers WHERE CustomerId = " + CustomerId);
        if (!rs.next()) {
            JOptionPane.showMessageDialog(null, made.append("Customer does not exist"), "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        // check if the event exists
        rs = get("SELECT * FROM events WHERE EventId = " + EventId);
        if (!rs.next()) {
            JOptionPane.showMessageDialog(null, made.append("Event does not exist"), "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        // check if there enough tickets available
        rs = get("SELECT * FROM ticketsRegular WHERE EventId = " + EventId + " AND Availability = 1");
        while (rs.next()) {
            availableRegular++;
        }

        rs = get("SELECT * FROM ticketsVIP WHERE EventId = " + EventId + " AND Availability = 1");
        while (rs.next()) {
            availableVIP++;
        }

        if (tReg > availableRegular || tVIP > availableVIP) {
            JOptionPane.showMessageDialog(null, made.append("Not enough tickets available"), "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        update("INSERT INTO bookings (BookingDate, CustomerId, EventId, tReg, tVIP, Cost) VALUES ('" + Date + "', " + CustomerId + ", " + EventId + ", " + tReg + ", " + tVIP + ", " + cost + ")");
        // store the booking id from last added booking
        rs = get("SELECT * FROM bookings ORDER BY BookingId DESC LIMIT 1");
        rs.next();
        int Boo = rs.getInt("BookingId");


        // make tickets unavailable and assign them to the booking and add the cost to the booking cost
        rs = get("SELECT * FROM ticketsRegular WHERE EventId = " + EventId + " AND Availability = 1 LIMIT " + tReg);
        while (rs.next()) {
            update("UPDATE ticketsRegular SET Availability = 0, BookingId = " + Boo + " WHERE TicketId = " + rs.getInt("TicketId"));
            cost += rs.getFloat("Price");
        }

        rs = get("SELECT * FROM ticketsVIP WHERE EventId = " + EventId + " AND Availability = 1 LIMIT " + tVIP);
        while (rs.next()) {
            update("UPDATE ticketsVIP SET Availability = 0, BookingId = " + Boo + " WHERE TicketId = " + rs.getInt("TicketId"));
            cost += rs.getFloat("Price");
        }

        JOptionPane.showMessageDialog(null, "Booking added successfully", "Success", JOptionPane.INFORMATION_MESSAGE);
        update("UPDATE bookings SET Cost = " + cost + " WHERE BookingId = " + Boo);

        return true;
    }

    public static boolean deleteBooking(int BookingId) throws SQLException {
        // check if the booking exists
        ResultSet rs = get("SELECT * FROM bookings WHERE BookingId = " + BookingId);
        if (!rs.next()) {
            JOptionPane.showMessageDialog(null, "Booking does not exist", "Error", JOptionPane.ERROR_MESSAGE);
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


        JOptionPane.showMessageDialog(null, "Booking deleted successfully", "Success", JOptionPane.INFORMATION_MESSAGE);
        update("DELETE FROM bookings WHERE BookingId = " + BookingId);
        return true;
    }





}
