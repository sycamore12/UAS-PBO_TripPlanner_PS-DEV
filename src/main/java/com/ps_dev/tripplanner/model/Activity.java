package com.ps_dev.tripplanner.model;

import java.time.LocalTime;

/**
 * Activity.java - Abstract base class for all activities in a trip.
 * This class demonstrates Encapsulation and provides a foundation for Inheritance.
 * It also includes an abstract method for Polymorphism.
 */
public abstract class Activity {

    // --- Attributes (Encapsulation: private) ---
    private int id;
    private LocalTime startTime;
    private String description;

    // --- Constructor ---
    public Activity(LocalTime startTime, String description) {
        this.startTime = startTime;
        this.description = description;
    }

    // --- Abstract Method (for Polymorphism) ---
    /**
     * Gets a detailed string representation of the activity.
     * Each subclass must implement this method to provide specific details.
     * @return A formatted string with activity details.
     */
    public abstract String getDetail();

    // --- Getters and Setters (Encapsulation) ---
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
