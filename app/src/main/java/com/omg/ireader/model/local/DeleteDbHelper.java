package com.omg.ireader.model.local;

import com.omg.ireader.model.bean.AuthorBean;
import com.omg.ireader.model.bean.BookCommentBean;
import com.omg.ireader.model.bean.BookHelpfulBean;
import com.omg.ireader.model.bean.BookHelpsBean;
import com.omg.ireader.model.bean.BookReviewBean;
import com.omg.ireader.model.bean.ReviewBookBean;

import java.util.List;

/**
 * . on 17-4-28.
 */

public interface DeleteDbHelper {
    void deleteBookComments(List<BookCommentBean> beans);
    void deleteBookReviews(List<BookReviewBean> beans);
    void deleteBookHelps(List<BookHelpsBean> beans);
    void deleteAuthors(List<AuthorBean> beans);
    void deleteBooks(List<ReviewBookBean> beans);
    void deleteBookHelpful(List<BookHelpfulBean> beans);
    void deleteAll();
}
