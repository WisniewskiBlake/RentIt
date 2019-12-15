package edu.svsu.rentit.models;

import android.app.AlertDialog;
import android.content.Context;
import android.os.AsyncTask;

import edu.svsu.rentit.models.HttpURLConnectionReader;

public class SubmitReviewBackgroundWorker extends AsyncTask<String, String, String> {

    Context context;
    AlertDialog alertDialog;


    public SubmitReviewBackgroundWorker(Context ctx) {
        context = ctx;
    }

    @Override
    protected String doInBackground(String... params) {

        HttpURLConnectionReader reader = new HttpURLConnectionReader("submit_review.php");

        reader.addParam("review", params[0]);
        reader.addParam("userid", params[1]);
        reader.addParam("reviewCount", params[2]);



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
//            Toast toast = Toast.makeText(context,"submit successful",Toast.LENGTH_SHORT);
//            toast.show();
        } catch (Exception e) {}
    }
}

