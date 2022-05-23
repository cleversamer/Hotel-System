package com.application.models;

public class Room {

    private int id, ownerId;
    private String ownerName;
    private boolean isReserved;

    public Room(int id, int ownerId, String ownerName, boolean isReserved) {
        this.id = id;
        this.ownerId = ownerId;
        this.ownerName = ownerName;
        this.isReserved = isReserved;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    public boolean isReserved() {
        return isReserved;
    }

    public void setReserved(boolean reserved) {
        isReserved = reserved;
    }

    public void reserveBy(String owner) {
        if (isReserved) {
            return;
        }

        this.isReserved = true;
        this.ownerName = owner;
    }

    public void checkout() {
        if (!isReserved) {
            return;
        }

        this.isReserved = false;
        this.ownerName = null;
    }

    public int getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(int ownerId) {
        this.ownerId = ownerId;
    }

    @Override
    public String toString() {
        return id + " - " + ownerName;
    }
}
