package com.omg.ireader.ui.adapter;

import android.content.Context;

import com.omg.ireader.model.bean.SortBookBean;
import com.omg.ireader.ui.adapter.view.BookSortListHolder;
import com.omg.ireader.ui.base.adapter.IViewHolder;
import com.omg.ireader.widget.adapter.WholeAdapter;

/**
 * . on 17-5-3.
 */

public class BookSortListAdapter extends WholeAdapter<SortBookBean> {
    public BookSortListAdapter(Context context, Options options) {
        super(context, options);
    }

    @Override
    protected IViewHolder<SortBookBean> createViewHolder(int viewType) {
        return new BookSortListHolder();
    }
}
