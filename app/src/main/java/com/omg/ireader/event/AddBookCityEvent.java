package com.omg.ireader.event;

import com.omg.ireader.model.bean.BookCityBean;


public class AddBookCityEvent {
public BookCityBean bookCityBean;

    public AddBookCityEvent(BookCityBean bookCityBean) {
        this.bookCityBean = bookCityBean;
    }
}
