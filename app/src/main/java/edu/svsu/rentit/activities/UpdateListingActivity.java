package edu.svsu.rentit.activities;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.provider.OpenableColumns;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import edu.svsu.rentit.R;
import edu.svsu.rentit.RentItApplication;
import edu.svsu.rentit.models.Listing;
import edu.svsu.rentit.workers.RemoveListingBackgroundWorker;
import edu.svsu.rentit.workers.UpdateListingBackgroundWorker;

import static java.lang.Double.parseDouble;

public class UpdateListingActivity extends AppCompatActivity implements View.OnClickListener{

    int listingId;
    Listing currentListing;

    EditText txt_title, txt_description, txt_price, txt_address, txt_city, txt_zip, txt_contact;
    Spinner spinner_state, spinner_country;
    Button btn_remove, btn_save;

    private Bitmap bitmap;
    private String bitmap_string = null;
    private final int IMG_REQUEST = 1;
    private Button btnSelectImage;
    private EditText name;
    private String image_name = null;
    private ImageView imgView;

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

        name = findViewById(R.id.name);
        imgView = findViewById(R.id.img_view);

        btnSelectImage = findViewById(R.id.btn_selectImage);
        btnSelectImage.setOnClickListener(this);
        // Get controls
        txt_title = findViewById(R.id.txt_Title);
        txt_description = findViewById(R.id.txt_Description);

        txt_address = findViewById(R.id.txt_Address);
        txt_city = findViewById(R.id.txt_City);
        spinner_state = findViewById(R.id.spinner_State);
        txt_zip = findViewById(R.id.txt_Zip);
        spinner_country = findViewById(R.id.spinner_Country);

        txt_price = findViewById(R.id.txt_Price);
        txt_contact = findViewById(R.id.txt_Contact);

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


        btn_remove = findViewById(R.id.btn_Remove);
        btn_remove.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                //((RentItApplication)getApplication()).removeListingById(currentListing.getId());

                RemoveListingBackgroundWorker listingWorker = new RemoveListingBackgroundWorker(UpdateListingActivity.this);
                listingWorker.execute(String.valueOf(currentListing.getId()));
            }
        });

        btn_save = findViewById(R.id.btn_Save);
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

                // Update remote
                UpdateListingBackgroundWorker listingWorker = new UpdateListingBackgroundWorker(UpdateListingActivity.this);
                listingWorker.execute(String.valueOf(currentListing.getId()), title, description, address, contact, price, bitmap_string, image_name);


            }
        });
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.btn_selectImage:
                selectImage();
                break;
        }
    }

    private void selectImage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, IMG_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode,resultCode,data);
        if(requestCode == IMG_REQUEST && resultCode == RESULT_OK && data != null) {
            Uri path = data.getData();
            image_name = getFileName(path);
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(),path);
                imgView.setImageBitmap(bitmap);
                name.setText(image_name);
                bitmap_string = imageToString(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private String getFileName(Uri uri) {
        String result = null;
        if (uri.getScheme().equals("content")) {
            Cursor cursor = getContentResolver().query(uri, null, null, null, null);
            try {
                if (cursor != null && cursor.moveToFirst()) {
                    result = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
                }
            } finally {
                cursor.close();
            }
        }
        if (result == null) {
            result = uri.getPath();
            int cut = result.lastIndexOf('/');
            if (cut != -1) {
                result = result.substring(cut + 1);
            }
        }
        return result;
    }

    private String imageToString(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,100,byteArrayOutputStream);
        byte[] imgBytes = byteArrayOutputStream.toByteArray();
        return Base64.encodeToString(imgBytes,Base64.DEFAULT);
    }


}
