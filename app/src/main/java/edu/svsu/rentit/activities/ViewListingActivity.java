package edu.svsu.rentit.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
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

        Button btn_Owner = findViewById(R.id.button_Owner);
        btn_Owner.setText(currentListing.getUsername());

        TextView txt_Title = findViewById(R.id.textView_title);
        txt_Title.setText(currentListing.getTitle());

        TextView txt_Price = findViewById(R.id.textView_price);
        txt_Price.setText(currentListing.getPrice());

        TextView txt_Description = findViewById(R.id.textView_description);
        txt_Description.setText(currentListing.getDescription());




        btn_Owner.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(ViewListingActivity.this, ProfileActivity.class);
                intent.putExtra("USER_ID", String.valueOf(currentListing.getUserId()));
                startActivity(intent);

            }
        });
    }
}
