package com.omg.ireader.ui.adapter;

import com.omg.ireader.model.bean.BookCityBean;
import com.omg.ireader.ui.adapter.view.BookCityHolder;
import com.omg.ireader.ui.base.adapter.BaseListAdapter;
import com.omg.ireader.ui.base.adapter.IViewHolder;

/**
 * Created by yhl on 2017/10/12.
 */

public class BookCityAdapter  extends BaseListAdapter <BookCityBean>{
    @Override
    protected IViewHolder createViewHolder(int viewType) {
        return new BookCityHolder();
    }
}
