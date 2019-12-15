package edu.svsu.rentit.models;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.widget.Toast;

import org.json.JSONObject;

import edu.svsu.rentit.models.HttpURLConnectionReader;
import edu.svsu.rentit.activities.LoginActivity;

public class RegisterWorker extends AsyncTask<String, String, String> {

    Context context;


    public RegisterWorker(Context ctx) {
        context = ctx;
    }

    @Override
    protected String doInBackground(String... params) {

        //
        HttpURLConnectionReader reader = new HttpURLConnectionReader("register.php");
        reader.addParam("firstname", params[0]);
        reader.addParam("lastname", params[1]);
        reader.addParam("username", params[2]);
        reader.addParam("password", params[3]);

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

            int success = response.getInt("success");
            String message = response.getString("message");


            if (success == 1) {
                context.startActivity(new Intent(context, LoginActivity.class));
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