package com.qicode.kakaxicm.customviewtopic;

import android.content.Intent;
import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import com.qicode.kakaxicm.customviewtopic.CustomWidget.FlowLayout;
import com.qicode.kakaxicm.customviewtopic.SizeUtils.SizeUtils;
import com.qicode.kakaxicm.customviewtopic.activity.FlowLayoutActivity;
import com.qicode.kakaxicm.customviewtopic.activity.ViewMeasureActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    /*
     * View的Measure原理
     */
    public void gotoMeasure(View v){
        Intent i = new Intent(this, ViewMeasureActivity.class);
        startActivity(i);

    }

    /*
     * FlowLayout
     */
    public void gotoFlowLayout(View v){
        Intent i = new Intent(this, FlowLayoutActivity.class);
        startActivity(i);

    }


    /**
     * 动态生成TextView
     */
    private TextView createTextView(String text) {
        TextView nameView = new TextView(this);
        int topGap = (int) SizeUtils.dp2Px(getResources(), 6);
        int leftGap = (int) SizeUtils.dp2Px(getResources(), 14);

        nameView.setPadding(leftGap, topGap, leftGap, topGap);
        nameView.setTextColor(Color.WHITE);
        nameView.setTextSize(12);
        nameView.setText(text);
        return nameView;
    }
}
