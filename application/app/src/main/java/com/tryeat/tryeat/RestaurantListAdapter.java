package com.tryeat.tryeat;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.tryeat.rest.model.Restaurant;
import com.tryeat.rest.model.Review;
import com.tryeat.team.tryeat_service.R;

import java.util.ArrayList;

import static com.tryeat.tryeat.Utils.safeDivide;


/**
 * Created by socce on 2018-05-08.
 */

public class RestaurantListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public interface ClickListener {
        void onItemClick(int position, View v);
    }

    private static ClickListener clickListener;

    private ArrayList<Restaurant> mList;

    private static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView who;
        public RatingBar rate;
        public TextView count;
        public TextView name;
        public ImageView image;

        public ViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            who = itemView.findViewById(R.id.who);
            rate = itemView.findViewById(R.id.rate);
            count = itemView.findViewById(R.id.count);
            image = itemView.findViewById(R.id.image);
            name = itemView.findViewById(R.id.name);
        }

        @Override
        public void onClick(View view) {
            clickListener.onItemClick(getAdapterPosition(), view);
        }
    }

    public void setOnItemClickListener(ClickListener clickListener) {
        this.clickListener = clickListener;
    }

    public RestaurantListAdapter(ArrayList<Restaurant> item) {
        this.mList = item;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.restaurant_list_item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ViewHolder viewHolder = (ViewHolder) holder;
        Restaurant item = mList.get(position);

        viewHolder.rate.setRating(safeDivide(item.getTotalRate(), item.getReviewCount()));
        viewHolder.count.setText(item.getReviewCount() + "");

        if(item.getImage()!=null) {
            BitmapLoader bitmapLoader = new BitmapLoader(viewHolder.image);
            bitmapLoader.execute(item.getImage());
        }

        viewHolder.name.setText(item.getName());
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }
}
