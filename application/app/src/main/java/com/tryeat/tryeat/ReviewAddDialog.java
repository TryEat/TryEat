package com.tryeat.tryeat;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;

import com.tryeat.team.tryeat_service.R;

import java.io.File;

/**
 * Created by socce on 2018-05-20.
 */

public class ReviewAddDialog extends Dialog implements View.OnClickListener{
    Button img_btn = null;
    Context mcontext;
    private static final int PICK_FROM_CAMERA =0;
    private static final int PICK_FROM_ALBUM =1;
    private static final int CROP_FROM_iMAGE =2;

    private Uri mImageCaptureUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.onCreate(savedInstanceState);
        WindowManager.LayoutParams lpWindow = new WindowManager.LayoutParams();
        lpWindow.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        lpWindow.dimAmount = 0.7f;
        lpWindow.width = WindowManager.LayoutParams.MATCH_PARENT;
        lpWindow.height = WindowManager.LayoutParams.MATCH_PARENT;
        getWindow().setAttributes(lpWindow);
        img_btn = (Button) findViewById(R.id.image_btn);
        img_btn.setOnClickListener(this);
    }

    public ReviewAddDialog(@NonNull Context context) {
        super(context);
        mcontext=context;
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setCanceledOnTouchOutside(true);
        setContentView(R.layout.review_add_fragment);
    }

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

                        intent.putExtra(android.provider.MediaStore.EXTRA_OUTPUT, mImageCaptureUri);
                        ((Activity)mcontext).startActivityForResult(intent, PICK_FROM_CAMERA);
                    }
                };
                DialogInterface.OnClickListener albumListener = new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Intent intent = new Intent(Intent.ACTION_PICK);
                        intent.setType(android.provider.MediaStore.Images.Media.CONTENT_TYPE);
                        ((Activity)mcontext).startActivityForResult(intent, PICK_FROM_ALBUM);
                    }
                };
                DialogInterface.OnClickListener cancelListener = new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                };
                new AlertDialog.Builder(mcontext).setTitle("업로드할 이미지")
                        .setPositiveButton("사진촬영",null)
                        .setNeutralButton("앨범선택",null)
                        .setNegativeButton("취소",null)
                        .show();
        }
    }

}
