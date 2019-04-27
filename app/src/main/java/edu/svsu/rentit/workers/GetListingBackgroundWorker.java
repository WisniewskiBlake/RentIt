package edu.svsu.rentit.workers;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import org.json.JSONArray;
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
import java.util.ArrayList;

import edu.svsu.rentit.HttpURLConnectionReader;
import edu.svsu.rentit.RentItApplication;
import edu.svsu.rentit.activities.LoginActivity;
import edu.svsu.rentit.models.Listing;
import edu.svsu.rentit.activities.MainActivity;
import edu.svsu.rentit.R;
import edu.svsu.rentit.ListingViewAdapter;

import static java.lang.Float.parseFloat;


public class GetListingBackgroundWorker extends AsyncTask<String, String, String> {

    Context context;
    AlertDialog alertDialog;


    public GetListingBackgroundWorker(Context ctx) {
        context = ctx;
    }

    @Override
    protected String doInBackground(String... params) {

        if (params.length == 1) {

        }

        //
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

    @Override
    protected void onPostExecute(String result) {

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
                String img = jb.getString("image");
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