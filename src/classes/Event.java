package classes;

import java.sql.*;

import static classes.Ticket.*;
import static db.dataBase.*;

public class Event {

    public static void addEvent(String Name, Date Date, Time Time, String Type, int Capacity, int tReg, int tVIP) throws SQLException {
        int generatedId = -1;
        ResultSet rs;
        // I use my update to insert the data into the database from the db.database class
        update("INSERT INTO events (Name, Date, Time, Type, Capacity, tReg, tVIP) VALUES ('" + Name + "', '" + Date + "', '" + Time + "', '" + Type + "', " + Capacity + ", " + tReg + ", " + tVIP + ")");
        // I get the last generated id from the database
        rs = get("SELECT * FROM events ORDER BY EventId DESC LIMIT 1");
        if (rs.next()) {
            generatedId = rs.getInt("EventId");
        }

        for (int i = 0; i < tReg; i++) {
            addTicketRegular(100, 1, generatedId);
        }

        for (int i = 0; i < tVIP; i++) {
            addTicketVIP(200, 1, generatedId);
        }
    }

    public static void deleteEvent(int EventId) throws SQLException {
        // reduce the tReg and tVIP tickets from the bookings
        ResultSet rs = get("SELECT * FROM bookings WHERE EventId = " + EventId);
        while (rs.next()) {
            update("UPDATE bookings SET tReg = 0, tVIP = 0 WHERE BookingId = " + rs.getInt("BookingId"));
        }

        // delete all tickets for this event
        rs = get("SELECT * FROM ticketsRegular WHERE EventId = " + EventId);
        while (rs.next()) {
            deleteTicketRegular(rs.getInt("TicketId"));
        }

        rs = get("SELECT * FROM ticketsVIP WHERE EventId = " + EventId);
        while (rs.next()) {
            deleteTicketVIP(rs.getInt("TicketId"));
        }

        update("DELETE FROM events WHERE EventId = " + EventId);
    }

}
