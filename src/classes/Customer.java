package classes;

import java.sql.*;
import static db.dataBase.*;

public class Customer {
    public static void addCustomer(String FullName, String email, String CreditCardInfo) throws SQLException {
        update("INSERT INTO customers (FullName, email, CreditCardInfo) VALUES ('" + FullName + "', '" + email + "', '" + CreditCardInfo + "')");
    }

    public static void deleteCustomer(Connection conn, int CustomerId) throws SQLException {
        update("DELETE FROM customers WHERE CustomerId = " + CustomerId);

        // making all tickets from this customer's bookings available again
        ResultSet rs = get("SELECT * FROM bookings WHERE CustomerId = " + CustomerId);
        while (rs.next()) {
            Booking.deleteBooking(conn, rs.getInt("BookingId"));
        }

    }



}
