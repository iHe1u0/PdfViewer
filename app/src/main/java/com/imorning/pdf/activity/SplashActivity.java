package com.imorning.pdf.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.imorning.pdf.R;

public class SplashActivity extends BaseActivity {
    public static final String PATH = "filePath";
    private static final int PERMISSION_REQUEST_CODE = 1;
    private final String[] permissionList = new String[]{Manifest.permission.READ_EXTERNAL_STORAGE};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            checkPermission();
        } else {
            showActivity();
        }
    }

    private void showActivity() {
        if (getIntent().getDataString() != null) {
            Intent intent = new Intent(this, PdfActivity.class);
            intent.putExtra(PATH, getIntent().getData());
            startActivity(intent);
            SplashActivity.this.finish();
        }
    }

    /**
     * 检查权限
     */
    private void checkPermission() {
        int permissionCheck = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE);
        if (permissionCheck == PackageManager.PERMISSION_DENIED) {
            AlertDialog.Builder builder = new AlertDialog.Builder(SplashActivity.this);
            builder.setTitle("应用需要以下权限以正常运行");
            builder.setMessage("读取/写入到您的存储空间");
            builder.setPositiveButton("确定", (dialog, which) -> {
                ActivityCompat.requestPermissions(this, permissionList, PERMISSION_REQUEST_CODE);
            });
            builder.setNegativeButton("不同意", (dialog, which) -> {
                Toast.makeText(getApplicationContext(), "应用可能无法正常运行", Toast.LENGTH_LONG).show();
            });
            builder.setCancelable(false);
            builder.show();
        } else {
            showActivity();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == PERMISSION_REQUEST_CODE) {
            for (int permissionCode : grantResults) {
                if (grantResults.length < permissions.length || permissionCode == PackageManager.PERMISSION_DENIED) {
                    Toast.makeText(getApplicationContext(), "应用可能无法正常运行", Toast.LENGTH_LONG).show();
                }
            }
        }
        showActivity();
    }
}