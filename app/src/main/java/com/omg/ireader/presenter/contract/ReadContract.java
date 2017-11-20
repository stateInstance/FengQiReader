package com.omg.ireader.presenter.contract;

import com.omg.ireader.model.bean.BookChapterBean;
import com.omg.ireader.ui.base.BaseContract;
import com.omg.ireader.widget.page.TxtChapter;

import java.util.List;

/**
 * . on 17-5-16.
 */

public interface ReadContract extends BaseContract {
    interface View extends BaseView {
        void showCategory(List<BookChapterBean> bookChapterList);
        void finishChapter();
        void errorChapter();
    }

    interface Presenter extends BasePresenter<View>{
        void loadCategory(String bookId);
        void loadChapter(String bookId, List<TxtChapter> bookChapterList);
    }
}
