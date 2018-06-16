package com.tryeat.tryeat;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.tryeat.team.tryeat_service.R;

public class LoadingActivity extends AppCompatActivity {
    private Thread welcome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading);

        MyLocation.setActivity(this);

        startLoading();
    }

    private void startLoading() {

        MyLocation.searchLocation();

        welcome = new Thread(new Runnable() {
            @Override
            public void run() {
                while(MyLocation.getLocation()==null){
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                Intent intent = new Intent(getApplicationContext(), SignInActivity.class);
                startActivity(intent);
                finish();
                overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
            }
        });

        welcome.start();
    }
}
