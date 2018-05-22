package com.namjongbin.fridge_angel;

//import android.app.Fragment;


import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TableLayout;


public class HomeFragment extends Fragment {


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View rootView = (View) inflater.inflate(R.layout.fragment_home, container, false);

        container.removeAllViews();

        TableLayout table = (TableLayout) rootView.findViewById(R.id.table);
        Button youtubeButton = (Button)rootView.findViewById(R.id.recipeBtn);

        youtubeButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_SEARCH);
                intent.setPackage("com.google.android.youtube");

                intent.putExtra("query", "최유정 직캠");

                try {
                    startActivity(intent);
                } catch (ActivityNotFoundException e) {
                }
            }
        });

        table.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               Intent intent=new Intent(getActivity().getApplicationContext(),ListActivity.class);
               startActivity(intent);

            }
        });
        return rootView;
    }
}
