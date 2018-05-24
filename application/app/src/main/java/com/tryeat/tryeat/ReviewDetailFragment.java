package com.tryeat.tryeat;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.tryeat.team.tryeat_service.R;


/**
 * Created by socce on 2018-05-08.
 */


public class ReviewDetailFragment extends Fragment{
    View view;

    public ReviewDetailFragment(){

    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.review_detail_fragment,container,false);
        ReviewListItem item = (ReviewListItem)getArguments().getSerializable("item");
        TextView name = view.findViewById(R.id.name);
        name.setText(item.getmName());
        TextView rate = view.findViewById(R.id.rate);
        rate.setText(item.getmRate());
        TextView header = view.findViewById(R.id.header);
        header.setText(item.getmHeader());
        TextView desc = view.findViewById(R.id.desc);
        desc.setText(item.getmDesc());

        return view;
    }
}
