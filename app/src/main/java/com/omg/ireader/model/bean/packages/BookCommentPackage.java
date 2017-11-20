package com.omg.ireader.model.bean.packages;

import com.omg.ireader.model.bean.BaseBean;
import com.omg.ireader.model.bean.BookCommentBean;

import java.util.List;

/**
 * . on 17-4-20.
 */
public class BookCommentPackage extends BaseBean {

    private List<BookCommentBean> posts;

    public List<BookCommentBean> getPosts() {
        return posts;
    }

    public void setPosts(List<BookCommentBean> posts) {
        this.posts = posts;
    }
}
