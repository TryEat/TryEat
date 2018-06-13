package com.tryeat.rest.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GoogleAutoComplete{
    @SerializedName("predictions")
    List<Prediction> predictions;

    public class Prediction{
        String id;
        String place_id;
        StructuredFomatting structured_formatting;

        public String getId(){
            return id;
        }

        public String getPlaceId(){
            return place_id;
        }

        public String getName(){
            return structured_formatting.main_text;
        }

        public String getAddress(){
            return structured_formatting.secondary_text;
        }
    }

    class StructuredFomatting{
        String main_text;
        String secondary_text;
    }

    public int size(){
        return predictions.size();
    }

    public Prediction get(int index){
        return predictions.get(index);
    }
}
