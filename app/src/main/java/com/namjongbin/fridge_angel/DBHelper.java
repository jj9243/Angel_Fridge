package com.namjongbin.fridge_angel;

/**
 * @brief  Managing and processing Database
 * @details Insert, Delete, Deformed, update DataBase
 * @author Seok bin Im
 */

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {


    // DBHelper Creator, Receive DB name and version
    public DBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // new table
        db.execSQL("CREATE TABLE ITEM (_id INTEGER PRIMARY KEY AUTOINCREMENT, item TEXT, year INT, month INT, day INT);");
    }

    // Upgrade DB
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String sql = "DROP TABLE IF EXISTS ITEM";
        db.execSQL(sql);
        onCreate(db);
    }

    public void insert(String item, int year,int month,int day) {
        // Load DB able to read
        SQLiteDatabase db = getWritableDatabase();
        // Add DB to data
        db.execSQL("INSERT INTO ITEM VALUES(null, '" + item + "'," + year +","+ month +","+ day +  ");");
        db.close();
    }

    public void update(int id, String item, int year, int month, int day) {
        SQLiteDatabase db = getWritableDatabase();
        // changing
        db.execSQL("UPDATE ITEM SET item ='"+ item + "', year=" + year + " ,month=" + month + ",day= " + day + " WHERE _id=" + id + ";");
        db.close();
    }

    public void delete(int id) {
        SQLiteDatabase db = getWritableDatabase();
        // check ID and Delete
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
        db.close();
        return date;
    }

    public int getId(String item, int year,int month, int day){
        int result = 0;
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT _id FROM ITEM WHERE item= '" + item + "'and year="+year + " and month ="+month + " and day =" + day+";",null);
        while(cursor.moveToNext()) {
            result = cursor.getInt(0);
        }
        db.close();
        return result;
    }
    public String getItem(int id){
        String result="";
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT item FROM ITEM WHERE _id = " + id + ";",null);
        while(cursor.moveToNext()) {
            result = cursor.getString(0);
        }
        db.close();
        return result;
    }

    public String getResult() {
        // Load DB
        SQLiteDatabase db = getReadableDatabase();
        String result = "";

        // Processing DB. all of Data process using Cursor
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
        db.close();
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
        db.close();
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
        db.close();
        return result;
    }
}
