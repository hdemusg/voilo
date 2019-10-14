package com.voilo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class CreateReminder extends AppCompatActivity {

    EditText loc;
    EditText veh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_reminder);
    }

    public void createReminder(View view) {
        loc = findViewById(R.id.location);
        veh = findViewById(R.id.vehicle);
        String l = loc.getText().toString();
        String v = veh.getText().toString();
        Reminder r = new Reminder(l, v);
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        //SharedPreferences sharedPref = getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        //String locate = getString(R.string.current_location);
        editor.putString("current_location", l);
        //editor.putString(getString(R.string.current_vehicle), v);
        editor.commit();
        MainActivity.setRem(r);
        //Intent intent = new Intent(CreateReminder.this, MainActivity.class);
        //startActivity(intent);
        finish();
    }

}
