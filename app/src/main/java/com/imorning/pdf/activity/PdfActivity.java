package com.imorning.pdf.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import com.github.barteksc.pdfviewer.PDFView;
import com.imorning.pdf.R;

public class PdfActivity extends BaseActivity {
    private PDFView pdfView;
    private String filePath;
    private Uri uriPath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pdf);
        pdfView = findViewById(R.id.main_pdfviewer);
        uriPath = Uri.parse(getIntent().getExtras().get(SplashActivity.PATH).toString());
        filePath = uriPath.getPath();
        if (filePath == null)
            finish();
        grantUriPermission(getPackageName(), uriPath, Intent.FLAG_GRANT_READ_URI_PERMISSION);//解决夜间模式切换时的数据丢失
    }

    @Override
    protected void onStart() {
        super.onStart();
        pdfView.fromUri(uriPath).load();
    }
}