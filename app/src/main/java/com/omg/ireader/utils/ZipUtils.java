package com.omg.ireader.utils;

import android.util.Log;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

/**
 * Created by yhl on 2017/11/8.
 */

public class ZipUtils {
    private static final int BUFF_SIZE = 4 * 1024;

    /**
     * 解压
     *
     * @param zipFilePath 压缩文件
     * @param unzipPath   解压路径
     * @return return true if success
     */
    public static String unzip(String zipFilePath, String unzipPath) {
        String filePath=null;
        try {
            ZipFile zipFile = new ZipFile(zipFilePath);
            Enumeration emu = zipFile.entries();
            int i = 0;
            while (emu.hasMoreElements()) {
                ZipEntry entry = (ZipEntry) emu.nextElement();
                if (entry.isDirectory()) {
                    new File(unzipPath + "/" + entry.getName()).mkdirs();
                    continue;
                }

                BufferedInputStream bis = new BufferedInputStream(zipFile.getInputStream(entry));
                filePath=unzipPath + "/" + entry.getName();
                File file = new File(unzipPath + "/" + entry.getName());
                File parent = file.getParentFile();
                if (parent != null && (!parent.exists())) {
                    parent.mkdirs();
                }

                FileOutputStream fos = new FileOutputStream(file);
                BufferedOutputStream bos = new BufferedOutputStream(fos);

                int count;
                byte data[] = new byte[BUFF_SIZE];
                while ((count = bis.read(data, 0, BUFF_SIZE)) != -1) {
                    bos.write(data, 0, count);
                }

                bos.flush();
                bos.close();
                bis.close();
            }
            zipFile.close();
            return  filePath;
        } catch (Exception e) {
            Log.e("ZipUtil", "unzip error! zip file:" + zipFilePath + " unzip to path:" + unzipPath);
            e.printStackTrace();
            return filePath;
        }
    }
}

