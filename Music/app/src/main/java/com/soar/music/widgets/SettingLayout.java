package com.soar.music.widgets;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.soar.music.R;
import com.soar.music.adapters.ViewpagerAdapter;
import com.soar.music.model.EqualizerStyleValues;
import com.soar.music.utils.RandowColors;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by gaofei on 2016/12/27.
 */
public class SettingLayout extends ParentLinearLayout {

    private Context context;


    private EqualizerProgressBar progressBar1;
    private EqualizerProgressBar progressBar2;
    private EqualizerProgressBar progressBar3;
    private EqualizerProgressBar progressBar4;
    private EqualizerProgressBar progressBar5;
    private EqualizerProgressBar progressBar6;
    private EqualizerProgressBar progressBar7;
    private EqualizerProgressBar progressBar8;
    private EqualizerProgressBar progressBar9;
    private EqualizerProgressBar progressBar10;

    private LineView lineView;

    private ViewPager viewPager;
    private LinearLayout viewpagerParent;

    private Map<String , Float> datas;

    private List<String> styls = new ArrayList<String>(){
        {
            add("关闭");
            add("自定义");
            add("流行");
            add("舞曲");
            add("蓝调");
            add("古典");
            add("爵士");
            add("慢歌");
            add("电子乐");
            add("摇滚");
            add("乡村");
        }
    };



    public SettingLayout(Context context) {
        super(context);
        this.context = context;
        initView();
    }

