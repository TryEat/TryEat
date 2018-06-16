package com.tryeat.tryeat;

import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.view.MenuItem;

import com.tryeat.team.tryeat_service.R;

class NavigationManager implements BottomNavigationView.OnNavigationItemSelectedListener {

    private static BottomNavigationView mBottomNavigationView;

    public static void setNavigation(BottomNavigationView bottomNavigationView){
        mBottomNavigationView = bottomNavigationView;
    }

    public static void setVisibility(int visibility){
        mBottomNavigationView.setVisibility(visibility);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        Bundle bundle;
        switch (item.getItemId()) {
            case R.id.nav_recommand:
                bundle = new Bundle(2);
                bundle.putInt("user",LoginToken.getId());
                FragmentLoader.startFragment(R.id.frament_place,RestaurantListFragment.class,bundle,false);
                return true;
            case R.id.nav_add_review:
                FragmentLoader.startFragment(R.id.frament_place,ReviewMenuFragment.class,false);
                return true;
            case R.id.nav_user:
                bundle = new Bundle(2);
                bundle.putInt("id",LoginToken.getId());
                FragmentLoader.startFragment(R.id.frament_place,UserFragment.class,bundle,false);
                return true;
        }
        return false;
    }
}
