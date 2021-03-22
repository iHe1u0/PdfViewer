package com.imorning.pdf.activity;

import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.imorning.pdf.BuildConfig;

import org.litepal.tablemanager.Connector;

public abstract class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Init database
        Connector.getDatabase();
    }

    public void LogV(Object... msg) {
        if (BuildConfig.DEBUG) {
            StringBuilder logMsg = new StringBuilder();
            for (Object m : msg) {
                if (m == null)
                    m = "NULL";
                logMsg.append(m.toString());
            }
            Log.v("In Class:" + getClass().getSimpleName(), logMsg.toString());
        }
    }
}