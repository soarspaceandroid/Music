package com.soar.music.services;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

import com.soar.music.interfaces.MusicPlayTimeLisenter;
import com.soar.music.model.MusicInfo;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Created by gaofei on 2016/12/26.
 */
public class SoarPlayService extends Service{

    private MediaPlayer mPlayer;
    public ArrayList<MusicInfo> musicPathLists;
    private MusicPlayTimeLisenter playTimeLisenter ;
    private int currentPos;

    private ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(3);

    public interface CallBack {
        void playMusicByPosition(int position);
        boolean isPlayerMusic();
        int getTotalDate();
        int getCurrentTime();
        void seekTo(int millsecond);
        void playPre();
        void playNext();
        void setPlayMode();
        boolean isPlayering();
        void setMusicList(ArrayList<MusicInfo> musicList);
        void setPlayTimeLisenter(MusicPlayTimeLisenter musicPlayTimeLisenter);
    }

    public class MyBinder extends Binder implements CallBack {



        @Override
        public boolean isPlayerMusic() {
            return toPlay();
        }

        @Override
        public int getTotalDate() {
            if (mPlayer != null) {
                return mPlayer.getDuration();
            } else {
                return 0;
            }
        }


        @Override
        public void setPlayMode() {

        }

        @Override
        public int getCurrentTime() {
            if (mPlayer != null) {
                return mPlayer.getCurrentPosition();
            } else {
                return 0;
            }
        }

        @Override
        public void seekTo(int m_second) {
            if (mPlayer != null) {
                mPlayer.seekTo(m_second);
            }
        }

        @Override
            public void playPre() {
            if (--currentPos < 0) {
                currentPos = 0;
            }
            resetMusic();
            toPlay();
        }

        @Override
        public void playNext() {
            if (++currentPos > musicPathLists.size() - 1) {
                currentPos = musicPathLists.size() - 1;
            }
            resetMusic();
            toPlay();
        }


        @Override
        public boolean isPlayering() {
            if(mPlayer.isPlaying()){
                return true;
            }else{
                return false;
            }
        }

        @Override
        public void playMusicByPosition(int position) {
            currentPos = position;
            resetMusic();
            toPlay();
        }


        @Override
        public void setMusicList(ArrayList<MusicInfo> musicList) {
            musicPathLists = musicList;
        }


        @Override
        public void setPlayTimeLisenter(MusicPlayTimeLisenter musicPlayTimeLisenter) {
            playTimeLisenter = musicPlayTimeLisenter;
        }
    }


    @Override
        public void onCreate() {
            super.onCreate();
            mPlayer = new MediaPlayer();
        }

        private void resetMusic() {
            mPlayer.reset();
            try {
                mPlayer.setDataSource(musicPathLists.get(currentPos).getPath());
                mPlayer.prepare();

                mPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mp) {
                        currentPos++;
                        if (currentPos >= musicPathLists.size()) {
                            currentPos = 0;
                        }
                        //       mp.start();
                        resetMusic();
                        toPlay();
                    }
                });
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

        public boolean toPlay() {
            if (mPlayer.isPlaying()) {
                mPlayer.pause();
                scheduledExecutorService.shutdown();
                return false;
            } else {
                mPlayer.start();
                scheduledExecutorService.scheduleAtFixedRate(new Runnable() {
                    @Override
                    public void run() {
                        playTimeLisenter.updateTimeUI(mPlayer.getCurrentPosition());
                    }
                },0 , 1 , TimeUnit.SECONDS);
                return true;
            }
        }


    @Override
        public IBinder onBind(Intent intent) {
            return new MyBinder();
        }

        @Override
        public void onDestroy() {
            super.onDestroy();
            if (mPlayer != null) {
                if (mPlayer.isPlaying()) {
                    mPlayer.stop();
                }
                mPlayer.release();
            }
        }
    }