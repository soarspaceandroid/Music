package com.soar.music.widgets;

import android.content.Context;
import android.net.Uri;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.soar.music.R;
import com.soar.music.model.MusicInfo;
import com.soar.music.services.SoarPlayService;
import com.soar.music.utils.DateUtils;

/**
 * Created by gaofei on 2016/12/27.
 */
public class PlayLayout extends ParentLinearLayout {

    public final static int UPDATEUI = 1000;

    private Context context;
    private ImageView ablum ;
    private SeekBar seekBar;
    private TextView lengthText;
    private TextView playedText ;
    private SoarPlayService.CallBack callBack;
    private MusicInfo musicInfo;


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
                float scale = (float)seekBar.getProgress()/100;
                playedText.setText(DateUtils.getLength((int)(scale * musicInfo.getLength())));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                float scale = (float)seekBar.getProgress()/100;
                callBack.seekTo((int)(scale * musicInfo.getLength()));
                playedText.setText(DateUtils.getLength((int)(scale * musicInfo.getLength())));
            }
        });
    }


    public void updateUI(final MusicInfo musicInfo){
        this.musicInfo = musicInfo;
        if(!TextUtils.isEmpty(musicInfo.getAlbumImage())) {
            ablum.setImageURI(Uri.parse(musicInfo.getAlbumImage()));
        }
        lengthText.setText(DateUtils.getLength(musicInfo.getLength()));
    }




    public void updateTime(final long playedMill){
        if(musicInfo != null) {
            post(new Runnable() {
                @Override
                public void run() {
                    playedText.setText(DateUtils.getLength(playedMill));
                    seekBar.setProgress((int) ((float) (playedMill) / musicInfo.getLength() * 100));
                }
            });

        }
    }


}
