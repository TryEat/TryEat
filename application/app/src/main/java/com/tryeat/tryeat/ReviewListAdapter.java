package com.tryeat.tryeat;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.tryeat.rest.model.Review;
import com.tryeat.team.tryeat_service.R;

import java.util.ArrayList;


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
        ViewHolder viewHolder = (ViewHolder) holder;
        Review item = mListItem1.get(position);

        BitmapLoader bitmapLoader = new BitmapLoader(viewHolder.image);
        bitmapLoader.execute(item.getImage());

        viewHolder.name.setText(item.getWriter());
        viewHolder.rate.setRating(item.getRate());
        viewHolder.text.setText(item.getText());
    }

    @Override
    public int getItemCount() {
        return mListItem1.size();
    }

}

