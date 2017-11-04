package com.ggg.testapi.utils;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.ggg.testapi.data.ItemData;

import java.util.ArrayList;

public class DBManager extends SQLiteOpenHelper {

    public DBManager(Context context, String name,
                     SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE MOMO(item TEXT PRIMARY KEY, selected INTEGER, stage INTEGER);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void insert(String item, int selected,int stage) {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("insert into momo values('" + item + "', " + stage+ ","+ selected+");");
        db.close();
    }

    public void update(String item, int selected) {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("update momo set selected = " + selected + " where item = '"+ item +"';");
        db.close();


    }


    public ArrayList<ItemData> getResult(int stage) {
        // 읽기가 가능하게 DB 열기
        SQLiteDatabase db = getReadableDatabase();
        ArrayList<ItemData> resultArr =  new ArrayList<>();

        Cursor cursor = db.rawQuery("SELECT * FROM MOMO WHERE stage="+stage+";", null);
        while (cursor.moveToNext()) {
            resultArr.add(new ItemData(cursor.getString(0),cursor.getInt(1)));

        }

        return resultArr;
    }



}