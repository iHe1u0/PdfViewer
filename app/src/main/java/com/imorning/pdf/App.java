package com.imorning.pdf;

import android.app.Application;

import org.litepal.LitePal;
import org.litepal.LitePalDB;

public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        LitePal.initialize(App.this);
    }
}
