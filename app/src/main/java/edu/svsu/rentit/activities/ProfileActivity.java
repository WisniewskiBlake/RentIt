package edu.svsu.rentit.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import edu.svsu.rentit.models.User;
import edu.svsu.rentit.workers.ProfileBackgroundWorker;
import edu.svsu.rentit.R;

public class ProfileActivity extends AppCompatActivity {

    User currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("RentIT - Profile");
        setSupportActionBar(toolbar);


        currentUser = (User)getIntent().getSerializableExtra("USER");

        TextView txt_Name = findViewById(R.id.tv_Profilename);
        txt_Name.setText(currentUser.getFirstname() + " " + currentUser.getLastname());
        EditText txt_Bio = findViewById(R.id.eT_bio);
        txt_Bio.setText(currentUser.getBio());


        Button btnCreate = findViewById(R.id.btn_CreateListing);
        btnCreate.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(ProfileActivity.this, CreateListingActivity.class);
                intent.putExtra("USER", currentUser);
                startActivity(intent);

            }
        });


        Button btnView = findViewById(R.id.btn_ViewListings);
        btnView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                startActivity(new Intent(ProfileActivity.this, ViewListingActivity.class));

            }
        });
    }

}