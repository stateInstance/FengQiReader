package com.omg.ireader.presenter.contract;

import com.omg.ireader.model.bean.packages.BookSortPackage;
import com.omg.ireader.model.bean.packages.BookSubSortPackage;
import com.omg.ireader.ui.base.BaseContract;

/**
 * . on 17-4-23.
 */

public interface BookSortContract {

    interface View extends BaseContract.BaseView{
        void finishRefresh(BookSortPackage sortPackage, BookSubSortPackage subSortPackage);
    }

    interface Presenter extends BaseContract.BasePresenter<View>{
        void refreshSortBean();
    }
}
