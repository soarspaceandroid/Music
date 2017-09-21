package com.soar.music.widgets;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;

/**
 * Created by Administrator on 2017/9/21.
 */

public class ParentLinearLayout extends LinearLayout {

    public ParentLinearLayout(Context context) {
        super(context);
    }

    public ParentLinearLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ParentLinearLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    public LayoutFrames getLayoutFrames(){
        return (LayoutFrames) getParent().getParent().getParent();
    }

}
