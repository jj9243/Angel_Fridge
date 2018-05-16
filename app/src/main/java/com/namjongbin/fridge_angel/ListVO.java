package com.namjongbin.fridge_angel;

import android.graphics.drawable.Drawable;

/**
 * Created by YooJongHyeok on 2017-08-07.
 */

public class ListVO {
    private Drawable img;
    private String Title;
    private String context;

    public Drawable getImg() {
        return img;
    }

    public void setImg(Drawable img) {
        this.img = img;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getContext() {
        return context;
    }

    public void setContext(String context) {
        this.context = context;
    }
}