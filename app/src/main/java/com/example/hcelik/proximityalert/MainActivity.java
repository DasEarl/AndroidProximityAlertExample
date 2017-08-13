package com.example.hcelik.proximityalert;

import android.Manifest;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

public class MainActivity extends AppCompatActivity {

    private static final int REQUEST_LOCATION_PERMISSION = 99;
    private static final String TAG = "ProximityAlert";
    private static final String PROXIMITY_ALERT = "com.example.hcelik.proximityalert";
    private LocationManager locationManager;
    private Intent proximityAlertIntent; // TODO: Mit oder ohne Konstante?
    private PendingIntent pendingIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        registerReceiver(new MainReciever(), new IntentFilter(PROXIMITY_ALERT));
        requestPermission();
    }

    private void requestPermission() {
        Log.d(TAG, "requestPermission: Request permissions");
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION_PERMISSION);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case REQUEST_LOCATION_PERMISSION: {
                if (grantResults.length > 0) {
                    if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                        Log.d(TAG, "onRequestPermissionsResult: PERMISSION_GRANTED");
                        initLocationManager();
                    } else {
                        Log.d(TAG, "onRequestPermissionsResult: PERMISSION_DENIED");
                    }
                } else {
                    requestPermission();
                }
            }
        }
    }

    public void initLocationManager() {
        if (locationManager == null)
            locationManager = (LocationManager) getApplicationContext().getSystemService(Context.LOCATION_SERVICE);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            Log.d(TAG, "initLocationManager: Register proximity alerts");
            proximityAlertIntent = new Intent(PROXIMITY_ALERT);
            pendingIntent = PendingIntent.getBroadcast(this, 98, proximityAlertIntent, 0);
            locationManager.addProximityAlert(53.14735431, 8.1862882, 10, -1, pendingIntent); // Lng Lat must be long format
        } else {
            Log.e(TAG, "initLocationManager: Permission not granted");
        }
    }

}
