package gui;

import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import static classes.Event.*;
import static classes.Booking.*;
import static classes.Customer.*;
import static classes.Ticket.*;

import java.io.IOException;
import java.util.Scanner;

import static db.dataBase.*;



public class Main {
    public static void main(String[] args) throws IOException, SQLException, ParseException {
        createDb();
        initDb();

        // make 2 customers
        addCustomer("John Doe", "kati@gmail.com", "1234");
        addCustomer("Jane Doe", "peripou@gmail.com", "5678");

        // make an event with 10 capacity
        addEvent("Event 1", java.sql.Date.valueOf("2021-12-12"), java.sql.Time.valueOf("12:00:00"), "Concert", 100, 60, 40);

        // make a booking for customer 1 for event 1 with 5 regular tickets and 2 VIP tickets
        addBooking(java.sql.Date.valueOf("2021-12-12"), 1, 1, 5, 2);

        // make a booking for customer 2 for event 1 with 3 regular tickets and 1 VIP ticket
        addBooking(java.sql.Date.valueOf("2021-12-12"), 2, 1, 3, 1);

        // wait for user to press something and then delete the event
        Scanner scanner = new Scanner(System.in);
        System.out.println("Press any key to delete the event");
        scanner.nextLine();
        deleteEvent(1);

    }
}
