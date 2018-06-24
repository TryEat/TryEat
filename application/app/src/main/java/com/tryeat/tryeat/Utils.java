package com.tryeat.tryeat;

import android.content.res.Resources;
import android.view.View;
import android.widget.RatingBar;
import android.widget.TextView;

class Utils {
    public static float safeDivide(float a, float b) {
        if (a != 0 && b != 0) return a / b;
        return 0;
    }

    public static void safeSetObject(View view, Object object) {
        if (view instanceof RatingBar) {
            float rate = 0;
            if (object == null) {
            } else if (object instanceof Float) {
                rate = (Float) object;
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
