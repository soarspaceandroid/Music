package com.soar.music.utils;

import android.os.Handler;
import android.os.Message;

/**
 * Created by gaofei on 2016/12/29.
 */
public class CountThread extends Thread {

    private Handler handler;
    private boolean isStop = false;
    private int playedMill = 0;


    public void setHandler(Handler handler){
        this. handler = handler;
    }

    @Override
    public void run() {
        super.run();

        while (!isStop) {
            try {
                Thread.sleep(1000);
            } catch (Exception e) {
                e.printStackTrace();
            }
            playedMill = playedMill + 1000;
            if (handler != null) {
                Message msg = new Message();
                msg.what = playedMill;
                handler.sendMessage(msg);
            }
        }
    }

    @Override
    public synchronized void start() {
        isStop = false;
        super.start();

    }


    public boolean isCounting(){
        return isStop;
    }

    public void toStop(){
        isStop = true;
        playedMill = 0;
    }

    public long getCount(){
        return playedMill;
    }

}
