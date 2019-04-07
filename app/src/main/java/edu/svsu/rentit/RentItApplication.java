package edu.svsu.rentit;

import android.app.Application;
import android.util.Log;

import java.util.ArrayList;

import edu.svsu.rentit.models.Listing;
import edu.svsu.rentit.models.User;

public class RentItApplication extends Application {

    private User currentUser;

    private boolean listingsChangedFlag;
    private ArrayList<Listing> currentListings;

    public void RentItApplication() {

        // Initialize listings array
        currentListings = new ArrayList<>();
    }

    public boolean hasUser() {
        return currentUser != null;
    }

    public User getUser() {
        return currentUser;
    }

    public void setUser(User newUser) {
        this.currentUser = newUser;
    }

    public boolean hasListing() { return (currentListings != null && currentListings.size() > 0); }

    public ArrayList<Listing> getListings() { return currentListings; }
    public ArrayList<Listing> getActiveListings() {
        return currentListings;
    }

    public Listing getListingById(int id) {
        for (int i = 0; i < currentListings.size(); i++) {
            if (currentListings.get(i).getId() == id) {
                return currentListings.get(i);
            }
        }

        return null;
    }

    public void setListings(ArrayList<Listing> newListings) { currentListings = newListings; }

    public void addListing(Listing newListing) {
        Log.d("DEBUG", "BEFORE " + currentListings.size());
        currentListings.add(newListing);
        Log.d("DEBUG", "AFTER " + currentListings.size());
    }

    public void updateListing(Listing updatedListing) {
        for (int i = 0; i < currentListings.size(); i++) {
            if (currentListings.get(i).getId() == updatedListing.getId())
                currentListings.set(i, updatedListing);
        }
    }

    public void removeListingById(int id)
    {
        boolean found = false;
        for (int i = 0; i < currentListings.size() && !found; i++) {
            if (currentListings.get(i).getId() == id) {
                found = true;
                currentListings.get(i).setStatus("inactive");
            }
        }
    }
}
