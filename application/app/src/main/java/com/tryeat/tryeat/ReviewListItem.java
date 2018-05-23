package com.tryeat.tryeat;

import android.graphics.drawable.Drawable;


/**
 * Created by socce on 2018-05-08.
 */

public class ReviewListItem {
    public Drawable mIcon;
    public String mName;
    public String mRate;

    public ReviewListItem(Drawable mIcon, String mName, String mRate) {
        this.mIcon = mIcon;
        this.mName = mName;
        this.mRate = mRate;
    }
}

