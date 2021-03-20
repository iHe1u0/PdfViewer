package com.imorning.pdf.activity;

import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.imorning.pdf.BuildConfig;

public abstract class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public void LogV(Object... msg) {
        if (BuildConfig.DEBUG) {
            StringBuffer logMsg = new StringBuffer();
            for (Object m : msg) {
                logMsg.append(m.toString());
            }
            Log.v("In Class:" + getClass().getSimpleName(), logMsg.toString());
        }
    }
}