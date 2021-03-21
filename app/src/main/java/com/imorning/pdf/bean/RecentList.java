package com.imorning.pdf.bean;

/**
 * 最近阅读列表
 */
public class RecentList {

    private String fileName;
    private String filePath;
    private int page;
    private long lastTime;

    public RecentList(String fileName, String filePath, long lastTime,int page) {
        this.fileName = fileName;
        this.filePath = filePath;
        this.lastTime = lastTime;
        this.page=page;
    }

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
