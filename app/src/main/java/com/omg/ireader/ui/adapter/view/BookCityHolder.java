package com.omg.ireader.ui.adapter.view;

import android.widget.ImageView;
import android.widget.TextView;

import com.omg.ireader.R;
import com.omg.ireader.model.bean.BookCityBean;
import com.omg.ireader.model.bean.SectionBean;
import com.omg.ireader.ui.base.adapter.ViewHolderImpl;

/**
 * Created by yhl on 2017/10/12.
 */

public class BookCityHolder extends ViewHolderImpl<BookCityBean> {
    private ImageView mIvIcon;
    private TextView mTvTitle;
    private TextView mTvUrl;
    @Override
    public void initView() {
        mIvIcon =findById(R.id.bookcity_item_iv_icon);
        mTvTitle =findById(R.id.bookcity_item_tv_title);
        mTvUrl =findById(R.id.bookcity_item_tv_url);
    }

    @Override
    public void onBind(BookCityBean data, int pos) {
        mIvIcon.setImageResource(data.getDrawableId());
        mTvTitle.setText(data.getTitle());
        mTvUrl.setText(data.getPathUrl());
    }

    @Override
    protected int getItemLayoutId() {
        return R.layout.item_book_city;
    }
}
