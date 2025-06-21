/**
 * Module definition for the TripPlanner application.
 * This file specifies the application's dependencies and which packages it makes available.
 */
module com.ps_dev.tripplanner {

    // Required modules for JavaFX UI controls and FXML
    requires javafx.controls;
    requires javafx.fxml;

    // Required module for database connectivity (JDBC)
    requires java.sql;

    // We need to open our packages to the JavaFX libraries.
    // This allows the FXML loader to use reflection to access controllers and models.

    // Open the main package to export the Main class.
    exports com.ps_dev.tripplanner;

    // Open the controller package to javafx.fxml so it can inject FXML components.
    opens com.ps_dev.tripplanner.controller to javafx.fxml;

    // (Optional but good practice) Open the model package to javafx.base if you use
    // PropertyValueFactory or other data binding features in the future.
    opens com.ps_dev.tripplanner.model to javafx.base;
}
