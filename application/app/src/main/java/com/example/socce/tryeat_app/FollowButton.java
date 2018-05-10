package com.example.socce.tryeat_app;

import android.content.Context;
import android.widget.Button;

/**
 * Created by socce on 2018-05-08.
 */

public class FollowButton extends android.support.v7.widget.AppCompatButton{
    int mTargetId;
    public FollowButton(Context context) {
        super(context);
    }

    public void setTarget(int mTargetId){
        this.mTargetId = mTargetId;
    }
}
