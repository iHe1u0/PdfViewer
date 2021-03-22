package com.imorning.pdf.utils;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.util.Log;

import java.io.File;

public class NormalFileUtils {
    static {
        System.loadLibrary("fileutils");
    }

    public native static String getFileName(String filePath);
}
