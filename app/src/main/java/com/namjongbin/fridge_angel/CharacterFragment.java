package com.namjongbin.fridge_angel;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.GlideDrawableImageViewTarget;
import com.felipecsl.gifimageview.library.GifImageView;

public class CharacterFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    int healthy;
    Boolean touchflag=false;
    // TODO: Rename and change types of parameters


    public CharacterFragment() {
        // Required empty public constructor
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_character,container,false);
        container.removeAllViews();
        healthy=100;
        ImageView image =(ImageView)rootView.findViewById(R.id.imageView);
        ProgressBar progress=rootView.findViewById(R.id.progressBar);

        final GlideDrawableImageViewTarget cd=new GlideDrawableImageViewTarget(image);

        if(healthy>85) {
            Glide.with(this).load(R.drawable.cdhappy).into(cd);
        }

        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Glide.with(getContext()).load(R.drawable.cdtouch).into(cd);
            }
        });
//
//        if(progress !=null){
//            progress.setVisibility(View.VISIBLE);
//            progress.setIndeterminate(true);
//            progress.getIndeterminateDrawable().setColorFilter(Color.rgb(10,180,190), PorterDuff.Mode.MULTIPLY);
//        }

        //http://m.todayhumor.co.kr/view.php?table=programmer&no=13496
        //progress.getProgressDrawable().setColorFilter(Color.rgb(10,210,200), PorterDuff.Mode.MULTIPLY);
        return rootView;
    }

}
