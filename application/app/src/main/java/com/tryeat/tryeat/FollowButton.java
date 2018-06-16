package com.tryeat.tryeat;

import android.content.Context;

/**
 * Created by socce on 2018-05-08.
 */

public class FollowButton extends android.support.v7.widget.AppCompatButton{
    private int mTargetId;
    public FollowButton(Context context) {
        super(context);
    }

    public void setTarget(int mTargetId){
        this.mTargetId = mTargetId;
    }
}
