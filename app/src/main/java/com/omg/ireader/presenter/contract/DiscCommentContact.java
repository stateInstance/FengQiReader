package com.omg.ireader.presenter.contract;

import com.omg.ireader.model.bean.BookCommentBean;
import com.omg.ireader.model.flag.BookDistillate;
import com.omg.ireader.model.flag.BookSort;
import com.omg.ireader.model.flag.CommunityType;
import com.omg.ireader.ui.base.BaseContract;

import java.util.List;

/**
 * . on 17-4-20.
 */

public interface DiscCommentContact {

    interface View extends BaseContract.BaseView{
        void finishRefresh(List<BookCommentBean> beans);
        void finishLoading(List<BookCommentBean> beans);
        void showErrorTip();
    }

    interface Presenter extends BaseContract.BasePresenter<View>{
        void firstLoading(CommunityType block, BookSort sort, int start, int limited, BookDistillate distillate);
        void refreshComment(CommunityType block, BookSort sort, int start, int limited, BookDistillate distillate);
        void loadingComment(CommunityType block, BookSort sort, int start, int limited, BookDistillate distillate);
        void saveComment(List<BookCommentBean> beans);
    }
}
