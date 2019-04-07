package edu.svsu.rentit.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;

import edu.svsu.rentit.ListingViewAdapter;
import edu.svsu.rentit.R;
import edu.svsu.rentit.RentItApplication;
import edu.svsu.rentit.models.Listing;
import edu.svsu.rentit.models.User;

public class ManageListingsActivity extends AppCompatActivity {

    User currentUser;

    ArrayList<Listing> listings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_listings);
        setTitle("Manage Listings");

        currentUser = (User)getIntent().getSerializableExtra("USER");
        listings = ((RentItApplication) getApplication()).getListings();

        updateListings();


        Button btnCreate = findViewById(R.id.btn_CreateListing);
        btnCreate.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(ManageListingsActivity.this, CreateListingActivity.class);
                intent.putExtra("USER", currentUser);
                startActivity(intent);

            }
        });
    }

    @Override
    public void onResume()
    {
        super.onResume();

        if (((RentItApplication) getApplication()).hasListing()) {
            updateListings();
        }
    }

    public void updateListings()
    {

        ArrayList<Listing> userListings = new ArrayList<Listing>();
        for (Listing listing : listings) {
            if (listing.getUserId() == currentUser.getId() && !listing.getStatus().equals("inactive"))
                userListings.add(listing);
        }

        //
        RecyclerView recyclerView = findViewById(R.id.listing_recycler);
        ListingViewAdapter adapter = new ListingViewAdapter(userListings, ManageListingsActivity.this);

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(ManageListingsActivity.this));
    }
}
