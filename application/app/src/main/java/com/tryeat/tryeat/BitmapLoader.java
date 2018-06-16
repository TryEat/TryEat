package com.tryeat.tryeat;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.load.model.LazyHeaderFactory;
import com.bumptech.glide.load.model.LazyHeaders;
import com.tryeat.rest.model.Image;

class BitmapLoader {
    private Activity mActivity;
    private ImageView mImageView;

    public BitmapLoader(Activity activity, ImageView imageView) {
        mActivity = activity;
        mImageView = imageView;
    }

    public void Load(String uri) {
        if (uri == null||uri=="") return;
        if (mActivity == null) return;
        GlideUrl glideUrl = new GlideUrl(uri, new LazyHeaders.Builder()
                .addHeader("id", String.valueOf(LoginToken.getId()))
                .addHeader("Authorization", LoginToken.getToken())
                .build());
        Glide.with(mActivity)
                .load(glideUrl)
                .into(mImageView);

    }
}