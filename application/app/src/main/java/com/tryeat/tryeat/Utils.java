package com.tryeat.tryeat;

public class Utils {
    public static float safeDivide(float a, int b){
        if(a!=0&&b!=0)return a/b;
        return 0;
    }
}
