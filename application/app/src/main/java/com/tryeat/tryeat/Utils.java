package com.tryeat.tryeat;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import org.w3c.dom.Text;

public class Utils {
    public static float safeDivide(float a, float b) {
        if (a != 0 && b != 0) return a / b;
        return 0;
    }

    public static void safeSetObject(View view, Object object) {
        if (view instanceof RatingBar) {
            float rate = 0;
            if (object == null) {
            } else if (object instanceof Float) {
                rate = ((Float) object).floatValue();
            }
            ((RatingBar) view).setRating(rate);
        } else if (view instanceof TextView) {
            String str = "";
            if (object == null) {
            } else if (object instanceof Integer) {
                str = object.toString();
            } else if (object instanceof Float) {
                str = object.toString();
            } else if (object instanceof String) {
                str = (String) object;
            }
            ((TextView) view).setText(str);
        }
    }

    public static int dpToPx(int dp) {
        return (int) (dp * Resources.getSystem().getDisplayMetrics().density);
    }

    public static int pxToDp(int px) {
        return (int) (px / Resources.getSystem().getDisplayMetrics().density);
    }
}
