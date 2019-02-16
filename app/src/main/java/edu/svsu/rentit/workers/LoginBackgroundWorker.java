package edu.svsu.rentit.workers;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONObject;

import edu.svsu.rentit.activities.LoginActivity;
import edu.svsu.rentit.HttpURLConnectionReader;
import edu.svsu.rentit.models.User;

import static android.content.Context.MODE_PRIVATE;

public class LoginBackgroundWorker extends AsyncTask<String, String, String> {

    Context context;


    public LoginBackgroundWorker(Context ctx) {
        context = ctx;
    }

    @Override
    protected String doInBackground(String... params) {

        // Login with username and password
        HttpURLConnectionReader reader = new HttpURLConnectionReader("login.php");

        reader.addParam("username", params[0]);
        reader.addParam("password", params[1]);
        if (Boolean.valueOf(params[2])) reader.addParam("set_token", params[2]);

        try {
            return reader.getResponse();

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

//                Intent intent = new Intent(context, LoggedInUserActivity.class);
//                intent.putExtra("USER", currentUser);
//                context.startActivity(intent);

                // Set login token or erase
                SharedPreferences sp = context.getSharedPreferences("Login", MODE_PRIVATE);
                SharedPreferences.Editor editor = sp.edit();
                if (response.has("token")) {
                    //Log.d("DEBUG", "TOKEN " + response.getString("token"));

                    editor.putString("UserId", currentUser.getIdString());
                    editor.putString("Token", response.getString("token"));
                } else {
                    editor.remove("UserId");
                    editor.remove("Token");
                }
                editor.commit();


                Intent intent = new Intent("loginSuccess");
                intent.putExtra("USER",currentUser);
                context.sendBroadcast(intent);

                ((LoginActivity)context).finish();

            } else {

                Toast toast = Toast.makeText(context,
                        message,
                        Toast.LENGTH_SHORT);

                toast.show();
            }

        } catch (Exception e) {

            e.printStackTrace();
            Log.d("DEBUG", "SET TOKEN FAILED - " + e.toString());
        }
    }
}



