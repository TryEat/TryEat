package com.tryeat.tryeat;

import android.app.Activity;
import android.app.ProgressDialog;

public class ProgressDialogManager {
    private static ProgressDialog progressDialog;

    public static void show(Activity activity, String str){
        if(progressDialog!=null&&progressDialog.isShowing())progressDialog.dismiss();
        progressDialog = new ProgressDialog(activity);
        progressDialog.setCancelable(false);
        progressDialog.setIndeterminate(true);
        progressDialog.setTitle("잠시만 기다려주세요...");
        progressDialog.setMessage(str);
        progressDialog.show();
    }

    public static void dismiss(){
        progressDialog.dismiss();
    }
}
