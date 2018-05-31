package com.tryeat.tryeat;

import android.app.Activity;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;

public class AlertDialogBuilder {
    public static void createAlert(Activity activity, String message) {
        AlertDialog.Builder alert = new AlertDialog.Builder(activity);
        alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        alert.setMessage(message);
        alert.show();
    }


}
