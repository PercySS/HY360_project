package classes;

import java.sql.*;
import static db.dataBase.*;

public class Customer {
    public static void addCustomer(String FullName, String email, String CreditCardInfo) throws SQLException {
        update("INSERT INTO customers (FullName, email, CreditCardInfo) VALUES ('" + FullName + "', '" + email + "', '" + CreditCardInfo + "')");
    }

    public static void deleteCustomer(Connection conn, int CustomerId) throws SQLException {
        update("DELETE FROM customers WHERE CustomerId = " + CustomerId);
    }



}
