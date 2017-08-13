package com.example.hcelik.proximityalert;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.location.LocationManager;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by hcelik on 12.08.17
 */

public class MainReciever extends BroadcastReceiver {
    private static final String TAG = "ProximityAlert";

    @Override
    public void onReceive(Context context, Intent intent) {
        String key = LocationManager.KEY_PROXIMITY_ENTERING;
        if (intent.getBooleanExtra(key, false)) {
            Log.d(TAG, "onReceive: Entering area");
            Toast.makeText(context, "Entering", Toast.LENGTH_LONG).show();
        } else {
            Log.d(TAG, "onReceive: Exiting area");
            Toast.makeText(context, "Exiting", Toast.LENGTH_LONG).show();
        }
    }
}
