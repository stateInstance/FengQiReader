package com.omg.ireader.ui.adapter;

import android.content.Context;

import com.omg.ireader.model.bean.BookHelpsBean;
import com.omg.ireader.ui.adapter.view.DiscHelpsHolder;
import com.omg.ireader.ui.base.adapter.IViewHolder;
import com.omg.ireader.widget.adapter.WholeAdapter;

/**
 * . on 17-4-21.
 */

public class DiscHelpsAdapter extends WholeAdapter<BookHelpsBean> {

    public DiscHelpsAdapter(Context context, Options options) {
        super(context, options);
    }

    @Override
    protected IViewHolder<BookHelpsBean> createViewHolder(int viewType) {
        return new DiscHelpsHolder();
    }
}
