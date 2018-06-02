package com.tryeat.tryeat;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.tryeat.rest.model.Review;
import com.tryeat.rest.service.ReviewService;
import com.tryeat.team.tryeat_service.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FollowListItemAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public interface ClickListener {
        void onItemClick(int position, View v);
    }

    private static ClickListener clickListener;

    private int mTargetId;
    private ArrayList<Review> mListItem;

    private static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public ImageView image;

        public ViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            image = itemView.findViewById(R.id.image);
        }

        @Override
        public void onClick(View view) {
            clickListener.onItemClick(getAdapterPosition(), view);
        }
    }

    public void setOnItemClickListener(ClickListener clickListener) {
        this.clickListener = clickListener;
    }

    public FollowListItemAdapter(int targetId,ArrayList<Review> listItem) {
        mListItem = listItem;
        mTargetId = targetId;

        ReviewService.getUserReviews(mTargetId, 0, new Callback<ArrayList<Review>>() {
            @Override
            public void onResponse(Call<ArrayList<Review>> call, Response<ArrayList<Review>> response) {
                if (response.isSuccessful()) {
                    List<Review> reviews = response.body();
                    int size = reviews.size();
                    for (int i = 0; i < size; i++) {
                        Review item = reviews.get(i);
                        mListItem.add(item);
                    }
                    notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<ArrayList<Review>> call, Throwable t) {
                Log.d("debug", "getRestaurantReviews onFailure" + t);
            }
        });
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.folloe_list_item_item, parent, false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ViewHolder viewHolder = (ViewHolder) holder;
        Review item = mListItem.get(position);

        if(item.getImage()!=null) {
            BitmapLoader bitmapLoader = new BitmapLoader(viewHolder.image);
            bitmapLoader.execute(item.getImage());
        }

    }

    @Override
    public int getItemCount() {
        return mListItem.size();
    }
}
