package msahil432.click_away.connections;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Service;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.IBinder;
import android.support.v4.app.ActivityCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

import org.greenrobot.eventbus.EventBus;

import java.io.Serializable;

import msahil432.click_away.extras.MyApplication;

import static msahil432.click_away.extras.MyApplication.Report;

public class MyGPSLocService extends Service {

    private static final String TAG = "ClickAway:LocService";

    private FusedLocationProviderClient mFusedLocationClient;
    private LocationRequest locationRequest;
    private LocationCallback locationCallback;

    @SuppressLint("MissingPermission")
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Report(TAG, "onStartCommand");
        try {
            mFusedLocationClient = LocationServices.getFusedLocationProviderClient(
                    getApplicationContext());

            locationRequest = new LocationRequest();
            locationRequest.setExpirationDuration(1000 * 60 * 5);
            locationRequest.setInterval(1000);
            locationRequest.setFastestInterval(100);
            locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
            locationCallback = new LocationCallback() {
                @Override
                public void onLocationResult(LocationResult locationResult) {
                    if (locationResult == null) {
                        return;
                    }
                    for (Location location : locationResult.getLocations()) {
                        sendLocation(location);
                    }
                }
            };
            if (!arePermissionsGranted()) {
                Report(TAG, "== Error On onConnected() Permission not granted");
                stopForeground(true);
                stopSelf();
                return START_STICKY;
            }
            mFusedLocationClient.getLastLocation()
                    .addOnSuccessListener(new OnSuccessListener<Location>() {
                        @Override
                        public void onSuccess(Location location) {
                            if (location != null) {
                                sendLocation(location);
                            }
                        }
                    });
            mFusedLocationClient.requestLocationUpdates(locationRequest,
                    locationCallback,
                    null);
        }catch (Exception e){
            Report(TAG, "onStartCommand", e);
            stopForeground(true);
            stopSelf();
        }
        return START_STICKY;
    }

    @Override
    public IBinder onBind(Intent arg0) {
        Report(TAG, "onBind");
        return null;
    }

    private void sendLocation(Location location){
        Report(TAG, "new location received");
        LocationData locData = new LocationData(
                location.getLatitude(),
                location.getLongitude(),
                location.getAltitude());
        EventBus.getDefault().post(locData);
        MyApplication.LastLocation lastLocation =
                MyApplication.getLastLocation(getApplicationContext());
        if (lastLocation.getLastLocatino().equals(locData)) {
            Report(TAG, "new location received, but it is same as last one");
            return;
        }
        lastLocation.setLastLocation(locData);
        (new FetchAndSaveWorker(locData)).doWork(getApplicationContext());
    }

    @Override
    public void onDestroy() {
        Report(TAG, "onDestroy");
        if(mFusedLocationClient!=null)
            mFusedLocationClient.removeLocationUpdates(locationCallback);
        super.onDestroy();
    }

    private boolean arePermissionsGranted(){
        int granted = PackageManager.PERMISSION_GRANTED;
        if(ActivityCompat.checkSelfPermission(getApplicationContext(),
                Manifest.permission.ACCESS_FINE_LOCATION) == granted)
            return ActivityCompat.checkSelfPermission(getApplicationContext(),
                    Manifest.permission.ACCESS_COARSE_LOCATION) == granted;
        return false;
    }

    public static class LocationData implements Serializable {
        private double latitude, longitude, altitude;

        public LocationData(double latitude, double longitude, double altitude) {
            this.latitude = latitude;
            this.longitude = longitude;
            this.altitude = altitude;
        }

        public double getLatitude() {
            return latitude;
        }

        public double getLongitude() {
            return longitude;
        }

        public double getAltitude() {
            return altitude;
        }

        public boolean equals(LocationData obj) {
            if(this.longitude==obj.longitude)
                if(this.latitude==obj.latitude)
                    return this.altitude==obj.altitude;
            return false;
        }
    }
}