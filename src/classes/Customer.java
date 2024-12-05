package classes;

import java.sql.*;

import static classes.Booking.*;
import static db.dataBase.*;

public class Customer {
    public static boolean addCustomer(String FullName, String email, String CreditCardInfo) throws SQLException {
        ResultSet rs;
        StringBuilder made = new StringBuilder();
        made.append("Customer not created: ");

        // check for valid input
        if (FullName == null || email == null || CreditCardInfo == null || FullName.isEmpty() || email.isEmpty() || CreditCardInfo.isEmpty()) {
            System.out.println(made.append("Invalid input"));
            return false;
        }

        // check if the customer is already in the database
        rs = get("SELECT * FROM customers WHERE FullName = '" + FullName + "' AND email = '" + email + "'");
        if (rs.next()) {
            System.out.println(made.append("Customer already exists"));
            return false;
        }

        // check if the email is valid form
        if (!email.contains("@") || !email.contains(".")) {
            System.out.println(made.append("Invalid email"));
            return false;
        }

        // check if the credit card info is valid form (2 first char GR followed by 16 digits)
        if (!CreditCardInfo.startsWith("GR") || CreditCardInfo.length() != 28) {
            System.out.println(made.append("Invalid Credit Card Info"));
            return false;
        }

        made.setLength(0);
        made.append("Customer created: ").append(FullName);

        update("INSERT INTO customers (FullName, email, CreditCardInfo) VALUES ('" + FullName + "', '" + email + "', '" + CreditCardInfo + "')");
        return true;
    }

    public static boolean deleteCustomer(int CustomerId) throws SQLException {
        // check if the customer exists
        ResultSet rs = get("SELECT * FROM customers WHERE CustomerId = " + CustomerId);
        if (!rs.next()) {
            System.out.println("Customer does not exist");
            return false;
        }

        // delete all bookings of this customer
        rs = get("SELECT * FROM bookings WHERE CustomerId = " + CustomerId);
        while (rs.next()) {
            deleteBooking(rs.getInt("BookingId"));
        }

        update("DELETE FROM customers WHERE CustomerId = " + CustomerId);
        return true;
    }



}
