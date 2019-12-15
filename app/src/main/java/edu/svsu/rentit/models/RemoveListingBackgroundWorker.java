package edu.svsu.rentit.models;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import edu.svsu.rentit.models.HttpURLConnectionReader;
import edu.svsu.rentit.RentItApplication;
import edu.svsu.rentit.activities.UpdateListingActivity;

public class RemoveListingBackgroundWorker extends AsyncTask<String, String, String> {

    Context context;

     String listingId;


    public RemoveListingBackgroundWorker(Context ctx) {
        context = ctx;
    }

    @Override
    protected String doInBackground(String... params) {

        // Store params
        listingId = params[0];

        //
        HttpURLConnectionReader reader = new HttpURLConnectionReader("remove_listing.php");

        reader.addParam("listingid", listingId);

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
            Log.d("DEBUG", "REMOVE RESULT " + result);

            Toast toast = Toast.makeText(context,"Removed successful", Toast.LENGTH_SHORT);
            toast.show();


            ((RentItApplication)((UpdateListingActivity)context).getApplication()).removeListingById(Integer.parseInt(listingId));

            ((UpdateListingActivity)context).finish();

        } catch (Exception e) {

        }


    }
}