package edu.svsu.rentit.models;

import android.app.AlertDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import edu.svsu.rentit.models.HttpURLConnectionReader;
import edu.svsu.rentit.R;
import edu.svsu.rentit.UserViewAdapter;
import edu.svsu.rentit.activities.ManageUsersActivity;
import edu.svsu.rentit.models.User;


public class GetUsersBackgroundWorker extends AsyncTask<String, String, String> {

    Context context;
    AlertDialog alertDialog;


    public GetUsersBackgroundWorker(Context ctx) {
        context = ctx;
    }

    @Override
    protected String doInBackground(String... params)
    {

        //
        HttpURLConnectionReader reader = new HttpURLConnectionReader("get_users.php");

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

        ArrayList<User> users = new ArrayList<>();

        try {

            JSONArray jArray = new JSONArray(result);

            for (int i = 0; i < jArray.length(); i++) {
                JSONObject jb = jArray.getJSONObject(i);

                Log.d("DEBUG", jb.toString());
//                int id = jb.getInt("id");
//                int userId = jb.getInt("userId");
//                String username = jb.getString("username");
//                String title = jb.getString("title");
//                String description = jb.getString("description");
//                String address = jb.getString("address");
//                String contact = jb.getString("contact");
//                double price = jb.getDouble("price");
//                String status = jb.getString("status");
//                double lat1 = jb.getDouble("lat");
//                double lon1 = jb.getDouble("lon");

                users.add(new User(jb));
                //listings.add(new Listing(id, userId, username, title, description,  address, lat1, lon1, contact, price, status));
            }

            RecyclerView recyclerView = (RecyclerView) ((ManageUsersActivity)context).findViewById(R.id.UserRecyclerView);
            UserViewAdapter adapter = new UserViewAdapter(users, context);

            recyclerView.setAdapter(adapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(context));
            // Create local store of listings
            //((RentItApplication) ((MainActivity)context).getApplication()).setListings(listings);

            // Notify MainActivity that listings are available
            //context.sendBroadcast(new Intent("listingsGet"));



        } catch (Exception e) {
            e.printStackTrace();
            Log.d("DEBUG", "Exeption: " + e.toString());
        }


    }

}