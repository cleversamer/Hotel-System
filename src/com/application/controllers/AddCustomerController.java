package com.application.controllers;

import com.application.Run;
import com.application.models.Customer;
import com.application.models.DB;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Objects;

public class AddCustomerController {

    public static final URL LOCATION = Run.class.getResource("fxml/AddCustomer.fxml");

    @FXML
    TextField fieldUsername, fieldName, fieldAge;

    @FXML
    PasswordField fieldPassword;

    public static void load() {
        try {
            Parent root = FXMLLoader.load(Objects.requireNonNull(LOCATION));
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Add Customer");
            stage.setResizable(false);
            stage.show();
        } catch (MalformedURLException e) {
            System.out.println("MalformedURLException");
        } catch (IOException e) {
            System.out.println("IOException");
        }
    }

    @FXML
    public void handleAddCustomer() {
        try {
            String username = fieldUsername.getText().trim();
            String password = fieldPassword.getText().trim();
            String name = fieldName.getText().trim();
            int age = Integer.parseInt(fieldAge.getText().trim());

            boolean isValidInput = username.length() >= 3 && username.length() <= 16;
            isValidInput = isValidInput && password.length() >= 8 && password.length() <= 16;
            isValidInput = isValidInput && name.length() >= 3 && name.length() <= 64;
            isValidInput = isValidInput && age >= 18 && age <= 100;

            if (!isValidInput) {
                System.out.println("Invalid inputs");
                throw new Exception();
            }

            Customer customer = new Customer(age, username, name, password);
            DB.insertCustomer(customer);

            clearFields();
        } catch (NumberFormatException ex) {
            System.out.println("Please enter a valid age.");
        } catch (Exception e) {
            System.out.println("Please enter a valid data.");
        }
    }

    public void clearFields() {
        fieldUsername.clear();
        fieldPassword.clear();
        fieldName.clear();
        fieldAge.clear();
    }
}
