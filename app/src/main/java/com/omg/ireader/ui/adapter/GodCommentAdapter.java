package com.omg.ireader.ui.adapter;

import com.omg.ireader.model.bean.CommentBean;
import com.omg.ireader.ui.adapter.view.CommentHolder;
import com.omg.ireader.ui.base.adapter.BaseListAdapter;
import com.omg.ireader.ui.base.adapter.IViewHolder;

/**
 * . on 17-4-29.
 */

public class GodCommentAdapter extends BaseListAdapter<CommentBean> {
    @Override
    protected IViewHolder<CommentBean> createViewHolder(int viewType) {
        return new CommentHolder(true);
    }
}
