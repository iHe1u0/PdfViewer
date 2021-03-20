package com.imorning.pdf.activity;

import android.os.Bundle;

import com.imorning.pdf.R;

public class SplashActivity extends BaseActivity {
    private final String[] permissionList = {};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        checkPermission();
    }

    /**
     * 检查权限
     */
    private void checkPermission() {
    }
}