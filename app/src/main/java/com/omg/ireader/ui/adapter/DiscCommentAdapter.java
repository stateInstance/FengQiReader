package com.omg.ireader.ui.adapter;

import android.content.Context;

import com.omg.ireader.model.bean.BookCommentBean;
import com.omg.ireader.ui.adapter.view.DiscCommentHolder;
import com.omg.ireader.ui.base.adapter.IViewHolder;
import com.omg.ireader.widget.adapter.WholeAdapter;

/**
 * . on 17-4-20.
 */

public class DiscCommentAdapter extends WholeAdapter<BookCommentBean> {

    public DiscCommentAdapter(Context context, Options options) {
        super(context, options);
    }

    @Override
    protected IViewHolder<BookCommentBean> createViewHolder(int viewType) {
        return new DiscCommentHolder();
    }
}
