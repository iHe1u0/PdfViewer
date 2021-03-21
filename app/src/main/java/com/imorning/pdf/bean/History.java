package com.imorning.pdf.bean;

import org.litepal.annotation.Column;
import org.litepal.crud.LitePalSupport;

/**
 * 数据库结构
 */
public class History extends LitePalSupport {
    @Column(defaultValue = "unknown")
    private String fileName;
    @Column
    private String filePath;
    @Column(defaultValue = "0")
    private int page;


    @Column(defaultValue = "1537977600")
    private long lastTime;

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public long getLastTime() {
        return lastTime;
    }

    public void setLastTime(long lastTime) {
        this.lastTime = lastTime;
    }

}
