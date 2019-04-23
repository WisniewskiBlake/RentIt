package edu.svsu.rentit.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import edu.svsu.rentit.RentItApplication;
import edu.svsu.rentit.models.Listing;
import edu.svsu.rentit.models.User;
import edu.svsu.rentit.workers.CreateListingBackgroundWorker;
import edu.svsu.rentit.R;

public class CreateListingActivity extends AppCompatActivity {

    User currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_listing);
        //Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //toolbar.setTitle("RentIT - Create Listing");
        //setSupportActionBar(toolbar);

        currentUser = (User)getIntent().getSerializableExtra("USER");


        Button btnCreate = findViewById(R.id.btn_Create);
        btnCreate.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                EditText etTitle = findViewById(R.id.txt_Title);
                String title = etTitle.getEditableText().toString();
                EditText etDescription = findViewById(R.id.txt_Description);
                String description = etDescription.getEditableText().toString();
                // Address info
                EditText etAddress = findViewById(R.id.txt_Address);
                String address = etAddress.getEditableText().toString();
                EditText etCity = findViewById(R.id.txt_City);
                String city = etCity.getEditableText().toString();
                Spinner etState = findViewById(R.id.spinner_State);
                String state = etState.getSelectedItem().toString();
                EditText etZip = findViewById(R.id.txt_Zip);
                String zip = etZip.getEditableText().toString();
                Spinner etCountry = findViewById(R.id.spinner_Country);
                String country = etCountry.getSelectedItem().toString();

                EditText etContact = findViewById(R.id.txt_Contact);
                String contact = etContact.getEditableText().toString();
                EditText etPrice = findViewById(R.id.txt_Price);
                String price = etPrice.getEditableText().toString();

                address = address + ", " + city + ", " + state + " " + zip + ", " + country;


                // Update remote
                CreateListingBackgroundWorker listingWorker = new CreateListingBackgroundWorker(CreateListingActivity.this);
                listingWorker.execute(currentUser.getIdString(), title, description, address, contact, price);

            }
        });

        Button btnSelectImage = findViewById(R.id.btn_selectImage);
        btnSelectImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Context context = v.getContext();
                //Intent intent = new Intent(context, ImageUploadActivity.class);
                //context.startActivity(intent);
            }
        });

        Button btnCancel = findViewById(R.id.btn_Cancel);
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

}