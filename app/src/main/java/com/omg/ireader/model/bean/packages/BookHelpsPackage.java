package com.omg.ireader.model.bean.packages;

import com.omg.ireader.model.bean.BaseBean;
import com.omg.ireader.model.bean.BookHelpsBean;

import java.util.List;

/**
 * . on 17-4-20.
 */

public class BookHelpsPackage extends BaseBean {

    private List<BookHelpsBean> helps;

    public List<BookHelpsBean> getHelps() {
        return helps;
    }

    public void setHelps(List<BookHelpsBean> helps) {
        this.helps = helps;
    }

}
