package com.tryeat.tryeat;

import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.view.MenuItem;

import com.tryeat.team.tryeat_service.R;

public class NavigationManager implements BottomNavigationView.OnNavigationItemSelectedListener {

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
                bundle.putSerializable("user",LoginToken.getId());
                FragmentLoader.startFragment(R.id.frament_place,RestaurantListFragment.class,bundle,true);
                return true;
            case R.id.nav_add_review:
                FragmentLoader.startFragment(R.id.frament_place,ReviewMenuFragment.class,true);
                return true;
            case R.id.nav_user:
                bundle = new Bundle(2);
                bundle.putSerializable("id",LoginToken.getId());
                FragmentLoader.startFragment(R.id.frament_place,UserFragment.class,bundle,true);
                return true;
        }
        return false;
    }
}
