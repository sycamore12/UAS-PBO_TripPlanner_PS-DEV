package com.ps_dev.tripplanner.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class DayPlan {
    private int id;
    private LocalDate planDate;
    private List<Activity> activities;

    public DayPlan(LocalDate planDate) {
        this.planDate = planDate;
        this.activities = new ArrayList<>();
    }

    public void addActivity(Activity activity) {
        this.activities.add(activity);
    }

    // Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public LocalDate getPlanDate() { return planDate; }
    public void setPlanDate(LocalDate planDate) { this.planDate = planDate; }
    public List<Activity> getActivities() { return new ArrayList<>(activities); }
    public void setActivities(List<Activity> activities) { this.activities = activities; }
}