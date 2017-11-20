package com.omg.ireader.presenter.contract;


import com.omg.ireader.model.bean.BookCityBean;
import com.omg.ireader.ui.base.BaseContract;

import java.util.List;

/**
 * Created by yhl on 2017/11/14.
 */

public interface BookCityContract {
    interface View extends BaseContract.BaseView{
    }

    interface Presenter extends BaseContract.BasePresenter<View>{
        List<BookCityBean> getBookCityOptions();
        void deleteBookCityOption(BookCityBean deleBookCityBean);
        void updateBookCityOptions(int pos);
    }
}
