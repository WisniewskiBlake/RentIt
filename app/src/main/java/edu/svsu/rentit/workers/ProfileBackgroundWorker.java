package edu.svsu.rentit.workers;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.EditText;
import android.widget.TextView;

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

import edu.svsu.rentit.R;
import edu.svsu.rentit.activities.ProfileActivity;
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

        URL url = null;
        HttpURLConnection httpURLConnection;
        String HttpURL = "http://18.224.109.190/rentit/scripts/get_profile.php";

        try {
            url = new URL(HttpURL);

        } catch (MalformedURLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return e.toString();
        }

        try {
            httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setDoInput(true);

            OutputStream outputStream = httpURLConnection.getOutputStream();
            BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
            String post_data = URLEncoder.encode("userid", "UTF-8") + "=" + URLEncoder.encode(currentUser.getIdString(), "UTF-8");
            bufferedWriter.write(post_data);
            bufferedWriter.flush();
            bufferedWriter.close();
            outputStream.close();
            httpURLConnection.connect();
        }catch (IOException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
            return e1.toString();
        }

        try {
            int response_code = httpURLConnection.getResponseCode();

            // Check if successful connection made
            if (response_code == HttpURLConnection.HTTP_OK) {

                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "iso-8859-1"));
                String result = "";
                String line = "";
                while((line = bufferedReader.readLine()) != null){
                    result += line;
                }
                bufferedReader.close();
                inputStream.close();

                return result;

            }
            else {
                return("Connection error");
            }

        } catch (IOException e) {
            e.printStackTrace();
            return e.toString();
        } finally {
            httpURLConnection.disconnect();
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