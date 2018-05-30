package com.namjongbin.fridge_angel;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {

    // DBHelper 생성자로 관리할 DB 이름과 버전 정보를 받음
    public DBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    // DB를 새로 생성할 때 호출되는 함수
    @Override
    public void onCreate(SQLiteDatabase db) {
        // 새로운 테이블 생성
        /* 이름은 MONEYBOOK이고, 자동으로 값이 증가하는 _id 정수형 기본키 컬럼과
        item 문자열 컬럼, price 정수형 컬럼, create_at 문자열 컬럼으로 구성된 테이블을 생성. */
        db.execSQL("CREATE TABLE ITEM (_id INTEGER PRIMARY KEY AUTOINCREMENT, item TEXT, year INT, month INT, day INT);");
    }

    // DB 업그레이드를 위해 버전이 변경될 때 호출되는 함수
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String sql = "DROP TABLE IF EXISTS ITEM";
        db.execSQL(sql);
        //db.execSQL("CREATE TABLE ITEM (_id INTEGER PRIMARY KEY AUTOINCREMENT, item TEXT, year INT, month INT, day INT);");
        onCreate(db);
    }

    public void insert(String item, int year,int month,int day) {
        // 읽고 쓰기가 가능하게 DB 열기
        SQLiteDatabase db = getWritableDatabase();
        // DB에 입력한 값으로 행 추가
        db.execSQL("INSERT INTO ITEM VALUES(null, '" + item + "'," + year +","+ month +","+ day +  ");");
        db.close();
    }

    public void update(int id, String item, int year, int month, int day) {
        SQLiteDatabase db = getWritableDatabase();
        // 입력한 항목과 일치하는 행의 가격 정보 수정
        db.execSQL("UPDATE ITEM SET item ='"+ item + "', year=" + year + " ,month=" + month + ",day= " + day + " WHERE _id=" + id + ";");
        db.close();
    }

    public void delete(int id) {
        SQLiteDatabase db = getWritableDatabase();
        // 입력한 항목과 일치하는 행 삭제
        db.execSQL("DELETE FROM ITEM WHERE _id=" + id + ";");
        db.close();
    }
    public int[] getDate(int id){
        int[] date = new int[3];
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT year, month, day FROM ITEM WHERE _id = " + id + ";",null);
        while(cursor.moveToNext()){
            date[0] = cursor.getInt(0);
            date[1] = cursor.getInt(1);
            date[2] = cursor.getInt(2);
        }
        return date;
    }

    public int getId(String item, int year,int month, int day){
        int result = 0;
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT _id FROM ITEM WHERE item= '" + item + "'and year="+year + " and month ="+month + " and day =" + day+";",null);
        while(cursor.moveToNext()) {
            result = cursor.getInt(0);
        }
        return result;
    }
    public String getItem(int id){
        String result="";
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT item FROM ITEM WHERE _id = " + id + ";",null);
        while(cursor.moveToNext()) {
            result = cursor.getString(0);
        }
        return result;
    }

    public String getResult() {
        // 읽기가 가능하게 DB 열기
        SQLiteDatabase db = getReadableDatabase();
        String result = "";

        // DB에 있는 데이터를 쉽게 처리하기 위해 Cursor를 사용하여 테이블에 있는 모든 데이터 출력
        Cursor cursor = db.rawQuery("SELECT * FROM ITEM ORDER BY year ASC,month ASC,day ASC;", null);
        while (cursor.moveToNext()) {
            result += cursor.getString(0)
                    +" "
                    + cursor.getString(1)
                    +":"
                    + cursor.getString(2)
                    + "년 "
                    + cursor.getString(3)
                    + "월 "
                    + cursor.getString(4)
                    + "일\n";
        }
        return result;
    }
    public void setIndex(){
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("UPDATE SQLITE_SEQUENCE SET seq =1 WHERE name = 'ITEM';");
        db.close();
    }
    public int columnNum(){
        int result = 0;
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM ITEM", null);
        result = cursor.getCount();
        System.out.println("아이템이 디비에 몇개 있나" + result);
        return result;

    }
    public String getExpiredItem(int year,int month,int day) {
        String result = "";
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM ITEM WHERE  year=" + year + " and month =" + month + " and day =" + day + ";", null);
        while (cursor.moveToNext()) {
            result += cursor.getString(0)
                    + " "
                    + cursor.getString(1)
                    + ":"
                    + cursor.getString(2)
                    + "년 "
                    + cursor.getString(3)
                    + "월 "
                    + cursor.getString(4)
                    + "일\n";
        }
        return result;
    }
}
