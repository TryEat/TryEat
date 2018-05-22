package com.tryeat.rest.model;

import java.sql.Timestamp;

public class Review {
    int review_id;
    int restaurant_id;
    int user_id;
    int img_id;
    String content;
    Timestamp date;
    int rate;
}
