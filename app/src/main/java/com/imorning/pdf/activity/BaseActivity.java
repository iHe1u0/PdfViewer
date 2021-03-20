package com.imorning.pdf.activity;

import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.imorning.pdf.BuildConfig;

public class BaseActivity extends AppCompatActivity {
    public void LogV(Object... msg) {
        if (BuildConfig.DEBUG) {
            StringBuffer logMsg=new StringBuffer();
            for (Object m : msg) {
                logMsg.append(m.toString());
            }
            Log.v("In Class:" + getClass().getSimpleName(), logMsg.toString());
        }
    }
}