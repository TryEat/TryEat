package com.example.socce.tryeat_app;

import android.graphics.drawable.Drawable;


/**
 * Created by socce on 2018-05-08.
 */

public class RestaurantListItem {
    private Drawable mIcon;
    private String mName;
    private String mRate;

    public RestaurantListItem(Drawable Icon, String Name, String Rate) {
        this.mIcon = Icon;
        this.mName = Name;
        this.mRate = Rate;
    }

    public Drawable getmIcon() {
        return mIcon;
    }

    public String getmName() {
        return mName;
    }

    public String getmRate() {
        return mRate;
    }

    public void setmIcon(Drawable mIcon) {
        this.mIcon = mIcon;
    }

    public void setmName(String mName) {
        this.mName = mName;
    }

    public void setmRate(String mRate) {
        this.mRate = mRate;
    }
}

