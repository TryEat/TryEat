package com.tryeat.tryeat;

import android.content.Context;
import android.widget.Button;

/**
 * Created by loog on 2018-05-07.
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
