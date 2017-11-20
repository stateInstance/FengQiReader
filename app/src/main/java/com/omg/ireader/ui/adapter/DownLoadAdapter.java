package com.omg.ireader.ui.adapter;

import com.omg.ireader.model.bean.DownloadTaskBean;
import com.omg.ireader.ui.adapter.view.DownloadHolder;
import com.omg.ireader.ui.base.adapter.BaseListAdapter;
import com.omg.ireader.ui.base.adapter.IViewHolder;

/**
 * . on 17-5-12.
 */

public class DownLoadAdapter extends BaseListAdapter<DownloadTaskBean> {

    @Override
    protected IViewHolder<DownloadTaskBean> createViewHolder(int viewType) {
        return new DownloadHolder();
    }
}
