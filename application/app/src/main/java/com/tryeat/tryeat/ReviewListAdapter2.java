package com.tryeat.tryeat;

import android.app.Activity;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.RatingBar;
import android.widget.TextView;

import com.tryeat.rest.model.Review;
import com.tryeat.rest.model.Status;
import com.tryeat.rest.service.ReviewService;
import com.tryeat.team.tryeat_service.R;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * Created by socce on 2018-05-08.
 */


class ReviewListAdapter2 extends SimpleAdapter<Review>{

    private static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        ImageView image;
        TextView restaurantName;
        RatingBar rate;
        TextView text;
        TextView address;
        TextView date;
        ImageView menu;
        LinearLayout open;

        ViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            image = itemView.findViewById(R.id.image);
            restaurantName = itemView.findViewById(R.id.restaurant_name);
            rate = itemView.findViewById(R.id.rate);
            text = itemView.findViewById(R.id.text);
            address = itemView.findViewById(R.id.address);
            open = itemView.findViewById(R.id.view_restaurant_button);
            date = itemView.findViewById(R.id.date);
            menu = itemView.findViewById(R.id.menu);
        }

        @Override
        public void onClick(View view) {
            clickListener.onItemClick(getAdapterPosition(),view);
        }
    }

    public ReviewListAdapter2(ArrayList<Review> item){
        this.mItemList = item;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.review_list_item2, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final ViewHolder viewHolder = (ViewHolder) holder;
        final Review reviewItem = mItemList.get(position);

        viewHolder.open.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle(2);
                bundle.putInt("id", reviewItem.getRestaurantId());
                FragmentLoader.startFragment(R.id.frament_place, RestaurantDetailFragment.class, bundle, false);
            }
        });

        viewHolder.menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                final PopupMenu popupMenu = new PopupMenu(v.getContext(),v);
                popupMenu.inflate(R.menu.review_menu);
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(final MenuItem item) {
                        switch (item.getItemId()){
                            case R.id.m1:
                                Bundle bundle = new Bundle(2);
                                bundle.putSerializable("revise",reviewItem);
                                FragmentLoader.startFragment(R.id.frament_place,ReviewAddFragment.class,bundle,true);
                                break;
                            case R.id.m2:
                                AlertDialogBuilder.createChoiceAlert((Activity)v.getContext(), "리뷰를 지우시겠습니까?", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        ReviewService.deleteReview(reviewItem.getReviewId(), new Callback<Status>() {
                                            @Override
                                            public void onResponse(Call<Status> call, Response<Status> response) {
                                                if(response.isSuccessful()){
                                                    int position = mItemList.indexOf(reviewItem);
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
                                break;
                            case R.id.m3:
                                popupMenu.dismiss();
                                break;
                            default:
                                break;
                        }
                        return false;
                    }
                });
                popupMenu.show();
            }
        });

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy년 MM월 dd일 a HH:mm");
        Date currenTimeZone = new Date(reviewItem.getDate().getTime());

        Utils.safeSetObject(viewHolder.date, sdf.format(currenTimeZone));

        BitmapLoader bitmapLoader = new BitmapLoader(viewHolder.image);
        bitmapLoader.execute(reviewItem.getImage());

        viewHolder.restaurantName.setText(reviewItem.getRestaurantName());
        viewHolder.rate.setRating(reviewItem.getRate());
        viewHolder.text.setText(reviewItem.getText());
        viewHolder.address.setText(reviewItem.getAddress());
    }

    @Override
    public int getItemCount() {
        return mItemList.size();
    }

}

