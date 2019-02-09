package edu.svsu.rentit.workers;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

import edu.svsu.rentit.HttpURLConnectionReader;
import edu.svsu.rentit.activities.LoggedInUserActivity;
import edu.svsu.rentit.models.User;

public class LoginBackgroundWorker extends AsyncTask<String, String, String> {

    Context context;


    public LoginBackgroundWorker(Context ctx) {
        context = ctx;
    }

    @Override
    protected String doInBackground(String... params) {

        // Login with username and password
        HttpURLConnectionReader reader = new HttpURLConnectionReader("login.php");

        String response;
        reader.addParam("username", params[0]);
        reader.addParam("password", params[1]);

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

            int success = response.getInt("success");
            String message = response.getString("message");

            if (success == 1) {

                JSONObject userJSONObject = response.getJSONObject("user");
                User currentUser = new User(userJSONObject);

                Intent intent = new Intent(context, LoggedInUserActivity.class);
                intent.putExtra("USER", currentUser);
                context.startActivity(intent);

            } else {

                Toast toast = Toast.makeText(context,
                        message,
                        Toast.LENGTH_SHORT);

                toast.show();
            }

        } catch (Exception e) {
        }


    }
}