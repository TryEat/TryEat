package com.tryeat.tryeat;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.tryeat.rest.model.Restaurant;
import com.tryeat.team.tryeat_service.R;

import java.util.ArrayList;

import static com.tryeat.tryeat.Utils.safeDivide;


/**
 * Created by socce on 2018-05-08.
 */

class RestaurantListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public interface ClickListener {
        void onItemClick(int position, View v);
    }

    private static ClickListener clickListener;

    private ArrayList<Restaurant> mList;

    private Activity mActivity;

    private static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView address;
        TextView rate;
        TextView count;
        TextView bookmark;
        TextView name;
        ImageView image;
        TextView distance;

        ViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            address = itemView.findViewById(R.id.address);
            rate = itemView.findViewById(R.id.rate);
            bookmark = itemView.findViewById(R.id.addbookmark);
            count = itemView.findViewById(R.id.count);
            image = itemView.findViewById(R.id.image);
            name = itemView.findViewById(R.id.name);
            distance = itemView.findViewById(R.id.distance);
        }

        @Override
        public void onClick(View view) {
            clickListener.onItemClick(getAdapterPosition(), view);
        }
    }

    public void setOnItemClickListener(ClickListener clickListener) {
        RestaurantListAdapter.clickListener = clickListener;
    }

    public void setActivity(Activity activity){
        mActivity = activity;
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


        Utils.safeSetObject(viewHolder.rate,String.format("%.1f" , safeDivide(item.getTotalRate(), item.getReviewCount())));
        Utils.safeSetObject(viewHolder.address,item.getAddress());
        Utils.safeSetObject(viewHolder.bookmark,item.getTotalBookMark());
        Utils.safeSetObject(viewHolder.count,item.getReviewCount());

        if(item.getDistance()!=0){
            viewHolder.distance.setVisibility(View.VISIBLE);
            double distance = item.getDistance();
            if(distance<1) Utils.safeSetObject(viewHolder.distance,String.format("%.0fm" , distance*1000));
            else Utils.safeSetObject(viewHolder.distance,String.format("%.1fkm" , distance));
        }else{
            viewHolder.distance.setVisibility(View.GONE);
        }

        if(item.getImgUri()!=null) {
            BitmapLoader bitmapLoader = new BitmapLoader(mActivity,viewHolder.image);
            bitmapLoader.Load(item.getImgUri());
        }

        viewHolder.name.setText(item.getName());
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }
}
