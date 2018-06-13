package com.tryeat.tryeat;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.widget.ImageView;

import com.tryeat.rest.model.Image;

public class BitmapLoader extends AsyncTask<Image,Void,Bitmap> {
    private ImageView mImageView;

    public BitmapLoader(ImageView imageView) {
        mImageView = imageView;
    }

    @Override
    protected Bitmap doInBackground(Image... images) {
        if(images[0]==null)return null;
        if(images[0].bitmap==null) {
            if(images[0].data==null)return null;
            byte[] v = images[0].data;
            images[0].data = null;
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inSampleSize = 4;
            images[0].bitmap = BitmapFactory.decodeByteArray(v, 0, v.length, options);
        }
        return images[0].bitmap;
    }

    @Override
    protected void onPostExecute(Bitmap bitmap) {
        if(bitmap!=null)mImageView.setImageBitmap(bitmap);
    }
}