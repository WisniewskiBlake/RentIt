package edu.svsu.rentit.models;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

public class User implements Serializable {

    private int Id;

    private String FirstName;
    private String LastName;

    private String Address;
    private String City;
    private String State;
    private String Zipcode;

    private String Bio;

    public User(JSONObject userJSONObject)
    {
        try {

            Id = userJSONObject.getInt("id");
            FirstName = userJSONObject.getString("firstname");
            LastName = userJSONObject.getString("lastname");
            Address = userJSONObject.getString("address");
            City = userJSONObject.getString("city");
            State = userJSONObject.getString("state");
            Zipcode = userJSONObject.getString("zipcode");
            Bio = userJSONObject.getString( "bio" );

        } catch (JSONException ex) {
            Log.d("DEBUG", ex.toString());
        }
    }

    public int getId()
    {
        return Id;
    }

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
}
