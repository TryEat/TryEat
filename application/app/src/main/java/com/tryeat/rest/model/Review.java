package com.tryeat.rest.model;

import java.sql.Timestamp;

public class Review {
    public int review_id;
    public int restaurant_id;
    public int user_id;
    public int img_id;
    public String content;
    public Timestamp date;
    public int rate;
}
