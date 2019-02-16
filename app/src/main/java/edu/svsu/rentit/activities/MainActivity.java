package edu.svsu.rentit.activities;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import edu.svsu.rentit.R;
import edu.svsu.rentit.models.User;
import edu.svsu.rentit.workers.GetListingBackgroundWorker;
import edu.svsu.rentit.workers.GetLoginTokenBackgroundWorker;

public class MainActivity extends AppCompatActivity {

    private boolean validating;
    private boolean validated;
    User currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        // Check if login token exists
        SharedPreferences sp = getSharedPreferences("Login", MODE_PRIVATE);

        if (sp.contains("Token") && sp.contains("UserId")) {
            String userId = sp.getString("UserId", "");
            String token = sp.getString("Token", "");

            // Update menu
            validating = true;
            invalidateOptionsMenu();

            // Validate login token
            GetLoginTokenBackgroundWorker tokenWorker = new GetLoginTokenBackgroundWorker(MainActivity.this);
            tokenWorker.execute(userId, token);
        }

        // Populate listings
        GetListingBackgroundWorker listingWorker = new GetListingBackgroundWorker(MainActivity.this);
        listingWorker.execute();


        // Register successful login receiver
        registerReceiver(new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                currentUser = (User)intent.getSerializableExtra("USER");

                validating = false;
                validated = true;
                invalidateOptionsMenu();
            }
        }, new IntentFilter("loginSuccess"));

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        menu.findItem(R.id.validate).setVisible(false);
        menu.findItem(R.id.action_logout).setVisible(false);
        menu.findItem(R.id.action_view_profile).setVisible(false);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        if (validating) {
            menu.findItem(R.id.validate).setVisible(true);
            menu.findItem(R.id.action_login).setVisible(false);
            menu.findItem(R.id.action_register).setVisible(true);
            menu.findItem(R.id.action_logout).setVisible(false);
            menu.findItem(R.id.action_view_profile).setVisible(false);
        } else if (validated) {
            menu.findItem(R.id.validate).setVisible(false);
            menu.findItem(R.id.action_login).setVisible(false);
            menu.findItem(R.id.action_register).setVisible(false);
            menu.findItem(R.id.action_logout).setVisible(true);
            menu.findItem(R.id.action_view_profile).setVisible(true);
        }

        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_register) {
            startActivity(new Intent(this, RegisterActivity.class));
        }else if(id == R.id.action_login){
            startActivity(new Intent(this, LoginActivity.class));
        } else if (id == R.id.action_logout) {

            SharedPreferences sp = getSharedPreferences("Login", MODE_PRIVATE);
            SharedPreferences.Editor editor = sp.edit();
            editor.remove("Token");
            editor.remove("UserId");
            editor.commit();

            validated = false;
            invalidateOptionsMenu();
        } else if (id == R.id.action_view_profile) {
            Intent intent = new Intent(this, ProfileActivity.class);
            intent.putExtra("USER", currentUser);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }

    public void setUserValidated() {
        validating = false;
        validated = true;
    }
    public void setUserInvalid() {
        validating = false;
        validated = false;
    }

}

