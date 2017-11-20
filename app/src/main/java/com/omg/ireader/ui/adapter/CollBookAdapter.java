package com.omg.ireader.ui.adapter;

import com.omg.ireader.model.bean.CollBookBean;
import com.omg.ireader.ui.adapter.view.CollBookHolder;
import com.omg.ireader.ui.base.adapter.IViewHolder;
import com.omg.ireader.widget.adapter.WholeAdapter;

/**
 * . on 17-5-8.
 */

public class CollBookAdapter extends WholeAdapter<CollBookBean> {

    @Override
    protected IViewHolder<CollBookBean> createViewHolder(int viewType) {
        return new CollBookHolder();
    }

}
