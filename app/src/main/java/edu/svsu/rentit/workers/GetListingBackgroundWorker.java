package edu.svsu.rentit.workers;

import android.app.AlertDialog;
import android.content.Context;
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
import java.util.List;

import edu.svsu.rentit.HttpURLConnectionReader;
import edu.svsu.rentit.models.Listing;
import edu.svsu.rentit.activities.MainActivity;
import edu.svsu.rentit.R;
import edu.svsu.rentit.ListingViewAdapter;

import static android.icu.lang.UCharacter.GraphemeClusterBreak.T;


public class GetListingBackgroundWorker extends AsyncTask<String, String, String> {

    Context context;
    AlertDialog alertDialog;
    ArrayList<Listing> listings = new ArrayList<>();

    public GetListingBackgroundWorker(Context ctx) {
        context = ctx;
    }

    @Override
    public String doInBackground(String... params) {

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
    public void onPostExecute(String result) {

        try {

            JSONArray jArray = new JSONArray(result);

            for (int i = 0; i < jArray.length(); i++) {
                JSONObject jb = jArray.getJSONObject(i);
                String title = jb.getString("title");
                String description = jb.getString("description");
                String price = jb.getString("price");

                //make new identical class and change this to have a text array and data
                //array just like Caravan app
                listings.add(new Listing(title, description, "", "", "$" + price + ".00"));

            }

            RecyclerView recyclerView = (RecyclerView) ((MainActivity)context).findViewById(R.id.listing_recycler);
            ListingViewAdapter adapter = new ListingViewAdapter(listings, context);

            recyclerView.setAdapter(adapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(context));

        } catch (Exception e) {
            Log.d("DEBUG", "Exeption: " + e.toString());
        }

    }

}