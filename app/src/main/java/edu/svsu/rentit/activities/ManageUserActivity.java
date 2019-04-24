package edu.svsu.rentit.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import edu.svsu.rentit.R;
import edu.svsu.rentit.models.User;
import edu.svsu.rentit.workers.LoginBackgroundWorker;
import edu.svsu.rentit.workers.ProfileBackgroundWorker;

public class ManageUserActivity extends AppCompatActivity {

    User user;

    Toolbar toolbar;
    TextView txt_Name;
    TextView txt_Bio;

    TextView txt_ReviewText;
    TextView txt_ReportsText;

    TextView txt_Review;
    TextView txt_Reports;

    Button disableButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_user);
        //toolbar = findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);

        txt_Name = findViewById(R.id.tv_Profilename);
        txt_Bio = findViewById(R.id.textView_bio);

        txt_Review = findViewById(R.id.textView6);
        txt_Reports = findViewById(R.id.textView7);

        txt_ReviewText = findViewById(R.id.textView9);
        txt_ReportsText = findViewById(R.id.textView10);



        // Grab User object from Intent
        if (getIntent().hasExtra("USER")) {
            user = (User)getIntent().getSerializableExtra("USER");
            setUser(user);
        }

        disableButton = findViewById(R.id.disableButton);
        disableButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                Toast toast = Toast.makeText(ManageUserActivity.this,
                        user.getUsername() + " has been disabled",
                        Toast.LENGTH_SHORT);

                toast.show();

                finish();
            }
        });

    }

    public void setUser(User outputUser)
    {
        txt_Name.setText(user.getUsername());
        txt_Bio.setText(user.getFirstname() + " " + user.getLastname());

        txt_ReviewText.setText("Review:");
        txt_Review.setText("4.3");

        txt_ReportsText.setText("Reports:");
        txt_Reports.setText("0");
        //setOutput(outputUser.getFirstname(), outputUser.getBio());
    }

    public void setOutput(String name, String bio)
    {
        toolbar.setTitle(name);
        txt_Name.setText(name);
        txt_Bio.setText(bio);
    }

}