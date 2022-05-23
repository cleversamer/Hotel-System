package com.application.models;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;
import java.util.List;

public class DB {

    private static final String url = "jdbc:mysql://localhost:3306/hotel";
    private static final String username = "root";
    private static final String password = "";
    private static Connection connection = null;
    private static Hotel hotel;
    private static Customer customer;
    private static Employee employee;
    private static Room room;

    public static void getConnection() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(url, username, password);
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println("Database connection failed.");
        }
    }

    public static void closeConnection() {
        try {
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException e) {
            System.out.println("Database connection is already closed.");
        }
    }

    /////////////////////////////////// Employee ///////////////////////////////////
    public static ObservableList<Employee> getAllEmployees() {
        ObservableList<Employee> employees = FXCollections.observableArrayList();

        if (connection == null) {
            getConnection();
        }

        try {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM Employees");
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String username = resultSet.getString("username");
                String name = resultSet.getString("name");
                String password = resultSet.getString("password");
                int age = resultSet.getInt("age");

                Employee employee = new Employee(id, age, username, name, password);
                employees.add(employee);
            }

        } catch (SQLException e) {
            System.out.println("Failed to GET all students from the database: " + "Error Code " + e.getErrorCode());
        }

        return employees;
    }

    public static Employee getEmployeeById(int employeeId) {
        Employee employee = null;

        if (connection == null) {
            getConnection();
        }

        try {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM Employees WHERE id = " + employeeId);
            ResultSet resultSet = statement.executeQuery();

            resultSet.next();

            int id = resultSet.getInt("id");
            String username = resultSet.getString("username");
            String name = resultSet.getString("name");
            String password = resultSet.getString("password");
            int age = resultSet.getInt("age");

            employee = new Employee(id, age, username, name, password);
        } catch (SQLException e) {
            System.out.println("Failed to GET employee with id " + employeeId);
        }

        return employee;
    }

    public static Employee getEmployeeByCredentials(String user, String pass) {
        Employee employee = null;

        if (connection == null) {
            getConnection();
        }

        try {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM Employees WHERE username = ? AND password = ?");
            statement.setString(1, user);
            statement.setString(2, pass);
            ResultSet resultSet = statement.executeQuery();

            resultSet.next();

            int id = resultSet.getInt("id");
            String username = resultSet.getString("username");
            String name = resultSet.getString("name");
            String password = resultSet.getString("password");
            int age = resultSet.getInt("age");

            employee = new Employee(id, age, username, name, password);
        } catch (SQLException e) {
            System.out.println("Failed to GET employee with id " + user);
        }

        return employee;
    }

    public static void insertEmployee(Employee employee) {
        if (connection == null) {
            getConnection();
        }

        try {
            PreparedStatement statement = connection.prepareStatement("INSERT INTO Employees VALUES (?, ?, ?, ?, ?)");
            statement.setInt(1, employee.getId());
            statement.setString(2, employee.getName());
            statement.setInt(3, employee.getAge());
            statement.setString(4, employee.getPassword());
            statement.setString(5, employee.getUsername());

            statement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("An error occurred when inserting the employee into the database.");
        }
    }

    /////////////////////////////////// Customer ///////////////////////////////////
    public static ObservableList<Customer> getAllCustomers() {
        ObservableList<Customer> customers = FXCollections.observableArrayList();

        if (connection == null) {
            getConnection();
        }

        try {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM Customers");
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                String username = resultSet.getString("username");
                String password = resultSet.getString("password");
                int age = resultSet.getInt("age");
                List<Room> roomsList = getCustomerRooms(id);

                Customer customer = new Customer(id, age, username, name, password, roomsList);
                customers.add(customer);
            }
        } catch (SQLException e) {
            System.out.println("Failed to GET all customers from the database: " + "Error Code " + e.getErrorCode());
        }

        return customers;
    }

    public static Customer getCustomerById(int customerId) {
        Customer customer = null;

        if (connection == null) {
            getConnection();
        }

        try {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM Customers WHERE id = ?");
            statement.setInt(1, customerId);
            ResultSet resultSet = statement.executeQuery();

            resultSet.next();

            int id = resultSet.getInt("id");
            String username = resultSet.getString("username");
            String name = resultSet.getString("name");
            String password = resultSet.getString("password");
            int age = resultSet.getInt("age");
            List<Room> roomsList = getCustomerRooms(customerId);

            customer = new Customer(id, age, username, name, password, roomsList);
        } catch (SQLException e) {
            System.out.println("Failed to GET customer with id " + customerId);
        }

        return customer;
    }

    public static Customer getCustomerByCredentials(String user, String pass) {
        Customer customer = null;

        if (connection == null) {
            getConnection();
        }

        try {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM Customers WHERE username = ? AND password = ?");
            statement.setString(1, user);
            statement.setString(2, pass);
            ResultSet resultSet = statement.executeQuery();

            resultSet.next();

            int id = resultSet.getInt("id");
            String username = resultSet.getString("username");
            String name = resultSet.getString("name");
            String password = resultSet.getString("password");
            int age = resultSet.getInt("age");

            customer = new Customer(id, age, username, name, password);
        } catch (SQLException e) {
            System.out.println("Failed to GET customer with username `" + user + "`");
        }

        return customer;
    }

    public static void insertCustomer(Customer customer) {
        if (connection == null) {
            getConnection();
        }

        try {
            PreparedStatement statement = connection.prepareStatement("INSERT INTO Customers VALUES (?, ?, ?, ?, ?)");
            statement.setInt(1, customer.getId());
            statement.setString(2, customer.getName());
            statement.setInt(3, customer.getAge());
            statement.setString(4, customer.getPassword());
            statement.setString(5, customer.getUsername());

            statement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("An error occurred when inserting the customer into the database.");
        }
    }

    /////////////////////////////////// Room ///////////////////////////////////
    public static ObservableList<Room> getAllRooms() {
        ObservableList<Room> rooms = FXCollections.observableArrayList();

        if (connection == null) {
            getConnection();
        }

        try {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM Rooms");
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                int ownerId = resultSet.getInt("ownerId");
                String ownerName = resultSet.getString("ownerName");
                boolean isReserved = resultSet.getBoolean("isReserved");

                Room room = new Room(id, ownerId, ownerName, isReserved);
                rooms.add(room);
            }
        } catch (SQLException e) {
            System.out.println("Failed to GET all rooms from the database: " + "Error Code " + e.getErrorCode());
        }

        return rooms;
    }

    public static Room getRoom(int roomId) {
        Room room = null;

        if (connection == null) {
            getConnection();
        }

        try {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM Rooms WHERE id = " + roomId);
            ResultSet resultSet = statement.executeQuery();

            resultSet.next();

            int id = resultSet.getInt("id");
            int ownerId = resultSet.getInt("ownerId");
            String ownerName = resultSet.getString("ownerName");
            boolean isReserved = resultSet.getBoolean("isReserved");

            room = new Room(id, ownerId, ownerName, isReserved);
        } catch (SQLException e) {
            System.out.println("Failed to GET customer with id " + roomId);
        }

        return room;
    }

    public static ObservableList<Room> getCustomerRooms(int customerId) {
        ObservableList<Room> rooms = FXCollections.observableArrayList();

        if (connection == null) {
            getConnection();
        }

        try {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM Rooms WHERE ownerId = ?");
            statement.setInt(1, customerId);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                int ownerId = resultSet.getInt("ownerId");
                String owner = resultSet.getString("owner");
                boolean isReserved = resultSet.getBoolean("isReserved");

                Room room = new Room(id, ownerId, owner, isReserved);
                rooms.add(room);
            }
        } catch (SQLException e) {
            System.out.println("Failed to GET customer rooms from the database: " + "Error Code " + e.getErrorCode());
        }

        return rooms;
    }

    public static void insertRoom(Room room) {
        if (connection == null) {
            getConnection();
        }

        try {
            PreparedStatement statement = connection.prepareStatement("INSERT INTO Rooms VALUES (?, ?, ?, ?)");
            statement.setInt(1, room.getId());
            statement.setString(2, room.getOwnerName());
            statement.setBoolean(3, room.isReserved());
            statement.setInt(4, room.getOwnerId());

            statement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("An error occurred when inserting the room into the database.");
        }
    }

    public static void insertRoom() {
        if (connection == null) {
            getConnection();
        }

        try {
            PreparedStatement statement = connection.prepareStatement("INSERT INTO Rooms VALUES ()");
            statement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("An error occurred when inserting the room into the database.");
        }
    }

    public static void refreshHotel() {
        hotel = new Hotel(1, "Hotel", getAllRooms(), getAllCustomers(), getAllEmployees());
    }

    public static Hotel getHotel() {
        return hotel;
    }

    public static void refreshCustomer() {
        if (customer == null) {
            return;
        }

        customer = getCustomerById(customer.getId());
    }

    public static Customer getCustomer() {
        return customer;
    }

    public static void setCustomer(Customer cus) {
        customer = cus;
    }

    public static void refreshEmployee() {
        if (employee == null) {
            return;
        }

        employee = getEmployeeById(employee.getId());
    }

    public static Employee getEmployee() {
        return employee;
    }

    public static void setEmployee(Employee emp) {
        employee = emp;
    }

    public static void refreshRoom() {
        if (room == null) {
            return;
        }

        room = getRoom(room.getId());
    }

    public static Room getRoom() {
        return room;
    }

    public static void setRoom(Room r) {
        room = r;
    }
}
