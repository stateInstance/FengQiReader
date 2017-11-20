package com.omg.ireader.ui.adapter;

import com.omg.ireader.model.bean.BookSortBean;
import com.omg.ireader.ui.adapter.view.BookSortHolder;
import com.omg.ireader.ui.base.adapter.BaseListAdapter;
import com.omg.ireader.ui.base.adapter.IViewHolder;

/**
 * . on 17-4-23.
 */

public class BookSortAdapter extends BaseListAdapter<BookSortBean> {

    @Override
    protected IViewHolder<BookSortBean> createViewHolder(int viewType) {
        return new BookSortHolder();
    }
}
