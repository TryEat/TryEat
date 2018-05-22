package com.example.socce.tryeat_app;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.Window;
import android.view.WindowManager;

/**
 * Created by socce on 2018-05-20.
 */

public class ReviewAddDialog extends Dialog {
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
    }

    public ReviewAddDialog(@NonNull Context context) {
        super(context);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setCanceledOnTouchOutside(true);
        setContentView(R.layout.review_add_fragment);
    }
}
