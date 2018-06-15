package com.tryeat.rest.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GoogleDetail {
    @SerializedName("result")
    private Result result;

    class Result {
        Geometry geometry;
        String name;
        String formatted_address;
        String formatted_phone_number;
        List<Photo> photos;
    }

    class Geometry {
        Location location;
    }

    class Location {
        double lat;
        @SerializedName("lng")
        double lon;
    }

    class Photo {
        String photo_reference;
    }

    public String getName() {
        return result.name;
    }

    public String getAddress() {
        return result.formatted_address;
    }

    public String getPhoneNumber() {
        return result.formatted_phone_number;
    }

    public Boolean hasPhoto(){
        return result.photos!=null;
    }

    public String getPhotoUrl() {
        if (result.photos==null) return null;
        String url = "https://maps.googleapis.com/maps/api/place/photo?maxwidth=1920&photoreference=" + result.photos.get(0).photo_reference + "&key=AIzaSyAfDHQhe4fmz5FAitOnTjrOFPesb88GXFE";
        return url;
    }

    public Double getLat(){
        return result.geometry.location.lat;
    }

    public Double getLon(){
        return result.geometry.location.lon;
    }
}
