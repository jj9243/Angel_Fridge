package com.namjongbin.fridge_angel;

import android.graphics.drawable.Drawable;
import android.view.View;

/**
 * @details initialization list item
 * @author Jong keon Kim
 */

public class ListVO {
    private Drawable img;
    private String Title;
    private String context;

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