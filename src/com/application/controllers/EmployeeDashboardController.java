package com.application.controllers;

import com.application.Run;
import com.application.models.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class EmployeeDashboardController implements Initializable {

    public static final URL LOCATION = Run.class.getResource("fxml/EmployeeDashboard.fxml");

    @FXML
    TextField fieldHotelName, fieldHotelRooms, fieldHotelEmployees, fieldHotelCustomers, fieldEmpId, fieldEmpName, fieldEmpAge, fieldCustomerId, fieldRoomId;

    @FXML
    Label labelRoomId, labelRoomStatus, labelRoomOwner, labelCustomerId, labelCustomerName, labelCustomerAge, labelCustomerRooms;

    @FXML
    ListView<Object> listView;

    private String searchTerm;

    public static void load() {
        try {
            Parent root = FXMLLoader.load(Objects.requireNonNull(LOCATION));
            Run.stage.setScene(new Scene(root));
            Run.stage.setTitle("Employee dashboard");
        } catch (MalformedURLException e) {
            System.out.println("MalformedURLException");
        } catch (IOException e) {
            System.out.println("IOException");
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Hotel hotel = DB.getHotel();
        Employee employee = DB.getEmployee();

        fieldHotelName.setText(hotel.getName());
        fieldHotelRooms.setText(hotel.getRoomsList().size() + "");
        fieldHotelEmployees.setText(hotel.getEmployeesList().size() + "");
        fieldHotelCustomers.setText(hotel.getCustomersList().size() + "");

        fieldEmpId.setText(employee.getId() + "");
        fieldEmpName.setText(employee.getName());
        fieldEmpAge.setText(employee.getAge() + "");

        listView.setOnMouseClicked(event -> {
            if (searchTerm.equals("room")) {
                Room room = (Room) listView.getSelectionModel().getSelectedItem();

            }
        });
    }

    @FXML
    public void handleGetCustomer() {
        try {
            int customerId = Integer.parseInt(fieldCustomerId.getText().trim());
            Customer customer = DB.getCustomerById(customerId);
            if (customer == null) {
                throw new Exception();
            }

            listView.getItems().clear();
            listView.getItems().add(customer);
            searchTerm = "customer";
            DB.setCustomer(customer);
            setCustomerInfo();
        } catch (NumberFormatException ex) {
            System.out.println("Please enter a valid ID.");
        } catch (Exception ex) {
            System.out.println("Customer with the given ID was not found.");
        } finally {
            fieldCustomerId.clear();
        }
    }

    @FXML
    public void handleGetRoom() {
        try {
            int roomId = Integer.parseInt(fieldRoomId.getText().trim());
            Room room = DB.getRoom(roomId);
            if (room == null) {
                throw new Exception();
            }

            listView.getItems().clear();
            listView.getItems().add(room);
            searchTerm = "room";
            DB.setRoom(room);
            setRoomInfo();
        } catch (NumberFormatException ex) {
            System.out.println("Please enter a valid ID.");
        } catch (Exception ex) {
            System.out.println("Room with the given ID was not found.");
        } finally {
            fieldRoomId.clear();
        }
    }

    @FXML
    public void handleAddCustomer() {
        AddCustomerController.load();
    }

    @FXML
    public void handleGetAllCustomers() {
        listView.getItems().clear();
        listView.getItems().addAll(DB.getAllCustomers());
    }

    @FXML
    public void handleGetAllRooms() {
        listView.getItems().clear();
        listView.getItems().addAll(DB.getAllRooms());
    }

    @FXML
    public void handleAddRoom() {
        DB.insertRoom();
    }

    private void setRoomInfo() {
        Room room = DB.getRoom();
        String status = room.isReserved() ? "Reserved" : "Empty";
        labelRoomId.setText("ID: " + room.getId() + "");
        labelRoomStatus.setText("Status: " + status);
        labelRoomOwner.setText("Owner: " + room.getOwnerName());
    }

    private void setCustomerInfo() {
        Customer customer = DB.getCustomer();
        labelCustomerId.setText("ID: " + customer.getId() + "");
        labelCustomerName.setText("Name: " + customer.getName());
        labelCustomerAge.setText("Age: " + customer.getAge() + "");
        labelCustomerRooms.setText("Rooms: " + customer.getRoomsList().size() + "");
    }

    @FXML
    public void handleClose() {
        Run.stage.close();
    }
}
