package gui;


import classes.Event;
import classes.Booking;
import classes.Customer;
import classes.Ticket;

import java.io.IOException;

import static db.dataBase.*;



public class Main {
    public static void main(String[] args) {
        createDb();
        initDb();

    }
}
