package com.omg.ireader.presenter.contract;

import com.omg.ireader.model.bean.CommentBean;
import com.omg.ireader.model.bean.HelpsDetailBean;
import com.omg.ireader.ui.base.BaseContract;

import java.util.List;

/**
 * . on 17-4-30.
 */

public interface HelpsDetailContract{

    interface View extends BaseContract.BaseView{
        //全部加载的时候显示
        void finishRefresh(HelpsDetailBean commentDetail,
                           List<CommentBean> bestComments, List<CommentBean> comments);
        void finishLoad(List<CommentBean> comments);
        void showLoadError();
    }

    interface Presenter extends BaseContract.BasePresenter<View>{
        void refreshHelpsDetail(String detailId, int start, int limit);
        void loadComment(String detailId, int start, int limit);
    }
}
