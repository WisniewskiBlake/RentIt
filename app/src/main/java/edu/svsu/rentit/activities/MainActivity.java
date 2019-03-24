package edu.svsu.rentit.activities;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Filter;

import java.util.ArrayList;
import java.util.Iterator;

import edu.svsu.rentit.ListingViewAdapter;
import edu.svsu.rentit.R;
import edu.svsu.rentit.RentItApplication;
import edu.svsu.rentit.models.Listing;
import edu.svsu.rentit.models.User;
import edu.svsu.rentit.workers.GetListingBackgroundWorker;
import edu.svsu.rentit.workers.GetLoginTokenBackgroundWorker;

public class MainActivity extends AppCompatActivity {

    private boolean validating;
    private boolean validated;
    User currentUser;

    ArrayList<Listing> listings = new ArrayList<Listing>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        // Check if login token exists
        SharedPreferences sp = getSharedPreferences("Login", MODE_PRIVATE);

        if (sp.contains("Token") && sp.contains("UserId")) {
            String userId = sp.getString("UserId", "");
            String token = sp.getString("Token", "");

            Log.d("DEBUG", "TOKEN FOUND - " + userId + " - " + token);

            // Update menu
            validating = true;
            invalidateOptionsMenu();

            // Validate login token
            GetLoginTokenBackgroundWorker tokenWorker = new GetLoginTokenBackgroundWorker(MainActivity.this);
            tokenWorker.execute(userId, token);
        }

        // Populate listings
        GetListingBackgroundWorker listingWorker = new GetListingBackgroundWorker(MainActivity.this);
        listingWorker.execute();


        // Register successful login receiver
        registerReceiver(new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                validating = false;
                validated = true;
                invalidateOptionsMenu();

                if (((RentItApplication) getApplication()).hasUser())
                    currentUser = ((RentItApplication) getApplication()).getUser();
            }
        }, new IntentFilter("loginSuccess"));

        // Register get listing receiver
        registerReceiver(new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                listings = ((RentItApplication) getApplication()).getListings();
                updateListings(listings);
            }
        }, new IntentFilter("listingsGet"));

    }

    private void updateListings(ArrayList<Listing> newListings)
    {
        // Only display active listings in MainActivity
        ArrayList<Listing> activeListings = new ArrayList<>();
        for (Listing l : newListings) {

            if (!l.getStatus().equals("inactive")) {
                activeListings.add(l);
            }
        }

        RecyclerView recyclerView = findViewById(R.id.listing_recycler);
        ListingViewAdapter adapter = new ListingViewAdapter(activeListings, MainActivity.this);

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        menu.findItem(R.id.validate).setVisible(false);
        menu.findItem(R.id.action_manage).setVisible(false);
        menu.findItem(R.id.action_view_profile).setVisible(false);
        menu.findItem(R.id.action_logout).setVisible(false);

        SearchView searchView = (SearchView)findViewById(R.id.action_search);
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
    public boolean onPrepareOptionsMenu(Menu menu) {
        if (validating) {
            menu.findItem(R.id.validate).setVisible(true);
            menu.findItem(R.id.action_login).setVisible(false);
            menu.findItem(R.id.action_register).setVisible(true);
            menu.findItem(R.id.action_manage).setVisible(false);
            menu.findItem(R.id.action_view_profile).setVisible(false);
            menu.findItem(R.id.action_logout).setVisible(false);
        } else if (validated) {
            menu.findItem(R.id.validate).setVisible(false);
            menu.findItem(R.id.action_login).setVisible(false);
            menu.findItem(R.id.action_register).setVisible(false);
            menu.findItem(R.id.action_manage).setVisible(true);
            menu.findItem(R.id.action_view_profile).setVisible(true);
            menu.findItem(R.id.action_logout).setVisible(true);
        }

        return super.onPrepareOptionsMenu(menu);
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
        }else if(id == R.id.action_login) {
            startActivity(new Intent(this, LoginActivity.class));
        } else if (id == R.id.action_manage) {
            Intent intent = new Intent(this, ManageListingsActivity.class);

            ArrayList<Listing> userListings = new ArrayList<Listing>();
            for (Listing listing : listings) {
                if (listing.getUserId() == currentUser.getId())
                    userListings.add(listing);
            }

            intent.putExtra("USER", currentUser);
            intent.putExtra("USER_LISTINGS", userListings);

            startActivity(intent);

        } else if (id == R.id.action_logout) {

            SharedPreferences sp = getSharedPreferences("Login", MODE_PRIVATE);
            SharedPreferences.Editor editor = sp.edit();
            editor.remove("Token");
            editor.remove("UserId");
            editor.commit();

            validated = false;
            invalidateOptionsMenu();
        } else if (id == R.id.action_view_profile) {
            Intent intent = new Intent(this, ProfileActivity.class);
            intent.putExtra("CURRENT_USER", currentUser);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onResume()
    {
        super.onResume();

        if (((RentItApplication) getApplication()).hasListing()) {
            updateListings(listings);
        }
    }

    //filters the exampleFullList containing all entries based on query and returns
    //the results which then populate the exampleList
    //@Override
    public Filter getFilter() {
        return exampleFilter;
    }
    private Filter exampleFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            ArrayList<Listing> filteredList = new ArrayList<>();

            if(constraint == null || constraint.length() == 0){
                filteredList.addAll(listings);
            }else{
                String filterPattern = constraint.toString().toLowerCase().trim();
                for(Listing item : listings){
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

            updateListings((ArrayList)results.values);
            /*
            exampleList.clear();
            exampleList.addAll((List)results.values);
            adapter = new ListingViewAdapter(exampleList, MainActivity.this);
            recyclerView.setAdapter(adapter);
            adapter.notifyDataSetChanged();
            */
        }
    };

    public void setUserValidated() {
        validating = false;
        validated = true;
    }
    public void setUserInvalid() {
        validating = false;
        validated = false;
    }

}

