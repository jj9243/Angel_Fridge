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
            R.drawable.tu1,
            R.drawable.tu2,
            R.drawable.tu3,
            R.drawable.tu4,
            R.drawable.tu5,
            R.drawable.tu6,
            R.drawable.tu7
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
       // imageView.(16,16,16,16);
        imageView.setScaleType(ImageView.ScaleType.CENTER);
        container.addView(imageView, 0);
        return imageView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((ImageView) object);
    }
}