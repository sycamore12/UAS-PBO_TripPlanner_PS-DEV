package com.ps_dev.tripplanner.controller;

import com.ps_dev.tripplanner.db.DatabaseConnector;
import com.ps_dev.tripplanner.model.*;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class TripController {

    // --- FXML Components from Left Pane ---
    @FXML private TextField destinationField;
    @FXML private DatePicker startDatePicker;
    @FXML private DatePicker endDatePicker;
    @FXML private Button saveTripButton;
    @FXML private Label statusLabel;

    // --- FXML Components from Right Pane ---
    @FXML private Label itineraryTitleLabel;
    @FXML private VBox itineraryContainer;
    @FXML private VBox addActivityBox;
    @FXML private ComboBox<String> daySelectionComboBox;
    @FXML private ComboBox<String> activityTypeComboBox;
    @FXML private TextField activityTimeField;
    @FXML private TextArea activityDescriptionArea;
    @FXML private GridPane tourActivityPane;
    @FXML private TextField tourLocationField;
    @FXML private TextField tourTicketField;
    @FXML private GridPane culinaryActivityPane;
    @FXML private TextField culinaryRestaurantField;
    @FXML private TextField culinaryCuisineField;
    @FXML private Button addActivityButton;

    private Trip currentTrip;
    private final DatabaseConnector dbConnector = DatabaseConnector.getInstance();

    @FXML
    public void initialize() {
        // Initialize the activity type combo box
        activityTypeComboBox.setItems(FXCollections.observableArrayList("Wisata", "Kuliner"));
        activityTypeComboBox.valueProperty().addListener((obs, oldVal, newVal) -> toggleActivityPanes(newVal));

        // Set default state
        statusLabel.setText("Status: Siap mengisi data perjalanan.");
        toggleActivityPanes(null);
    }

    private void toggleActivityPanes(String type) {
        if ("Wisata".equals(type)) {
            tourActivityPane.setVisible(true);
            culinaryActivityPane.setVisible(false);
        } else if ("Kuliner".equals(type)) {
            tourActivityPane.setVisible(false);
            culinaryActivityPane.setVisible(true);
        } else {
            tourActivityPane.setVisible(false);
            culinaryActivityPane.setVisible(false);
        }
    }

    @FXML
    void handleSaveTrip() {
        String destination = destinationField.getText();
        LocalDate startDate = startDatePicker.getValue();
        LocalDate endDate = endDatePicker.getValue();

        if (destination.isEmpty() || startDate == null || endDate == null) {
            updateStatus("Error: Semua field harus diisi!", Color.RED);
            return;
        }
        if (startDate.isAfter(endDate)) {
            updateStatus("Error: Tanggal mulai tidak boleh setelah tanggal akhir!", Color.RED);
            return;
        }

        currentTrip = new Trip(destination, startDate, endDate);

        try {
            dbConnector.saveTrip(currentTrip);
            updateStatus("Sukses! Trip ke " + destination + " berhasil disimpan.", Color.GREEN);
            displayItinerary();
            populateDaySelectionComboBox();
            addActivityBox.setDisable(false);
        } catch (SQLException e) {
            updateStatus("Error: Gagal menyimpan trip ke database.", Color.RED);
            e.printStackTrace();
        }
    }

    @FXML
    void handleAddActivity() {
        try {
            String selectedDayStr = daySelectionComboBox.getValue();
            String activityType = activityTypeComboBox.getValue();
            LocalTime time = LocalTime.parse(activityTimeField.getText(), DateTimeFormatter.ofPattern("HH:mm"));
            String description = activityDescriptionArea.getText();

            if (selectedDayStr == null || activityType == null || description.isEmpty()) {
                updateStatus("Error: Field aktivitas harus diisi lengkap!", Color.RED);
                return;
            }

            LocalDate selectedDate = LocalDate.parse(selectedDayStr.split(" - ")[0]);
            DayPlan selectedDayPlan = currentTrip.getDayPlans().stream()
                    .filter(dp -> dp.getPlanDate().equals(selectedDate))
                    .findFirst().orElse(null);

            if (selectedDayPlan == null) {
                updateStatus("Error: Hari yang dipilih tidak valid.", Color.RED);
                return;
            }

            Activity newActivity = null;
            if ("Wisata".equals(activityType)) {
                String location = tourLocationField.getText();
                double ticketPrice = Double.parseDouble(tourTicketField.getText());
                newActivity = new TourActivity(time, description, location, ticketPrice);
            } else if ("Kuliner".equals(activityType)) {
                String restaurant = culinaryRestaurantField.getText();
                String cuisine = culinaryCuisineField.getText();
                newActivity = new CulinaryActivity(time, description, restaurant, cuisine);
            }

            if (newActivity != null) {
                dbConnector.saveActivity(newActivity, selectedDayPlan.getId());
                selectedDayPlan.addActivity(newActivity); // Update model in memory
                displayItinerary(); // Refresh view
                clearActivityInputs();
                updateStatus("Aktivitas baru berhasil ditambahkan!", Color.GREEN);
            }

        } catch (DateTimeParseException e) {
            updateStatus("Error: Format waktu salah! Gunakan HH:mm.", Color.RED);
        } catch (NumberFormatException e) {
            updateStatus("Error: Harga tiket harus berupa angka.", Color.RED);
        } catch (SQLException e) {
            updateStatus("Error: Gagal menyimpan aktivitas ke database.", Color.RED);
            e.printStackTrace();
        }
    }

    private void displayItinerary() {
        itineraryTitleLabel.setText("Itinerary: " + currentTrip.getDestination());
        itineraryContainer.getChildren().clear(); // Clear previous content

        for (DayPlan dayPlan : currentTrip.getDayPlans()) {
            VBox dayBox = new VBox(5);
            dayBox.setStyle("-fx-border-color: #cccccc; -fx-border-radius: 5; -fx-padding: 10;");

            Label dayLabel = new Label(dayPlan.getPlanDate().format(DateTimeFormatter.ofPattern("EEEE, dd MMMM yyyy")));
            dayLabel.setFont(Font.font("System", FontWeight.BOLD, 14));
            dayBox.getChildren().add(dayLabel);

            ListView<String> activityListView = new ListView<>();
            for(Activity activity : dayPlan.getActivities()) {
                activityListView.getItems().add(activity.getDetail());
            }
            dayBox.getChildren().add(activityListView);

            itineraryContainer.getChildren().add(dayBox);
        }
    }

    private void populateDaySelectionComboBox() {
        daySelectionComboBox.getItems().clear();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd - EEEE");
        for (DayPlan dayPlan : currentTrip.getDayPlans()) {
            daySelectionComboBox.getItems().add(dayPlan.getPlanDate().format(formatter));
        }
    }

    private void clearActivityInputs() {
        activityTimeField.clear();
        activityDescriptionArea.clear();
        tourLocationField.clear();
        tourTicketField.clear();
        culinaryRestaurantField.clear();
        culinaryCuisineField.clear();
        activityTypeComboBox.getSelectionModel().clearSelection();
    }

    private void updateStatus(String message, Color color) {
        statusLabel.setText("Status: " + message);
        statusLabel.setTextFill(color);
    }
}