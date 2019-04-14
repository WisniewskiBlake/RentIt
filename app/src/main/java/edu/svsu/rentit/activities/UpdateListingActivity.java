package edu.svsu.rentit.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.ArrayList;

import edu.svsu.rentit.R;
import edu.svsu.rentit.RentItApplication;
import edu.svsu.rentit.models.Listing;
import edu.svsu.rentit.workers.RemoveListingBackgroundWorker;
import edu.svsu.rentit.workers.UpdateListingBackgroundWorker;

import static java.lang.Double.parseDouble;

public class UpdateListingActivity extends AppCompatActivity {

    int listingId;
    Listing currentListing;

    EditText txt_title, txt_description, txt_price, txt_address, txt_city, txt_zip, txt_contact;
    Spinner spinner_state, spinner_country;
    Button btn_remove, btn_save;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_listing);
        setTitle("Update Listing");

        // Get extras from view listing
        if (getIntent().hasExtra("LISTING_ID")) {
            listingId = getIntent().getIntExtra("LISTING_ID", -1);

            currentListing = ((RentItApplication) getApplication()).getListingById(listingId);
        }

        // Get controls
        txt_title = findViewById(R.id.txt_title);
        txt_description = findViewById(R.id.txt_description);

        txt_address = findViewById(R.id.txt_address);
        txt_city = findViewById(R.id.txt_city);
        spinner_state = findViewById(R.id.spinner_state);
        txt_zip = findViewById(R.id.txt_zip);
        spinner_country = findViewById(R.id.spinner_country);

        txt_price = findViewById(R.id.txt_price);
        txt_contact = findViewById(R.id.txt_contact);

        // Set control values
        txt_title.setText(currentListing.getTitle());
        txt_description.setText(currentListing.getDescription());

        txt_address.setText(currentListing.getAddress());
        if (currentListing.hasFullAddress()) {
            txt_city.setText(currentListing.getCity());

            ArrayAdapter myAdapter = (ArrayAdapter) spinner_state.getAdapter();
            spinner_state.setSelection(myAdapter.getPosition(currentListing.getState()));

            txt_zip.setText(currentListing.getZip());

            ArrayAdapter countryAdapter = (ArrayAdapter) spinner_country.getAdapter();
            spinner_country.setSelection(countryAdapter.getPosition(currentListing.getCountry()));
        }

        txt_price.setText(String.format("%.2f", currentListing.getPrice()));

        txt_contact.setText(currentListing.getContact());


        btn_remove = findViewById(R.id.button_remove);
        btn_remove.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                //((RentItApplication)getApplication()).removeListingById(currentListing.getId());

                RemoveListingBackgroundWorker listingWorker = new RemoveListingBackgroundWorker(UpdateListingActivity.this);
                listingWorker.execute(String.valueOf(currentListing.getId()));
            }
        });

        btn_save = findViewById(R.id.button_save);
        btn_save.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                // Grab updated values
                String title = txt_title.getText().toString();
                String description = txt_description.getText().toString();
                String price = txt_price.getText().toString();

                String address = txt_address.getText().toString();
                String city = txt_city.getText().toString();

                String zip = txt_zip.getText().toString();

                String state = spinner_state.getSelectedItem().toString();

                String country = spinner_country.getSelectedItem().toString();

                address = address + ", " + city + ", " + state + " " + zip + ", " + country;

                String contact = txt_contact.getText().toString();


                // This should happen after successful response - TODO
                currentListing.setTitle(title);
                currentListing.setDescription(description);
                currentListing.setPrice(parseDouble(price));

                currentListing.setAddress(address);
                currentListing.setContact(contact);

                // Update local listing in array
                ((RentItApplication)getApplication()).updateListing(currentListing);

                // Update remote listing in database
                UpdateListingBackgroundWorker listingWorker = new UpdateListingBackgroundWorker(UpdateListingActivity.this);
                listingWorker.execute(String.valueOf(currentListing.getId()), title, description, address, contact, price);

            }
        });
    }


}
