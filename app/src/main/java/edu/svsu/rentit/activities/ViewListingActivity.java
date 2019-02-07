package edu.svsu.rentit.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import edu.svsu.rentit.R;
import edu.svsu.rentit.models.Listing;

public class ViewListingActivity extends AppCompatActivity {

    private Listing currentListing;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_listing);

        if (getIntent().hasExtra("LISTING"))
        currentListing = (Listing)getIntent().getSerializableExtra("LISTING");

        TextView txt_Title = findViewById(R.id.textView_title);
        txt_Title.setText(currentListing.getTitle());

        TextView txt_Price = findViewById(R.id.textView_price);
        txt_Price.setText(currentListing.getPrice());

        TextView txt_Description = findViewById(R.id.textView_description);
        txt_Description.setText(currentListing.getDescription());
    }
}
