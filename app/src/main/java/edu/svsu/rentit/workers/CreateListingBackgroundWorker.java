package edu.svsu.rentit.workers;

import android.app.AlertDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONObject;

import edu.svsu.rentit.HttpURLConnectionReader;
import edu.svsu.rentit.RentItApplication;
import edu.svsu.rentit.activities.CreateListingActivity;
import edu.svsu.rentit.activities.UpdateListingActivity;
import edu.svsu.rentit.models.Listing;

public class CreateListingBackgroundWorker extends AsyncTask<String, String, String> {

    Context context;

    private String userId, title, description, address, contact, price;

    public CreateListingBackgroundWorker(Context ctx) {
        context = ctx;
    }

    @Override
    protected String doInBackground(String... params) {

        //
        HttpURLConnectionReader reader = new HttpURLConnectionReader("create_listing_geo.php");

        userId = params[0];
        title = params[1];
        description = params[2];
        address = params[3];
        contact = params[4];
        price = params[5];

        reader.addParam("userid", userId);
        reader.addParam("title", title);
        reader.addParam("description", description);
        reader.addParam("address", address);
        reader.addParam("contact", contact);
        reader.addParam("price", price);

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

            JSONObject json = new JSONObject(result);
            int listingId = json.getInt("listingid");
            String username = json.getString("username");
            double lat = json.getInt("lat");
            double lon = json.getInt("lon");

            ((RentItApplication)((CreateListingActivity)context).getApplication()).addListing(new Listing(listingId, Integer.parseInt(userId), username, title, description, address, lat, lon, contact, Double.parseDouble(price), ""));

            ((CreateListingActivity)context).finish();
            /*
            JSONObject response = new JSONObject(result);

            int success = response.getInt("success");
            String message = response.getString("message");


            if (success == 1) {
                context.startActivity(new Intent(context, LoginActivity.class));
            } else {*/
                Toast toast = Toast.makeText(context,
                    "submit successful",
                    Toast.LENGTH_SHORT);


                toast.show();
            //}

        } catch (Exception e) {
            Log.d("DEBUG", "CREATE LISTING " + e.toString());
        }


    }
}