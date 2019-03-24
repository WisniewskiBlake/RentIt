package edu.svsu.rentit.models;

import android.util.Log;

import java.io.Serializable;

public class Listing implements Serializable {

    private int id;
    private int userId;
    private String username;
    private String title;
    private String description;
    private String address;
    private String contact;
    private double price;
    private double distance;
    private String status;

    public Listing(int newId, int newUserId, String newUsername, String newTitle, String newDescription, String newAddress, double newDistance,
                   String newContact, double newPrice, String newStatus) {

        id = newId;
        userId = newUserId;
        username = newUsername;
        title = newTitle;
        description = newDescription;
        address = newAddress;
        contact = newContact;
        price = newPrice;
        distance = newDistance;
        status = newStatus;
    }

    public int getId() { return id; }
    public int getUserId() { return userId; }
    public String getUsername() { return username; }
    public String getTitle()
    {
        return title;
    }
    public String getDescription()
    {
        return description;
    }
    public String getAddress()
    {
        return address;
    }
    public String getContact()
    {
        return contact;
    }
    public double getPrice()
    {
        return price;
    }
    public double getDistance() { return distance; }
    public String getStatus() { return status; }

    public void setTitle(String newTitle) { title = newTitle; }
    public void setDescription(String newDescription) { description = newDescription; }
    public void setPrice(double newPrice) { price = newPrice; }
    public void setStatus(String newStatus) { status = newStatus; }

}
