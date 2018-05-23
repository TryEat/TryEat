package com.tryeat.tryeat;

public class LoginToken {
    private static String token = null;
    private static int id = -1;

    public static Boolean hasLoginToken(){
        return token != null || id !=-1;
    }

    public static int getId() {
        return id;
    }

    public static String getToken() {
        return token;
    }

    public static void setToken(int id, String token) {
        LoginToken.token = token;
        LoginToken.id = id;
    }

    public static void removeToken(){
        LoginToken.token = null;
        LoginToken.id = -1;
    }
}
