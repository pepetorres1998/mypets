package com.example.mypets.controllers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.mypets.DataBaseHelper;
import com.example.mypets.models.Dog;

import java.util.ArrayList;

public class DogsController {
    private DataBaseHelper dataBaseHelper;
    private String TABLE_NAME = "dogs";

    public DogsController(Context context){
        this.dataBaseHelper = new DataBaseHelper(context, this.TABLE_NAME);
    }

    public long newDog(Dog dog){
        SQLiteDatabase database = dataBaseHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", dog.getName());
        contentValues.put("age", dog.getAge());
        return database.insert(TABLE_NAME, null, contentValues);
    }

    public int updateDog(Dog editedDog){
        SQLiteDatabase database = dataBaseHelper.getWritableDatabase();
        ContentValues updateValues = new ContentValues();
        updateValues.put("name", editedDog.getName());
        updateValues.put("age", editedDog.getAge());
        // where id...
        String updateColumn = "id = ?";
        // = idDog
        String[] updateArguments = {String.valueOf(editedDog.getId())};
        return database.update(TABLE_NAME, updateValues, updateColumn, updateArguments);
    }

    public int deleteDog(Dog dog){
        SQLiteDatabase database = dataBaseHelper.getWritableDatabase();
        String[] deleteArguments = {String.valueOf(dog.getId())};
        return database.delete(TABLE_NAME, "id = ?", deleteArguments);
    }

    public ArrayList<Dog> getDogs(){
        ArrayList<Dog> dogs = new ArrayList<>();
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
            return dogs;
        }

        if (!cursor.moveToFirst()) return dogs;

        do {
            String nameFromDatabase = cursor.getString(0);
            int ageFromDatabase = cursor.getInt(1);
            long dogId = cursor.getLong(2);

            dogs.add(new Dog(nameFromDatabase, ageFromDatabase, dogId));
        } while (cursor.moveToNext());

        cursor.close();
        return dogs;
    }
}
