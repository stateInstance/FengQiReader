package com.omg.ireader.model.local;

import com.omg.ireader.model.bean.AuthorBean;
import com.omg.ireader.model.bean.BookCommentBean;
import com.omg.ireader.model.bean.BookHelpfulBean;
import com.omg.ireader.model.bean.BookHelpsBean;
import com.omg.ireader.model.bean.BookReviewBean;
import com.omg.ireader.model.bean.DownloadTaskBean;
import com.omg.ireader.model.bean.ReviewBookBean;
import com.omg.ireader.model.bean.packages.BillboardPackage;
import com.omg.ireader.model.bean.packages.BookSortPackage;

import java.util.List;

/**
 * . on 17-4-28.
 */

public interface SaveDbHelper {
    void saveBookComments(List<BookCommentBean> beans);
    void saveBookHelps(List<BookHelpsBean> beans);
    void saveBookReviews(List<BookReviewBean> beans);
    void saveAuthors(List<AuthorBean> beans);
    void saveBooks(List<ReviewBookBean> beans);
    void saveBookHelpfuls(List<BookHelpfulBean> beans);

    void saveBookSortPackage(BookSortPackage bean);
    void saveBillboardPackage(BillboardPackage bean);
    /*************DownloadTask*********************/
    void saveDownloadTask(DownloadTaskBean bean);
}
