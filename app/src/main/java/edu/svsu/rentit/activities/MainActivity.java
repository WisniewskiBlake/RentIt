package edu.svsu.rentit.activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import edu.svsu.rentit.R;
import edu.svsu.rentit.workers.GetListingBackgroundWorker;
import edu.svsu.rentit.workers.GetLocationBackgroundWorker;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //check for a location resource - WiFi, data, etc.
        // if not ask to enable - or use static location
        // stop location services at some point
        GetLocationBackgroundWorker locationWorker = new GetLocationBackgroundWorker(this);
        locationWorker.execute();

        GetListingBackgroundWorker listingWorker = new GetListingBackgroundWorker(MainActivity.this);
        listingWorker.execute(locationWorker);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
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
        }

        return super.onOptionsItemSelected(item);
    }

}

