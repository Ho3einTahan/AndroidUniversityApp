package com.example.androidjavauniversity;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.Cursor;

import java.util.ArrayList;
import java.util.List;

import model.User;

class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "mydatabase.db";
    private static final int DATABASE_VERSION = 1;

    // نام جدول و ستون‌ها
    private static final String TABLE_USERS = "users";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_NAME = "name";
    private static final String COLUMN_EMAIL = "email";

    // SQL برای ایجاد جدول
    private static final String CREATE_TABLE_USERS = "CREATE TABLE " + TABLE_USERS + " (" +
            COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            COLUMN_NAME + " TEXT, " +
            COLUMN_EMAIL + " TEXT);";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // ایجاد دیتابیس و جدول‌ها
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_USERS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
        onCreate(db);
    }

    // افزودن کاربر به دیتابیس
    public void addUser(String name, String email) {
        SQLiteDatabase db = this.getWritableDatabase();
        String insertSQL = "INSERT INTO " + TABLE_USERS + " (" + COLUMN_NAME + ", " + COLUMN_EMAIL + ") VALUES (?, ?)";
        db.execSQL(insertSQL, new Object[]{name, email});
        db.close();
    }

    public List<User> readUsers() {
        SQLiteDatabase db = this.getReadableDatabase();
        String selectSQL = "SELECT * FROM " + TABLE_USERS;
        Cursor cursor = db.rawQuery(selectSQL, null);

        List<User> userList = new ArrayList<>();

        if (cursor.moveToFirst()) {
            do {

                int id = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID));
                String name = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NAME));
                String email = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_EMAIL));

                User user = new User(id, name, email);
                userList.add(user);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return userList;
    }

    public void deleteUser(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        String deleteSQL = "DELETE FROM " + TABLE_USERS + " WHERE " + COLUMN_ID + " = ?";
        db.execSQL(deleteSQL, new Object[]{id});
        db.close();
    }
}
