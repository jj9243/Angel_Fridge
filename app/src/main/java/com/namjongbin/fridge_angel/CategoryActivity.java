package com.namjongbin.fridge_angel;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

public class CategoryActivity extends Activity {
    public ExpandableListView expandableListView; // ExpandableListView 변수 선언
    public CustomExpandableListViewAdapter mCustomExpListViewAdapter; // 위 ExpandableListView를 받을 CustomAdapter(2번 class에 해당)를 선언
    public ArrayList<String> parentList; // ExpandableListView의 Parent 항목이 될 List 변수 선언
    public ArrayList<ChildListData> fruit; // ExpandableListView의 Child 항목이 될 List를 각각 선언
    public ArrayList<ChildListData> vegetables;
    public ArrayList<ChildListData> meat;
    public ArrayList<ChildListData> fish;
    public ArrayList<ChildListData> etc;
    public HashMap<String, ArrayList<ChildListData>> childList; // 위 ParentList와 ChildList를 연결할 HashMap 변수 선언

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categry); // activity_main.xml을 MainActivity에 연결

        // ExpandableListView의 ParentList에 해당할 항목을 입력
        parentList = new ArrayList<String>();
        parentList.add("과일");
        parentList.add("채소");
        parentList.add("어패류");
        parentList.add("육류");
        parentList.add("기타");


        // 앞서 ParentList에 연결할 ChildList 항목을 선언 및 입력
        ChildListData apple = new ChildListData(getResources().getDrawable(R.mipmap.apple), "사과","3주");
        ChildListData pair = new ChildListData(getResources().getDrawable(R.mipmap.pear), "배","일주일");
        ChildListData banana = new ChildListData(getResources().getDrawable(R.mipmap.banana), "바나나","6일");
        ChildListData blueberry = new ChildListData(getResources().getDrawable(R.mipmap.blueberries), "블루베리","일주일");
        ChildListData grape = new ChildListData(getResources().getDrawable(R.mipmap.grapes), "포도","4일");
        ChildListData peach = new ChildListData(getResources().getDrawable(R.mipmap.peach), "복숭아","5일");
        ChildListData arbo = new ChildListData(getResources().getDrawable(R.mipmap.avocado), "아보카도","5일");
        ChildListData watermelon = new ChildListData(getResources().getDrawable(R.mipmap.watermelon), "수박","5일");
        ChildListData lemon= new ChildListData(getResources().getDrawable(R.mipmap.lemon), "레몬","3주");
        ChildListData strawberry = new ChildListData(getResources().getDrawable(R.mipmap.strawberry), "딸기","3일");

        fruit = new ArrayList<ChildListData>();
        fruit.add(apple);
        fruit.add(pair);
        fruit.add(banana);
        fruit.add(blueberry);
        fruit.add(grape);
        fruit.add(peach);
        fruit.add(arbo);
        fruit.add(watermelon);
        fruit.add(lemon);
        fruit.add(strawberry );


        ChildListData onion = new ChildListData(getResources().getDrawable(R.mipmap.onion), "양파","4일");
        ChildListData cabbage = new ChildListData(getResources().getDrawable(R.mipmap.cabbage), "양배추","10일");
        ChildListData potato = new ChildListData(getResources().getDrawable(R.mipmap.potatoes), "감자","4일");
        ChildListData sweetPotato = new ChildListData(getResources().getDrawable(R.mipmap.sweetpotato), "고구마", "4일");
        ChildListData carrot = new ChildListData(getResources().getDrawable(R.mipmap.carrot), "당근", "2주");
        ChildListData mu = new ChildListData(getResources().getDrawable(R.mipmap.radish), "무", "일주일");
        ChildListData ssam = new ChildListData(getResources().getDrawable(R.mipmap.salad1), "쌈 채소", "4일");
        ChildListData pimang = new ChildListData(getResources().getDrawable(R.mipmap.pepper), "피망", "4일");
        ChildListData cucumber = new ChildListData(getResources().getDrawable(R.mipmap.cucumber), "오이", "일주일");

        vegetables = new ArrayList<ChildListData>();
        vegetables.add(onion);
        vegetables.add(cabbage);
        vegetables.add(potato);
        vegetables.add(sweetPotato);
        vegetables.add(carrot);
        vegetables.add(mu);
        vegetables.add(ssam);
        vegetables.add(pimang);
        vegetables.add(cucumber);

        ChildListData kkong = new ChildListData(getResources().getDrawable(R.mipmap.fish), "꽁치","2개월");
        ChildListData bluefish = new ChildListData(getResources().getDrawable(R.mipmap.fish), "고등어","3개월");
        ChildListData yeonfish = new ChildListData(getResources().getDrawable(R.mipmap.salmon), "훈제연어", "한달");
        ChildListData kalchi = new ChildListData(getResources().getDrawable(R.mipmap.fish), "갈치", "3개월");
        ChildListData oging = new ChildListData(getResources().getDrawable(R.mipmap.octopus), "오징어", "한달");
        ChildListData jogae = new ChildListData(getResources().getDrawable(R.mipmap.clam), "조개류", "한달");
        ChildListData goll = new ChildListData(getResources().getDrawable(R.mipmap.clam), "굴", "4개월");
        ChildListData shrimp = new ChildListData(getResources().getDrawable(R.mipmap.shrimp), "새우", "6개월");

        fish = new ArrayList<ChildListData>();
        fish.add(kkong);
        fish.add(bluefish);
        fish.add(yeonfish);
        fish.add(kalchi);
        fish.add(oging);
        fish.add(jogae);
        fish.add(goll);
        fish.add(shrimp);


        ChildListData pig = new ChildListData(getResources().getDrawable(R.mipmap.meat1), "돼지","5일");
        ChildListData cow = new ChildListData(getResources().getDrawable(R.mipmap.steak), "쇠고기","5일");
        ChildListData chicken = new ChildListData(getResources().getDrawable(R.mipmap.meat), "닭고기", "2일");
        ChildListData pigI = new ChildListData(getResources().getDrawable(R.mipmap.meat1), "냉동 돼지고기", "6개월");
        ChildListData cowI = new ChildListData(getResources().getDrawable(R.mipmap.steak), "냉동 쇠고기", "6개월");
        ChildListData chickenI = new ChildListData(getResources().getDrawable(R.mipmap.meat), "냉동 닭고기", "1년");

       meat = new ArrayList<ChildListData>();
        meat.add(pig);
        meat.add(cow);
        meat.add(chicken);
        meat.add(pigI);
        meat.add(cowI);
        meat.add(chickenI);

        ChildListData yo = new ChildListData(getResources().getDrawable(R.mipmap.dairy), "요거트", "일주일");
        ChildListData egg = new ChildListData(getResources().getDrawable(R.mipmap.egg), "달걀", "한달");
        ChildListData coffee = new ChildListData(getResources().getDrawable(R.mipmap.coffee), "액상커피", "한달");
        etc = new ArrayList<ChildListData>();
        etc.add(yo);
        etc.add(egg);
        etc.add(coffee);

        // 위에서 선언한 ParentList와 ChildList를 HashMap을 통해
        childList = new HashMap<String, ArrayList<ChildListData>>();
        childList.put(parentList.get(0), fruit);
        childList.put(parentList.get(1), vegetables);
        childList.put(parentList.get(2), fish);
        childList.put(parentList.get(3), meat);
        childList.put(parentList.get(4), etc);

        // 앞서 정의해 놓은 ExpandableListView와 그 CustomAdapter를 선언 및 연결한 후
        // ExpandableListView에 대한 OnClickListener 등을 선언
        expandableListView = (ExpandableListView)findViewById(R.id.expandablelist);
        mCustomExpListViewAdapter = new CustomExpandableListViewAdapter(this, parentList, childList);
        expandableListView.setAdapter(mCustomExpListViewAdapter);

        expandableListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
            @Override
            public void onGroupExpand(int groupPosition) {
            }
        });
        expandableListView.setOnGroupCollapseListener(new ExpandableListView.OnGroupCollapseListener() {
            @Override
            public void onGroupCollapse(int groupPosition) {
            }
        });

        expandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                Log.i("aka","G" +groupPosition+"  C" +childPosition+ "id "+id);
                int day=0;
                String item="";
                if(groupPosition==0)
                {
                    if(childPosition==0) {
                        day = 21;
                        item="사과";   //apple
                    }else if(childPosition==1)
                    {
                        day=7;      //pear
                        item="배";
                    }
                    else if(childPosition==2)
                    {
                        day=6;      //banana
                        item="바나나";
                    }
                    else if(childPosition==3)
                    {
                        day=7;      //blueb
                        item="블루베리";
                    }
                    else if(childPosition==4)
                    {
                        day=4;      //grape
                        item="포도";
                    }
                    else if(childPosition==5)
                    {
                        day=5;      //peach
                        item="복숭아";
                    }
                    else if(childPosition==6)
                    {
                        day=5;      //arbo
                        item="아보카도";
                    }
                    else if(childPosition==7)
                    {
                        day=5;      //water
                        item="수박";
                    }
                    else if(childPosition==8)
                    {
                        day=21;      //lemon
                        item="레몬";
                    }
                    else if(childPosition==9)
                    {
                        day=3;      //straw
                        item="딸기";
                    }
                }
                else if(groupPosition==1)
                {
                    if(childPosition==0)
                    {
                        day=4;     //onion
                        item="양파";
                    }
                    else if(childPosition==1)
                    {
                        day=10;      //양배추
                        item="양배추";
                    }
                    else if(childPosition==2)
                    {
                        day=4;      //potato
                        item="감자";
                    }
                    else if(childPosition==3)
                    {
                        day=4;      //sweetpotato
                        item="고구마";
                    }
                    else if(childPosition==4)
                    {
                        day=14;      //carrot
                        item="당근";
                    }
                    else if(childPosition==5)
                    {
                        day=7;      //무
                        item="무";
                    }
                    else if(childPosition==6)
                    {
                        day=4;      //쌈채소
                        item="쌈채소";
                    }
                    else if(childPosition==7)
                    {
                        day=4;      //피망
                        item="피망";
                    }
                    else if(childPosition==8)
                    {
                        day=7;      //오이
                        item="오이";
                    }
                }
                else if(groupPosition==2)
                {
                    //ㅇㅓ패류
                    Toast.makeText(getApplicationContext(),"어패류는 냉동보관 기준입니다",Toast.LENGTH_LONG).show();
                    if(childPosition==0)
                    {
                        day=60;
                        item="꽁치";
                    }
                    else if(childPosition==1)
                    {
                        day=90;
                        item="고등어";
                    }
                    else if(childPosition==2)
                    {
                        day=30;
                        item="훈제연어";
                    }
                    else if(childPosition==3)
                    {
                        day=180;
                        item="갈치";
                    }
                    else if(childPosition==4)
                    {
                        day=30;
                        item="오징어";
                    }
                    else if(childPosition==5)
                    {
                        day=30;
                        item="조개류";
                    }
                    else if(childPosition==6)
                    {
                        day=120;
                        item="굴";
                    }
                    else if(childPosition==7) {
                        day = 180;
                        item="새우";
                    }
                }
                else if(groupPosition==3)
                {
                    //고기
                    if(childPosition==0)
                    {
                        day=5;     //onion
                        item="돼지고기";
                    }
                    else if(childPosition==1)
                    {
                        day=5;      //양배추
                        item="쇠고기";
                    }
                    else if(childPosition==2)
                    {
                        day=2;
                        item="닭고기";
                    }
                    else if(childPosition==3)
                    {
                        day=180;      //sweetpotato
                        item="냉동 돼지";
                    }
                    else if(childPosition==4)
                    {
                        day=180;      //carrot
                        item="냉동 쇠고기";
                    }
                    else if(childPosition==5)
                    {
                        day=365;      //무
                        item="냉동 닭";
                    }
                }
                else{
                    if(childPosition==0)
                    {
                        day=7;     //onion
                        item="요거트";
                    }
                    else if(childPosition==1)
                    {
                        day=30;      //양배추
                        item="달걀";
                    }
                    else if(childPosition==2)
                    {
                        day=30;      //양배추
                        item="액상커피";
                    }
                }

                addToList(item,day);
                finish();
                return false;
            }
        });
    }

    private void addToList(String i, int d) {
        final DBHelper db = new DBHelper(getApplicationContext(), "ITEM.db", null, 2);

//        long now=System.currentTimeMillis();
//        Date date=new Date(now);
//        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");


        Calendar cal = Calendar.getInstance();

        cal.setTime(new Date());
        cal.add(Calendar.DATE,d);

        int year = cal.get(cal.YEAR);
        int month = cal.get(cal.MONTH) + 1;
        int day = cal.get(cal.DATE);

        String item = i;
        db.insert(item.trim(), year, month, day);

        Intent intent = new Intent(getApplicationContext(), ListActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }
}
