package com.omg.ireader.presenter.contract;

import com.omg.ireader.model.bean.BookDetailBean;
import com.omg.ireader.model.bean.BookListBean;
import com.omg.ireader.model.bean.CollBookBean;
import com.omg.ireader.model.bean.HotCommentBean;
import com.omg.ireader.ui.base.BaseContract;

import java.util.List;

public interface BookDetailContract {
    interface View extends BaseContract.BaseView{
        void finishRefresh(BookDetailBean bean);
        void finishHotComment(List<HotCommentBean> beans);
        void finishRecommendBookList(List<BookListBean> beans);

        void waitToBookShelf();
        void errorToBookShelf();
        void succeedToBookShelf();
    }

    interface Presenter extends BaseContract.BasePresenter<View>{
        void refreshBookDetail(String bookId);
        //添加到书架上
        void addToBookShelf(CollBookBean collBook);
    }
}
