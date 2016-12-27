package com.soar.music.widgets;

import android.content.Context;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.soar.music.R;
import com.soar.music.model.MusicInfo;
import com.soar.music.services.SoarPlayService;
import com.soar.music.utils.DateUtils;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by gaofei on 2016/12/27.
 */
public class PlayLayout extends LinearLayout {

    private final static int UPDATEUI = 1000;

    private Context context;
    private ImageView ablum ;
    private SeekBar seekBar;
    private TextView lengthText;
    private TextView playedText ;
    private SoarPlayService.CallBack callBack;
    private MusicInfo musicInfo;


    private int countLen = 0;
    private Handler handler = new Handler(){

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case UPDATEUI:
                    playedText.setText(DateUtils.getLength(countLen));
                    seekBar.setProgress((int)(((float)countLen/musicInfo.getLength())*100));
                    break;
            }
        }
    };


    private Timer timer = new Timer();
    private TimerTask timerTask = new TimerTask() {
        @Override
        public void run() {
            countLen = countLen + 1000;
            handler.sendEmptyMessage(UPDATEUI);
        }
    };


    public PlayLayout(Context context) {
        super(context);
        this.context = context;
        initView();
    }

    public PlayLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        initView();
    }

    public PlayLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        initView();
    }
    public void setCallBack(SoarPlayService.CallBack callBack){
        this.callBack = callBack;
    }

    private void initView(){
        LayoutInflater layoutInflater  = LayoutInflater.from(context);
        View playView = layoutInflater.inflate(R.layout.layout_play , this);
        ablum = (ImageView)playView.findViewById(R.id.album_image);
        seekBar = (SeekBar) playView.findViewById(R.id.seekbar);
        lengthText = (TextView) playView.findViewById(R.id.music_length);
        playedText = (TextView) playView.findViewById(R.id.music_played);
        seekBar.setMax(100);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                float scale = seekBar.getProgress()/100;
                callBack.iSeekTo((int)(scale * musicInfo.getLength() / 1000));
            }
        });
    }


    public void updateUI(final MusicInfo musicInfo){
        this.musicInfo = musicInfo;
        if(!TextUtils.isEmpty(musicInfo.getAlbumImage())) {
            ablum.setImageURI(Uri.parse(musicInfo.getAlbumImage()));
        }
        lengthText.setText(DateUtils.getLength(musicInfo.getLength()));
        countLen = 0;
        timer.schedule(timerTask, 0, 1000);

    }

}
