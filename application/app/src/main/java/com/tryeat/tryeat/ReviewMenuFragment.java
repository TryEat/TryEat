package com.tryeat.tryeat;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.tryeat.team.tryeat_service.R;

public class ReviewMenuFragment extends Fragment {

    private View view;

    private LinearLayout addReview;
    private ImageView image;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.review_menu_fragment,container,false);

        NavigationManager.setVisibility(View.VISIBLE);

        image = view.findViewById(R.id.image);

        addReview = view.findViewById(R.id.addReview);
        addReview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fm = getActivity().getSupportFragmentManager();
                AutoSearchFragment dialogFragment = new AutoSearchFragment();
                dialogFragment.show(fm, "frament_place");
            }
        });

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Glide.with(getActivity())
                .load(R.drawable.list_header_image2)
                .into(image);
    }
}
