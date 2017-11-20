package com.omg.ireader.ui.adapter;

import com.omg.ireader.model.bean.HotCommentBean;
import com.omg.ireader.ui.adapter.view.HotCommentHolder;
import com.omg.ireader.ui.base.adapter.BaseListAdapter;
import com.omg.ireader.ui.base.adapter.IViewHolder;

/**
 * . on 17-5-4.
 */

public class HotCommentAdapter extends BaseListAdapter<HotCommentBean> {
    @Override
    protected IViewHolder<HotCommentBean> createViewHolder(int viewType) {
        return new HotCommentHolder();
    }
}
