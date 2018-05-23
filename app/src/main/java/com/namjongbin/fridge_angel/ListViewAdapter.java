package com.namjongbin.fridge_angel;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
//http://yoo-hyeok.tistory.com/53

public class ListViewAdapter extends BaseAdapter {
    CheckBox check;
    TextView title;
    TextView content;
    private ArrayList<ListVO> listVO = new ArrayList<ListVO>() ;
    public ListViewAdapter() {

    }

    @Override
    public int getCount() {
        return listVO.size() ;
    }

    // ** 이 부분에서 리스트뷰에 데이터를 넣어줌 **
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //postion = ListView의 위치      /   첫번째면 position = 0
        final int pos = position;
        final Context context = parent.getContext();


        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.list_item, parent, false);
        }

//        ImageButton img=convertView.findViewById(R.id.deleteBtn);
        check = (CheckBox) convertView.findViewById(R.id.checkItem) ;
        title = (TextView) convertView.findViewById(R.id.title) ;
        content = (TextView) convertView.findViewById(R.id.context) ;

        ListVO listViewItem = listVO.get(position);

        // 아이템 내 각 위젯에 데이터 반영
        check.setVisibility(View.INVISIBLE);
                //.setImageDrawable(listViewItem.getImg());
        title.setText(listViewItem.getTitle());
        content.setText(listViewItem.getContext());


        //리스트뷰 클릭 이벤트
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, (pos+1)+"번째 리스트가 클릭되었습니다.", Toast.LENGTH_SHORT).show();
            }
        });


        return convertView;
    }


    @Override
    public long getItemId(int position) {
        return position ;
    }


    @Override
    public Object getItem(int position) {
        return listVO.get(position) ;
    }

    // 데이터값 넣어줌
    public void addVO(Boolean value, String title, String desc) {
        ListVO item = new ListVO();

        item.setChecked(value);
        item.setTitle(title);
        item.setContext(desc);

        listVO.add(item);
    }

    public void visibleCheck(Boolean value){
        if(value==true)
            check.setVisibility(View.VISIBLE);
        else
            check.setVisibility(View.GONE);
    }
}