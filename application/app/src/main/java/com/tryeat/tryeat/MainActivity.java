package com.tryeat.tryeat;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;

import com.tryeat.team.tryeat_service.R;

public class MainActivity extends AppCompatActivity{

    private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomNavigationView = findViewById(R.id.nav_view);

        bottomNavigationView.setOnNavigationItemSelectedListener(new NavigationManager());

        NavigationManager.setNavigation(bottomNavigationView);

        MyLocation.setActivity(this);
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        FragmentLoader.setActivity(this);
        FragmentLoader.startFragment(R.id.frament_place,RestaurantListFragment.class,true);
    }

    public static class ImageSaver{
        public static Bitmap bitmap;
    }
}
