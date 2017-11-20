package com.omg.ireader.ui.adapter;

import android.content.Context;

import com.omg.ireader.model.bean.CommentBean;
import com.omg.ireader.ui.adapter.view.CommentHolder;
import com.omg.ireader.ui.base.adapter.IViewHolder;
import com.omg.ireader.widget.adapter.WholeAdapter;

/**
 * . on 17-4-29.
 */

public class CommentAdapter extends WholeAdapter<CommentBean> {

    public CommentAdapter(Context context, Options options) {
        super(context, options);
    }

    @Override
    protected IViewHolder<CommentBean> createViewHolder(int viewType) {
        return new CommentHolder(false);
    }
}
