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


class BookMarkListAdapter extends SimpleAdapter<Restaurant> {

    private static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView address;
        TextView rate;
        TextView count;
        TextView bookmark;
        TextView name;
        TextView date;
        ImageView image;

        ImageView bookMarkOff;

        ViewHolder(View itemView) {
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

    public BookMarkListAdapter(ArrayList<Restaurant> item) {
        this.mItemList = item;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.bookmark_list_item, parent, false);
        return new BookMarkListAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
        final ViewHolder viewHolder = (ViewHolder) holder;
        final Restaurant item = mItemList.get(position);

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
                                    int position = mItemList.indexOf(item);
                                    mItemList.remove(position);
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

        Utils.safeSetObject(viewHolder.rate, String.format("%.1f" , safeDivide(item.getTotalRate(), item.getReviewCount())));
        Utils.safeSetObject(viewHolder.address, item.getAddress());
        Utils.safeSetObject(viewHolder.bookmark, item.getTotalBookMark());

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy년 MM월 dd일 a HH:mm");
        Date currenTimeZone = new Date(item.getDate().getTime());

        Utils.safeSetObject(viewHolder.date, sdf.format(currenTimeZone));
        viewHolder.count.setText(item.getReviewCount() + "");

        BitmapLoader bitmapLoader = new BitmapLoader(mActivitiy,viewHolder.image);
        bitmapLoader.Load(item.getImgUri());

        viewHolder.name.setText(item.getName());
    }

    @Override
    public int getItemCount() {
        return mItemList.size();
    }
}