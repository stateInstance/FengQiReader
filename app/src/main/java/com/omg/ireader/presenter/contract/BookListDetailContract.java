package com.omg.ireader.presenter.contract;

import com.omg.ireader.model.bean.BookListDetailBean;
import com.omg.ireader.ui.base.BaseContract;


public interface BookListDetailContract {

    interface View extends BaseContract.BaseView{
        void finishRefresh(BookListDetailBean bean);
    }

    interface Presenter extends BaseContract.BasePresenter<View>{
        void refreshBookListDetail(String detailId);
    }
}
