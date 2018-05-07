package com.tryeat.tryeat;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;

public class ReviewLIstFragment extends Fragment {
    View view;
    ListView lv;
    ReviewListAdapter rAdapter;

    ArrayList<ReviewListItem> mList = new ArrayList<>();

    public ReviewLIstFragment(){

    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.review_list_fragment,container,false);
        lv = view.findViewById(R.id.listView);
        rAdapter = new ReviewListAdapter(view.getContext(),R.layout.review_list_item,mList);
        lv.setAdapter(rAdapter);

        return view;
    }
}
