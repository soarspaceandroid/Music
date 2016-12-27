package com.soar.music.application;

import android.app.Application;

import com.soar.music.savemanager.DataManager;

/**
 * Created by gaofei on 2016/12/26.
 */
public class MusicApplication extends Application {

    private final static String DATABASE_NAME = "beng_music";

    @Override
    public void onCreate() {
        super.onCreate();
        initDataSaveManager();
    }


    private void initDataSaveManager(){
        DataManager.init(getBaseContext() ,DATABASE_NAME );
    }





}
