package com.tryeat.rest.model;

public class StatusCode{
    public static final int SIGNIN_SUCCESS = 200;
    public static final int SIGNIN_FAIL = 401;

    public static final int SIGNUP_SUCCESS = 201;
    public static final int SIGNUP_FAIL = 401;
    public static final int SIGNUP_ID_DUPLICATION = 409;

    public static final int PROFILE_REVISE_SEUCCESS = 201;
    public static final int PROFILE_REVISE_FAIL = 400;

    public static final int DELETE_USER_SUCCESS = 201;
    public static final int DELETE_USER_FAIL = 400;

    public static final int WRITE_REVIEW_SUCCESS = 201;
    public static final int WRITE_REVIEW_FAIL = 400;

    public static final int UPDATE_REVIEW_SUCCESS = 201;
    public static final int UPDATE_REVIEW_FAIL =400;

    public static final int DELETE_REVIEW_SUCCESS = 201;
    public static final int DELETE_REVIEW_FAIL = 400;

    public static final int RESTAURANT_IS_EXIST = 200;
    public static final int RESTAURANT_IS_NOT_EXIST = 400;

    public static final int ADD_RESTAURANT_SUCCESS = 201;
    public static final int ADD_RESTAURANT_FAIL = 400;

    public static final int UPDATE_RESTAURANT_SUCCESS = 201;
    public static final int UPDATE_RESTAURANT_FAIL =400;

    public static final int DELETE_RESTAURANT_SUCCESS = 201;
    public static final int DELETE_RESTAURANT_FAIL = 400;

    public static final int FOLLOW_SUCCESS = 201;
    public static final int FOLLOW_FAIL =400;

    public static final int DELETE_FOLLOW_SUCCESS = 201;
    public static final int DELETE_FOLLOW_FAIL = 400;
}
