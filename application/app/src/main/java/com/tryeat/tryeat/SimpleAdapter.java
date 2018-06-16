package com.tryeat.tryeat;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.tryeat.rest.model.Restaurant;

import java.util.ArrayList;

public abstract class SimpleAdapter<T> extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    static ClickListener clickListener;

    ArrayList<T> mItemList;

    public interface ClickListener {
        void onItemClick(int position, View v);
    }

    public void setOnItemClickListener(ClickListener clickListener) {
        this.clickListener = clickListener;
    }

}
