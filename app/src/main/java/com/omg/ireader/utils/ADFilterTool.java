package com.omg.ireader.utils;

import android.content.Context;
import android.content.res.Resources;

import com.omg.ireader.R;

/**
 * Created by yhl on 2017/10/24.
 * use this util to filter the   web ads out
 */

public class ADFilterTool {

    public static String getClearAdDivJs(Context context) {
        String js = "javascript:";
        Resources res = context.getResources();
        String[] adDivs = res.getStringArray(R.array.adBlockDiv);
        for (int i = 0; i < adDivs.length; i++) {

            js += "var adDiv" + i + "= document.getElementById('" + adDivs[i] + "');if(adDiv" + i + " != null)adDiv" + i + ".parentNode.removeChild(adDiv" + i + ");";
        }
        return js;
    }
}
