package com.voilo;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.fragment.app.DialogFragment;

public class PrivacyStatementFragment extends DialogFragment {
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage("voilo uses your location to help you find the exact location of your " +
                "vehicle. Your location is only stored locally in your phone's storage, and is not " +
                "shared with anyone or stored by us on another database. If you still want to opt " +
                "out of the location/direction features, you can disable location access for our app or turn off location.")
                .setTitle("Privacy Statement")
                .setNeutralButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User cancelled the dialog
                    }
                });
        // Create the AlertDialog object and return it
        return builder.create();
    }
}
