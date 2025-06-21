package com.ps_dev.tripplanner.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Trip.java - Represents a complete travel itinerary.
 * This is the main data model class, containing destination, dates,
 * and a list of daily plans. MUEHEHEHEHE
 */
public class Trip {

    // --- Attributes (Encapsulation) ---
    private int id;
    private String destination;
    private LocalDate startDate;
    private LocalDate endDate;
    // Composition: A Trip "has-a" list of DayPlan objects.
    private List<DayPlan> dayPlans;

    // --- Constructor ---
    public Trip(String destination, LocalDate startDate, LocalDate endDate) {
        this.destination = destination;
        this.startDate = startDate;
        this.endDate = endDate;
        this.dayPlans = new ArrayList<>();
        // Automatically create DayPlan objects for each day of the trip
        generateDayPlans();
    }

    // --- Business Logic Methods ---
    /**
     * Populates the dayPlans list based on the start and end dates.
     */
    private void generateDayPlans() {
        LocalDate currentDate = startDate;
        while (!currentDate.isAfter(endDate)) {
            dayPlans.add(new DayPlan(currentDate));
            currentDate = currentDate.plusDays(1);
        }
    }

    /**
     * Adds a specific day plan. Useful when loading from a database.
     * @param dayPlan The DayPlan object to add.
     */
    public void addDayPlan(DayPlan dayPlan) {
        if (!this.dayPlans.contains(dayPlan)) {
            this.dayPlans.add(dayPlan);
        }
    }

    // --- Getters and Setters ---
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public List<DayPlan> getDayPlans() {
        return new ArrayList<>(dayPlans);
    }

    public void setDayPlans(List<DayPlan> dayPlans) {
        this.dayPlans = dayPlans;
    }
}