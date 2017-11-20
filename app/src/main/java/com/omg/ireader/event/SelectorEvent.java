package com.omg.ireader.event;

import com.omg.ireader.model.flag.BookDistillate;
import com.omg.ireader.model.flag.BookSort;
import com.omg.ireader.model.flag.BookType;



public class SelectorEvent {

    public BookDistillate distillate;

    public BookType type;

    public BookSort sort;

    public SelectorEvent(BookDistillate distillate,
                         BookType type,
                         BookSort sort) {
        this.distillate = distillate;
        this.type = type;
        this.sort = sort;
    }
}
