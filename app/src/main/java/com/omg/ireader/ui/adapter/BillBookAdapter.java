package com.omg.ireader.ui.adapter;

import com.omg.ireader.model.bean.BillBookBean;
import com.omg.ireader.ui.adapter.view.BillBookHolder;
import com.omg.ireader.ui.base.adapter.BaseListAdapter;
import com.omg.ireader.ui.base.adapter.IViewHolder;

/**
 * . on 17-5-3.
 */

public class BillBookAdapter extends BaseListAdapter<BillBookBean> {
    @Override
    protected IViewHolder<BillBookBean> createViewHolder(int viewType) {
        return new BillBookHolder();
    }
}
