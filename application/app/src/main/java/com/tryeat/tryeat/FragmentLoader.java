package com.tryeat.tryeat;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.text.Layout;

import java.util.HashMap;

public class FragmentLoader {
    private static HashMap<String,Fragment> fragmentMap = new HashMap<>();
    private static FragmentManager fragmentManager;

    public static void setActivity(AppCompatActivity activity){
        fragmentManager = activity.getSupportFragmentManager();
    }

    public static Fragment getFragmentInstance(Class fragmentClass) {
        if (!fragmentMap.containsKey(fragmentClass.getName())) {
            try {
                Fragment fragment = (Fragment) fragmentClass.newInstance();
                fragmentMap.put(fragmentClass.getName(), fragment);
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return fragmentMap.get(fragmentClass.getName());
    }

    public static void startFragment(int layout, Class fragmentClass){
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_out_right );
        fragmentTransaction.replace(layout, getFragmentInstance(fragmentClass));
        fragmentTransaction.addToBackStack(null).commit();
    }

    public static void startFragment(int layout, Class fragmentClass, Bundle bundle){
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_out_right );
        Fragment fragment = getFragmentInstance(fragmentClass);
        fragment.setArguments(bundle);
        fragmentTransaction.replace(layout,fragment);
        fragmentTransaction.addToBackStack(null).commit();
    }
}
