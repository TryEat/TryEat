package com.tryeat.tryeat;

import android.app.Activity;
import android.content.DialogInterface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.tryeat.rest.model.Restaurant;
import com.tryeat.rest.model.Status;
import com.tryeat.rest.service.BookMarkService;
import com.tryeat.team.tryeat_service.R;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.tryeat.tryeat.Utils.safeDivide;


public class FollowListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public interface ClickListener {
        void onItemClick(int position, View v);
    }

    private static ClickListener clickListener;

    private ArrayList<Restaurant> mList;

    private static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView address;
        public TextView rate;
        public TextView count;
        public TextView bookmark;
        public TextView name;
        public TextView date;
        public ImageView image;

        public ImageView bookMarkOff;

        public ViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            address = itemView.findViewById(R.id.address);
            rate = itemView.findViewById(R.id.rate);
            bookmark = itemView.findViewById(R.id.addbookmark);
            count = itemView.findViewById(R.id.count);
            image = itemView.findViewById(R.id.image);
            name = itemView.findViewById(R.id.name);
            date = itemView.findViewById(R.id.date);
            bookMarkOff = itemView.findViewById(R.id.bookmarkoff);
        }

        @Override
        public void onClick(View view) {
            clickListener.onItemClick(getAdapterPosition(), view);
        }
    }

    public void setOnItemClickListener(ClickListener clickListener) {
        this.clickListener = clickListener;
    }

    public FollowListAdapter(ArrayList<Restaurant> item) {
        this.mList = item;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.follow_list_item, parent, false);
        return new FollowListAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
        final ViewHolder viewHolder = (ViewHolder) holder;
        final Restaurant item = mList.get(position);

        viewHolder.bookMarkOff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialogBuilder.createChoiceAlert((Activity) v.getContext(), "북마크를 지우시겠습니까?", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        BookMarkService.removeBookMark(LoginToken.getId(), item.getId(), new Callback<Status>() {
                            @Override
                            public void onResponse(Call<Status> call, Response<Status> response) {
                                if (response.code() == 201) {
                                    int position = mList.indexOf(item);
                                    mList.remove(position);
                                    notifyItemRemoved(position);
                                }
                            }

                            @Override
                            public void onFailure(Call<Status> call, Throwable t) {

                            }
                        });
                    }
                });

            }
        });

        Utils.safeSetObject(viewHolder.rate, safeDivide(item.getTotalRate(), item.getReviewCount()));
        Utils.safeSetObject(viewHolder.address, item.getAddress());
        Utils.safeSetObject(viewHolder.bookmark, item.getTotalBookMark());

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy년 MM월 dd일 a HH:mm");
        Date currenTimeZone = new Date(item.getDate().getTime());

        Utils.safeSetObject(viewHolder.date, sdf.format(currenTimeZone));
        viewHolder.count.setText(item.getReviewCount() + "");

        if (item.getImage() != null)

        {
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