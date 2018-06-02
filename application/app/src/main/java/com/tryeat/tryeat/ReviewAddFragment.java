package com.tryeat.tryeat;

import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContentResolverCompat;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RatingBar;

import com.tryeat.rest.model.Restaurant;
import com.tryeat.rest.model.Status;
import com.tryeat.rest.model.StatusCode;
import com.tryeat.rest.service.RestaurantService;
import com.tryeat.rest.service.ReviewService;
import com.tryeat.team.tryeat_service.R;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by socce on 2018-05-20.
 */

public class ReviewAddFragment extends Fragment {
    View view;

    EditText header;
    EditText desc;
    RatingBar rate;


    Button btn;

    Bitmap image_bitmap;

    int restaurantId;

    private static final int PICK_FROM_CAMERA =0;
    private static final int PICK_FROM_ALBUM =1;
    private static final int CROP_FROM_iMAGE =2;
    private  String absolutePath;
    private Uri mImageCaptureUri;
    ImageView res_img;
    private EditText restaurantName;
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.review_add_fragment,container,false);
        Button addButton = view.findViewById(R.id.addButton);
        addButton.setOnClickListener(addReview());
        desc = view.findViewById(R.id.desc);
        rate = view.findViewById(R.id.rate);

        res_img = view.findViewById(R.id.res_image);
        restaurantName = view.findViewById(R.id.restaurant_name);

        ImageButton search = view.findViewById(R.id.rest_search);
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RestaurantService.getRestaurant(restaurantName.getText().toString(), new Callback<ArrayList<Restaurant>>() {
                    @Override
                    public void onResponse(Call<ArrayList<Restaurant>> call, Response<ArrayList<Restaurant>> response) {
                        int status = response.code();
                        if(status == StatusCode.RESTAURANT_IS_EXIST) {
                            AlertDialogBuilder.createAlert(getActivity(), "있습니다.");
                            restaurantId = response.body().get(0).getId();
                        }else if(status == StatusCode.RESTAURANT_IS_NOT_EXIST) {
                            AlertDialogBuilder.createAlert(getActivity(), "없습니다.");
                            AlertDialogBuilder.createChoiceAlert(getActivity(), "음식점을 등록하시겠습니까?", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    startAddRestaurant();
                                }
                            });
                        }
                    }

                    @Override
                    public void onFailure(Call<ArrayList<Restaurant>> call, Throwable t) {

                    }
                });
            }
        });

        btn = view.findViewById(R.id.image_btn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (view.getId()) {
                    case R.id.image_btn:
                        DialogInterface.OnClickListener cameraListener = new DialogInterface.OnClickListener(){
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                                String url = "tmp_" + String.valueOf(System.currentTimeMillis()) + ".jpg";
                                mImageCaptureUri = Uri.fromFile(new File(Environment.getExternalStorageDirectory(), url));

                                intent.putExtra(MediaStore.EXTRA_OUTPUT, mImageCaptureUri);
                                startActivityForResult(intent, PICK_FROM_CAMERA);
                            }
                        };
                        DialogInterface.OnClickListener albumListener = new DialogInterface.OnClickListener(){
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Intent intent = new Intent(Intent.ACTION_PICK);
                                intent.setType(MediaStore.Images.Media.CONTENT_TYPE);
                                startActivityForResult(intent, PICK_FROM_ALBUM);
                            }
                        };
                        DialogInterface.OnClickListener cancelListener = new DialogInterface.OnClickListener(){
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();
                            }
                        };
                        new AlertDialog.Builder(getActivity()).setTitle("악로드할 이미지")
                                .setPositiveButton("사진촬영",cameraListener)
                                .setNeutralButton("앨범선택",albumListener)
                                .setNegativeButton("취소",cancelListener)
                                .show();
                }
            }
        });
        return view;
    }

    private void startAddRestaurant(){
        FragmentManager fm = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        fragmentTransaction.setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_out_right );
        fragmentTransaction.replace(R.id.frament_place, FragmentLoader.getFragmentInstance(RestaurantAddFragment.class));
        fragmentTransaction.addToBackStack(null).commit();
    }

    public View.OnClickListener addReview(){
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                android.net.Uri selectedImage = mImageCaptureUri;
                String[] filePathColumn = {MediaStore.Images.Media.DATA};
                android.database.Cursor cursor = getActivity().getContentResolver().query(selectedImage, filePathColumn, null, null, null);
                if (cursor == null)
                    return;

                cursor.moveToFirst();

                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                String filePath = cursor.getString(columnIndex);
                cursor.close();

                File file = new File(filePath);

                ReviewService.writeReview(LoginToken.getId(), restaurantId, desc.getText().toString(),image_bitmap,rate.getNumStars(), new Callback<Status>() {
                    @Override
                    public void onResponse(Call<Status> call, Response<Status> response) {
                        int code = response.code();
                        if(code == StatusCode.WRITE_REVIEW_SUCCESS){}
                        else if(code == StatusCode.WRITE_REVIEW_FAIL){}
                    }

                    @Override
                    public void onFailure(Call<Status> call, Throwable t) {
                        Log.d("onFailure",t.toString());
                    }
                });
            }
        };
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode)
        {
            case PICK_FROM_ALBUM:
                mImageCaptureUri = data.getData();
                Log.d("SmartWheel", mImageCaptureUri.getPath().toString());

                try {
                    image_bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(),data.getData());
                    res_img.setImageBitmap(image_bitmap);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            case PICK_FROM_CAMERA:
                Intent intent = new Intent("com.android.amera.action.CROP");
                intent.setDataAndType(mImageCaptureUri,"image/*");
                //CROP
                intent.putExtra("outputX",200);
                intent.putExtra("outputY",200);
                intent.putExtra("aspectX",1);
                intent.putExtra("aspectY",1);
                intent.putExtra("scale", true);
                intent.putExtra("return-data",true);
                startActivityForResult(intent, CROP_FROM_iMAGE);
                break;
            case CROP_FROM_iMAGE:
            {

            }

        }
    }

}