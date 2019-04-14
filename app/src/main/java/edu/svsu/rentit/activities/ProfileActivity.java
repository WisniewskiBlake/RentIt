package edu.svsu.rentit.activities;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import edu.svsu.rentit.models.User;
import edu.svsu.rentit.workers.GetListingBackgroundWorker;
import edu.svsu.rentit.workers.ProfileBackgroundWorker;
import edu.svsu.rentit.R;

public class ProfileActivity extends AppCompatActivity {

    User currentUser;

    Toolbar toolbar;
    TextView txt_Name;
    TextView txt_Bio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        txt_Name = findViewById(R.id.tv_Profilename);
        txt_Bio = findViewById(R.id.textView_bio);

        // Grab User object from Intent
        if (getIntent().hasExtra("CURRENT_USER")) {
            currentUser = (User)getIntent().getSerializableExtra("CURRENT_USER");
            setUser(currentUser);
        }

        if (getIntent().hasExtra("USER_ID")) {

            ProfileBackgroundWorker profileWorker = new ProfileBackgroundWorker(ProfileActivity.this);
            profileWorker.execute(getIntent().getStringExtra("USER_ID"));
        }


    }

    public void setUser(User outputUser)
    {
        setOutput(outputUser.getFirstname(), outputUser.getBio());
    }

    public void setOutput(String name, String bio)
    {
        toolbar.setTitle(name + "'s Profile");
        txt_Name.setText(name);
        txt_Bio.setText(bio);
    }

}