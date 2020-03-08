package com.example.mypets2.controllers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.mypets2.DataBaseHelper;
import com.example.mypets2.models.Cat;

import java.util.ArrayList;

public class CatsController {
    private DataBaseHelper dataBaseHelper;
    private String TABLE_NAME = "cats";

    public CatsController(Context context){
        this.dataBaseHelper = new DataBaseHelper(context, this.TABLE_NAME);
    }

    public long newCat(Cat cat){
        SQLiteDatabase database = dataBaseHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", cat.getName());
        contentValues.put("age", cat.getAge());
        return database.insert(TABLE_NAME, null, contentValues);
    }

    public int updateCat(Cat editedCat){
        SQLiteDatabase database = dataBaseHelper.getWritableDatabase();
        ContentValues updateValues = new ContentValues();
        updateValues.put("name", editedCat.getName());
        updateValues.put("age", editedCat.getAge());
        // where id...
        String updateColumn = "id = ?";
        // = idCat
        String[] updateArguments = {String.valueOf(editedCat.getId())};
        return database.update(TABLE_NAME, updateValues, updateColumn, updateArguments);
    }

    public int deleteCat(Cat cat){
        SQLiteDatabase database = dataBaseHelper.getWritableDatabase();
        String[] deleteArguments = {String.valueOf(cat.getId())};
        return database.delete(TABLE_NAME, "id = ?", deleteArguments);
    }

    public ArrayList<Cat> getCats(){
        ArrayList<Cat> cats = new ArrayList<>();
        SQLiteDatabase database = dataBaseHelper.getReadableDatabase();
        String[] consultedColumns = {"name", "age", "id"};

        Cursor cursor = database.query(
                TABLE_NAME,
                consultedColumns,
                null,
                null,
                null,
                null,
                null
        );

        if (cursor == null){
            return cats;
        }

        if (!cursor.moveToFirst()) return cats;

        do {
            String nameFromDatabase = cursor.getString(0);
            int ageFromDatabase = cursor.getInt(1);
            long catId = cursor.getLong(2);

            cats.add(new Cat(nameFromDatabase, ageFromDatabase, catId));
        } while (cursor.moveToNext());

        cursor.close();
        return cats;
    }
}
