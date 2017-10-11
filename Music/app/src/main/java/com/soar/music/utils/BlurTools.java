package com.soar.music.utils;

import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.view.View;
import android.widget.ImageView;

import com.soar.music.activity.MusicMainActivity;
import com.soar.music.interfaces.OnfinishLisenter;

import net.robinx.lib.blur.StackBlur;

/**
 * Created by gaofei on 2016/12/26.
 */
public class BlurTools {


        public static void blur(final MusicMainActivity activity  , View fromView, final ImageView toView , final OnfinishLisenter blurOnfinishLisenter) {
                //获取View的截图
                fromView.destroyDrawingCache();
                fromView.setDrawingCacheEnabled(true);
                fromView.buildDrawingCache();
                final Bitmap bkg = fromView.getDrawingCache();
                new AsyncTask<Void, Void, Bitmap>() {
                        @Override
                        protected Bitmap doInBackground(Void... params) {
                                return StackBlur.blurNatively(bkg, 15, false);
                        }

                        @Override
                        protected void onPostExecute(final Bitmap bitmap) {
                                super.onPostExecute(bitmap);
                                toView.setImageBitmap(bitmap);
                                if(blurOnfinishLisenter != null){
                                        blurOnfinishLisenter.onFinish();
                                }
                        }
                }.execute();


        }



}
