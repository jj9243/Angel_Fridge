package com.namjongbin.fridge_angel;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

public class ImageAdapter extends PagerAdapter {

    Context context;
    private final int[] drawableImgs = new int[] {
//            R.drawable.tutorial_01,
//            R.drawable.tutorial_02,
//            R.drawable.tutorial_03,
//            R.drawable.tutorial_04,
//            R.drawable.tutorial_05,
//            R.drawable.tutorial_06
    R.drawable.tu1,
    R.drawable.calendar
    };

    ImageAdapter(Context context) {
        this.context = context;
    }

    @Override
    public int getCount() {
        return drawableImgs.length;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        ImageView imageView = new ImageView(context);
        Bitmap drawImg = BitmapFactory.decodeResource(context.getResources(), drawableImgs[position]);

        imageView.setImageBitmap(drawImg);
        container.addView(imageView, 0);
        return imageView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((ImageView) object);
    }
}