package com.imorning.pdf.activity;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.imorning.pdf.R;
import com.imorning.pdf.bean.History;
import com.imorning.pdf.bean.RecentAdapter;
import com.imorning.pdf.bean.RecentList;

import org.litepal.LitePal;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SplashActivity extends BaseActivity {
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.recent_list)
    RecyclerView recyclerView;

    private List<RecentList> recentLists;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        ButterKnife.bind(this);
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

}