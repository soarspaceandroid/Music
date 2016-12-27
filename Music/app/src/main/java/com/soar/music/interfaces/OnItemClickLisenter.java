package com.soar.music.interfaces;

import android.view.MotionEvent;
import android.view.View;

/**
 * Created by gaofei on 2016/12/27.
 */
public interface OnItemClickLisenter {
    void onItemClick(View view  , int position , MotionEvent motionEvent);
}
