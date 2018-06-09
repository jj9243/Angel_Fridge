package com.namjongbin.fridge_angel;
import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class ListViewAdapter extends ArrayAdapter {
    CheckBox check;
    TextView title;
    TextView content;
    ListView listView;
    private ArrayList<ListVO> listVO = new ArrayList<ListVO>() ;
    private boolean mClick = false;
    private ArrayList<Integer> checkArr = new ArrayList<>();
    int year,month,day;
    private ArrayList<Integer> listItem;

    public ListViewAdapter(Context context, int textViewResourceId, String[] items){
        super(context, textViewResourceId, items);
        listItem = new ArrayList();
    }

    @Override
    public int getCount() {
        return listVO.size() ;
    }

    public ArrayList<Integer> getCheckArr(){
        return listItem;
    }

    // ** 이 부분에서 리스트뷰에 데이터를 넣어줌 **
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //postion = Listew의 위치      /   첫번째면 position = 0
        final int pos = position;
        final Context context = parent.getContext();
        final DBHelper db = new DBHelper(parent.getContext(),"ITEM.db",null,2);
        final ListVO listViewItem = listVO.get(position);

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.list_item, parent, false);
        }

        ImageButton ytBtn=convertView.findViewById(R.id.ytButton);
        check = convertView.findViewById(R.id.checkbox);
        title = (TextView) convertView.findViewById(R.id.title);
        content = (TextView) convertView.findViewById(R.id.context);

        // 아이템 내 각 위젯에 데이터 반영
        title.setText(listViewItem.getTitle());
        content.setText(listViewItem.getContext());

        //리스트뷰 클릭 이벤트
        convertView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                ListVO list = (ListVO)listVO.get(pos);
                String item = list.getTitle();
                String date = list.getContext();
                parseItem(date);
                Intent intent = new Intent(context,AddActivity.class);
                System.out.println("*****************************아이디: "+db.getId(item.trim(),year,month,day));
                intent.putExtra("item_id",db.getId(item.trim(),year,month,day));
                System.out.println("*************&&&&&&&&&&&&&&&&&&&품목 이름: "+item.trim());
                intent.putExtra("item",item.trim());
                intent.putExtra("date",date);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                Toast.makeText(context, (pos+1)+"번째 리스트가 클릭되었습니다.", Toast.LENGTH_SHORT).show();
                context.startActivity(intent);

                return true;
            }
        });

        //http://chan180.tistory.com/102
        if (check != null) { // 체크박스의 상태 변화를 체크한다.
            check.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    // 체크를 할 때 if (isChecked)
                    {
                        if (isChecked) {
                            for (int i = 0; i < listItem.size(); i++) {
                                if (listItem.get(i) == pos) {
                                    return;
                                }
                            }
                            listItem.add(pos);
                            // 체크가 해제될 때
                        } else {
                            for (int i = 0; i < listItem.size(); i++) {
                                if (listItem.get(i) == pos) {
                                    listItem.remove(i);
                                    break;
                                }
                            }
                        }
                    }
                }
            });
            // 체크된 아이템인지 판단할 boolean변수
            boolean isChecked = false;
            for (int i = 0; i < listItem.size(); i++)
            { // 만약 체크되었던 아이템이라면
                if (listItem.get(i) == pos) { // 체크를 한다.
                    check.setChecked(true);
                    isChecked = true;
                    break; }
            } // 아니라면 체크 안함!
             if (!isChecked) {
                check.setChecked(false);
            }
        }

        ytBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String fname = listVO.get(pos).getTitle().trim().toString() + " " + "레시피";

                Intent intent = new Intent(Intent.ACTION_SEARCH);
                intent.setPackage("com.google.android.youtube");

                intent.putExtra("query", fname);

                try {
                    context.startActivity(intent);
                } catch (ActivityNotFoundException e) {
                }
            }
        });

        if(mClick) {
            check.setVisibility(View.VISIBLE);
        } else {
            check.setVisibility(View.GONE);
        }
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


        item.setTitle(title);
        item.setContext(desc);

        listVO.add(item);
    }
    public void toggleCheckBox(boolean bClick) {
        mClick = bClick;
        notifyDataSetChanged();
    }

    public void parseItem(String item){

        Date today = new Date();
        SimpleDateFormat date = new SimpleDateFormat("yyyy년MM월dd일");
        String d = date.format(today).toString();

        if(item.contains("년")) {
            year = Integer.parseInt(item.substring(item.indexOf('년') - 4, item.indexOf('년')));

        }
        else
            year = Integer.parseInt(d.substring(d.indexOf('년')-4,d.indexOf('년')));
        if(item.contains("월"))
            month = Integer.parseInt(item.substring(item.indexOf('월')-2,item.indexOf('월')).trim());
        else
            month = Integer.parseInt(d.substring(d.indexOf('월')-2,d.indexOf('월')));
        if(item.contains("일"))
            day = Integer.parseInt(item.substring(item.indexOf('일')-2,item.indexOf('일')).trim());
        else
            day = Integer.parseInt(d.substring(d.indexOf('일')-2,d.indexOf('일')));

    }

}
