package com.omg.ireader.model.flag;

import android.support.annotation.DrawableRes;
import android.support.annotation.StringRes;

import com.omg.ireader.App;
import com.omg.ireader.R;

/**
 * Created by yhl on 2017/10/12.
 */

public enum BookCityType {

    QIDIAN(R.string.nb_fragment_city_qidian, R.string.nb_fragment_city_qidian_url, R.drawable.read_online),
    SEVENTEENK(R.string.nb_fragment_city_seventeenk, R.string.nb_fragment_city_seventeenk_url, R.drawable.read_online),
    ZONGHENG(R.string.nb_fragment_city_zongheng, R.string.nb_fragment_city_zongheng_url, R.drawable.read_online),
    REDSWEET(R.string.nb_fragment_city_redsweet, R.string.nb_fragment_city_redsweet_url, R.drawable.read_online),
    READNOVEL(R.string.nb_fragment_city_readnovel, R.string.nb_fragment_city_readnovel_url, R.drawable.read_online),

    XIAOXIANG(R.string.nb_fragment_city_xiaoxiang, R.string.nb_fragment_city_xiaoxiang_url, R.drawable.downlowd_file),
    CHUANGSHI(R.string.nb_fragment_city_chuangshi, R.string.nb_fragment_city_chuangshi_url, R.drawable.downlowd_file),
    WANGYI(R.string.nb_fragment_city_wangyi, R.string.nb_fragment_city_wangyi_url, R.drawable.downlowd_file),
    KUAIYAN(R.string.nb_fragment_city_uu, R.string.nb_fragment_city_uu_url, R.drawable.downlowd_file);

    private String title;

    BookCityType(@StringRes int title, @StringRes int pathUrl, @DrawableRes int iconId) {
        this.title = App.getContext().getResources().getString(title);
        this.pathUrl = App.getContext().getResources().getString(pathUrl);
        this.iconId = iconId;
    }

    private String pathUrl;
    private int iconId;

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

    public int getIconId() {
        return iconId;
    }

    public void setIconId(int iconId) {
        this.iconId = iconId;
    }
}
