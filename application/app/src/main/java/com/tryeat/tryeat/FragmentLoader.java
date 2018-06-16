package com.tryeat.tryeat;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import java.util.HashMap;

class FragmentLoader {
    private static HashMap<String, Fragment> fragmentMap = new HashMap<>();
    private static FragmentManager fragmentManager;

    public static void setActivity(AppCompatActivity activity) {
        fragmentManager = activity.getSupportFragmentManager();
    }

    /*public static Fragment getFragmentInstance(Class fragmentClass) {
        if (!fragmentMap.containsKey(fragmentClass.getName())) {
            try {
                Fragment fragment = (Fragment) fragmentClass.newInstance();
                fragmentMap.put(fragmentClass.getName(), fragment);
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
            Log.d("fragment",fragmentClass.getSimpleName());
        }
        return fragmentMap.get(fragmentClass.getName());
    }*/

    private static Fragment getFragmentInstance(Class fragmentClass) {
        try {
            return (Fragment) fragmentClass.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Fragment startFragment(int layout, Class fragmentClass, Boolean needBackStack) {
        Fragment fragment = getFragmentInstance(fragmentClass);
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
        fragmentTransaction.replace(layout, fragment);
        if (needBackStack) fragmentTransaction.addToBackStack(null).commit();
        else fragmentTransaction.commit();
        return fragment;
    }

    public static Fragment startFragment(int layout, Class fragmentClass, Bundle bundle, Boolean needBackStack) {
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
        Fragment fragment = getFragmentInstance(fragmentClass);
        fragment.setArguments(bundle);
        fragmentTransaction.replace(layout, fragment);
        if (needBackStack) fragmentTransaction.addToBackStack(null).commit();
        else fragmentTransaction.commit();
        return fragment;
    }



    public static void back() {
        fragmentManager.popBackStack();
    }
}
