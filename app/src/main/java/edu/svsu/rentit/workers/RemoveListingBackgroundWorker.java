package edu.svsu.rentit.workers;

import android.app.AlertDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import edu.svsu.rentit.HttpURLConnectionReader;
import edu.svsu.rentit.activities.UpdateListingActivity;

public class RemoveListingBackgroundWorker extends AsyncTask<String, String, String> {

    Context context;


    public RemoveListingBackgroundWorker(Context ctx) {
        context = ctx;
    }

    @Override
    protected String doInBackground(String... params) {

        //
        HttpURLConnectionReader reader = new HttpURLConnectionReader("remove_listing.php");

        reader.addParam("listingid", params[0]);

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
            Log.d("DEBUG", "REMOVE RESULT " + result);

            Toast toast = Toast.makeText(context,"Removed successful", Toast.LENGTH_SHORT);
            toast.show();

            ((UpdateListingActivity)context).finish();

        } catch (Exception e) {

        }


    }
}