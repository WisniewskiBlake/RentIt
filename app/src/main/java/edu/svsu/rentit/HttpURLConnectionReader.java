package edu.svsu.rentit;

import android.content.ContentValues;
import android.util.Log;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class HttpURLConnectionReader {

    private static String baseUrl = "http://18.224.109.190/rentit/scripts/";

    private String scriptName;

    private ContentValues params;

    public HttpURLConnectionReader(String script)
    {
        scriptName = script;
        params = new ContentValues();
    }

    public void addParam(String paramName, String paramValue)
    {
        params.put(paramName, paramValue);
    }

    private String getPostData()
    {
        StringBuilder postData = new StringBuilder();
        Boolean first = true;

        try {

            for (String key: params.keySet()){
                if (first) {
                    first = false;
                } else {
                    postData.append("&");
                }
                postData.append(URLEncoder.encode(key, "UTF-8") + "=" + URLEncoder.encode(params.getAsString(key), "UTF-8"));
            }

        } catch (IOException e) {
            Log.d("DEBUG", "Error building POST data: " + e.toString());
        }

        return postData.toString();
    }

    public String getResponse()
            throws Exception
    {
        URL url = null;
        BufferedReader reader = null;
        StringBuilder stringBuilder;

        HttpURLConnection connection;

        try {
            url = new URL(baseUrl + scriptName);

            // Create the Http connection
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setDoOutput(true);
            //connection.setDoInput(true);

            // Write POST data from params
            OutputStream outputStream = connection.getOutputStream();

            Log.d("DEBUG", getPostData());

            BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
            bufferedWriter.write(getPostData());
            bufferedWriter.flush();
            bufferedWriter.close();

            outputStream.close();

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            throw e;
        }


        try {
            int response_code = connection.getResponseCode();

            // Check if successful connection made
            if (response_code == HttpURLConnection.HTTP_OK) {

                // give it 15 seconds to respond
                connection.setReadTimeout(15000);
                connection.connect();

                // Read the output from the server
                reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                stringBuilder = new StringBuilder();

                String line = null;
                while ((line = reader.readLine()) != null)
                {
                    stringBuilder.append(line + "\n");
                }
                return stringBuilder.toString();

            }
            else {
                return("Connection error");
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        } finally {
            // This can throw an exception too, so
            // wrap it in another try/catch block.
            if (reader != null)
            {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
