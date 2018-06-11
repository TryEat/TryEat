package com.tryeat.tryeat;

import android.app.Dialog;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.os.Bundle;
import android.provider.BaseColumns;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.FilterQueryProvider;
import android.widget.SimpleCursorAdapter;

import com.tryeat.rest.model.Restaurant;
import com.tryeat.rest.service.RestaurantService;
import com.tryeat.team.tryeat_service.R;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AutoSearchFragment extends DialogFragment {
    View view;
    AutoCompleteTextView autoCompleteTextView;
    SimpleCursorAdapter simpleCursorAdapter;
    MatrixCursor matrixCursor;

    String[] from = {"name"};
    int[] to = {android.R.id.text1};
    String[] columnNames = {BaseColumns._ID, "id", "name"};

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.auto_search_fragment, container, false);

        autoCompleteTextView = view.findViewById(R.id.auto);
        autoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                MatrixCursor matrix = (MatrixCursor)adapterView.getItemAtPosition(i);
                Bundle bundle = new Bundle(2);
                bundle.putSerializable("id",matrix.getInt(1));
                bundle.putSerializable("name",matrix.getString(2));
                FragmentLoader.startFragment(R.id.frament_place,ReviewAddFragment.class,bundle,true);
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
                RestaurantService.getRestaurant(constraint.toString(), new Callback<ArrayList<Restaurant>>() {
                    @Override
                    public void onResponse(Call<ArrayList<Restaurant>> call, Response<ArrayList<Restaurant>> response) {
                        if (response.body() != null) {
                            matrixCursor = new MatrixCursor(columnNames);
                            ArrayList<Restaurant> items = response.body();
                            int i;
                            for (i = 0; i < items.size(); i++) {
                                matrixCursor.newRow().add(i).add(items.get(i).getId()).add(items.get(i).getName());
                            }
                            matrixCursor.newRow().add(i).add(-1).add("원하는 음식점이 없습니다.");
                        }
                    }

                    @Override
                    public void onFailure(Call<ArrayList<Restaurant>> call, Throwable t) {

                    }
                });
                return matrixCursor;
            }
        };
        simpleCursorAdapter.setFilterQueryProvider(provider);
        autoCompleteTextView.setAdapter(simpleCursorAdapter);

        return view;
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

    public void selfDismiss(){
        this.dismiss();
    }
}
