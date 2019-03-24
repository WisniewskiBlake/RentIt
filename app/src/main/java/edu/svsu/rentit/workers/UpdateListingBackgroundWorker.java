package edu.svsu.rentit.workers;

import android.app.AlertDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import edu.svsu.rentit.HttpURLConnectionReader;
import edu.svsu.rentit.activities.UpdateListingActivity;

public class UpdateListingBackgroundWorker extends AsyncTask<String, String, String> {

    Context context;
    AlertDialog alertDialog;


    public UpdateListingBackgroundWorker(Context ctx) {
        context = ctx;
    }

    @Override
    protected String doInBackground(String... params) {

        //
        HttpURLConnectionReader reader = new HttpURLConnectionReader("update_listing.php");

        reader.addParam("listingid", params[0]);
        reader.addParam("title", params[1]);
        reader.addParam("description", params[2]);
        reader.addParam("price", params[3]);

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

        // No checking for success yet..
        try {

            Toast toast = Toast.makeText(context,"Update successful", Toast.LENGTH_SHORT);
            toast.show();

            ((UpdateListingActivity)context).finish();

        } catch (Exception e) {

        }


    }
}