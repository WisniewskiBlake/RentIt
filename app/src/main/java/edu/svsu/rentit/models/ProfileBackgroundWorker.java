package edu.svsu.rentit.models;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONObject;

import edu.svsu.rentit.models.HttpURLConnectionReader;
import edu.svsu.rentit.activities.ProfileActivity;

public class ProfileBackgroundWorker extends AsyncTask<String, String, String> {

    Context context;

    // It's really an int
    String userId;

    public ProfileBackgroundWorker(Context ctx) {
        context = ctx;
    }

    @Override
    protected String doInBackground(String... params) {

        userId = params[0];

        HttpURLConnectionReader reader = new HttpURLConnectionReader("get_profile.php");
        reader.addParam("userid", userId);

        String response;
        try {
            response = reader.getResponse();
            return response;

        } catch (Exception e) {
            e.printStackTrace();
            return e.toString();
        }
    }

    @Override
    protected void onPostExecute(String result) {

        Log.d("DEBUG", result);
        try {

            JSONObject response = new JSONObject(result);

            String first = response.getString("first");
            String last = response.getString("last");
            String bio = response.getString("bio");
            String review = response.getString("review");
            String reviewCount = response.getString("reviewCount");
            ((ProfileActivity)context).setOutput(first, bio, review, reviewCount, userId);

        } catch (Exception e) {

        }


    }
}