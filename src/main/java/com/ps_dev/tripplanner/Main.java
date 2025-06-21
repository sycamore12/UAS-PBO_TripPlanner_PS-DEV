package com.ps_dev.tripplanner;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) {
        try {
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("TripPlannerView.fxml")));
            Scene scene = new Scene(root, 900, 600);

            primaryStage.setTitle("TripPlanner - Rencanakan Perjalanan Anda");
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
            System.err.println("Gagal memuat file FXML. Pastikan path sudah benar.");
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}