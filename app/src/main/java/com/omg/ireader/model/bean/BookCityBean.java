package com.omg.ireader.model.bean;

import android.support.annotation.DrawableRes;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by yhl on 2017/10/12.
 */
@Entity
public class BookCityBean {
    private String title;
    private String pathUrl;
    private int drawableId;
    @Id
    private  String _id;

    @Generated(hash = 1709411675)
    public BookCityBean(String title, String pathUrl, int drawableId, String _id) {
        this.title = title;
        this.pathUrl = pathUrl;
        this.drawableId = drawableId;
        this._id = _id;
    }

    @Generated(hash = 481818146)
    public BookCityBean() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPathUrl() {
        return pathUrl;
    }

    public void setPathUrl(String pathUrl) {
        this.pathUrl = pathUrl;
    }

    public int getDrawableId() {
        return drawableId;
    }

    public void setDrawableId(int drawableId) {
        this.drawableId = drawableId;
    }

    public String get_id() {
        return this._id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }
}
