package com.omg.ireader.ui.adapter;

import com.omg.ireader.model.bean.packages.SearchBookPackage;
import com.omg.ireader.ui.adapter.view.SearchBookHolder;
import com.omg.ireader.ui.base.adapter.BaseListAdapter;
import com.omg.ireader.ui.base.adapter.IViewHolder;

/**
 * . on 17-6-2.
 */

public class SearchBookAdapter extends BaseListAdapter<SearchBookPackage.BooksBean> {
    @Override
    protected IViewHolder<SearchBookPackage.BooksBean> createViewHolder(int viewType) {
        return new SearchBookHolder();
    }
}
