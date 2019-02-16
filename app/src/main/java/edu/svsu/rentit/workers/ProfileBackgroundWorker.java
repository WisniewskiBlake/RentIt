package edu.svsu.rentit.workers;

import android.content.Context;
import android.os.AsyncTask;

import org.json.JSONObject;

import edu.svsu.rentit.HttpURLConnectionReader;
import edu.svsu.rentit.models.User;

public class ProfileBackgroundWorker extends AsyncTask<User, String, String> {

    Context context;
    User currentUser;

    public ProfileBackgroundWorker(Context ctx) {
        context = ctx;
    }

    @Override
    protected String doInBackground(User... params) {

        currentUser = params[0];

        //
        HttpURLConnectionReader reader = new HttpURLConnectionReader("register.php");
        reader.addParam("userid", currentUser.getIdString());

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

        try {

            JSONObject response = new JSONObject(result);

            String first = response.getString("first");
            String last = response.getString("last");
            String bio = response.getString("bio");

        } catch (Exception e) {

        }


    }
}