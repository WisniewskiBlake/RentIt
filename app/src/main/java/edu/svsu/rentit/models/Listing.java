package edu.svsu.rentit.models;

import android.media.Image;
import android.util.Log;
import android.widget.ArrayAdapter;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.Serializable;

public class Listing implements Serializable {

    private int id;
    private int userId;
    private String username;
    private String title;
    private String description;

    private Boolean fullAddress = false;
    private String address;
    private String city;
    private String state;
    private String zip;
    private String country;

    private String contact;
    private double price;

    private double latitude;
    private double longitude;
    private double distance;

    private String status;
    private String image;

    private String review;
    private String reviewCount;

    public Listing(int newId, int newUserId, String newUsername, String newTitle, String newDescription, String newAddress, double newLat, double newLon,
                   String newContact, double newPrice, String newStatus, String newImage, String newReview, String newReviewCount) {

        id = newId;
        userId = newUserId;
        username = newUsername;
        title = newTitle;
        description = newDescription;

        setAddress(newAddress);

        contact = newContact;
        price = newPrice;
        setDistance(newLat, newLon);
        status = newStatus;
        image = newImage;
        review = newReview;
        reviewCount = newReviewCount;
    }
    public Listing(int newId, int newUserId, String newUsername, String newTitle, String newDescription, String newAddress, double newLat, double newLon,
                   String newContact, double newPrice, String newStatus, String newImage) {

        id = newId;
        userId = newUserId;
        username = newUsername;
        title = newTitle;
        description = newDescription;

        setAddress(newAddress);

        contact = newContact;
        price = newPrice;
        setDistance(newLat, newLon);
        status = newStatus;
        image = newImage;

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
    public String getCity() { return city; }
    public String getState() { return state; }
    public String getZip() { return zip; }
    public String getCountry() { return country; }
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
    public String getImage() { return image; }
    public String getReview() { return review; }
    public String getReviewCount() { return reviewCount; }
    public Boolean hasFullAddress() { return fullAddress; }

    public void setImage(String img) { image = img; }

    public void setTitle(String newTitle) { title = newTitle; }
    public void setDescription(String newDescription) { description = newDescription; }
    public void setAddress(String newAddress) {

        address = newAddress;

        String[] addressParts = address.split(",");
        if (addressParts.length == 4) {
            fullAddress = true;

            address = addressParts[0];
            city = addressParts[1];

            String[] stateZip = addressParts[2].trim().split(" ");

            state = stateZip[0];
            zip = stateZip[1];

            country = addressParts[3];

        }
    }
    public void setContact(String newContact) { contact = newContact; }
    public void setPrice(double newPrice) { price = newPrice; }
    public void setDistance(double newLat, double newLon) {
        latitude = newLat;
        longitude = newLon;
        distance = distance(latitude, longitude,43.549014678840194,-83.95262718200684);
    }
    public void setStatus(String newStatus) { status = newStatus; }


    /** calculates the distance between two locations in MILES */
    private double distance(double lat1, double lng1, double lat2, double lng2) {

        double earthRadius = 3958.75; // in miles, change to 6371 for kilometer output

        double dLat = Math.toRadians(lat2-lat1);
        double dLng = Math.toRadians(lng2-lng1);

        double sindLat = Math.sin(dLat / 2);
        double sindLng = Math.sin(dLng / 2);

        double a = Math.pow(sindLat, 2) + Math.pow(sindLng, 2)
                * Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2));

        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));

        double dist = earthRadius * c;

        return dist; // output distance, in MILES
    }
}