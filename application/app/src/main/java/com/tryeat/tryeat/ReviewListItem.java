package com.tryeat.tryeat;

import android.graphics.drawable.Drawable;

import java.io.Serializable;


/**
 * Created by socce on 2018-05-08.
 */

public class ReviewListItem implements Serializable {
    private Drawable mIcon;
    private String mName;
    private String mHeader;
    private String mDesc;
    private String mRate;

    public ReviewListItem(Drawable mIcon, String mName, String mHeader,String mDesc, String mRate) {
        this.mIcon = mIcon;
        this.mName = mName;
        this.mHeader = mHeader;
        this.mDesc = mDesc;
        this.mRate = mRate;
    }

    public Drawable getmIcon() {
        return mIcon;
    }

    public String getmName() {
        return mName;
    }

    public String getmHeader() {
        return mHeader;
    }

    public String getmDesc() {
        return mDesc;
    }

    public String getmRate() {
        return mRate;
    }

    public void setmDesc(String mDesc) {
        this.mDesc = mDesc;
    }

    public void setmIcon(Drawable mIcon) {
        this.mIcon = mIcon;
    }

    public void setmHeader(String mHeader) {
        this.mHeader = mHeader;
    }

    public void setmName(String mName) {
        this.mName = mName;
    }

    public void setmRate(String mRate) {
        this.mRate = mRate;
    }
}

