package com.voilo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class CreateReminder extends AppCompatActivity implements LocationListener {

    EditText loc;
    EditText veh;
    float lat = (float) 0.0;
    float lon = (float) 0.0;
    Button create;

    @Override
    public void onLocationChanged(Location location) {
        lat = (float) location.getLatitude();
        lon = (float) location.getLongitude();
    }

    @Override
    public void onProviderDisabled(String provider) {
        Log.d("Latitude", "disable");
    }

    @Override
    public void onProviderEnabled(String provider) {
        Log.d("Latitude", "enable");
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
        Log.d("Latitude", "status");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_reminder);
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    Activity#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for Activity#requestPermissions for more details.
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 0);
            return;
        } else {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);
        }
        create = findViewById(R.id.createButton);
        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createReminder(view);
            }
        });
    }

    public void createReminder(View view) {
        loc = findViewById(R.id.location);
        //veh = findViewById(R.id.vehicle);
        String l = loc.getText().toString();
        //String v = veh.getText().toString();
        Reminder r = new Reminder(l, "default", lat, lon);
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        //SharedPreferences sharedPref = getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        //String locate = getString(R.string.current_location);
        editor.putString("current_location", l);
        editor.putString("current_lat", Float.toString(lat));
        editor.putString("current_lon", Float.toString(lon));
        //editor.putString(getString(R.string.current_vehicle), v);
        editor.commit();
        MainActivity.setRem(r);
        //Intent intent = new Intent(CreateReminder.this, MainActivity.class);
        //startActivity(intent);
        finish();
    }

}
