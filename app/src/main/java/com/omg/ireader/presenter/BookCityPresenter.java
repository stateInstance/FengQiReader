package com.omg.ireader.presenter;

import com.omg.ireader.model.bean.BookCityBean;
import com.omg.ireader.model.flag.BookCityType;
import com.omg.ireader.model.local.BookCityRepository;
import com.omg.ireader.presenter.contract.BookCityContract;
import com.omg.ireader.presenter.contract.BookCityContract.Presenter;
import com.omg.ireader.ui.base.RxPresenter;

import java.util.Collections;
import java.util.List;

/**
 * Created by yhl on 2017/11/14.
 */

public class BookCityPresenter extends RxPresenter<BookCityContract.View> implements Presenter{


    @Override
    public List<BookCityBean> getBookCityOptions() {

        List<BookCityBean>  bookCityOptions = BookCityRepository.getInstance().getBookCityOptions();
        if (bookCityOptions.size()<1){
            for (BookCityType type : BookCityType.values()){
                bookCityOptions.add(new BookCityBean(type.getTitle(),type.getPathUrl(),type.getIconId(),type.getPathUrl()));
            }
            BookCityRepository.getInstance().saveBookCityOptions(bookCityOptions);
        }

        return bookCityOptions;
    }

    @Override
    public void deleteBookCityOption(BookCityBean deleBookCityBean) {
        BookCityRepository.getInstance().deleteBookCityOption(deleBookCityBean);
    }

    @Override
    public void updateBookCityOptions(int pos) {
        List<BookCityBean>  bookCityOptions=getBookCityOptions();
        if (0 != pos) {
            for (int i = pos; i > 0; i--) {
                Collections.swap(bookCityOptions, i, i - 1);
            }
        }
        BookCityRepository.getInstance().saveBookCityOptionsWithAsync(bookCityOptions);

    }
}
