package com.application.models;

import java.util.List;

public class Customer {

    private int id, age;
    private String name, username, password;
    private List<Room> roomsList;

    public Customer(int age, String username, String name, String password) {
        this.age = age;
        this.username = username;
        this.name = name;
        this.password = password;
    }

    public Customer(int id, int age, String username, String name, String password) {
        this(age, username, name, password);
        this.id = id;
    }

    public Customer(int id, int age, String username, String name, String password, List<Room> roomsList) {
        this(id, age, username, name, password);
        this.roomsList = roomsList;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public String toString() {
        return id + " - " + name;
    }
}
