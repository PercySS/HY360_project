package classes;

import java.sql.Time;
import java.util.Date;

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

    // ==== Constructors ====
    public Event() {
    }

    public Event(int EventId, String Name, Date Date, Time Time, String Type, int Capacity) {
        this.EventId = EventId;
        this.Name = Name;
        this.Date = Date;
        this.Time = Time;
        this.Type = Type;
        this.Capacity = Capacity;
    }

    // ==== Methods ====
    @Override
    public String toString() {
        return "Event{" +
                "EventId=" + EventId +
                ", Name='" + Name + '\'' +
                ", Date=" + Date +
                ", Time=" + Time +
                ", Type='" + Type + '\'' +
                ", Capacity=" + Capacity +
                '}';
    }

    public void print() {
        System.out.println(toString());
    }


}
