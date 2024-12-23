package classes;

import javax.swing.*;
import java.sql.*;

import static classes.Booking.deleteBooking;
import static classes.Ticket.*;
import static db.dataBase.*;

public class Event {

    public static boolean addEvent(String Name, Date Date, Time Time, String Type, int Capacity, int tReg, int tVIP, float Price) throws SQLException {
        ResultSet rs;
        StringBuilder made = new StringBuilder();
        made.append("Event not created: ").append(Name);
        // check for valid input
        if (Name == null || Date == null || Time == null || Type == null || Capacity <= 0 || tReg < 0 || tVIP < 0 || Price < 0) {
            System.out.println(made.append("Invalid input"));
            return false;
        }

        // check if the event is in the past
        Date today = new Date(System.currentTimeMillis());
        if (Date.before(today)) {
            System.out.println(made.append("Invalid Date"));
            return false;
        }

        // check for valid time
        if (Time.before(java.sql.Time.valueOf("00:00:00")) || Time.after(java.sql.Time.valueOf("23:59:59"))) {
            made.append("Invalid Time");
            JOptionPane.showMessageDialog(null, made, "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        // check if there are enough tickets available
        if (tReg + tVIP != Capacity) {
            made.append("Invalid number of tickets Regular tickets and VIP tickets must be equal to capacity");
            JOptionPane.showMessageDialog(null, made, "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        // check if the event is already in the database
        rs = get("SELECT * FROM events WHERE Name = '" + Name + "' AND Date = '" + Date + "' AND Time = '" + Time + "'");
        if (rs.next()) {
            made.append("Event already exists");
            JOptionPane.showMessageDialog(null, made, "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }


        made.setLength(0);
        made.append("Event created: ").append(Name);
        int generatedId = -1;
        update("INSERT INTO events (Name, Date, Time, Type, Capacity, tReg, tVIP, Price) VALUES ('" + Name + "', '" + Date + "', '" + Time + "', '" + Type + "', " + Capacity + ", " + tReg + ", " + tVIP + ", " + Price + ")");
        // I get the last generated id from the database
        rs = get("SELECT * FROM events ORDER BY EventId DESC LIMIT 1");
        if (rs.next()) {
            generatedId = rs.getInt("EventId");
        }

        for (int i = 0; i < tReg; i++) {
            addTicketRegular(Price, 1, generatedId);
        }

        for (int i = 0; i < tVIP; i++) {
            addTicketVIP(3*Price, 1, generatedId);
        }

        JOptionPane.showMessageDialog(null, made, "Event created", JOptionPane.INFORMATION_MESSAGE);
        return true;
    }

    public static boolean deleteEvent(int EventId) throws SQLException {
        // check if the event exists
        ResultSet rs = get("SELECT * FROM events WHERE EventId = " + EventId);
        if (!rs.next()) {
            JOptionPane.showMessageDialog(null, "Event not found", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        // delete all bookings for this event
        rs = get("SELECT * FROM bookings WHERE EventId = " + EventId);
        while (rs.next()) {
            deleteBooking(rs.getInt("BookingId"));
        }

        // delete all tickets for this event
        rs = get("SELECT * FROM ticketsRegular WHERE EventId = " + EventId);
        while (rs.next()) {
            deleteTicket(rs.getInt("EventId"));
        }

        rs = get("SELECT * FROM ticketsVIP WHERE EventId = " + EventId);
        while (rs.next()) {
            deleteTicketVIP(rs.getInt("EventId"));
        }

        update("DELETE FROM events WHERE EventId = " + EventId);

        JOptionPane.showMessageDialog(null, "Event deleted", "Event deleted", JOptionPane.INFORMATION_MESSAGE);
        return true;
    }

    public static int availableTickets(int EventId, int Type) throws SQLException {
        ResultSet rs;
        int count = 0;

        if (Type == 1) {
            rs = get("SELECT * FROM ticketsRegular WHERE EventId = " + EventId + " AND Availability = 1");
        } else {
            rs = get("SELECT * FROM ticketsVIP WHERE EventId = " + EventId + " AND Availability = 1");
        }

        while (rs.next()) {
            count++;
        }

        return count;
    }


}
