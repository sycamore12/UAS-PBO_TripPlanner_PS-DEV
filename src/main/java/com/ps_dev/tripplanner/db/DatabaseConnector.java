package com.ps_dev.tripplanner.db;

import com.ps_dev.tripplanner.model.*;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;

/**
 * DatabaseConnector.java - Handles all database operations.
 * Uses the Singleton pattern to ensure only one database connection instance.
 */
public class DatabaseConnector {

    // --- Singleton Instance ---
    private static DatabaseConnector instance;
    private Connection connection;

    // --- Database Credentials (Ganti dengan informasi Anda) ---
    private static final String DB_URL = "jdbc:postgresql://localhost:5432/trip_planner_db";
    private static final String DB_USER = "postgres"; // Ganti dengan username Anda
    private static final String DB_PASSWORD = "hilmy123"; // GANTI DENGAN PASSWORD ANDA

    /**
     * Private constructor to prevent direct instantiation.
     */
    private DatabaseConnector() {
        try {
            connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            System.out.println("Koneksi ke PostgreSQL berhasil!");
        } catch (SQLException e) {
            System.err.println("Koneksi database gagal: " + e.getMessage());
            // Di aplikasi nyata, tampilkan dialog error ke pengguna
            e.printStackTrace();
        }
    }

    /**
     * Provides a single global access point to the instance.
     * @return The singleton instance of the DatabaseConnector.
     */
    public static synchronized DatabaseConnector getInstance() {
        if (instance == null) {
            instance = new DatabaseConnector();
        }
        return instance;
    }

    /**
     * Saves a complete Trip object to the database, including its day plans and activities.
     * @param trip The Trip object to save.
     * @return The generated ID of the saved trip.
     */
    public int saveTrip(Trip trip) throws SQLException {
        // SQL to insert a new trip and get its generated ID
        String tripSQL = "INSERT INTO trips (destination, start_date, end_date) VALUES (?, ?, ?) RETURNING id";

        try (PreparedStatement tripStmt = connection.prepareStatement(tripSQL)) {
            connection.setAutoCommit(false); // Start transaction

            tripStmt.setString(1, trip.getDestination());
            tripStmt.setDate(2, Date.valueOf(trip.getStartDate()));
            tripStmt.setDate(3, Date.valueOf(trip.getEndDate()));

            ResultSet rs = tripStmt.executeQuery();
            if (rs.next()) {
                int tripId = rs.getInt(1);
                trip.setId(tripId);

                // Save each DayPlan associated with this trip
                for (DayPlan dayPlan : trip.getDayPlans()) {
                    saveDayPlan(dayPlan, tripId);
                }

                connection.commit(); // Commit transaction if everything is successful
                return tripId;
            } else {
                connection.rollback(); // Rollback if trip insertion failed
                throw new SQLException("Gagal menyimpan trip, tidak ada ID yang dihasilkan.");
            }
        } catch (SQLException e) {
            connection.rollback(); // Rollback on any SQL error
            System.err.println("Transaksi gagal, rollback dilakukan: " + e.getMessage());
            throw e; // Re-throw the exception
        } finally {
            connection.setAutoCommit(true); // End transaction
        }
    }

    /**
     * Saves a DayPlan object.
     */
    private int saveDayPlan(DayPlan dayPlan, int tripId) throws SQLException {
        String dayPlanSQL = "INSERT INTO day_plans (trip_id, plan_date) VALUES (?, ?) RETURNING id";
        try (PreparedStatement dayPlanStmt = connection.prepareStatement(dayPlanSQL)) {
            dayPlanStmt.setInt(1, tripId);
            dayPlanStmt.setDate(2, Date.valueOf(dayPlan.getPlanDate()));

            ResultSet rs = dayPlanStmt.executeQuery();
            if (rs.next()) {
                int dayPlanId = rs.getInt(1);
                dayPlan.setId(dayPlanId);

                // Save each activity for this day plan
                for (Activity activity : dayPlan.getActivities()) {
                    saveActivity(activity, dayPlanId);
                }
                return dayPlanId;
            } else {
                throw new SQLException("Gagal menyimpan day plan.");
            }
        }
    }

    /**
     * Saves an Activity object (handles both Tour and Culinary types).
     */
    public void saveActivity(Activity activity, int dayPlanId) throws SQLException {
        String activitySQL = "INSERT INTO activities (day_plan_id, start_time, description, activity_type, location, ticket_price, restaurant_name, cuisine_type) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement activityStmt = connection.prepareStatement(activitySQL)) {
            activityStmt.setInt(1, dayPlanId);
            activityStmt.setTime(2, Time.valueOf(activity.getStartTime()));
            activityStmt.setString(3, activity.getDescription());

            if (activity instanceof TourActivity tour) {
                activityStmt.setString(4, "TOUR");
                activityStmt.setString(5, tour.getLocation());
                activityStmt.setDouble(6, tour.getTicketPrice());
                activityStmt.setNull(7, Types.VARCHAR);
                activityStmt.setNull(8, Types.VARCHAR);
            } else if (activity instanceof CulinaryActivity culinary) {
                activityStmt.setString(4, "CULINARY");
                activityStmt.setNull(5, Types.VARCHAR);
                activityStmt.setNull(6, Types.NUMERIC);
                activityStmt.setString(7, culinary.getRestaurantName());
                activityStmt.setString(8, culinary.getCuisineType());
            }
            activityStmt.executeUpdate();
        }
    }
}
