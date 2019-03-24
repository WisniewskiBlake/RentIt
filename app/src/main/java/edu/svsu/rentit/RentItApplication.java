package edu.svsu.rentit;

import android.app.Application;

import edu.svsu.rentit.models.User;

public class RentItApplication extends Application {

    private User currentUser;

    public boolean hasUser() {
        return currentUser != null;
    }

    public User getUser() {
        return currentUser;
    }

    public void setUser(User newUser) {
        this.currentUser = newUser;
    }
}
