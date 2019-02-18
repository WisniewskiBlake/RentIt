package edu.svsu.rentit.workers;

import android.app.AlertDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import edu.svsu.rentit.HttpURLConnectionReader;

public class CreateListingBackgroundWorker extends AsyncTask<String, String, String> {

    Context context;
    AlertDialog alertDialog;


    public CreateListingBackgroundWorker(Context ctx) {
        context = ctx;
    }

    @Override
    protected String doInBackground(String... params) {

        //
        HttpURLConnectionReader reader = new HttpURLConnectionReader("create_listing_geo.php");

        reader.addParam("userid", params[0]);
        reader.addParam("title", params[1]);
        reader.addParam("description", params[2]);
        reader.addParam("address", params[3]);
        reader.addParam("contact", params[4]);
        reader.addParam("price", params[5]);

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

        }


    }
}