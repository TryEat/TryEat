package com.tryeat.tryeat;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;

public class Utils {
    public static float safeDivide(float a, int b){
        if(a!=0&&b!=0)return a/b;
        return 0;
    }
}
