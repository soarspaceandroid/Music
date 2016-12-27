package com.soar.music.model;

import java.util.ArrayList;

/**
 * Created by gaofei on 2016/12/26.
 */
public class AllMusic {

    private ArrayList<MusicInfo> list ;

    public AllMusic(ArrayList<MusicInfo> list) {
        this.list = list;
    }


    public ArrayList<MusicInfo> getList() {
        return list;
    }

    public void setList(ArrayList<MusicInfo> list) {
        this.list = list;
    }
}
