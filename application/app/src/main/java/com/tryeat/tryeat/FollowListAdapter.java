package com.tryeat.tryeat;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.tryeat.rest.model.BookMark;
import com.tryeat.rest.model.Review;
import com.tryeat.team.tryeat_service.R;

import java.util.ArrayList;


public class FollowListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public interface ClickListener {
        void onItemClick(int position, View v);
    }

    private static ClickListener clickListener;

    private Context mContext;
    private ArrayList<BookMark> mList;

    private static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView name;
        public RecyclerView recyclerView;

        public ViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            name = itemView.findViewById(R.id.name);
            recyclerView = itemView.findViewById(R.id.listView);
        }

        @Override
        public void onClick(View view) {
            clickListener.onItemClick(getAdapterPosition(), view);
        }
    }

    public void setOnItemClickListener(ClickListener clickListener) {
        this.clickListener = clickListener;
    }

    public FollowListAdapter(Context context, ArrayList<BookMark> item) {
        this.mList = item;
        mContext = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.follow_list_item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ViewHolder viewHolder = (ViewHolder) holder;
        BookMark item = mList.get(position);

        viewHolder.name.setText(item.getRestaurantName());

        ArrayList<Review> listItem = new ArrayList<>();

        FollowListItemAdapter adapter = new FollowListItemAdapter(item.getRestaurantId(),listItem);
        adapter.setOnItemClickListener(new FollowListItemAdapter.ClickListener() {
            @Override
            public void onItemClick(int position, View v) {

            }
        });
        viewHolder.recyclerView.setHasFixedSize(true);
        viewHolder.recyclerView.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false));
        viewHolder.recyclerView.setAdapter(adapter);
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }
}