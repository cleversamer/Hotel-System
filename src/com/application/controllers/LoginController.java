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

public class LoginController implements Initializable {

    public static final URL LOCATION = Run.class.getResource("fxml/Login.fxml");

    @FXML
    TextField fieldUsername;

    @FXML
    PasswordField fieldPassword;

    ToggleGroup toggleGroup;

    @FXML
    RadioButton radioCustomer, radioEmployee;

    public static void load() {
        try {
            Parent root = FXMLLoader.load(Objects.requireNonNull(LOCATION));
            Run.stage.setScene(new Scene(root));
            Run.stage.setTitle("Login");
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
    public void handleLogin() {
        String username = fieldUsername.getText().trim();
        String password = fieldPassword.getText().trim();

        String client = ((RadioButton) toggleGroup.getSelectedToggle()).getText().toLowerCase();
        if (client.equals("customer")) {
            Customer customer = DB.getCustomerByCredentials(username, password);
            if (customer == null) {
                System.out.println("Invalid login info.");
            } else {
                System.out.println("Login succeeded");
                DB.setCustomer(customer);
//                CustomerController.load();
            }
        } else {
            Employee employee = DB.getEmployeeByCredentials(username, password);
            if (employee == null) {
                System.out.println("Invalid login info.");
            } else {
                System.out.println("Login succeeded");
                DB.setEmployee(employee);
                EmployeeDashboardController.load();
            }
        }

        clearFields();
    }

    public void clearFields() {
        fieldUsername.clear();
        fieldPassword.clear();
    }

    @FXML
    public void switchToRegister() {
        RegisterController.load();
    }
}
