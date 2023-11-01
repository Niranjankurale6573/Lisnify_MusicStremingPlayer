package com.musicstremingapplication.utils;

import android.app.Activity;

import androidx.viewpager.widget.ViewPager;

import java.util.Timer;
import java.util.TimerTask;

public class SliderTiming extends TimerTask {
    private Activity activity;
    private ViewPager slider;
    private int size;
    public SliderTiming(Activity activity, ViewPager slider,int size) {
        this.activity = activity;
        this.slider = slider;
        this.size = size-1;
    }

    @Override
    public void run() {
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if(slider.getCurrentItem()<size){
                    slider.setCurrentItem(slider.getCurrentItem()+1);
                }
                else{
                    slider.setCurrentItem(0);
                }
            }
        });

    }
}
