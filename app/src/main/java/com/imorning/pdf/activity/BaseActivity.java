package com.imorning.pdf.activity;

import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.imorning.pdf.BuildConfig;

public class BaseActivity extends AppCompatActivity {
    public void LogV(Object... msg) {
        if (BuildConfig.DEBUG) {
            for (Object m : msg) {
                Log.v(getClass().getSimpleName(), m.toString());
            }
        }
    }
}