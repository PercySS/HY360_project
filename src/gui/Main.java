package gui;

import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import static classes.Event.*;
import static classes.Booking.*;
import static classes.Customer.*;
import static classes.Ticket.*;

import java.io.IOException;
import java.util.Objects;
import java.util.Scanner;

import static db.dataBase.*;



public class Main {
    public static void main(String[] args) throws IOException, SQLException, ParseException {
        createDb();
        initDb();

        // wait for user to press something and then delete the event
        Scanner scanner = new Scanner(System.in);
        scanner.nextInt();


        do {
            System.out.println("Choose an option:");
            System.out.println("0. Exit");
            System.out.println("1. Add event");
            System.out.println("2. Delete event");
            System.out.println("3. Add customer");
            System.out.println("4. Delete customer");
            System.out.println("5. Add booking");
            System.out.println("6. Delete booking");
            System.out.println("7. Available-Booked tickets for event");
            System.out.println("8. Income for event");
            System.out.println("9. Most popular event based on bookings");
            System.out.println("10. Most profitable event in a certain time period");
            System.out.println("11. Show bookings per time period");
            System.out.println("12. Show profits from VIP and Regular tickets per event or all events");


            switch (scanner.next()) {
                case "0":
                    System.out.println("Exiting...");
                    break;
                case "1":
                    System.out.println("Add event");
                    break;
                case "2":
                    System.out.println("Delete event");
                    break;
                case "3":
                    System.out.println("Add customer");
                    break;
                case "4":
                    System.out.println("Delete customer");
                    break;
                case "5":
                    System.out.println("Add booking");
                    break;
                case "6":
                    System.out.println("Delete booking");
                    break;
                case "7":
                    System.out.println("Available-Booked tickets for event");
                    break;
                case "8":
                    System.out.println("Income for event");
                    break;
                case "9":
                    System.out.println("Most popular event based on bookings");
                    break;
                case "10":
                    System.out.println("Most profitable event in a certain time period");
                    break;
                case "11":
                    System.out.println("Show bookings per time period");
                    break;
                case "12":
                    System.out.println("Show profits from VIP and Regular tickets per event or all events ");
                    break;
                default:
                    System.out.println("Invalid input");
                    break;
            }
        } while (scanner.nextInt() != 0);

    }
}
