package com.testdemo.chanian.progressviewdemo;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ProgressView progressView= (ProgressView) findViewById(R.id.ll_progress);
        progressView.setIv(R.mipmap.ic_uninstall);
        progressView.setTv("呵呵");
        progressView.setProgress(66);
        progressView.setBackgroundDrawable(new ColorDrawable());
    }
}
