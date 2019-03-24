package edu.svsu.rentit.activities;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import edu.svsu.rentit.R;
import edu.svsu.rentit.RentItApplication;
import edu.svsu.rentit.models.Listing;
import edu.svsu.rentit.models.User;

public class ViewListingActivity extends AppCompatActivity {

    private int listingId;
    private Listing currentListing;

    TextView txt_Title;
    TextView txt_Price;
    TextView txt_Description;

    FloatingActionButton btn_Edit;
    Button btn_Owner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_listing);

        //
        txt_Title = findViewById(R.id.textView_title);
        txt_Price = findViewById(R.id.textView_price);
        txt_Description = findViewById(R.id.textView_description);

        btn_Edit = findViewById(R.id.button_update);
        btn_Owner = findViewById(R.id.button_Owner);


        if (getIntent().hasExtra("LISTING_ID")) {
            listingId = getIntent().getIntExtra("LISTING_ID", -1);
            currentListing = ((RentItApplication) getApplication()).getListingById(listingId);
        }

        if (((RentItApplication) this.getApplication()).hasUser()) {
            User currentUser = ((RentItApplication) this.getApplication()).getUser();

            if (currentUser.getId() == currentListing.getUserId()){
                btn_Edit.show();
            }

        } else {

            btn_Edit.hide();
        }

        setOutput(currentListing);

        btn_Owner.setText(currentListing.getUsername());
        btn_Owner.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(ViewListingActivity.this, ProfileActivity.class);
                intent.putExtra("USER_ID", String.valueOf(currentListing.getUserId()));
                startActivity(intent);

            }
        });

        btn_Edit.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(ViewListingActivity.this, UpdateListingActivity.class);
                intent.putExtra("LISTING", currentListing);
                startActivity(intent);

            }
        });
    }

    @Override
    public void onResume()
    {
        super.onResume();
        Log.d("DEBUG", "VIEW LISTING RESUME " + currentListing.getPrice());
        currentListing = ((RentItApplication) getApplication()).getListingById(listingId);
        setOutput(currentListing);
    }


    public void setOutput(Listing listing)
    {
        txt_Title.setText(listing.getTitle());
        txt_Description.setText(listing.getDescription());
        txt_Price.setText("$" + String.format("%.2f", listing.getPrice()));
    }

    public void setOutput(String title, String description, String price)
    {
        txt_Title.setText(title);
        txt_Description.setText(description);
        txt_Price.setText("$" + String.format("%.2f", price));
    }
}
