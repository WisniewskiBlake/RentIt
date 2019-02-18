package edu.svsu.rentit.workers;

import android.app.AlertDialog;
import android.content.Context;
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
import edu.svsu.rentit.models.Listing;
import edu.svsu.rentit.activities.MainActivity;
import edu.svsu.rentit.R;
import edu.svsu.rentit.ListingViewAdapter;


public class GetListingBackgroundWorker extends AsyncTask<String, String, String> {

    Context context;
    AlertDialog alertDialog;


    public GetListingBackgroundWorker(Context ctx) {
        context = ctx;
    }

    @Override
    protected String doInBackground(String... params) {

        //
        HttpURLConnectionReader reader = new HttpURLConnectionReader("get_listing_geo.php");

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
                String title = jb.getString("title");
                String description = jb.getString("description");
                String price = jb.getString("price");
                double lat1 = jb.getDouble("lat");
                double lon1 = jb.getDouble("lon");

                double distance = distance(lat1,lon1,43.549014678840194,-83.95262718200684);

                listings.add(new Listing(title, description,  "", distance, "", "$" + price + ".00"));
            }


            RecyclerView recyclerView = (RecyclerView) ((MainActivity)context).findViewById(R.id.listing_recycler);
            ListingViewAdapter adapter = new ListingViewAdapter(listings, context);

            recyclerView.setAdapter(adapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(context));



        } catch (Exception e) {
            Log.d("DEBUG", "Exeption: " + e.toString());
        }


    }

    /** calculates the distance between two locations in MILES */
    private double distance(double lat1, double lng1, double lat2, double lng2) {

        double earthRadius = 3958.75; // in miles, change to 6371 for kilometer output

        double dLat = Math.toRadians(lat2-lat1);
        double dLng = Math.toRadians(lng2-lng1);

        double sindLat = Math.sin(dLat / 2);
        double sindLng = Math.sin(dLng / 2);

        double a = Math.pow(sindLat, 2) + Math.pow(sindLng, 2)
                * Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2));

        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));

        double dist = earthRadius * c;

        return dist; // output distance, in MILES
    }
}