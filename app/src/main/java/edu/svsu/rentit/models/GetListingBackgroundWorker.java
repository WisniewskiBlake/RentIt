package edu.svsu.rentit.models;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import edu.svsu.rentit.R;
import edu.svsu.rentit.models.HttpURLConnectionReader;
import edu.svsu.rentit.RentItApplication;
import edu.svsu.rentit.models.Listing;
import edu.svsu.rentit.activities.MainActivity;


public class GetListingBackgroundWorker
        extends AsyncTask<String, String, String>
        {

    Context context;
    AlertDialog alertDialog;

    public GetListingBackgroundWorker(Context ctx) {
        context = ctx;
    }
    @Override
    protected String doInBackground(String... params) {

        HttpURLConnectionReader reader = new HttpURLConnectionReader("get_listing.php");

        String response;
        try {
            response = reader.getResponse();
            return response;
        } catch (Exception e) {
            e.printStackTrace();
            return e.toString();
        }
    }

            //after we get the info we need, which in this case is all the listings, we pass it to
    //on post execute method to assign variables to each piece of information that we get
    //    //
    @Override
    protected void onPostExecute(String result) {

        String test = result;
        ArrayList<Listing> listings = new ArrayList<>();
        try {

            JSONArray jArray = new JSONArray(result);

            for (int i = 0; i < jArray.length(); i++) {
                JSONObject jb = jArray.getJSONObject(i);
                int id = jb.getInt("id");
                int userId = jb.getInt("userId");
                String username = jb.getString("username");
                String title = jb.getString("title");
                String description = jb.getString("description");
                String address = jb.getString("address");
                String contact = jb.getString("contact");
                double price = jb.getDouble("price");
                String status = jb.getString("status");
                double lat1 = jb.getDouble("lat");
                double lon1 = jb.getDouble("lon");
                int img = 0;
                if(i == 4) {
                    img = R.drawable.beets;
                } else if(i == 3) {
                    img = R.drawable.carrots;
                }else if(i == 2) {
                    img = R.drawable.lettuce;
                }else if(i == 1) {
                    img = R.drawable.tomatoes;
                }else if(i == 0) {
                    img = R.drawable.strawberries;
                }

                String review = jb.getString("review");
                String reviewCount = jb.getString("reviewCount");

                listings.add(0, new Listing(id, userId, username, title, description,  address, lat1, lon1, contact, price, status, img, review, reviewCount));
            }

            // Create local store of listings
            ((RentItApplication) ((MainActivity)context).getApplication()).setListings(listings);

            // Notify MainActivity that listings are available
            context.sendBroadcast(new Intent("listingsGet"));



        } catch (Exception e) {
            e.printStackTrace();
            Log.d("DEBUG", "Exeption: " + e.toString());
        }


    }

}