package com.omg.ireader.utils.down;

import android.util.Log;

import com.liulishuo.filedownloader.BaseDownloadTask;
import com.liulishuo.filedownloader.FileDownloadListener;
import com.liulishuo.filedownloader.FileDownloader;
import com.liulishuo.filedownloader.notification.FileDownloadNotificationHelper;
import com.omg.ireader.model.bean.CollBookBean;
import com.omg.ireader.model.local.BookRepository;
import com.omg.ireader.ui.notification.NotificationListener;
import com.omg.ireader.utils.Constant;
import com.omg.ireader.utils.FileUtils;
import com.omg.ireader.utils.StringUtils;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

/**
 * Created by yhl on 2017/10/13.
 */

public class DownLoadWebFileManager {
    private static DownLoadWebFileManager mInstance;

    private DownLoadWebFileManager() {
    }

    public static DownLoadWebFileManager getInstance() {
        if (mInstance == null) {
            synchronized (DownLoadWebFileManager.class) {
                if (mInstance == null) {
                    mInstance = new DownLoadWebFileManager();
                }
            }
        }
        return mInstance;
    }

    public void startDownLoad(String url) {
        String urlName = "未知名称";
        try {
            urlName = URLDecoder.decode(url, "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        String result = urlName.substring(urlName.lastIndexOf('/') + 1);
        Log.i("DownLoadWebFileManager", "result----->>" + result);

        FileDownloader.getImpl().create(url).setPath(FileUtils.createFile(result) + "")
                .setListener(new NotificationListener(new FileDownloadNotificationHelper<NotificationListener.NotificationItem>())).start();
    }
}
