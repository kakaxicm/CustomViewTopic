package com.qicode.kakaxicm.customviewtopic.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import com.qicode.kakaxicm.customviewtopic.CustomWidget.FlowLayout;
import com.qicode.kakaxicm.customviewtopic.R;
import com.qicode.kakaxicm.customviewtopic.SizeUtils.SizeUtils;

/**
 * Created by chenming on 2018/5/2
 */
public class FlowLayoutActivity extends Activity {

    private String[] MALE_NAMES = {"Adam", "Alan","Brian", "Edward", "Alex", "Steven","Tom","Wesley", "Sam", "Robinson", "Robert", "Philip", "Larry", "Kevin"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flowlayout);

        FlowLayout fl = findViewById(R.id.fl);
        for (int i = 0; i < MALE_NAMES.length; i++) {
            String name = MALE_NAMES[i];

            TextView maleNameTv = createTextView(name);
            maleNameTv.setTag(i);
            fl.addView(maleNameTv);
        }

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
