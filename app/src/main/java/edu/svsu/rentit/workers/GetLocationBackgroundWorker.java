package edu.svsu.rentit.workers;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;

public class GetLocationBackgroundWorker extends AsyncTask<String, String, String> {

    private double lati = 0.0;
    private double longi = 0.0;

    private static final long MIN_DISTANCE_CHANGE = 10;
    private static final long MIN_TIME_CHANGE = 1;

    protected LocationManager locationManager;
    private RILocationListener locationListener;

    private final Context lContext;

    public GetLocationBackgroundWorker(Context lContext) {
        this.lContext = lContext;
    }

    @Override
    protected void onPreExecute() {
        locationListener = new RILocationListener();
        locationManager = (LocationManager) lContext.getSystemService(Context.LOCATION_SERVICE);

        if (ActivityCompat.checkSelfPermission(lContext, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(lContext,
                Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }

        locationManager.requestLocationUpdates(
                LocationManager.NETWORK_PROVIDER, MIN_TIME_CHANGE, MIN_DISTANCE_CHANGE,
                locationListener);

    }

    @Override
    protected String doInBackground(String... strings) {
        while(this.lati == 0.0) {

        }
        return null;
    }

    public double getLati() {
        return lati;
    }

    public double getLongi() {
        return longi;
    }

    public class RILocationListener implements LocationListener {

        @Override
        public void onLocationChanged(Location location) {
            try {
                lati = location.getLatitude();
                longi = location.getLongitude();
            }
            catch(Exception e) {

            }
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {

        }

        @Override
        public void onProviderEnabled(String provider) {

        }

        @Override
        public void onProviderDisabled(String provider) {

        }
    }
}


