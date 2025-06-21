package com.ps_dev.tripplanner.model;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

/**
 * CulinaryActivity.java - Represents a culinary-related activity.
 * This class inherits from Activity and adds specific attributes for culinary experiences.
 */
public class CulinaryActivity extends Activity {

    // --- Specific Attributes ---
    private String restaurantName;
    private String cuisineType;

    // --- Constructor ---
    public CulinaryActivity(LocalTime startTime, String description, String restaurantName, String cuisineType) {
        // Call the constructor of the parent class (Activity)
        super(startTime, description);
        this.restaurantName = restaurantName;
        this.cuisineType = cuisineType;
    }

    // --- Polymorphism: Implementation of the abstract method ---
    @Override
    public String getDetail() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
        String formattedTime = getStartTime().format(formatter);
        return String.format("[%s] Kuliner: %s di %s (Jenis: %s)",
                formattedTime, getDescription(), restaurantName, cuisineType);
    }

    // --- Getters and Setters ---
    public String getRestaurantName() {
        return restaurantName;
    }

    public void setRestaurantName(String restaurantName) {
        this.restaurantName = restaurantName;
    }

    public String getCuisineType() {
        return cuisineType;
    }

    public void setCuisineType(String cuisineType) {
        this.cuisineType = cuisineType;
    }
}
