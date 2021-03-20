package com.imorning.pdf.activity;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.imorning.pdf.R;

public class SplashActivity extends BaseActivity {
    private static final int PERMISSION_REQUEST_CODE = 1;
    private final String[] permissionList = new String[]{Manifest.permission.READ_EXTERNAL_STORAGE};

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
        int permissionCheck = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE);
        if (permissionCheck == PackageManager.PERMISSION_DENIED) {
            AlertDialog.Builder builder = new AlertDialog.Builder(getApplicationContext());
            builder.setTitle("应用需要以下权限以正常运行");
            builder.setMessage("读取/写入到您的存储空间");
            builder.setPositiveButton("确定", (dialog, which) -> {
                ActivityCompat.requestPermissions(this, permissionList, PERMISSION_REQUEST_CODE);
            });
            builder.setNegativeButton("不同意", (dialog, which) -> {
                Toast.makeText(getApplicationContext(), "应用可能无法正常运行", Toast.LENGTH_LONG).show();
            });
            builder.create().show();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        for (String permission : permissions)
            LogV(permissions);
        switch (requestCode) {
            case PERMISSION_REQUEST_CODE:
                if (grantResults[0] == PackageManager.PERMISSION_DENIED) {
                    Toast.makeText(getApplicationContext(), "应用可能无法正常运行", Toast.LENGTH_LONG).show();
                }
                break;
        }
    }
}