package com.application;

import com.application.controllers.LoginController;
import com.application.models.DB;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.Objects;

public class Run extends Application {

    public static Stage stage;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void init() throws Exception {
        DB.getConnection();
        DB.refreshHotel();
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        try {
            Parent root = FXMLLoader.load(Objects.requireNonNull(LoginController.LOCATION));
            primaryStage.setScene(new Scene(root, 440, 250));
            primaryStage.setResizable(false);
            primaryStage.setTitle("Final Project");
            stage = primaryStage;
            stage.show();
        } catch (Exception e) {
            System.out.println("Error Run Application");
        }
    }

    @Override
    public void stop() throws Exception {
        DB.closeConnection();
    }
}
