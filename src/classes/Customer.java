package classes;

import java.sql.*;
import static db.dataBase.*;

public class Customer {
    private int CustomerId;
    private String FullName;
    private String email;
    private String CreditCardInfo;

    // ==== Setters ====
    public void setCustomerId(int CustomerId) {
        this.CustomerId = CustomerId;
    }

    public void setFullName(String FullName) {
        this.FullName = FullName;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setCreditCardInfo(String CreditCardInfo) {
        this.CreditCardInfo = CreditCardInfo;
    }

    // ==== Getters ====
    public int getCustomerId() {
        return CustomerId;
    }

    public String getFullName() {
        return FullName;
    }

    public String getEmail() {
        return email;
    }

    public String getCreditCardInfo() {
        return CreditCardInfo;
    }

    public static void addCustomer(int CustomerId, String FullName, String email, String CreditCardInfo) throws SQLException {
        update("INSERT INTO customers VALUES (" + CustomerId + ", '" + FullName + "', '" + email + "', '" + CreditCardInfo + "')");
    }

    public static void deleteCustomer(Connection conn, int CustomerId) throws SQLException {
        update("DELETE FROM customers WHERE CustomerId = " + CustomerId);
    }



}
