package com.namjongbin.fridge_angel;

/**
 * @brief   Character's Information
 * @author  Jong Keon Kim
 */

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.GlideDrawableImageViewTarget;

public class ChoudaFragment extends Fragment {

    Button blog,kakao;
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_chouda, container, false);
        container.removeAllViews();

        ImageView character=rootView.findViewById(R.id.cd);

        final GlideDrawableImageViewTarget charimg=new GlideDrawableImageViewTarget(character);

        Glide.with(this).load(R.drawable.cdw).into(charimg);

        blog=rootView.findViewById(R.id.blogBtn);
        kakao=rootView.findViewById(R.id.kakaoBtn);

        blog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://blog.naver.com/diamume"));
                startActivity(intent);

            }
        });

        kakao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://e.kakao.com/t/flying-chouda"));
                startActivity(intent);

            }
        });

        return rootView;
    }
}
