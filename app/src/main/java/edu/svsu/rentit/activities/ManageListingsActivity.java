package edu.svsu.rentit.activities;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
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
import edu.svsu.rentit.models.Listing;
import edu.svsu.rentit.models.User;

public class ManageListingsActivity extends AppCompatActivity {

    User currentUser;

    ArrayList<Listing> listings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_listings);


        currentUser = (User)getIntent().getSerializableExtra("USER");
        listings = (ArrayList<Listing>)getIntent().getSerializableExtra("USER_LISTINGS");


        //
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.listing_recycler);
        ListingViewAdapter adapter = new ListingViewAdapter(listings, ManageListingsActivity.this);

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(ManageListingsActivity.this));



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
}
