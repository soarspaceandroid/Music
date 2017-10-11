package com.soar.music.widgets;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.soar.music.R;
import com.soar.music.activity.MusicMainActivity;
import com.soar.music.interfaces.OnfinishLisenter;
import com.soar.music.statemachine.BaseStatesMachine;
import com.soar.music.statemachine.UIUpdateInter;
import com.soar.music.utils.AnimHelper;
import com.soar.music.utils.BlurTools;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by gaofei on 2016/12/26.
 */
public class LayoutFrames extends FrameLayout {


    public ImageView backLayout;
    private FrameLayout playLayout;
    private FrameLayout settingLayout;
    private FrameLayout lyiceLayout;
    private FrameLayout listLayout;
    private FrameLayout stackFirstLayout;
    private FrameLayout stackSecondLayout;
    private Context context;

    private float distance = 0; // 手势滑动 临界点

    public final static int STATE_NONE = 0; // playing
    public final static int STATE_PLAY = 1; // playing
    public final static int STATE_SETTING = 2;
    public final static int STATE_LYICE = 3;
    public final static int STATE_LIST = 4;
    public final static int STATE_STACKFIRST = 5;
    public final static int STATE_STACKSECOND = 6;
    private BaseStatesMachine machine;


    private float downX = 0 , downY = 0;

    public LayoutFrames(Context context) {
        super(context);
        this.context = context;
        init();
    }

    public LayoutFrames(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        init();
    }

    public LayoutFrames(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        init();
    }



    private void init(){
        initView();
        initData();
    }

    private void initView(){
        View rootView = LayoutInflater.from(context).inflate(R.layout.layout_frames , this);
        backLayout = (ImageView)rootView.findViewById(R.id.back_layout);
        playLayout = (FrameLayout)rootView.findViewById(R.id.play_layout);
        settingLayout = (FrameLayout)rootView.findViewById(R.id.setting_layout);
        listLayout = (FrameLayout)rootView.findViewById(R.id.list_layout);
        lyiceLayout = (FrameLayout)rootView.findViewById(R.id.lytis_layout);
        stackFirstLayout = (FrameLayout)rootView.findViewById(R.id.stack_first);
        stackSecondLayout = (FrameLayout)rootView.findViewById(R.id.stack_second);

        playLayout.setTranslationY(getResources().getDisplayMetrics().heightPixels);
        listLayout.setTranslationY(-getResources().getDisplayMetrics().heightPixels);
        lyiceLayout.setTranslationX(getResources().getDisplayMetrics().widthPixels);
        settingLayout.setTranslationX(-getResources().getDisplayMetrics().widthPixels);
        stackFirstLayout.setTranslationX(-getResources().getDisplayMetrics().widthPixels);
        stackSecondLayout.setTranslationX(-getResources().getDisplayMetrics().widthPixels);
        backLayout.setAlpha(0f);

    }

    private void initData(){
        distance = getResources().getDisplayMetrics().widthPixels/3;
        machine = new BaseStatesMachine("layoutframes");
        List<BaseStatesMachine.BaseState> list = new ArrayList<>();
        list.add(machine.new BaseState(STATE_NONE));
        list.add(machine.new BaseState(STATE_PLAY));
        list.add(machine.new BaseState(STATE_SETTING));
        list.add(machine.new BaseState(STATE_LYICE));
        list.add(machine.new BaseState(STATE_LIST));
        list.add(machine.new BaseState(STATE_STACKFIRST));
        list.add(machine.new BaseState(STATE_STACKSECOND));
        machine.setStatesData(list);
        machine.setUIUpdateInter(new UIUpdateInter() {
            @Override
            public void updateUIByStatus(int state) {
                switch (state){
                    case STATE_LIST:

                        break;
                    case STATE_LYICE:

                        break;
                    case STATE_PLAY:
                        post(new Runnable() {
                            @Override
                            public void run() {
                                setVisibility(VISIBLE);
                                if(context != null){
                                    ((MusicMainActivity)context).updateBackAndOther();
                                }
                            }
                        });

                        AnimHelper.transY(playLayout , playLayout.getTranslationY() , 0);
                        break;
                    case STATE_SETTING:

                        break;
                    case STATE_NONE:
                        backLayout.post(new Runnable() {
                            @Override
                            public void run() {
                                hideBack();
                            }
                        });

                        break;
                }

            }
        });
        machine.start();
        machine.changeToState(STATE_NONE);
    }

    /**
     * change status
     * @param status
     */
    public  void changeStatus(int status){
        if(machine != null){
            machine.changeToState(status);
        }
    }


    public int getStatus(){
        if(machine != null){
            return machine.getLocalCurrentState();
        }
        return STATE_NONE;
    }


    public void displayBack(final View rootview){
        backLayout.post(new Runnable() {
            @Override
            public void run() {
                BlurTools.blur((MusicMainActivity) context , rootview, backLayout , new OnfinishLisenter(){
                    @Override
                    public void onFinish() {
                        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(backLayout , "alpha" , 0f , 1f);
                        objectAnimator.setDuration(AnimHelper.DURATIME);
                        objectAnimator.start();
                    }
                });
            }
        });
    }


    public void hideBack(){
        backLayout.setAlpha(0f);
        setVisibility(GONE);
    }

