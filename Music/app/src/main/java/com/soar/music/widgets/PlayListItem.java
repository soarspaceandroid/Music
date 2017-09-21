package com.soar.music.widgets;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Created by gaofei on 2016/12/30.
 */
public class PlayListItem extends ParentLinearLayout {
    public PlayListItem(Context context) {
        super(context);
    }

    public PlayListItem(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public PlayListItem(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return true;
    }


    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        return super.dispatchTouchEvent(ev);
    }
}
