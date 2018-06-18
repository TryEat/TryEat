package com.tryeat.tryeat;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.BaseColumns;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.FilterQueryProvider;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.tryeat.rest.model.GoogleAutoComplete;
import com.tryeat.rest.model.GoogleDetail;
import com.tryeat.rest.model.Restaurant;
import com.tryeat.rest.model.Status;
import com.tryeat.rest.service.GoogleApiService;
import com.tryeat.rest.service.RestaurantService;
import com.tryeat.team.tryeat_service.R;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AutoSearchFragment extends DialogFragment {

    private View view;
    private AutoCompleteTextView autoCompleteTextView;
    private SimpleCursorAdapter simpleCursorAdapter;
    private MatrixCursor matrixCursor;

    private String[] from = {"name", "address"};
    private int[] to = {android.R.id.text1, android.R.id.text2};
    private String[] columnNames = {BaseColumns._ID, "id", "name", "address"};

    private boolean prgresing = false;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.auto_search_fragment, container, false);

        autoCompleteTextView = view.findViewById(R.id.auto);
        autoCompleteTextView.setSingleLine();
        autoCompleteTextView.setImeOptions(EditorInfo.IME_ACTION_DONE);

        autoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                MatrixCursor matrix = (MatrixCursor) adapterView.getItemAtPosition(i);
                int type = matrix.getInt(0);
                if (type == 99) {
                    FragmentLoader.startFragment(R.id.frament_place, RestaurantAddFragment.class, true);
                } else if (type < 50) {
                    Bundle bundle = new Bundle(2);
                    bundle.putSerializable("id", matrix.getInt(1));
                    bundle.putSerializable("name", matrix.getString(2));
                    FragmentLoader.startFragment(R.id.frament_place, ReviewAddFragment.class, bundle, true);
                } else {
                    ProgressDialogManager.show(getActivity(),"음식점 추가 중입니다...");
                    addNewRestaurant(matrix.getString(1));
                }
                selfDismiss();
            }
        });

        autoCompleteTextView.setThreshold(1);
        simpleCursorAdapter = new SimpleCursorAdapter(getContext(), android.R.layout.simple_list_item_2, null, from, to, 0);
        simpleCursorAdapter.setStringConversionColumn(2);

        FilterQueryProvider provider = new FilterQueryProvider() {
            @Override
            public Cursor runQuery(CharSequence constraint) {
                if (constraint == null) return null;
                if (!prgresing) {
                    prgresing = true;
                    if (matrixCursor != null) matrixCursor.close();
                    matrixCursor = new MatrixCursor(columnNames);
                    getItem(constraint);
                }
                return matrixCursor;
            }
        };
        simpleCursorAdapter.setFilterQueryProvider(provider);
        autoCompleteTextView.setAdapter(simpleCursorAdapter);
        return view;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        return dialog;
    }

    private void getItem(final CharSequence constraint) {
        RestaurantService.getRestaurant(constraint.toString(), new Callback<ArrayList<Restaurant>>() {
            @Override
            public void onResponse(Call<ArrayList<Restaurant>> call, Response<ArrayList<Restaurant>> response) {
                if (response.body() != null) {
                    ArrayList<Restaurant> items = response.body();
                    for (int i = 0; i < items.size(); i++) {
                        Log.d("asdf", items.get(i).getName());
                        matrixCursor.newRow().add(i).add(items.get(i).getId()).add(items.get(i).getName()).add(items.get(i).getAddress());
                    }
                }
                getItem2(constraint);
            }

            @Override
            public void onFailure(Call<ArrayList<Restaurant>> call, Throwable t) {

            }
        });
    }

    private void getItem2(CharSequence constraint) {
        GoogleApiService.getRestaurant(constraint.toString(), MyLocation.getLocation().getLatitude(), MyLocation.getLocation().getLongitude(), 2000, new Callback<GoogleAutoComplete>() {
            @Override
            public void onResponse(Call<GoogleAutoComplete> call, Response<GoogleAutoComplete> response) {
                if (response.body() != null) {
                    GoogleAutoComplete items = response.body();
                    for (int i = 0; i < items.size(); i++) {
                        Log.d("asdf", items.get(i).getName());
                        matrixCursor.newRow().add(50 + i).add(items.get(i).getPlaceId()).add(items.get(i).getName()).add(items.get(i).getAddress());
                    }
                }
                matrixCursor.newRow().add(99).add(-1).add("원하는 음식점이 없습니다.").add("추가하겠습니다.");
                simpleCursorAdapter.changeCursor(matrixCursor);
                simpleCursorAdapter.notifyDataSetChanged();
                prgresing = false;
            }

            @Override
            public void onFailure(Call<GoogleAutoComplete> call, Throwable t) {
                Log.d("asdf", t.toString());
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();

        Dialog dialog = getDialog();
        if (dialog != null) {
            int width = ViewGroup.LayoutParams.MATCH_PARENT;
            int height = ViewGroup.LayoutParams.WRAP_CONTENT;
            dialog.getWindow().setLayout(width, height);
            getDialog().getWindow().setGravity(Gravity.TOP);
        }
    }

    private void selfDismiss() {
        this.dismiss();
    }


    class MyTarget extends SimpleTarget<Bitmap> {
        GoogleDetail mGoogleDetail;

        MyTarget(GoogleDetail googleDetail) {
            super();
            mGoogleDetail = googleDetail;
        }

        @Override
        public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
            RestaurantService.addRestaurant(mGoogleDetail.getName(),mGoogleDetail.getAddress(),mGoogleDetail.getPhoneNumber(), resource, mGoogleDetail.getLat(), mGoogleDetail.getLon(), new Callback<Status>() {
                @Override
                public void onResponse(Call<Status> call, Response<Status> response) {
                    Bundle bundle = new Bundle(2);
                    bundle.putSerializable("id", response.body().payLoadInt);
                    bundle.putSerializable("name", response.body().payLoadString);
                    FragmentLoader.startFragment(R.id.frament_place, ReviewAddFragment.class, bundle, true);
                    ProgressDialogManager.dismiss();
                }

                @Override
                public void onFailure(Call<Status> call, Throwable t) {
                    Toast.makeText(getActivity(),"다시 시도해 주십시오",Toast.LENGTH_LONG);
                    ProgressDialogManager.dismiss();
                }
            });
        }
    }

    private void addNewRestaurant(String placeId) {
        GoogleApiService.getDetail(placeId, new Callback<GoogleDetail>() {
            @Override
            public void onResponse(Call<GoogleDetail> call, Response<GoogleDetail> response) {
                GoogleDetail detailItem = response.body();
                if (detailItem != null) {
                    if (detailItem.hasPhoto()) {
                        Glide.with(view)
                                .asBitmap()
                                .load(detailItem.getPhotoUrl())
                                .into(new MyTarget(detailItem));
                    } else {
                        RestaurantService.addRestaurant(detailItem.getName(),detailItem.getAddress(),detailItem.getPhoneNumber(), null, detailItem.getLat(), detailItem.getLon(), new Callback<Status>() {
                            @Override
                            public void onResponse(Call<Status> call, Response<Status> response) {
                                Bundle bundle = new Bundle(2);
                                bundle.putSerializable("id", response.body().payLoadInt);
                                bundle.putSerializable("name", response.body().payLoadString);
                                FragmentLoader.startFragment(R.id.frament_place, ReviewAddFragment.class, bundle, true);
                                ProgressDialogManager.dismiss();
                            }

                            @Override
                            public void onFailure(Call<Status> call, Throwable t) {
                                Toast.makeText(getActivity(),"다시 시도해 주십시오",Toast.LENGTH_LONG);
                                ProgressDialogManager.dismiss();
                            }
                        });
                    }
                }
            }

            @Override
            public void onFailure(Call<GoogleDetail> call, Throwable t) {
                Toast.makeText(getActivity(),"다시 시도해 주십시오",Toast.LENGTH_LONG);
                ProgressDialogManager.dismiss();
            }
        });
    }
}
