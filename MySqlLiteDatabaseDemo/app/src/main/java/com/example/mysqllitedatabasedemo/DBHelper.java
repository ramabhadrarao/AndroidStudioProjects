package com.example.mysqllitedatabasedemo;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.*;

import androidx.annotation.Nullable;

public class DBHelper extends SQLiteOpenHelper {
    public DBHelper(@Nullable Context context) {
        super(context, "StudentDB", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE student (id INTEGER PRIMARY KEY AUTOINCREMENT , name TEXT )");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS student");
        onCreate(db);
    }

    public void insertData(String name){
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues cv=new ContentValues();
        cv.put("name",name);
        
        db.insert("student",null,cv);
    }
    public Cursor getData(){
        SQLiteDatabase db=this.getReadableDatabase();
        return db.rawQuery("select * from student",null);
    }
}
