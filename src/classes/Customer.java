package classes;

import jdk.nashorn.internal.scripts.JO;

import java.sql.*;
import javax.swing.*;

import static classes.Booking.*;
import static db.dataBase.*;

public class Customer {
    public static boolean addCustomer(String FullName, String email, String CreditCardInfo) throws SQLException {
        ResultSet rs;
        StringBuilder made = new StringBuilder();
        made.append("Customer not created: ");

        // check for valid input
        if (FullName == null || email == null || CreditCardInfo == null || FullName.isEmpty() || email.isEmpty() || CreditCardInfo.isEmpty()) {
            JOptionPane.showMessageDialog(null, made.append("Invalid input"), "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        // check if the customer is already in the database
        rs = get("SELECT * FROM customers WHERE FullName = '" + FullName + "' AND email = '" + email + "'");
        if (rs.next()) {
            JOptionPane.showMessageDialog(null, made.append("Customer already exists"), "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        // check if the email is valid form
        if (!email.contains("@") || !email.contains(".")) {
            JOptionPane.showMessageDialog(null, made.append("Invalid email"), "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        // check if the credit card info is valid form (2 first char GR followed by 16 digits)
        if (!CreditCardInfo.startsWith("GR") || CreditCardInfo.length() != 27) {
            JOptionPane.showMessageDialog(null, made.append("Invalid Credit Card Info"), "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        made.setLength(0);
        made.append("Customer created: ").append(FullName);

        update("INSERT INTO customers (FullName, email, CreditCardInfo) VALUES ('" + FullName + "', '" + email + "', '" + CreditCardInfo + "')");
        JOptionPane.showMessageDialog(null, made, "Success", JOptionPane.INFORMATION_MESSAGE);
        return true;
    }

    public static boolean deleteCustomer(int CustomerId) throws SQLException {
        // check if the customer exists
        ResultSet rs = get("SELECT * FROM customers WHERE CustomerId = " + CustomerId);
        if (!rs.next()) {
            JOptionPane.showMessageDialog(null, "Customer not found", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        // delete all bookings of this customer
        rs = get("SELECT * FROM bookings WHERE CustomerId = " + CustomerId);
        while (rs.next()) {
            deleteBooking(rs.getInt("BookingId"));
        }

        update("DELETE FROM customers WHERE CustomerId = " + CustomerId);
        JOptionPane.showMessageDialog(null, "Customer deleted", "Success", JOptionPane.INFORMATION_MESSAGE);
        return true;
    }



}
