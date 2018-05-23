package com.tryeat.tryeat;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.tryeat.team.tryeat_service.R;

import java.util.ArrayList;


/**
 * Created by socce on 2018-05-08.
 */


public class ReviewListAdapter extends BaseAdapter {
    private Context mContext = null;
    private int mLayout;
    private ArrayList<ReviewListItem> mList;

    private class ViewHolder{
        public ImageView mIcon;
        public TextView mName;
        public TextView mRate;
    }

    public ReviewListAdapter(Context mContext,int mLayout){
        super();
        this.mContext = mContext;
        this.mLayout = mLayout;
        this.mList = new ArrayList<>();
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();

            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(mLayout, null);

            holder.mIcon = (ImageView) convertView.findViewById(R.id.icon);
            holder.mName = (TextView) convertView.findViewById(R.id.name);
            holder.mRate = (TextView) convertView.findViewById(R.id.rate);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        ReviewListItem item = mList.get(position);

        holder.mIcon.setImageDrawable(item.mIcon);
        holder.mName.setText(item.mName);
        holder.mRate.setText(item.mRate);

        return convertView;
    }

    public void addItem(ReviewListItem item){
        mList.add(item);
    }
}
