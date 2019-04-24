package edu.svsu.rentit.models;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

public class User implements Serializable {

    private int Id;
    private String Username;

    private String FirstName;
    private String LastName;

    private String Address;
    private String City;
    private String State;
    private String Zipcode;

    private String Bio;

    private int Admin = 0;

    public User(JSONObject userJSONObject)
    {
        try {

            Id = userJSONObject.getInt("id");
            if (userJSONObject.has("username")) Username = userJSONObject.getString("username");
            FirstName = userJSONObject.getString("firstname");
            LastName = userJSONObject.getString("lastname");
            Address = userJSONObject.getString("address");
            City = userJSONObject.getString("city");
            State = userJSONObject.getString("state");
            Zipcode = userJSONObject.getString("zipcode");
            Bio = userJSONObject.getString( "bio" );
            Admin = userJSONObject.optInt("admin", 0);

        } catch (JSONException ex) {
            Log.d("DEBUG", ex.toString());
        }
    }


    public int getId()
    {
        return Id;
    }

    public String getUsername() { if (Username != null) return Username; else return ""; }

    public String getIdString()
    {
        return Integer.toString(Id);
    }

    public String getFirstname() { return FirstName; }

    public String getLastname() { return LastName; }

    public String getAddress() { return Address; }

    public String getCity() { return City; }

    public String getState() { return State; }

    public String getZipcode() { return Zipcode; }

    public String getBio() { return Bio; }

    public Boolean isAdmin() { return Admin == 1; }
}
