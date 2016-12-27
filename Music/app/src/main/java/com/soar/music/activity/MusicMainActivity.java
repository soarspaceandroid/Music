package com.soar.music.activity;

import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.soar.music.R;
import com.soar.music.adapters.MusicListAdapter;
import com.soar.music.helpers.MusicMainHelper;
import com.soar.music.interfaces.MusicMainInter;
import com.soar.music.interfaces.MusicScanLisenter;
import com.soar.music.interfaces.OnItemClickLisenter;
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


    private ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            callBack = (SoarPlayService.MyBinder)service;
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

    private void initView(){
        layoutFrames = (LayoutFrames)findViewById(R.id.layout_frames);
        initLayoutFrames();
        editText = (EditText)findViewById(R.id.edit_path);
        button = (Button) findViewById(R.id.to_choice_path);
        scanButton = (Button)findViewById(R.id.scan_button);
        listView = (ListView)findViewById(R.id.music_list);
        root = (LinearLayout)findViewById(R.id.main_root);

        musicListAdapter = new MusicListAdapter(this, new OnItemClickLisenter() {

            private float x , y ;
            @Override
            public void onItemClick(View view, int position , MotionEvent event) {
                switch (event.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        x = event.getX();
                        y = event.getY();

                        break;
                    case MotionEvent.ACTION_UP:
                        if(Math.abs(event.getX() - x) < 10 && Math.abs(event.getY() - y) < 10){
                            Intent intent = new Intent(MusicMainActivity.this, SoarPlayService.class);
                            intent.putParcelableArrayListExtra("MUSIC_LIST", musicList);
                            intent.putExtra("CURRENT_POSITION", position);
                            startService(intent);
                            bindService(intent, serviceConnection, Service.BIND_AUTO_CREATE);
                            mCurrentMusicInfo = musicList.get(position);
                            layoutFrames.changeStatus(LayoutFrames.STATE_PLAY);
                            musicMainHelper.blueMainUI(MusicMainActivity.this , root , layoutFrames);
                            playLayout.updateUI(mCurrentMusicInfo);
                        }
                        break;
                }

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
        playLayout.setCallBack(callBack);

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



    private void initData(){
        dataManager = musicMainHelper.initDataManager();
        AllMusic allMusic = dataManager.getObject(Keys.MUSIC_INFO , AllMusic.class);
        if(allMusic != null && allMusic.getList() != null && allMusic.getList().size()!= 0){
            musicList = allMusic.getList();
            musicListAdapter.setListData(allMusic.getList());
        }
    }

}
