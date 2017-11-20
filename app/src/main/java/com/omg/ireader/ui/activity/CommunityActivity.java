package com.omg.ireader.ui.activity;

import android.support.v7.widget.Toolbar;

import com.omg.ireader.R;
import com.omg.ireader.ui.base.BaseActivity;

/**
 * . on 17-5-26.
 */

public class CommunityActivity extends BaseActivity {

    @Override
    protected int getContentId() {
        return R.layout.activity_community;
    }

    @Override
    protected void setUpToolbar(Toolbar toolbar) {
        super.setUpToolbar(toolbar);
        getSupportActionBar().setTitle("社区");
    }
}
