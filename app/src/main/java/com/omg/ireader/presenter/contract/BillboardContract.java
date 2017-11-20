package com.omg.ireader.presenter.contract;

import com.omg.ireader.model.bean.packages.BillboardPackage;
import com.omg.ireader.ui.base.BaseContract;

public interface BillboardContract {

    interface View extends BaseContract.BaseView{
        void finishRefresh(BillboardPackage beans);
    }

    interface Presenter extends BaseContract.BasePresenter<View>{
        void loadBillboardList();
    }
}
