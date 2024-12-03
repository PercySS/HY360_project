package gui;

import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import static classes.Event.*;
import static classes.Booking.*;
import static classes.Customer.*;
import static classes.Ticket.*;

import java.io.IOException;

import static db.dataBase.*;



public class Main {
    public static void main(String[] args) throws IOException, SQLException, ParseException {
        createDb();
        initDb();

        // make a customer
        addCustomer(1, "John Doe", "niggerbitch@gmail.com", "1234-5678-9101-1121");
        addCustomer(2, "Jane Doe", "fuckaniggabitch@hotmail.com", "1234-5678-9101-1121");

        // make an event with 10 capacity

        addEvent(1, "Event1", java.sql.Date.valueOf("2024-02-13"), java.sql.Time.valueOf("12:00:00"), "Concert", 100);

        // make a booking for customer 1 for event 1 with 5 regular tickets and 2 VIP tickets
        addBooking(1, java.sql.Date.valueOf("2024-02-13"), 1, 1, 5, 2);

    }
}
