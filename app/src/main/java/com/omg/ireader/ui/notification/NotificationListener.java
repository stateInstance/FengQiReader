package com.omg.ireader.ui.notification;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Environment;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.Toast;

import com.liulishuo.filedownloader.BaseDownloadTask;
import com.liulishuo.filedownloader.model.FileDownloadStatus;
import com.liulishuo.filedownloader.notification.BaseNotificationItem;
import com.liulishuo.filedownloader.notification.FileDownloadNotificationHelper;
import com.liulishuo.filedownloader.notification.FileDownloadNotificationListener;
import com.liulishuo.filedownloader.util.FileDownloadHelper;
import com.omg.ireader.App;
import com.omg.ireader.R;
import com.omg.ireader.model.bean.CollBookBean;
import com.omg.ireader.model.local.BookRepository;
import com.omg.ireader.ui.activity.DownloadActivity;
import com.omg.ireader.ui.activity.SearchWebActivity;
import com.omg.ireader.utils.Constant;
import com.omg.ireader.utils.StringUtils;
import com.omg.ireader.utils.ToastUtils;
import com.omg.ireader.utils.ZipUtils;

/**
 * Created by yhl on 2017/10/17.
 */

public class NotificationListener extends FileDownloadNotificationListener {
    private static final String TAG = "NotificationListener";
    private String downLoadFilePath;

    @Override
    protected void completed(BaseDownloadTask task) {
        boolean isZip = task.getFilename().endsWith(".zip");
        Log.i(TAG,"isZip--->"+isZip);
        String path = Environment.getExternalStorageDirectory().getAbsolutePath();
        if (isZip){
            downLoadFilePath = ZipUtils.unzip(task.getTargetFilePath(), path);
        }
        CollBookBean collBook = new CollBookBean();
        collBook.setLocal(true);
        Log.i(TAG,"downLoadFilePath--->"+downLoadFilePath);
        if (downLoadFilePath==null){
            collBook.set_id(task.getTargetFilePath());
        }else {
            collBook.set_id(downLoadFilePath);
        }
        collBook.setTitle(task.getFilename().replace(".txt", ""));
        collBook.setLastChapter("开始阅读");
        collBook.setLastRead(StringUtils.
                dateConvert(System.currentTimeMillis(), Constant.FORMAT_BOOK_DATE));
        BookRepository.getInstance().saveCollBook(collBook);
        ToastUtils.show(task.getFilename()+"，下载完成。");
    }

    public NotificationListener(FileDownloadNotificationHelper helper) {
        super(helper);
    }

    @Override
    protected BaseNotificationItem create(BaseDownloadTask task) {
        return new NotificationItem(task.getId(),
                task.getFilename(), "任务");

    }

    @Override
    public void destroyNotification(BaseDownloadTask task) {
        super.destroyNotification(task);
        Toast.makeText(App.getContext(), "下载任务被取消了： "
                + task.getErrorCause(), Toast.LENGTH_LONG).show();
    }

    public static class NotificationItem extends BaseNotificationItem {

        PendingIntent pendingIntent;
        NotificationCompat.Builder builder;

        private NotificationItem(int id, String title, String desc) {
            super(id, title, desc);
            Intent[] intents = new Intent[2];
            intents[0] = Intent.makeMainActivity(new ComponentName(App.getContext(),
                    DownloadActivity.class));
            intents[1] = new Intent(App.getContext(), SearchWebActivity.class);

            this.pendingIntent = PendingIntent.getActivities(App.getContext(), 0, intents,
                    PendingIntent.FLAG_UPDATE_CURRENT);

            builder = new NotificationCompat.
                    Builder(FileDownloadHelper.getAppContext());

            builder.setDefaults(Notification.DEFAULT_LIGHTS)
                    .setOngoing(true)
                    .setPriority(NotificationCompat.PRIORITY_MIN)
                    .setContentTitle(getTitle())
                    .setContentText(desc)
                    .setContentIntent(pendingIntent)
                    .setSmallIcon(R.drawable.launcher_logo);
        }


        @Override
        public void show(boolean statusChanged, int status, boolean isShowProgress) {

            String desc = getDesc();
            switch (status) {
                case FileDownloadStatus.pending:
                    desc += " pending";
                    break;
                case FileDownloadStatus.started:
                    desc += " 开始";
                    break;
                case FileDownloadStatus.progress:
                    desc += " 正在下载";
                    break;
                case FileDownloadStatus.retry:
                    desc += " retry";
                    break;
                case FileDownloadStatus.error:
                    desc += " error";
                    break;
                case FileDownloadStatus.paused:
                    desc += " 暂停";
                    break;
                case FileDownloadStatus.completed:

                    desc += " 完成";
                    break;
                case FileDownloadStatus.warn:
                    desc += " warn";
                    break;
            }
            builder.setContentTitle(getTitle())
                    .setContentText(desc);
            if (statusChanged) {
                builder.setTicker(desc);
            }

            builder.setProgress(getTotal(), getSofar(), !isShowProgress);
            getManager().notify(getId(), builder.build());
        }
    }
}
