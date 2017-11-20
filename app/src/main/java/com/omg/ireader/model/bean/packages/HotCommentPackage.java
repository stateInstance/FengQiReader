package com.omg.ireader.model.bean.packages;

import com.omg.ireader.model.bean.BaseBean;
import com.omg.ireader.model.bean.HotCommentBean;

import java.util.List;

/**
 * . on 17-5-4.
 */

public class HotCommentPackage extends BaseBean {

    private List<HotCommentBean> reviews;

    public List<HotCommentBean> getReviews() {
        return reviews;
    }

    public void setReviews(List<HotCommentBean> reviews) {
        this.reviews = reviews;
    }
}
