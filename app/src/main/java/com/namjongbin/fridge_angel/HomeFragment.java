package com.namjongbin.fridge_angel;

//import android.app.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.Toast;

public class HomeFragment extends Fragment {
//    MainActivity main;
//    OnFragmentOpen mOpen;
//
//    public interface OnFragmentOpen{
//        public void onSentCode(int code);
//    }
//
//    public HomeFragment(){
//    }
//
//    public void onAttach(Activity activity){
//        super.onAttach(activity);
//        try{
//            mOpen=(OnFragmentOpen)activity;
//        }catch (ClassCastException e){
//            throw new ClassCastException(activity.toString()+" must implent OnFragmentOpen");
//        }
//    }

    @Nullable
    @Override

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_home, container, false);

        container.removeAllViews();

        TableLayout table = (TableLayout) rootView.findViewById(R.id.table);
        table.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ListsFragment listFragment=new ListsFragment();
                FragmentManager manager=getFragmentManager();
                manager.beginTransaction().replace(R.id.frame_fragment, listFragment, listFragment.getTag()).commit();
            }
        });


        return rootView;
    }
}
