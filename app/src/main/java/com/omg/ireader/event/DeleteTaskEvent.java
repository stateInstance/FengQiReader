package com.omg.ireader.event;

import com.omg.ireader.model.bean.CollBookBean;


public class DeleteTaskEvent {
    public CollBookBean collBook;

    public DeleteTaskEvent(CollBookBean collBook){
        this.collBook = collBook;
    }
}
