package android.lovemesomedatacom;

import android.app.Service;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.provider.Settings;
import android.support.v7.app.AlertDialog;
import android.util.Log;

/**
 * Created by Rebeu on 2017-11-27.
 */

public class GPSManager extends Service implements LocationListener {

    private final Context mContext;
    private static final String TAG = "GPSManager";

    Location location;
    double latitude, longitude;
    boolean GPSEnabled = false, networkEnabled = false, locationEnabled = false;
    protected LocationManager locationManager;

    private static final long DISTANCE = 100;
    private static final long TIME = 1000 * 60;

    public GPSManager(Context mContext) {
        this.mContext = mContext;
        getLocation();
    }

    public Location getLocation() {
        try {
            locationManager = (LocationManager) mContext.getSystemService(LOCATION_SERVICE);
            GPSEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
            networkEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

            if (!GPSEnabled && !networkEnabled) {
                //Can not perform geolocalisation
                Log.d(TAG, "Network or GPS not enabled");
            } else {
                locationEnabled = true;

                if (networkEnabled) {
                    Log.d(TAG, "Network enabled");
                    locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, TIME, DISTANCE, this);
                    if(locationManager != null){
                        location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                        if(location != null){
                            Log.d(TAG, "Location not null");
                            latitude = location.getLatitude();
                            longitude = location.getLongitude();
                        }
                    }
                }

                if(GPSEnabled){
                    Log.d(TAG, "GPS Enabled");
                    if(location == null){
                        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, TIME, DISTANCE, this);
                        if(locationManager != null){
                            location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                            if(location != null){
                                latitude = location.getLatitude();
                                longitude = location.getLongitude();
                            }
                        }
                    }

                }
            }

        }

        catch(Exception e){
            e.printStackTrace();
        }
        return location;
    }

    public double getLatitude(){
        if(location != null){
            latitude = location.getLatitude();
        }
        return latitude;
    }

    public double getLongitude(){
        if(location != null){
            latitude = location.getLongitude();
        }
        return longitude;
    }

    public boolean locationEnabled(){
        return this.locationEnabled;
    }

    public void requestLocation(){
        AlertDialog.Builder alert = new AlertDialog.Builder(mContext);
        alert.setTitle("Enable GPS");
        alert.setMessage("Please enable GPS in your settings");

        alert.setPositiveButton("Settings", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                mContext.startActivity(intent);
            }
        });

        alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });

        alert.show();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onLocationChanged(Location location) {

    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {

    }

    @Override
    public void onProviderDisabled(String s) {

    }
}
