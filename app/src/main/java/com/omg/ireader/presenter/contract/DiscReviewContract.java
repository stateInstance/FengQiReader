package com.omg.ireader.presenter.contract;

import com.omg.ireader.model.bean.BookReviewBean;
import com.omg.ireader.model.flag.BookDistillate;
import com.omg.ireader.model.flag.BookSort;
import com.omg.ireader.model.flag.BookType;
import com.omg.ireader.ui.base.BaseContract;

import java.util.List;

/**
 * . on 17-4-21.
 */

public interface DiscReviewContract {
    interface View extends BaseContract.BaseView {
        void finishRefresh(List<BookReviewBean> beans);
        void finishLoading(List<BookReviewBean> beans);
        void showErrorTip();
    }

    interface Presenter extends BaseContract.BasePresenter<View> {
        void firstLoading(BookSort sort, BookType bookType, int start, int limited, BookDistillate distillate);
        void refreshBookReview(BookSort sort, BookType bookType, int start, int limited, BookDistillate distillate);
        void loadingBookReview(BookSort sort, BookType bookType, int start, int limited, BookDistillate distillate);
        void saveBookReview(List<BookReviewBean> beans);
    }
}
