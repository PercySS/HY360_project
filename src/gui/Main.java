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
        String option = " ";



        while (!Objects.equals(option, "0")) {
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

            option = scanner.nextLine();

            switch (option) {
                case "0":
                    System.out.println("Exiting...");
                    break;
                case "1":
                    System.out.println("===== Add event =====");
                    // take input for event
                    System.out.println("Enter event name:");
                    String name = scanner.nextLine();
                    System.out.println("Enter event date (yyyy-MM-dd):");
                    String date = scanner.nextLine();
                    System.out.println("Enter event time (HH:mm:ss):");
                    String time = scanner.nextLine();
                    System.out.println("Enter event type:");
                    String type = scanner.nextLine();
                    System.out.println("Enter event capacity:");
                    int capacity = scanner.nextInt();
                    System.out.println("Enter number of regular tickets:");
                    int tReg = scanner.nextInt();
                    System.out.println("Enter number of VIP tickets:");
                    int tVIP = scanner.nextInt();
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                    java.util.Date date1 = sdf.parse(date);
                    java.sql.Date sqlDate = new java.sql.Date(date1.getTime());

                    System.out.println("Enter event basic price:");
                    float price = scanner.nextFloat();

                    if (addEvent(name, sqlDate, Time.valueOf(time), type, capacity, tReg, tVIP, price)) {
                        System.out.println("Event added successfully");
                    } else {
                        System.out.println("Event not added");
                    }
                    break;
                case "2":
                    System.out.println("Delete event");
                    System.out.println("Enter event id: ");
                    int eventId = scanner.nextInt();
                    if (deleteEvent(eventId)) {
                        System.out.println("Event deleted successfully");
                    } else {
                        System.out.println("Event not deleted");
                    }
                    break;
                case "3":
                    System.out.println("Add customer");
                    System.out.println("Enter customer name:");
                    String customerName = scanner.next();
                    System.out.println("Enter customer email:");
                    String email = scanner.next();
                    System.out.println("Enter customer credit card info:");
                    String creditCardInfo = scanner.next();
                    if (addCustomer(customerName, email, creditCardInfo)) {
                        System.out.println("Customer added successfully");
                    } else {
                        System.out.println("Customer not added");
                    }
                    break;
                case "4":
                    System.out.println("Delete customer");
                    // show all customers names and ids
                    ResultSet rs = get("SELECT * FROM customers");
                    while (rs.next()) {
                        System.out.println(rs.getInt("CustomerId") + ". " + rs.getString("FullName"));
                    }
                    System.out.println("Enter customer id: ");
                    int customerId = scanner.nextInt();
                    if (deleteCustomer(customerId)) {
                        System.out.println("Customer deleted successfully");
                    } else {
                        System.out.println("Customer not deleted");
                    }
                    break;
                case "5":
                    System.out.println("Add booking");
                    System.out.println("Enter booking date (yyyy-MM-dd):");
                    String bookingDate = scanner.nextLine();
                    // show all customers names and ids
                    ResultSet rs1 = get("SELECT * FROM customers");
                    while (rs1.next()) {
                        System.out.println(rs1.getInt("CustomerId") + ". " + rs1.getString("FullName"));
                    }
                    System.out.println("Enter customer id:");
                    int customerID = scanner.nextInt();
                    // show all events names and ids
                    ResultSet rs2 = get("SELECT * FROM events");
                    while (rs2.next()) {
                        System.out.println(rs2.getInt("EventId") + ". " + rs2.getString("Name"));
                    }
                    System.out.println("Enter event id:");
                    int eventID = scanner.nextInt();
                    System.out.println("Enter number of regular tickets:");
                    int tReg1 = scanner.nextInt();
                    System.out.println("Enter number of VIP tickets:");
                    int tVIP1 = scanner.nextInt();
                    SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
                    java.util.Date date2 = sdf1.parse(bookingDate);
                    java.sql.Date sqlDate1 = new java.sql.Date(date2.getTime());
                    if (addBooking(customerID, eventID, tReg1, tVIP1)) {
                        System.out.println("Booking added successfully");
                    } else {
                        System.out.println("Booking not added");
                    }
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
        }

    }
}
