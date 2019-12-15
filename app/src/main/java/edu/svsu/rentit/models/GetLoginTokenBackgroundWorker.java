package edu.svsu.rentit.models;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONObject;

import edu.svsu.rentit.RentItApplication;
import edu.svsu.rentit.activities.MainActivity;
import edu.svsu.rentit.models.User;
import edu.svsu.rentit.models.HttpURLConnectionReader;

import static android.content.Context.MODE_PRIVATE;

public class GetLoginTokenBackgroundWorker extends AsyncTask<String, String, String> {

    Context context;


    public GetLoginTokenBackgroundWorker(Context ctx) {
        context = ctx;
    }

    @Override
    protected String doInBackground(String... params) {

        HttpURLConnectionReader reader = new HttpURLConnectionReader("validate_token.php");
        reader.addParam("userid", params[0]);
        reader.addParam("token", params[1]);

        Log.d("DEBUG", "LOCAL TOKEN " + params[1]);

        try {
            return reader.getResponse();

        } catch (Exception e) {
            e.printStackTrace();
            Log.d("DEBUG", e.getMessage());
        }

        return null;
    }

    @Override
    protected void onPostExecute(String result) {

        // Start LoggedInUserActivity if token is valid
        try {
            JSONObject response = new JSONObject(result);

            int success = response.getInt("success");

            Log.d("DEBUG", "TOKEN SUCCESS " + String.valueOf(success));

            if (result != null && success == 1) {

                JSONObject userJSONObject = response.getJSONObject("user");
                User currentUser = new User(userJSONObject);

                // Set Application User
                ((RentItApplication) ((MainActivity)context).getApplication()).setUser(currentUser);

                // Broadcast validated User to MainActivity
                context.sendBroadcast(new Intent("loginSuccess"));

                ((MainActivity)context).setUserValidated();

            } else {

                // Erase local token since login was invalidated
                SharedPreferences sp = context.getSharedPreferences("Login", MODE_PRIVATE);
                SharedPreferences.Editor editor = sp.edit();
                if (sp.contains("Token")) {
                    editor.remove("UserId");
                    editor.remove("Token");
                }
                editor.commit();

                ((MainActivity)context).setUserInvalid();
            }
            ((MainActivity)context).invalidateOptionsMenu();

        } catch (Exception e) {
            e.printStackTrace();
            Log.d("DEBUG", e.getMessage());
        }
    }

}
