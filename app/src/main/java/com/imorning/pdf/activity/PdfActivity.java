package com.imorning.pdf.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import com.github.barteksc.pdfviewer.PDFView;
import com.imorning.pdf.R;
import com.imorning.pdf.bean.History;
import com.imorning.pdf.utils.NormalFileUtils;
import com.imorning.pdf.utils.PdfFileUtils;
import com.imorning.pdf.utils.Type;

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
    private History history;
    private int page = 0;
    private File pdfFile = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pdf);
        ButterKnife.bind(this);
        if (getIntent().getStringExtra(Type.OPEN_PDF_IN_LIST) != null) {
            filePath = getIntent().getStringExtra(Type.OPEN_PDF_IN_LIST);
        } else {
            filePath = PdfFileUtils.getFileAbsolutePath(this, getIntent().getData());
        }
        pdfFile = new File(filePath);
        LogV("FileInfo:\nfilePath:", filePath, "\n---exist:", PdfFileUtils.isAndroidQFileExists(this, getIntent().getDataString()));
        grantUriPermission(getPackageName(), Uri.fromFile(pdfFile), Intent.FLAG_GRANT_READ_URI_PERMISSION);//解决夜间模式切换时的数据丢失
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
            history.setFilePath(filePath);
        }
        pdfView.useBestQuality(true);
        if (pdfFile == null) {
            pdfView.fromFile(new File(filePath)).load();
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
        if (pdfView != null && filePath != null) {
            history.setPage(pdfView.getCurrentPage());
            history.setLastTime(System.currentTimeMillis());
            history.save();
        }
    }

}