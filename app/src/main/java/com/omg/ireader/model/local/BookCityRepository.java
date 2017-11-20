package com.omg.ireader.model.local;

import com.omg.ireader.model.bean.BookCityBean;
import com.omg.ireader.model.bean.CollBookBean;
import com.omg.ireader.model.gen.BookCityBeanDao;
import com.omg.ireader.model.gen.CollBookBeanDao;
import com.omg.ireader.model.gen.DaoSession;

import java.util.List;

/**
 * Created by yhl on 2017/11/10.
 */

public class BookCityRepository {
    private static final String TAG = "BookCityRepository";
    private DaoSession mSession;
    private  BookCityBeanDao mBookCityDao;
    private static volatile BookCityRepository sInstance;
    private BookCityRepository() {
        mSession = DaoDbHelper.getInstance()
                .getSession();
        mBookCityDao = mSession.getBookCityBeanDao();
    }
    public static BookCityRepository getInstance(){
        if (sInstance == null){
            synchronized (BookRepository.class){
                if (sInstance == null){
                    sInstance = new BookCityRepository();
                }
            }
        }
        return sInstance;
    }
    /**
     * 收藏网址
     */
    public void saveBookCityOption(BookCityBean bean){
        mBookCityDao.insertOrReplace(bean);
    }

    public void saveBookCityOptions(List<BookCityBean> beans){
        mBookCityDao.insertOrReplaceInTx(beans);
    }
    public void updateBookCityOption(BookCityBean bean){
        mBookCityDao.update(bean);
    }
    public void updateBookCityOptions(List<BookCityBean> beans){
        mBookCityDao.updateInTx(beans);
    }
    /**
     * 查询所有
     * @return
     */
    public  List<BookCityBean> getBookCityOptions(){
        return mBookCityDao
                .queryBuilder()
             //   .orderDesc(BookCityBeanDao.Properties.DrawableId)
                .list();
    }


    public BookCityBean getBookCityOption(String PathUrl){
        BookCityBean bean = mBookCityDao.queryBuilder()
                .where(BookCityBeanDao.Properties.PathUrl.eq(PathUrl))
                .unique();
        return bean;
    }
    public void deleteBookCityOption(BookCityBean bean){
        mBookCityDao.delete(bean);
    }
    public void saveBookCityOptionsWithAsync(List<BookCityBean> beans){
        mSession.startAsyncSession()
                .runInTx(
                        () -> {
                            //存储CollBook (确保先后顺序，否则出错)
                            mBookCityDao.insertOrReplaceInTx(beans);
                        }
                );
    }
}
