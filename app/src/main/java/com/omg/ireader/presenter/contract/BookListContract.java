package com.omg.ireader.presenter.contract;

import com.omg.ireader.model.bean.BookListBean;
import com.omg.ireader.model.flag.BookListType;
import com.omg.ireader.ui.base.BaseContract;

import java.util.List;


public interface BookListContract {
    interface View extends BaseContract.BaseView{
        void finishRefresh(List<BookListBean> beans);
        void finishLoading(List<BookListBean> beans);
        void showLoadError();
    }

    interface Presenter extends BaseContract.BasePresenter<View>{
        void refreshBookList(BookListType type, String tag, int start, int limited);
        void loadBookList(BookListType type, String tag, int start, int limited);
    }
}
