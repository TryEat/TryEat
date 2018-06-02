package com.tryeat.tryeat;

import android.support.v4.app.Fragment;

import java.util.HashMap;

public class FragmentLoader {
    private static HashMap<String,Fragment> fragmentMap = new HashMap<>();
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
}
