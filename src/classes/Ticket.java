package classes;

import java.sql.*;
import static db.dataBase.*;

public class Ticket {
    public static void addTicketVIP(float Price, int Availability, int EventId) throws SQLException {
        update("INSERT INTO ticketsVIP (Price, Availability, EventId, BookingId) VALUES (" + Price + ", " + Availability + ", " + EventId + ", NULL)");
    }

    public static void addTicketRegular(float Price, int Availability, int EventId) throws SQLException {
        update("INSERT INTO ticketsRegular (Price, Availability, EventId, BookingId) VALUES (" + Price + ", " + Availability + ", " + EventId + ", NULL)");
    }

    public static void deleteTicketVIP(int TicketId) throws SQLException {
        update("DELETE FROM ticketsVIP WHERE TicketId = " + TicketId);
    }

    public static void deleteTicketRegular(int TicketId) throws SQLException {
        update("DELETE FROM ticketsRegular WHERE TicketId = " + TicketId);
    }

    public static void updateTicketsPriceVIP(int EventId, float Price) throws SQLException {
        update("UPDATE ticketsVIP SET Price = " + Price + " WHERE EventId = " + EventId);
    }

    public static void updateTicketsPriceRegular(int EventId, float Price) throws SQLException {
        update("UPDATE ticketsRegular SET Price = " + Price + " WHERE EventId = " + EventId);
    }

    public static void updateTicketsAvailabilityVIP(int EventId, int Availability, int BookingId) throws SQLException {
        update("UPDATE ticketsVIP SET Availability = " + Availability + ", BookingId = " + BookingId + " WHERE EventId = " + EventId);

    }

    public static void updateTicketsAvailabilityRegular(int EventId, int Availability, int BookingId) throws SQLException {
        update("UPDATE ticketsRegular SET Availability = " + Availability + ", BookingId = " + BookingId + " WHERE EventId = " + EventId);
    }
}
