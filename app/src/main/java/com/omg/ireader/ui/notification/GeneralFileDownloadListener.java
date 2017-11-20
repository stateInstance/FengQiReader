package com.omg.ireader.ui.notification;

import android.util.Log;

import com.liulishuo.filedownloader.BaseDownloadTask;
import com.liulishuo.filedownloader.FileDownloadListener;
import com.omg.ireader.model.bean.CollBookBean;
import com.omg.ireader.model.local.BookRepository;
import com.omg.ireader.utils.Constant;
import com.omg.ireader.utils.StringUtils;

/**
 * Created by yhl on 2017/10/17.
 */

public class GeneralFileDownloadListener extends FileDownloadListener {

    @Override
    protected void pending(BaseDownloadTask task, int soFarBytes, int totalBytes) {
        Log.i("ReaderDownloadListener","startDownLoad>>>>>>getTargetFilePath"+task.getTargetFilePath());
    }

    @Override
    protected void connected(BaseDownloadTask task, String etag, boolean isContinue, int soFarBytes, int totalBytes) {
        Log.i("ReaderDownloadListener","connected>>>>>>"+task.getUrl());
    }

    @Override
    protected void progress(BaseDownloadTask task, int soFarBytes, int totalBytes) {
        Log.i("ReaderDownloadListener","soFarBytes>>>>>>"+soFarBytes);
    }

    @Override
    protected void blockComplete(BaseDownloadTask task) {
        Log.i("ReaderDownloadListener","blockComplete>>>>>>");
    }

    @Override
    protected void retry(final BaseDownloadTask task, final Throwable ex, final int retryingTimes, final int soFarBytes) {
    }

    @Override
    protected void completed(BaseDownloadTask task) {
        CollBookBean collBook = new CollBookBean();
        collBook.setLocal(true);
        collBook.set_id(task.getTargetFilePath());
        collBook.setTitle(task.getFilename().replace(".txt",""));
        collBook.setLastChapter("开始阅读");
        collBook.setLastRead(StringUtils.
                dateConvert(System.currentTimeMillis(), Constant.FORMAT_BOOK_DATE));
        BookRepository.getInstance().saveCollBook(collBook);
    }

    @Override
    protected void paused(BaseDownloadTask task, int soFarBytes, int totalBytes) {
    }

    @Override
    protected void error(BaseDownloadTask task, Throwable e) {
        Log.e("ReaderDownloadListener","error>>>>>>"+task.getErrorCause()+"---->>"+ e.getMessage());
    }

    @Override
    protected void warn(BaseDownloadTask task) {
    }








}
