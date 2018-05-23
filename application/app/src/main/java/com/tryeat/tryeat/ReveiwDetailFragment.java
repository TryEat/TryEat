package com.tryeat.tryeat;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tryeat.team.tryeat_service.R;


/**
 * Created by socce on 2018-05-08.
 */


public class ReveiwDetailFragment extends Fragment{
    View view;

    public ReveiwDetailFragment(){

    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.review_detail_fragment,container,false);

        return view;
    }
}
