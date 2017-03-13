package com.sauda.settinganimation;

import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Created by MY HP on 3/11/2017.
 */

public class MySwipeRefreshLayout extends SwipeRefreshLayout  {

    private Runnable onActionDown, onActionUp;
    Listener listener;

    public MySwipeRefreshLayout(Context context, AttributeSet attributeSet) {
        super(context,attributeSet);
    }

    public void setOnActionDown(Runnable onActionDown) {
        this.onActionDown = onActionDown;
    }

    public void setOnActionUp(Runnable onActionUp) {
        this.onActionUp = onActionUp;
    }

    @Override
    public boolean onTouchEvent (MotionEvent ev) {
        if (ev.getAction() == ev.ACTION_DOWN) {
            if (onActionDown != null) {
                onActionDown.run();
            }
        } else if (ev.getAction() == ev.ACTION_UP) {
            if (onActionUp != null) {
                onActionUp.run();
            }
        }
        return super.onTouchEvent(ev);
    }



}
