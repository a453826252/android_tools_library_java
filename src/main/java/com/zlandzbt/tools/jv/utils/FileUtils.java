package com.zlandzbt.tools.jv.utils;

import java.io.File;

public class FileUtils {

    /**
     * 删除文件
     * @param file
     */
    public static void deleteFile(File file) {
        if (!file.exists()) {
            return;
        }
        if (file.isDirectory()) {
            cleanDir(file);
        }
        file.delete();
    }

    /**
     * 清空文件夹
     * @param dir
     */
    public static void cleanDir(File dir) {
        if (!dir.exists() || !dir.isDirectory()) {
            return;
        }
        File[] files = dir.listFiles();
        if (files != null) {
            for (File file : files) {
                deleteFile(file);
            }
        }
    }
}
