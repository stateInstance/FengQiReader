package com.omg.ireader.widget;

import android.content.Context;
import android.support.annotation.AttrRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;

import com.omg.ireader.utils.ToastUtils;
import com.omg.ireader.widget.view.menu.AllAngleExpandableButton;

/**
 * Created by yhl on 2017/11/14.
 */

public class WebSearchOnTouchView extends FrameLayout {

    private long time;
    private float startX;
    private float startY;

    public WebSearchOnTouchView(@NonNull Context context) {
        this(context,null);
    }

    public WebSearchOnTouchView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public WebSearchOnTouchView(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
    AllAngleExpandableButton mWebButtonExpandable;

    public void setAllAngleExpandableButton(AllAngleExpandableButton mWebButtonExpandable){
        this.mWebButtonExpandable=mWebButtonExpandable;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        switch (ev.getAction()){
            case MotionEvent.ACTION_DOWN:
                startX = ev.getRawX();
                startY = ev.getRawY();
                if (System.currentTimeMillis()- time <300){
                    mWebButtonExpandable.setVisibility(VISIBLE);
                    mWebButtonExpandable.postDelayed(mRunnable,2000);
                }
                time = System.currentTimeMillis();
              //  ToastUtils.show("你已经按下了这个这个view");
                break;
        }
        return super.onInterceptTouchEvent(ev);
    }

    Runnable mRunnable=new Runnable() {
        @Override
        public void run() {
            mWebButtonExpandable.setVisibility(View.INVISIBLE);
        }
    };

    @Override
    protected void onDetachedFromWindow() {
        if (mWebButtonExpandable!=null){
            mWebButtonExpandable.removeCallbacks(mRunnable);
        }
        super.onDetachedFromWindow();
    }
}
