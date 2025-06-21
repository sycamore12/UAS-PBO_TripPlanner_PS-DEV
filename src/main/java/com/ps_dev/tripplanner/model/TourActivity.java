package com.ps_dev.tripplanner.model;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

/**
 * TourActivity.java - Represents a tourism-related activity.
 * This class inherits from Activity and adds specific attributes for tours.
 */
public class TourActivity extends Activity {

    // --- Specific Attributes ---
    private String location;
    private double ticketPrice;

    // --- Constructor ---
    public TourActivity(LocalTime startTime, String description, String location, double ticketPrice) {
        // Call the constructor of the parent class (Activity)
        super(startTime, description);
        this.location = location;
        this.ticketPrice = ticketPrice;
    }

    // --- Polymorphism: Implementation of the abstract method ---
    @Override
    public String getDetail() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
        String formattedTime = getStartTime().format(formatter);
        return String.format("[%s] Wisata: %s di %s (Tiket: Rp %.2f)",
                formattedTime, getDescription(), location, ticketPrice);
    }

    // --- Getters and Setters ---
    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public double getTicketPrice() {
        return ticketPrice;
    }

    public void setTicketPrice(double ticketPrice) {
        this.ticketPrice = ticketPrice;
    }
}
