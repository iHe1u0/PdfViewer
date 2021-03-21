package com.imorning.pdf.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import com.github.barteksc.pdfviewer.PDFView;
import com.imorning.pdf.R;
import com.imorning.pdf.bean.History;
import com.imorning.pdf.utils.NormalFileUtils;

import org.litepal.LitePal;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PdfActivity extends BaseActivity {
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.main_pdfviewer)
    PDFView pdfView;

    private String filePath;
    private Uri uriPath;
    private History history;
    private int page = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pdf);
        ButterKnife.bind(this);
        uriPath = getIntent().getData();
        filePath = uriPath.getPath();
        if (filePath == null)
            finish();
        grantUriPermission(getPackageName(), uriPath, Intent.FLAG_GRANT_READ_URI_PERMISSION);//解决夜间模式切换时的数据丢失
        history = new History();
        history.setFileName(NormalFileUtils.getFileName(filePath));
        history.setFilePath(filePath);
    }

    @Override
    protected void onStart() {
        super.onStart();
        List<History> queryList = LitePal.where("filepath like ?", filePath + "%").order("id").find(History.class);
        for (History r : queryList) {
            if (r.getFilePath().equals(filePath)) {
                page = r.getPage();
                history = r;
                break;
            }
        }
        pdfView.fromUri(uriPath)
                .defaultPage(page)
                .load();
    }

    @Override
    protected void onPause() {
        super.onPause();
        save();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        save();
    }

    /**
     * 保存当前的页码
     */
    private void save() {
        history.setPage(pdfView.getCurrentPage());
        history.setLastTime(System.currentTimeMillis());
        history.save();
    }

}