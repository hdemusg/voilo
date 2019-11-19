package com.voilo;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.preference.PreferenceManager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private static final Reminder[] rem = new Reminder[1];
    protected static TextView r;
    protected static Button clear;
    protected static TextView g;
    protected static NotificationCompat.Builder builder;
    protected static NotificationManagerCompat notificationManager;
    //protected static Button create;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //Context context = getApplicationContext();
        //SharedPreferences sharedPreferences = context.getSharedPreferences(R.string.action_settings, Context.MODE_PRIVATE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        clear = findViewById(R.id.buttonClear);
        g = findViewById(R.id.reminder_gps);
        //create = findViewById(R.id.buttonCreate);
        r = findViewById(R.id.reminder_location);
        notificationManager = NotificationManagerCompat.from(this);
        //SharedPreferences sharedPref = getPreferences(Context.MODE_PRIVATE);
        String vehicle = getResources().getString(R.string.current_vehicle);
        //String location = getResources().getString(R.string.current_location);
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        String location = sp.getString("current_location", null);
        String lat = sp.getString("current_lat", "0.0");
        String lon = sp.getString("current_lon", "0.0");
        builder = new NotificationCompat.Builder(this, "Text Reminder")
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setContentTitle("Reminder")
                .setContentText(location)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);
        if (location != null) {
            rem[0] = new Reminder(location, vehicle, Float.parseFloat(lat), Float.parseFloat(lon));
        }
        updateRem();
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*
                CoordinatorLayout mainActivity = (CoordinatorLayout) findViewById(R.id.mainActivity);
                LayoutInflater layoutInflater = (LayoutInflater) MainActivity.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View customView = layoutInflater.inflate(R.layout.reminder_form_popup, null);
                popupWindow = new PopupWindow(customView, CoordinatorLayout.LayoutParams.WRAP_CONTENT, CoordinatorLayout.LayoutParams.WRAP_CONTENT);

                popupWindow.showAtLocation(mainActivity, Gravity.CENTER, 0, 0);
                 try {
                    Thread.sleep(5000);
                 }
                 catch(InterruptedException i) {
                 }
                 popupWindow.dismiss();
                 Snackbar.make(view, "Create a reminder", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                 */
                Intent intent = new Intent(MainActivity.this, CreateReminder.class);
                startActivity(intent);
            }
        });
        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                SharedPreferences.Editor editor = sp.edit();
                editor.putString("current_location", null);
                editor.putString("current_lat", "0.0");
                editor.putString("current_lon", "0.0");
                editor.commit();
                clear();
            }
        });
        /*
        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, CreateReminder.class);
                startActivity(intent);
            }
        });
         */
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
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public static void setRem(Reminder r) {
        rem[0] = r;
        updateRem();
    }

    public static void updateRem() {
        if (rem[0] == null) {
            r.setText("No reminders exist");
            clear.setVisibility(View.INVISIBLE);
            g.setVisibility(View.INVISIBLE);
            //create.setVisibility(View.VISIBLE);
        } else {
            r.setText(rem[0].getLocation());
            clear.setVisibility(View.VISIBLE);
            if (!(rem[0].getLatitude() == 0 && rem[0].getLongitude() == 0)) {
                g.setVisibility(View.VISIBLE);
                g.setText("(" + Float.toString(rem[0].getLatitude()) + ", " + Float.toString(rem[0].getLongitude()) + ")");
            } else {
                g.setVisibility(View.INVISIBLE);
            }
            //notificationManager.notify(0, builder.build());
            //create.setVisibility(View.INVISIBLE);
        }
    }

    public TextView getR() {
        return r;
    }

    public static void clear() {
        rem[0] = null;
        updateRem();
    }

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)  {
            CharSequence name = "Text Reminder";
            String description = "A text reminder of your parking location";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel("Text Reminder", name, importance);
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }
}
