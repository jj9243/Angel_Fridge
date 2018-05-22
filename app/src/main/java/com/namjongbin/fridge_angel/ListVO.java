package com.namjongbin.fridge_angel;

import android.graphics.drawable.Drawable;

/**
 * Created by YooJongHyeok on 2017-08-07.
 */

public class ListVO {
    private Drawable img;
    private String Title;
    private String context;
    private boolean checked = false;

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
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