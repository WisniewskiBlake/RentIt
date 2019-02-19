package edu.svsu.rentit.activities;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.SearchView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import edu.svsu.rentit.HttpURLConnectionReader;
import edu.svsu.rentit.ListingViewAdapter;
import edu.svsu.rentit.R;
import edu.svsu.rentit.models.Listing;

public class MainActivity extends AppCompatActivity implements Filterable {
        ListingViewAdapter adapter;
        RecyclerView recyclerView;
        RecyclerView.LayoutManager layoutManager;

        //exampleList populates screen with only listing that match given query
        //exampleListFull contains every entry gathered from DB
        ArrayList<Listing> exampleList;
        ArrayList<Listing> exampleListFull;
        GetListingBackgroundWorker1 listingWorker = new GetListingBackgroundWorker1(MainActivity.this);


        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);
            Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);

            //initialization of recyclerView and execution of background worker
            recyclerView = (RecyclerView) (MainActivity.this).findViewById(R.id.listing_recycler);
            recyclerView.setHasFixedSize(true);
            layoutManager = new LinearLayoutManager(MainActivity.this);
            recyclerView.setLayoutManager(layoutManager);
            listingWorker.execute();
        }

        @Override
        public boolean onCreateOptionsMenu(Menu menu) {
            // Inflate the menu; this adds items to the action bar if it is present.
            MenuInflater inflater = getMenuInflater();
            inflater.inflate(R.menu.menu_main, menu);
            SearchView searchView = (SearchView)findViewById(R.id.action_search1);
            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String query) {
                    return false;
                }
                @Override
                public boolean onQueryTextChange(String newText) {
                    getFilter().filter(newText);
                    return false;
                }
            });
            return true;
        }

        @Override
        public boolean onOptionsItemSelected(MenuItem item) {
            // Handle action bar item clicks here. The action bar will
            // automatically handle clicks on the Home/Up button, so long
            // as you specify a parent activity in AndroidManifest.xml.
            int id = item.getItemId();
            //noinspection SimplifiableIfStatement
            if (id == R.id.action_register) {
                startActivity(new Intent(this, RegisterActivity.class));
            }else if(id == R.id.action_login){
                startActivity(new Intent(this, LoginActivity.class));
            }
            return super.onOptionsItemSelected(item);
        }
    //filters the exampleFullList containing all entries based on query and returns
    //the results which then populate the exampleList
    @Override
    public Filter getFilter() {
        return exampleFilter;
    }
    private Filter exampleFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<Listing> filteredList = new ArrayList<>();

            if(constraint == null || constraint.length() == 0){
                filteredList.addAll(exampleListFull);
            }else{
                String filterPattern = constraint.toString().toLowerCase().trim();
                for(Listing item : exampleListFull){
                    if(item.getTitle().toLowerCase().contains(filterPattern)){
                        filteredList.add(item);
                    }
                }
            }
            FilterResults results = new FilterResults();
            results.values = filteredList;
            return results;
        }

        //creates a new adapter and fills it with matched entries only
        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            exampleList.clear();
            exampleList.addAll((List)results.values);
            adapter = new ListingViewAdapter(exampleList, MainActivity.this);
            recyclerView.setAdapter(adapter);
            adapter.notifyDataSetChanged();
        }
    };

    public class GetListingBackgroundWorker1 extends AsyncTask<String, String, String> {
        Context context;
        public GetListingBackgroundWorker1(Context ctx) {
            context = ctx;
        }
        @Override
        public String doInBackground(String... params) {
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
            ArrayList<Listing> dummyList = new ArrayList<>();
            try {
                JSONArray jArray = new JSONArray(result);

                for (int i = 0; i < jArray.length(); i++) {
                    JSONObject jb = jArray.getJSONObject(i);
                    String title = jb.getString("title");
                    String description = jb.getString("description");
                    String price = jb.getString("price");

                    dummyList.add(new Listing(title, description, "", "", "$" + price + ".00"));

                }
                exampleList = dummyList;
                exampleListFull = new ArrayList<>(dummyList);
                adapter = new ListingViewAdapter(dummyList, MainActivity.this);
                recyclerView.setAdapter(adapter);

            } catch (Exception e) {
                Log.d("DEBUG", "Exeption: " + e.toString());
            }

        }

    }
}


