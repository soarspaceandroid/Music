package com.soar.music.interfaces;

import android.content.Context;
import android.view.View;

import com.soar.music.savemanager.DataManager;
import com.soar.music.widgets.LayoutFrames;

/**
 * Created by gaofei on 2016/12/26.
 */
public interface MusicMainInter {



    DataManager initDataManager();



    void scanMusic(Context context , DataManager dataManager , String path , MusicScanLisenter musicScanLisenter);


    void blueMainUI(Context context , View rootView , LayoutFrames layoutFrames);




}
