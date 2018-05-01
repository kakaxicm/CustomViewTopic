package com.qicode.kakaxicm.customviewtopic.MeasureView;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by chenming on 2018/4/26
 */
public class ChildView extends View {
    public ChildView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.e("TAG", "ChildView:" + event.getAction());
        return false;
    }

    /**
     * onMesaure流程代码
     */
    private int mDefaultWidth = 200;
    private int mDefaultHeight = 400;
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        //Step1:拿到父View期望的大小
        int resultWidth = 0;
        int resultHeight = 0;
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        //先赋值
        resultWidth = widthSize;
        resultHeight = heightSize;
        //Step2:wrap_content处理
        if (getLayoutParams().width == ViewGroup.LayoutParams.WRAP_CONTENT) {
            //在这里实现计算需要wrap_content时需要的宽
            resultWidth = mDefaultWidth;
        }
        if (getLayoutParams().height == ViewGroup.LayoutParams.WRAP_CONTENT) {
            //在这里实现计算需要wrap_content时需要的高
            resultHeight = mDefaultHeight;
        }
        //step3:自己定义View的逻辑，如宽高比，大小限制等等
        resultHeight = resultWidth;
        //step4:设置测量结果
        setMeasuredDimension(resultWidth, resultHeight);
    }
}
