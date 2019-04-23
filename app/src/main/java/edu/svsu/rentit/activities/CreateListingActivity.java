package edu.svsu.rentit.activities;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.OpenableColumns;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import edu.svsu.rentit.models.User;
import edu.svsu.rentit.workers.CreateListingBackgroundWorker;
import edu.svsu.rentit.R;

public class CreateListingActivity extends AppCompatActivity implements View.OnClickListener {

    User currentUser;
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
        setContentView(R.layout.activity_create_listing);
        //Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //toolbar.setTitle("RentIT - Create Listing");
        //setSupportActionBar(toolbar);
        name = findViewById(R.id.name);
        imgView = findViewById(R.id.img_view);
        currentUser = (User)getIntent().getSerializableExtra("USER");

        btnSelectImage = findViewById(R.id.btn_selectImage);
        btnSelectImage.setOnClickListener(this);

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

                if(bitmap_string != null && image_name != null) {
                    // Update remote
                    CreateListingBackgroundWorker listingWorker = new CreateListingBackgroundWorker(CreateListingActivity.this);
                    listingWorker.execute(currentUser.getIdString(), title, description, address, contact, price, bitmap_string, image_name);
                }
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