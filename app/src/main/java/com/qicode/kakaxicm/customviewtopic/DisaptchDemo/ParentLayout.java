package com.qicode.kakaxicm.customviewtopic.DisaptchDemo;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.LinearLayout;

/**
 * Created by chenming on 2018/4/26
 */
public class ParentLayout extends LinearLayout {
    public ParentLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.e("TAG", "ParentLayout:onTouchEvent"+event.getAction());
        return super.onTouchEvent(event);
    }
}
