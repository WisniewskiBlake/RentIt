package edu.svsu.rentit.models;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONObject;

import edu.svsu.rentit.models.HttpURLConnectionReader;
import edu.svsu.rentit.RentItApplication;
import edu.svsu.rentit.activities.UpdateListingActivity;
import edu.svsu.rentit.models.Listing;

public class UpdateListingBackgroundWorker extends AsyncTask<String, String, String> {

    Context context;

    String listingId;

    String image;

    public UpdateListingBackgroundWorker(Context ctx) {
        context = ctx;
    }

    @Override
    protected String doInBackground(String... params) {

        //
        HttpURLConnectionReader reader = new HttpURLConnectionReader("update_listing.php");

        //currentListing.getId()), title, description, address, contact, price);
        listingId = params[0];
        reader.addParam("listingid", listingId);
        reader.addParam("title", params[1]);
        reader.addParam("description", params[2]);
        reader.addParam("address", params[3]);
        reader.addParam("contact", params[4]);
        reader.addParam("price", params[5]);
        if(params[6] != null) {
            image = params[6];
            reader.addParam("image", image);
            reader.addParam("name", params[7]);
        }

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
    protected void onPostExecute(String result)
    {
        try {

            Toast toast = Toast.makeText(context,"Update successful", Toast.LENGTH_SHORT);
            toast.show();

            JSONObject coords = new JSONObject(result);

            try {
                Listing listing = ((RentItApplication) ((UpdateListingActivity)context).getApplication()).getListingById(Integer.parseInt(listingId));
                listing.setDistance(coords.getDouble("lat"), coords.getDouble("lon"));

//                listing.setImage(image);

                ((RentItApplication) ((UpdateListingActivity)context).getApplication()).updateListing(listing);

            } catch (Exception ex) {
                Log.d("DEBUG", "UPDATE DISTANCE " + ex.toString());
            }

            ((UpdateListingActivity)context).finish();

        } catch (Exception e) {

        }


    }
}