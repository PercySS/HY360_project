package classes;

import java.sql.*;

import static classes.Ticket.*;
import static db.dataBase.*;

public class Event {
    private int EventId;
    private String Name;
    private Date Date;
    private Time Time;
    private String Type;
    private int Capacity;


    // ==== Setters ====
    public void setEventId(int EventId) {
        this.EventId = EventId;
    }

    public void setName(String Name) {
        this.Name = Name;
    }

    public void setDate(Date Date) {
        this.Date = Date;
    }

    public void setTime(Time Time) {
        this.Time = Time;
    }

    public void setType(String Type) {
        this.Type = Type;
    }

    public void setCapacity(int Capacity) {
        this.Capacity = Capacity;
    }

    // ==== Getters ====
    public int getEventId() {
        return EventId;
    }

    public String getName() {
        return Name;
    }

    public Date getDate() {
        return Date;
    }

    public Time getTime() {
        return Time;
    }

    public String getType() {
        return Type;
    }

    public int getCapacity() {
        return Capacity;
    }

    public static void addEvent(int EventId, String Name, Date Date, Time Time, String Type, int Capacity) throws SQLException {
        // I use my update to insert the data into the database from the db.database class
        update("INSERT INTO events VALUES (" + EventId + ", '" + Name + "', '" + Date + "', '" + Time + "', '" + Type + "', " + Capacity + ")");

        for (int i = 0; i < Capacity; i++) {
            if (i%2 == 0) {
                addTicketVIP(i, 100, 1, EventId);
            } else {
                addTicketRegular(i, 50, 1, EventId);
            }
        }
    }

    public static void deleteEvent(Connection conn, int EventId) throws SQLException {
        update("DELETE FROM events WHERE EventId = " + EventId);
    }

}
