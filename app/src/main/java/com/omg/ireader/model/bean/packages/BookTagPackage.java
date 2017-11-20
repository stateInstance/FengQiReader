package com.omg.ireader.model.bean.packages;

import com.omg.ireader.model.bean.BaseBean;
import com.omg.ireader.model.bean.BookTagBean;

import java.util.List;

/**
 * . on 17-5-1.
 */

public class BookTagPackage extends BaseBean {

    private List<BookTagBean> data;

    public List<BookTagBean> getData() {
        return data;
    }

    public void setData(List<BookTagBean> data) {
        this.data = data;
    }


}
