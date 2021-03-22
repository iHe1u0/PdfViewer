package com.imorning.pdf.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import com.github.barteksc.pdfviewer.PDFView;
import com.imorning.pdf.R;
import com.imorning.pdf.bean.History;
import com.imorning.pdf.utils.NormalFileUtils;
import com.imorning.pdf.utils.Type;
import com.imorning.pdf.utils.UriUtils;

import org.litepal.LitePal;

import java.io.File;
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
    private File pdfFile = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pdf);
        ButterKnife.bind(this);
        if (getIntent().getStringExtra(Type.OPEN_PDF_IN_LIST) != null) {
            uriPath = Uri.parse(getIntent().getStringExtra(Type.OPEN_PDF_IN_LIST));
            filePath = UriUtils.getRealPathFromUri(PdfActivity.this, uriPath);
        } else {
            uriPath = getIntent().getData();
            filePath = uriPath.getPath();
        }
        pdfFile = new File(filePath);
        LogV("filePath:", filePath, "---uriPath:", uriPath, "---exist:", pdfFile.exists());
        if (filePath == null)
            finish();
        grantUriPermission(getPackageName(), uriPath, Intent.FLAG_GRANT_READ_URI_PERMISSION);//解决夜间模式切换时的数据丢失
    }

    @Override
    protected void onStart() {
        super.onStart();
        List<History> queryList = LitePal.findAll(History.class);
        for (History r : queryList) {
            if (r.getFilePath() == null)
                continue;
            if (r.getFilePath().equals(filePath)) {
                page = r.getPage();
                history = r;
                break;
            }
        }
        if (history == null) {
            history = new History();
            history.setFileName(NormalFileUtils.getFileName(filePath));
            history.setFilePath(uriPath.getPath());
        }
        pdfView.useBestQuality(true);
        if (pdfFile == null) {
            pdfView.fromUri(uriPath).load();
        } else {
            pdfView.fromFile(pdfFile).load();
        }
        pdfView.jumpTo(page);
        pdfView.loadPages();
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
        pdfView.recycle();
    }

    /**
     * 保存当前的页码
     */
    private void save() {
        if (pdfView != null && filePath != null && uriPath != null) {
            history.setPage(pdfView.getCurrentPage());
            history.setLastTime(System.currentTimeMillis());
            history.save();
        }
    }

}