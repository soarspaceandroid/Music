package com.soar.music.utils;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;

import com.soar.music.interfaces.OnfinishLisenter;

/**
 * Created by gaofei on 2016/12/26.
 */
public class AnimHelper {

    public final static int DURATIME = 400;

    public static void transX(View view , float start , float end){
        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(view , "translationX" , start , end);
        objectAnimator.setDuration(DURATIME);
        objectAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
        objectAnimator.start();
    }


    public static void transY(View view , float start , float end){
        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(view , "translationY" , start , end);
        objectAnimator.setDuration(DURATIME);
        objectAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
        objectAnimator.start();
    }


    public static void transY(View view , float start , float end ,final OnfinishLisenter onfinishLisenter){
        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(view , "translationY" , start , end);
        objectAnimator.setDuration(DURATIME);
        objectAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
        objectAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                onfinishLisenter.onFinish();
            }

            @Override
            public void onAnimationCancel(Animator animation) {
                onfinishLisenter.onFinish();
            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        objectAnimator.start();
    }



}
