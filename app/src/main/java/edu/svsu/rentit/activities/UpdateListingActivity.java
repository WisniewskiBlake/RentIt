package edu.svsu.rentit.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import edu.svsu.rentit.R;
import edu.svsu.rentit.models.Listing;
import edu.svsu.rentit.workers.RemoveListingBackgroundWorker;
import edu.svsu.rentit.workers.UpdateListingBackgroundWorker;

public class UpdateListingActivity extends AppCompatActivity {

    Listing currentListing;

    EditText txt_title, txt_description, txt_price;
    Button btn_remove, btn_save;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_listing);

        if (getIntent().hasExtra("LISTING"))
            currentListing = (Listing)getIntent().getSerializableExtra("LISTING");

        txt_title = findViewById(R.id.txt_title);
        txt_title.setText(currentListing.getTitle());

        txt_description = findViewById(R.id.txt_description);
        txt_description.setText(currentListing.getDescription());

        txt_price = findViewById(R.id.txt_price);
        txt_price.setText(String.format("%.2f", currentListing.getPrice()));

        btn_remove = findViewById(R.id.button_remove);
        btn_remove.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                RemoveListingBackgroundWorker listingWorker = new RemoveListingBackgroundWorker(UpdateListingActivity.this);
                listingWorker.execute(String.valueOf(currentListing.getId()));
            }
        });

        btn_save = findViewById(R.id.button_save);
        btn_save.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                String title = txt_title.getText().toString();
                String description = txt_description.getText().toString();
                String price = txt_price.getText().toString();

                UpdateListingBackgroundWorker listingWorker = new UpdateListingBackgroundWorker(UpdateListingActivity.this);
                listingWorker.execute(String.valueOf(currentListing.getId()), title, description, price);

        }
        });
    }
}
