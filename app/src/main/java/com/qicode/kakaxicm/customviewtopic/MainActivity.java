package com.qicode.kakaxicm.customviewtopic;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.qicode.kakaxicm.customviewtopic.activity.ViewMeasureActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    /*
     View的Measure原理
     */
    public void gotoMeasure(View v){
        Intent i = new Intent(this, ViewMeasureActivity.class);
        startActivity(i);
    }
}
