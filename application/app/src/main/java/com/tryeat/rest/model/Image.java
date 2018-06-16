package com.tryeat.rest.model;

import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Image implements Serializable,Parcelable{
    @SerializedName("type")
    public String type;
    @SerializedName("data")
    public byte[] data;
    @SerializedName("bitmap")
    public Bitmap bitmap;

    protected Image(Parcel in) {
        type = in.readString();
        data = in.createByteArray();
        bitmap = in.readParcelable(Bitmap.class.getClassLoader());
    }

    public static final Creator<Image> CREATOR = new Creator<Image>() {
        @Override
        public Image createFromParcel(Parcel in) {
            return new Image(in);
        }

        @Override
        public Image[] newArray(int size) {
            return new Image[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(type);
        dest.writeByteArray(data);
        dest.writeValue(bitmap);
    }
}
