package com.application.controllers;

import com.application.Run;
import com.application.models.Customer;
import com.application.models.DB;
import com.application.models.Employee;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class RegisterController implements Initializable {

    public static final URL LOCATION = Run.class.getResource("fxml/Register.fxml");

    @FXML
    TextField fieldUsername, fieldName, fieldAge;

    @FXML
    PasswordField fieldPassword;

    ToggleGroup toggleGroup;

    @FXML
    RadioButton radioCustomer, radioEmployee;

    public static void load() {
        try {
            Parent root = FXMLLoader.load(Objects.requireNonNull(LOCATION));
            Run.stage.setScene(new Scene(root));
            Run.stage.setTitle("Register");
        } catch (MalformedURLException e) {
            System.out.println("MalformedURLException");
        } catch (IOException e) {
            System.out.println("IOException");
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        toggleGroup = new ToggleGroup();
        toggleGroup.getToggles().addAll(radioCustomer, radioEmployee);
    }

    @FXML
    public void handleRegister() {
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

            String client = ((RadioButton) toggleGroup.getSelectedToggle()).getText().toLowerCase();
            if (client.equals("customer")) {
                Customer customer = new Customer(age, username, name, password);
                DB.insertCustomer(customer);
            } else {
                Employee employee = new Employee(age, username, name, password);
                DB.insertEmployee(employee);
            }

            clearFields();
            switchToLogin();
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

    @FXML
    public void switchToLogin() {
        LoginController.load();
    }
}
