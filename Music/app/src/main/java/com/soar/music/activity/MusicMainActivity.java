package com.soar.music.activity;

import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.soar.music.R;
import com.soar.music.adapters.MusicListAdapter;
import com.soar.music.helpers.MusicMainHelper;
import com.soar.music.interfaces.ItemClickLisenter;
import com.soar.music.interfaces.MusicMainInter;
import com.soar.music.interfaces.MusicPlayTimeLisenter;
import com.soar.music.interfaces.MusicScanLisenter;
import com.soar.music.model.AllMusic;
import com.soar.music.model.MusicInfo;
import com.soar.music.savemanager.DataManager;
import com.soar.music.services.SoarPlayService;
import com.soar.music.utils.Keys;
import com.soar.music.widgets.LayoutFrames;
import com.soar.music.widgets.PlayLayout;
import com.soar.music.widgets.SettingLayout;

import java.util.ArrayList;

public class MusicMainActivity extends AppCompatActivity {

    private final static int PLAY_TOTAL_TIME = 360*1000 ;// 6分钟
    private LayoutFrames layoutFrames;
    private final MusicMainInter musicMainHelper = new MusicMainHelper();

    private DataManager dataManager = null;

    private Button button;
    private Button scanButton;
    private EditText editText;
    private ListView listView;
    private LinearLayout root;

    private MusicListAdapter musicListAdapter;
    private SoarPlayService.CallBack callBack;
    private ArrayList<MusicInfo> musicList;
    private MusicInfo mCurrentMusicInfo;

    private PlayLayout playLayout;
    private SettingLayout settingLayout;


    private MusicPlayTimeLisenter musicPlayTimeLisenter = new MusicPlayTimeLisenter() {
        @Override
        public void updateTimeUI(long mill) {
            Log.e("soar" , "test ---");
            playLayout.updateTime(mill);
        }
    };


    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
        }
    };


    private ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {

            Log.e("soar" , "connect");
            callBack = (SoarPlayService.MyBinder)service;
            callBack.setMusicList(musicList);
            callBack.setPlayTimeLisenter(musicPlayTimeLisenter);
            playLayout.setCallBack(callBack);
        }
        @Override

        public void onServiceDisconnected(ComponentName name) {
            callBack = null;
        }
    };




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music_main);
        initView();
        initData();
    }

    @Override
    protected void onStart() {
        super.onStart();

    }

    private void initView(){
        layoutFrames = (LayoutFrames)findViewById(R.id.layout_frames);
        initLayoutFrames();
        editText = (EditText)findViewById(R.id.edit_path);
        button = (Button) findViewById(R.id.to_choice_path);
        scanButton = (Button)findViewById(R.id.scan_button);
        listView = (ListView)findViewById(R.id.music_list);
        root = (LinearLayout)findViewById(R.id.main_root);


        musicListAdapter = new MusicListAdapter(this, new ItemClickLisenter() {
            @Override
            public void clickCallBack(int position) {
                callBack.playMusicByPosition(position);
                mCurrentMusicInfo = musicList.get(position);
                layoutFrames.changeStatus(LayoutFrames.STATE_PLAY);
                musicMainHelper.blueMainUI(MusicMainActivity.this , root , layoutFrames);
                playLayout.updateUI(mCurrentMusicInfo);
            }
        });
        listView.setAdapter(musicListAdapter);
        scanButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                musicMainHelper.scanMusic(MusicMainActivity.this, dataManager, editText.getText().toString(), new MusicScanLisenter() {
                    @Override
                    public void onFinish(ArrayList<MusicInfo>  list) {
                        musicListAdapter.setListData(list);
                        musicList = list;
                        Intent intent = new Intent(MusicMainActivity.this, SoarPlayService.class);
                        bindService(intent, serviceConnection, Service.BIND_AUTO_CREATE);

                    }
                });
            }
        });
    }


    private void initLayoutFrames(){
        LayoutInflater layoutInflater  = LayoutInflater.from(this);
        // 加载子布局
        View listView = layoutInflater.inflate(R.layout.layout_list , null);
        initListView(listView);

        playLayout = new PlayLayout(this);

        View lyiceView = layoutInflater.inflate(R.layout.layout_lyice, null);
        initLyiceView(lyiceView);

        settingLayout = new SettingLayout(this);

        layoutFrames.addListView(listView);
        layoutFrames.addLyiceView(lyiceView);
        layoutFrames.addSettingView(settingLayout);
        layoutFrames.addPlayView(playLayout);
    }



    private void initListView(View rView){

    }


    private void initLyiceView(View rView){

    }



    private void initData() {
        dataManager = musicMainHelper.initDataManager();
        AllMusic allMusic = dataManager.getObject(Keys.MUSIC_INFO, AllMusic.class);
        if (allMusic != null && allMusic.getList() != null && allMusic.getList().size() != 0) {
            musicList = allMusic.getList();
            musicListAdapter.setListData(allMusic.getList());
            Intent intent = new Intent(MusicMainActivity.this, SoarPlayService.class);
            bindService(intent, serviceConnection, Service.BIND_AUTO_CREATE);
        }


    }



}
