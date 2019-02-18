package edu.svsu.rentit.models;

import java.io.Serializable;

public class Listing implements Serializable {

    public String title;
    public String description;
    public String address;
    public String contact;
    public String price;
    public double distance;

    public Listing(String newTitle, String newDescription, String newAddress, double newDistance,
                   String newContact, String newPrice) {

        title = newTitle;
        description = newDescription;
        address = newAddress;
        contact = newContact;
        price = newPrice;
        distance = newDistance;

    }

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
    public String getPrice()
    {
        return price;
    }
    public double getDistance() { return distance; }

}
