package com.example.mypets2;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DataBaseHelper extends SQLiteOpenHelper {
    private static final String DATA_BASE_NAME = "pets";
    private String tableName;

    private static final int DATA_BASE_VERSION = 1;

    public DataBaseHelper(Context context, String tableName){
        super(context, DATA_BASE_NAME, null, DATA_BASE_VERSION);
        this.tableName = tableName;
    }

    @Override
    public void onCreate(SQLiteDatabase db){
        db.execSQL(
                String.format(
                        "CREATE TABLE IF NOT EXISTS %s(id integer primary key autoincrement, name text, age int)",
                        this.tableName
                )
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){}
}
