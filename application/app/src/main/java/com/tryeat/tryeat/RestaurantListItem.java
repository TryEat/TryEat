package com.tryeat.tryeat;

import android.graphics.drawable.Drawable;

import java.io.Serializable;


/**
 * Created by socce on 2018-05-08.
 */

public class RestaurantListItem implements Serializable {
    private Drawable mIcon;
    private int restaurantId;
    private String mName;
    private double mRate;

    public RestaurantListItem(Drawable Icon, int restaurantId, String Name, double Rate) {
        this.mIcon = Icon;
        this.restaurantId = restaurantId;
        this.mName = Name;
        this.mRate = Rate;
    }

    public Drawable getmIcon() {
        return mIcon;
    }

    public String getmName() {
        return mName;
    }

    public double getmRate() {
        return mRate;
    }

    public int getRestaurantId() {
        return restaurantId;
    }

    public void setmIcon(Drawable mIcon) {
        this.mIcon = mIcon;
    }

    public void setmName(String mName) {
        this.mName = mName;
    }

    public void setmRate(double mRate) {
        this.mRate = mRate;
    }

    public void setRestaurantId(int restaurantId) {
        this.restaurantId = restaurantId;
    }
}

