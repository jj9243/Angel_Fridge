package com.namjongbin.fridge_angel;

/**
 * @brief   Data, Category List child
 * @author  Jong Keon Kim
 */

import android.graphics.drawable.Drawable;

class ChildListData {
    public Drawable cImg;
    // TextView01에 상응
    public String ctext1;
    // TextView02에 상응
    public String cDate;

    public ChildListData(Drawable drawable, String name, String date) {
        cImg=drawable;
        ctext1=name;
        cDate=date;
    }
}
