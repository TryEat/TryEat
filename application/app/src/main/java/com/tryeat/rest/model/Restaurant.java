package com.tryeat.rest.model;

import java.sql.Time;

public class Restaurant {
    public int restaurant_id;
    public int img_id;
    public String restaurant_name;
    public String locate_latitude;
    public String locate_longitude;
    public String restaurant_desc;
    public Time open_time;
    public Time close_time;
    public int review_count;
    public int total_rate;
}
