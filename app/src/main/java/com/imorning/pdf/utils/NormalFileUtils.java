package com.imorning.pdf.utils;

public class NormalFileUtils {
    static {
        System.loadLibrary("fileutils");
    }
    public native static String getFileName(String filePath);
}
