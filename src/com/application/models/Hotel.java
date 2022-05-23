package com.application.models;

import java.util.ArrayList;
import java.util.List;

public class Hotel {

    private int id;
    private String name;
    private List<Room> roomsList;
    private List<Customer> customersList;
    private List<Employee> employeesList;

    public Hotel(int id, String name) {
        this.id = id;
        this.name = name;
        this.roomsList = new ArrayList<>();
        this.customersList = new ArrayList<>();
        this.employeesList = new ArrayList<>();
    }

    public Hotel(int id, String name, List<Room> roomsList, List<Customer> customersList, List<Employee> employeesList) {
        this.id = id;
        this.name = name;
        this.roomsList = roomsList;
        this.customersList = customersList;
        this.employeesList = employeesList;
    }

    // For testing purposes
    public void printSlices(String title) {
        System.out.println("*** " + title + " ***" +
                "\nRooms: " + roomsList +
                "\nCustomers: " + customersList +
                "\nEmployees: " + employeesList + "\n\n"
        );
    }

    public Room getRoom(int roomId) {
        for (Room room : roomsList)
            if (room.getId() == roomId)
                return room;

        return null;
    }

    public void addRoom(Room room) {
        // Saving on the database
        roomsList.add(room);
    }

    public void updateRoom(int roomId, Room newRoom) {
        Room room = getRoom(roomId);
        if (room == null) {
            return;
        }

        // Calling the database

        // Shallow copy (Field-by-field copy)
        room.setId(newRoom.getId());
        room.setOwnerName(newRoom.getOwnerName());
        room.setReserved(newRoom.isReserved());
    }

    public void deleteRoom(int roomId) {
        // Calling the database
        roomsList.removeIf(room -> room.getId() == roomId);
    }

    public void reserveRoom(int roomId, int customerId) {
        Room room = getRoom(roomId);
        if (room == null || room.isReserved()) {
            return;
        }

        Customer customer = getCustomer(customerId);
        if (customer == null) {
            return;
        }

        // Calling the database

        room.reserveBy(customer.getName());
    }

    public void checkoutRoom(int roomId) {
        Room room = getRoom(roomId);
        if (room == null || !room.isReserved()) {
            return;
        }

        // Calling the database

        room.checkout();
    }

    public Customer getCustomer(int customerId) {
        for (Customer customer : customersList)
            if (customer.getId() == customerId)
                return customer;

        return null;
    }

    public void addCustomer(Customer customer) {
        // Calling the database
        customersList.add(customer);
    }

    public void updateCustomer(int customerId, Customer newCustomer) {
        Customer customer = getCustomer(customerId);
        if (customer == null) {
            return;
        }

        // Calling the database

        // Shallow copy (Field-by-field copy)
        customer.setId(newCustomer.getId());
        customer.setName(newCustomer.getName());
        customer.setAge(newCustomer.getAge());
        customer.setRoomsList(newCustomer.getRoomsList());
    }

    public void deleteCustomer(int customerId) {
        // Calling the database
        customersList.removeIf(customer -> customer.getId() == customerId);
    }

    public Employee getEmployee(int employeeId) {
        for (Employee employee : employeesList)
            if (employee.getId() == employeeId)
                return employee;

        return null;
    }

    public void addEmployee(Employee employee) {
        // Calling the database
        employeesList.add(employee);
    }

    public void updateEmployee(int employeeId, Employee newEmployee) {
        Employee employee = getEmployee(employeeId);
        if (employee == null) {
            return;
        }

        // Calling the database

        // Shallow copy (Field-by-field copy)
        employee.setId(newEmployee.getId());
        employee.setName(newEmployee.getName());
        employee.setAge(newEmployee.getAge());
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Room> getRoomsList() {
        return roomsList;
    }

    public void setRoomsList(List<Room> roomsList) {
        this.roomsList = roomsList;
    }

    public List<Customer> getCustomersList() {
        return customersList;
    }

    public void setCustomersList(List<Customer> customersList) {
        this.customersList = customersList;
    }

    public List<Employee> getEmployeesList() {
        return employeesList;
    }

    public void setEmployeesList(List<Employee> employeesList) {
        this.employeesList = employeesList;
    }

    @Override
    public String toString() {
        return "Hotel{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", roomsList=" + roomsList +
                ", customersList=" + customersList +
                ", employeesList=" + employeesList +
                '}';
    }
}
