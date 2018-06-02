package com.tryeat.tryeat;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.tryeat.rest.model.Restaurant;
import com.tryeat.rest.model.Review;
import com.tryeat.rest.service.RestaurantService;
import com.tryeat.team.tryeat_service.R;

import java.util.ArrayList;
import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * Created by socce on 2018-05-08.
 */


public class ReviewListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    public interface ClickListener{
        void onItemClick(int position, View v);
    }

    private  static ClickListener clickListener;

    private ArrayList<Review> mListItem1;

    private static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public ImageView image;
        public TextView name;
        public RatingBar rate;
        public TextView text;

        public ViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            image = itemView.findViewById(R.id.image);
            name = itemView.findViewById(R.id.name);
            rate = itemView.findViewById(R.id.rate);
            text = itemView.findViewById(R.id.text);
        }

        @Override
        public void onClick(View view) {
            clickListener.onItemClick(getAdapterPosition(),view);
        }
    }

    public void setOnItemClickListener(ClickListener clickListener) {
        this.clickListener = clickListener;
    }

    public ReviewListAdapter(ArrayList<Review> item){
        this.mListItem1 = item;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.review_list_item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ViewHolder myViewHolder = (ViewHolder) holder;
        Review item = mListItem1.get(position);

        task ta = new task(myViewHolder);
        ta.execute(item.getImage().data);

        myViewHolder.name.setText(item.getRestaurantName());
        myViewHolder.rate.setRating(item.getRate());
        myViewHolder.text.setText(item.getText());
    }

    @Override
    public int getItemCount() {
        return mListItem1.size();
    }

    private static class task extends AsyncTask<byte[],Void,Bitmap>{
        private ViewHolder mHolder;

        public task(ViewHolder holder) {
            mHolder = holder;
        }

        @Override
        protected Bitmap doInBackground(byte[]... bytes) {
            byte[] v = bytes[0];
            Bitmap bm = BitmapFactory.decodeByteArray(v,0,v.length);
            return bm;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            super.onPostExecute(bitmap);
            mHolder.image.setImageBitmap(bitmap);
        }
    }
}