    /**
     *  添加播放界面
     * @param view
     */
    public void addPlayView(View view){
        playLayout.addView(view);
    }


    /**
     * 添加 list 列表
     * @param view
     */
    public void addListView(View view){
        listLayout.addView(view);
    }

    /**
     *  添加 setting 界面
     * @param view
     */
    public void addSettingView(View view){
        settingLayout.addView(view);
    }

    /**
     *  添加歌词界面
     * @param view
     */
    public void addLyiceView(View view){
        lyiceLayout.addView(view);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if(machine.getLocalCurrentState() == STATE_NONE){
            return super.onTouchEvent(event);
        }
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                downX = event.getX();
                downY = event.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                if(Math.abs(event.getX() - downX)  > Math.abs(event.getY() - downY) ){
                    // 横滑
                    if(event.getX() - downX > 0){
                        // 右滑
                        if(machine.getLocalCurrentState() == STATE_PLAY) {
                            settingLayout.setTranslationX(-getResources().getDisplayMetrics().widthPixels + event.getX() - downX);
                        }else if(machine.getLocalCurrentState() == STATE_LYICE){
                            lyiceLayout.setTranslationX(event.getX() - downX);
                        }
                    }else{
                        //左滑
                        if(machine.getLocalCurrentState() == STATE_SETTING) {
                            settingLayout.setTranslationX(event.getX() - downX);
                        }else if(machine.getLocalCurrentState() == STATE_PLAY){
                            lyiceLayout.setTranslationX(getResources().getDisplayMetrics().widthPixels + event.getX() - downX);
                        }
                    }
                }else{
                    //竖滑
                    if(event.getY() - downY > 0){
                        //下拉
                        if(machine.getLocalCurrentState() == STATE_PLAY){
                            playLayout.setTranslationY(event.getY() - downY);
                        }
                    }else{
                        // 上啦
                        if(machine.getLocalCurrentState() == STATE_NONE){
                            playLayout.setTranslationY(getResources().getDisplayMetrics().heightPixels - (event.getY() - downY));
                        }
                    }

                }

                break;
            case MotionEvent.ACTION_UP:
                if(Math.abs(event.getX() - downX)  > Math.abs(event.getY() - downY) ){
                    // 横滑
                    if(Math.abs(event.getX() - downX) > getResources().getDisplayMetrics().widthPixels/3){
                        if(machine.getLocalCurrentState() == STATE_SETTING){
                            AnimHelper.transX(settingLayout , settingLayout.getTranslationX() , -getResources().getDisplayMetrics().widthPixels);
                            changeStatus(STATE_PLAY);
                        }else if(machine.getLocalCurrentState() == STATE_LYICE){
                            AnimHelper.transX(lyiceLayout , lyiceLayout.getTranslationX() , getResources().getDisplayMetrics().widthPixels);
                            changeStatus(STATE_PLAY);
                        }else if(machine.getLocalCurrentState() == STATE_PLAY){
                            if(event.getX() - downX > 0){
                                //右滑
                                AnimHelper.transX(settingLayout , settingLayout.getTranslationX() , 0);
                                changeStatus(STATE_SETTING);
                            }else{
                                AnimHelper.transX(lyiceLayout , lyiceLayout.getTranslationX() , 0);
                                changeStatus(STATE_LYICE);
                            }
                        }
                    }else{
                        // 回归中心
                        if(machine.getLocalCurrentState() == STATE_SETTING){
                            AnimHelper.transX(settingLayout , settingLayout.getTranslationX() , 0);
                            changeStatus(STATE_SETTING);
                        }else if(machine.getLocalCurrentState() == STATE_LYICE){
                            AnimHelper.transX(lyiceLayout , lyiceLayout.getTranslationX() , 0);
                            changeStatus(STATE_LYICE);
                        }else if(machine.getLocalCurrentState() == STATE_PLAY){
                            if(event.getX() - downX > 0){
                                //右滑
                                AnimHelper.transX(settingLayout , settingLayout.getTranslationX() , -getResources().getDisplayMetrics().widthPixels);
                                changeStatus(STATE_PLAY);
                            }else{
                                AnimHelper.transX(lyiceLayout , lyiceLayout.getTranslationX() , getResources().getDisplayMetrics().widthPixels);
                                changeStatus(STATE_PLAY);
                            }
                        }
                    }

                }else{
                    if(Math.abs(event.getY() - downY) > getResources().getDisplayMetrics().heightPixels/3){
                        if(machine.getLocalCurrentState() == STATE_PLAY){
                            AnimHelper.transY(playLayout, playLayout.getTranslationY(), getResources().getDisplayMetrics().heightPixels, new OnfinishLisenter() {
                                @Override
                                public void onFinish() {
                                    backLayout.setAlpha(0f);
                                    setVisibility(GONE);
                                }
                            });
                            changeStatus(STATE_NONE);
                        }
                    }else{
                        if(machine.getLocalCurrentState() == STATE_PLAY){
                            AnimHelper.transY(playLayout , playLayout.getTranslationY() , 0);
                            changeStatus(STATE_PLAY);
                        }

                    }

                }

                break;
            case MotionEvent.ACTION_CANCEL:

                break;

        }
        return true;

    }
}
