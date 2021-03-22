package com.imorning.pdf.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.IntRange;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.imorning.pdf.R;
import com.imorning.pdf.bean.History;
import com.imorning.pdf.bean.RecentAdapter;
import com.imorning.pdf.bean.RecentList;

import org.litepal.LitePal;
import org.litepal.tablemanager.Connector;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SplashActivity extends BaseActivity {
    private static final int PERMISSION_REQUEST_CODE = 1;
    private final String[] permissionList = new String[]{Manifest.permission.READ_EXTERNAL_STORAGE};
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.recent_list)
    RecyclerView recyclerView;
    private List<RecentList> recentLists;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        ButterKnife.bind(this);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            checkPermission();
        }
    }
    @Override
    protected void onStart() {
        super.onStart();
        recentLists = new ArrayList<>();
        List<History> histories = LitePal.findAll(History.class);
        initList(histories);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        RecentAdapter recentAdapter = new RecentAdapter(recentLists);
        recyclerView.setAdapter(recentAdapter);
    }

    private void initList(List<History> histories) {
        for (History history : histories) {
            RecentList recentList = new RecentList(history.getFileName(), history.getFilePath(), history.getLastTime(), history.getPage());
            recentLists.add(recentList);
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
    }
}