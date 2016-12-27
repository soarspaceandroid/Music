package com.soar.music.interfaces;

import com.soar.music.model.MusicInfo;

import java.util.ArrayList;

/**
 * Created by gaofei on 2016/12/26.
 */
public interface MusicScanLisenter {

    void onFinish(ArrayList<MusicInfo> list);

}