    public SettingLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        initView();
    }

    public SettingLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        initView();
    }

    private void initView(){
        LayoutInflater layoutInflater  = LayoutInflater.from(context);
        View playView = layoutInflater.inflate(R.layout.layout_setting , this);

        initPagerTab(playView);

        initLineViews(playView);

        initProgressBars(playView);

        initData();
    }


    private void initPagerTab(View rView){
        viewPager = (ViewPager) rView.findViewById(R.id.viewpager);
        viewpagerParent = (LinearLayout)rView.findViewById(R.id.viewpager_parent);
        viewpagerParent.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return viewPager.onTouchEvent(event);
            }
        });
    }

    private void initProgressBars(View rView){
        progressBar1 = (EqualizerProgressBar)rView.findViewById(R.id.progressbar1);
        progressBar2 = (EqualizerProgressBar)rView.findViewById(R.id.progressbar2);
        progressBar3 = (EqualizerProgressBar)rView.findViewById(R.id.progressbar3);
        progressBar4 = (EqualizerProgressBar)rView.findViewById(R.id.progressbar4);
        progressBar5 = (EqualizerProgressBar)rView.findViewById(R.id.progressbar5);
        progressBar6 = (EqualizerProgressBar)rView.findViewById(R.id.progressbar6);
        progressBar7 = (EqualizerProgressBar)rView.findViewById(R.id.progressbar7);
        progressBar8 = (EqualizerProgressBar)rView.findViewById(R.id.progressbar8);
        progressBar9 = (EqualizerProgressBar)rView.findViewById(R.id.progressbar9);
        progressBar10 = (EqualizerProgressBar)rView.findViewById(R.id.progressbar10);

        progressBar1.setOnEqualizerProgressBarListener(new EqualizerProgressBar.OnEqualizerProgressBarListener() {
            @Override
            public void onVlaueChanged(float value) {
                datas.put(EqualizerStyleValues.KEY_27 , value);
                lineView.setData(datas);
            }
        });

        progressBar2.setOnEqualizerProgressBarListener(new EqualizerProgressBar.OnEqualizerProgressBarListener() {
            @Override
            public void onVlaueChanged(float value) {
                datas.put(EqualizerStyleValues.KEY_55 , value);
                lineView.setData(datas);
            }
        });

        progressBar3.setOnEqualizerProgressBarListener(new EqualizerProgressBar.OnEqualizerProgressBarListener() {
            @Override
            public void onVlaueChanged(float value) {
                datas.put(EqualizerStyleValues.KEY_109 , value);
                lineView.setData(datas);
            }
        });

        progressBar4.setOnEqualizerProgressBarListener(new EqualizerProgressBar.OnEqualizerProgressBarListener() {
            @Override
            public void onVlaueChanged(float value) {
                datas.put(EqualizerStyleValues.KEY_219 , value);
                lineView.setData(datas);
            }
        });

        progressBar5.setOnEqualizerProgressBarListener(new EqualizerProgressBar.OnEqualizerProgressBarListener() {
            @Override
            public void onVlaueChanged(float value) {
                datas.put(EqualizerStyleValues.KEY_438 , value);
                lineView.setData(datas);
            }
        });

        progressBar6.setOnEqualizerProgressBarListener(new EqualizerProgressBar.OnEqualizerProgressBarListener() {
            @Override
            public void onVlaueChanged(float value) {
                datas.put(EqualizerStyleValues.KEY_875 , value);
                lineView.setData(datas);
            }
        });

        progressBar7.setOnEqualizerProgressBarListener(new EqualizerProgressBar.OnEqualizerProgressBarListener() {
            @Override
            public void onVlaueChanged(float value) {
                datas.put(EqualizerStyleValues.KEY_2k , value);
                lineView.setData(datas);
            }
        });

        progressBar8.setOnEqualizerProgressBarListener(new EqualizerProgressBar.OnEqualizerProgressBarListener() {
            @Override
            public void onVlaueChanged(float value) {
                datas.put(EqualizerStyleValues.KEY_4k , value);
                lineView.setData(datas);
            }
        });

        progressBar9.setOnEqualizerProgressBarListener(new EqualizerProgressBar.OnEqualizerProgressBarListener() {
            @Override
            public void onVlaueChanged(float value) {
                datas.put(EqualizerStyleValues.KEY_7k , value);
                lineView.setData(datas);
            }
        });

        progressBar10.setOnEqualizerProgressBarListener(new EqualizerProgressBar.OnEqualizerProgressBarListener() {
            @Override
            public void onVlaueChanged(float value) {
                datas.put(EqualizerStyleValues.KEY_14k , value);
                lineView.setData(datas);
            }
        });


    }


    private void initLineViews(View rView){
        lineView = (LineView)rView.findViewById(R.id.lineview);
    }


    private void initData(){
        datas = new HashMap<>();
        datas.put(EqualizerStyleValues.KEY_27 , progressBar1.getProcessValue());
        datas.put(EqualizerStyleValues.KEY_55 , progressBar2.getProcessValue());
        datas.put(EqualizerStyleValues.KEY_109 , progressBar3.getProcessValue());
        datas.put(EqualizerStyleValues.KEY_219 , progressBar4.getProcessValue());
        datas.put(EqualizerStyleValues.KEY_438 , progressBar5.getProcessValue());
        datas.put(EqualizerStyleValues.KEY_875 , progressBar6.getProcessValue());
        datas.put(EqualizerStyleValues.KEY_2k , progressBar7.getProcessValue());
        datas.put(EqualizerStyleValues.KEY_4k , progressBar8.getProcessValue());
        datas.put(EqualizerStyleValues.KEY_7k , progressBar9.getProcessValue());
        datas.put(EqualizerStyleValues.KEY_14k , progressBar10.getProcessValue());
        lineView.setData(datas);

        List<TextView> listViews = new ArrayList<>();
        for (int x = 0 ; x < styls.size() ; x ++){
            TextView textView = new TextView(context);
            textView.setBackgroundColor(Color.parseColor("#"+ RandowColors.getRandColorCode()));
            textView.setGravity(Gravity.CENTER);
            textView.setText(styls.get(x));
            listViews.add(textView);
        }
        viewPager.setOffscreenPageLimit(6);
        viewPager.setPageMargin(getResources().getDimensionPixelSize(R.dimen.view_page_margin));

        viewPager.setAdapter(new ViewpagerAdapter(listViews));

        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                Map<String , Float> value = null;
                switch (position){
                    case 0:
                        value = EqualizerStyleValues.getCloseStyleValue();
                        break;
                    case 1:
                        value = EqualizerStyleValues.getCustomStyleValue();
                        break;
                    case 2:
                        value = EqualizerStyleValues.getPupoStyleValue();
                        break;
                    case 3:
                        value = EqualizerStyleValues.getDanceStyleValue();
                        break;
                    case 4:
                        value = EqualizerStyleValues.getLayerStyleValue();
                        break;
                    case 5:
                        value = EqualizerStyleValues.getBlueStyleValue();
                        break;
                    case 6:
                        value = EqualizerStyleValues.getSirStyleValue();
                        break;
                    case 7:
                        value = EqualizerStyleValues.getSlowStyleValue();
                        break;
                    case 8:
                        value = EqualizerStyleValues.getElemusicStyleValue();
                        break;
                    case 9:
                        value = EqualizerStyleValues.getRockStyleValue();
                        break;
                    case 10:
                        value = EqualizerStyleValues.getContriyStyleValue();
                        break;

                }

                if(value == null){
                    return;
                }
                // set value
                progressBar1.setProcessValue(value.get(EqualizerStyleValues.KEY_27));
                progressBar2.setProcessValue(value.get(EqualizerStyleValues.KEY_55));
                progressBar3.setProcessValue(value.get(EqualizerStyleValues.KEY_109));
                progressBar4.setProcessValue(value.get(EqualizerStyleValues.KEY_219));
                progressBar5.setProcessValue(value.get(EqualizerStyleValues.KEY_438));
                progressBar6.setProcessValue(value.get(EqualizerStyleValues.KEY_875));
                progressBar7.setProcessValue(value.get(EqualizerStyleValues.KEY_2k));
                progressBar8.setProcessValue(value.get(EqualizerStyleValues.KEY_4k));
                progressBar9.setProcessValue(value.get(EqualizerStyleValues.KEY_7k));
                progressBar10.setProcessValue(value.get(EqualizerStyleValues.KEY_14k));

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        Log.e("soar" , "settinglayot   onInterceptTouchEvent");
        return false;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.e("soar" , "settinglayot   onTouchEvent");
        return super.onTouchEvent(event);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        Log.e("soar" , "settinglayot   dispatchTouchEvent");
        return super.dispatchTouchEvent(ev);

    }
}
