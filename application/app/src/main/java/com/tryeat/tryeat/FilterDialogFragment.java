package com.tryeat.tryeat;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.tryeat.team.tryeat_service.R;

public class FilterDialogFragment extends DialogFragment{

    private FilterInterface mFilterInterface;

    private TabLayout distanceTab;
    private TabLayout typeTab;


    private View view;

    private int distance;
    private int type;


    public interface FilterInterface{
        void setSetting(int type, int distance);
    }

    public void setInterface(FilterInterface filterInterface){
        mFilterInterface = filterInterface;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.filter, container, false);

        distanceTab = view.findViewById(R.id.distance);
        distanceTab.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                distance = tab.getPosition();
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        typeTab = view.findViewById(R.id.type);
        typeTab.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                type = tab.getPosition();
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        TextView confirm = view.findViewById(R.id.confirm);
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mFilterInterface.setSetting(type,distance);
                selfDismiss();
            }
        });


        type = getArguments().getInt("type");
        distance = getArguments().getInt("distance");

        typeTab.getTabAt(type).select();
        distanceTab.getTabAt(distance).select();

        TextView cancel = view.findViewById(R.id.cancel);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selfDismiss();
            }
        });

        return view;
    }

    private void selfDismiss(){
        this.dismiss();
    }

}
