package com.example.mypets2.controllers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.mypets2.DataBaseHelper;
import com.example.mypets2.models.User;

public class UsersController {
    private DataBaseHelper dataBaseHelper;
    private String TABLE_NAME = "users";

    public UsersController(Context context) {
        this.dataBaseHelper = new DataBaseHelper(context, this.TABLE_NAME);
    }

    public long registerUser(User user) {
        SQLiteDatabase database = dataBaseHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("email", user.getEmail());
        contentValues.put("password", user.getPassword());
        return database.insert(TABLE_NAME, null, contentValues);
    }

    public User getUser(String email) {
        User user = new User();
        SQLiteDatabase database = dataBaseHelper.getReadableDatabase();
        String[] selectionArgs = {email};
        Cursor cursor = database.rawQuery("SELECT * FROM users WHERE email = ?", selectionArgs);

        if (cursor == null) {
            return user;
        }

        if (!cursor.moveToFirst()) return user;

        cursor.moveToFirst();
        user.setEmail(cursor.getString(cursor.getColumnIndex("email")));
        user.setPassword(cursor.getString(cursor.getColumnIndex("password")));

        cursor.close();
        return user;
    }
}
