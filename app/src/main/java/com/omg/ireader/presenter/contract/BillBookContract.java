package com.omg.ireader.presenter.contract;

import com.omg.ireader.model.bean.BillBookBean;
import com.omg.ireader.ui.base.BaseContract;

import java.util.List;


public interface BillBookContract {
    interface View extends BaseContract.BaseView{
        void finishRefresh(List<BillBookBean> beans);
    }

    interface Presenter extends BaseContract.BasePresenter<View>{
        void refreshBookBrief(String billId);
    }
}
