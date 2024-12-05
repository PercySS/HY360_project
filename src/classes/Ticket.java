package classes;

import java.sql.*;
import static db.dataBase.*;

public class Ticket {
    public static boolean addTicket(String Type, float Price, int Availability, int EventId) throws SQLException {
        ResultSet rs;
        StringBuilder made = new StringBuilder();
        made.append("Ticket not created: ");

        // check for valid input
        if (Price <  0 || Availability < 0 || EventId < 0 || Type == null || Type.isEmpty()) {
            System.out.println(made.append("Invalid input"));
            return false;
        }

        // check if the event exists
        rs = get("SELECT * FROM events WHERE EventId = " + EventId);
        if (!rs.next()) {
            System.out.println("Event does not exist");
            return false;
        }

        made.setLength(0);
        made.append("Ticket created:");

        if (Type.equals("VIP")) {
            addTicketVIP(Price, Availability, EventId);
        } else {
            addTicketRegular(Price, Availability, EventId);
        }
        return true;
    }

    public static void addTicketVIP(float Price, int Availability, int EventId) throws SQLException {
        update("INSERT INTO ticketsVIP (Price, Availability, EventId, BookingId, Type) VALUES (" + Price + ", " + Availability + ", " + EventId + ", NULL, 'VIP')");
    }

    public static void addTicketRegular(float Price, int Availability, int EventId) throws SQLException {
        update("INSERT INTO ticketsRegular (Price, Availability, EventId, BookingId, Type) VALUES (" + Price + ", " + Availability + ", " + EventId + ", NULL, 'Regular')");
    }

    public static boolean deleteTicket(int EventId) throws SQLException {
        ResultSet rs;
        StringBuilder made = new StringBuilder();
        made.append("Ticket not deleted: ");

        // check for valid input
        if (EventId < 0) {
            System.out.println(made.append("Invalid input"));
            return false;
        }

        // check if the event exists
        rs = get("SELECT * FROM events WHERE EventId = " + EventId);
        if (!rs.next()) {
            System.out.println(made.append("Event does not exist"));
            return false;
        }

        made.setLength(0);
        // see if it is a VIP or Regular ticket
        rs = get("SELECT * FROM ticketsVIP WHERE EventId = " + EventId);
        if (rs.next()) {
            deleteTicketVIP(rs.getInt("TicketId"));
        } else {
            rs = get("SELECT * FROM ticketsRegular WHERE EventId = " + EventId);
            if (rs.next()) {
                deleteTicketRegular(rs.getInt("TicketId"));
            }
        }

        System.out.println(made.append("Ticket deleted"));
        return true;
    }


    public static void deleteTicketVIP(int TicketId) throws SQLException {
        update("DELETE FROM ticketsVIP WHERE TicketId = " + TicketId);
    }

    public static void deleteTicketRegular(int TicketId) throws SQLException {
        update("DELETE FROM ticketsRegular WHERE TicketId = " + TicketId);
    }
}
