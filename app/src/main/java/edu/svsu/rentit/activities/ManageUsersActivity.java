package edu.svsu.rentit.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import edu.svsu.rentit.R;
import edu.svsu.rentit.models.GetUsersBackgroundWorker;

public class ManageUsersActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_users);
        setTitle("Manage Users");


        // Get all users from database
        GetUsersBackgroundWorker listingWorker = new GetUsersBackgroundWorker(ManageUsersActivity.this);
        listingWorker.execute();
    }
}
