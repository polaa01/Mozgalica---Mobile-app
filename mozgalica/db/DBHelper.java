package com.example.mozgalica.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.mozgalica.db.model.GameResult;

import java.util.ArrayList;
import java.util.List;


public class DBHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "mozgalica.db";
    public static final int DATABASE_VERSION = 1;
    public static final String TABLE_USER = "User";
    public static final String COLUMN_USERNAME = "username";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_GAME_NAME = "game_name";
    public static final String COLUMN_SCORE = "score";

    public static final String TABLE_RESULT = "GameResult";
    public DBHelper(Context context)
    {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);

    }


    @Override
    public void onCreate(SQLiteDatabase db)
    {
        String createUserTable = "CREATE TABLE " + TABLE_USER + " (" +
                COLUMN_USERNAME + " TEXT PRIMARY KEY)";


        db.execSQL(createUserTable);



        String createResultTable = "CREATE TABLE " + TABLE_RESULT + " (" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_USERNAME + " TEXT, " +
                COLUMN_GAME_NAME + " TEXT, " +
                COLUMN_SCORE + " INTEGER, " +
                "FOREIGN KEY (" + COLUMN_USERNAME + ") REFERENCES " + TABLE_USER + "(" + COLUMN_USERNAME + "))";
        db.execSQL(createResultTable);
    }



    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_RESULT);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER);
        onCreate(db);
    }


    public boolean doesUserExists(String username)
    {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_USER,
                new String[]{COLUMN_USERNAME},
                COLUMN_USERNAME + "=?",
                new String[]{username},
                null, null, null);
        boolean result = cursor.moveToFirst();
        cursor.close();
        return result;
    }
    public boolean insertUser(String username)
    {
        if(this.doesUserExists(username))
        {
            return false;
        }
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_USERNAME, username);

        long result = db.insert(TABLE_USER, null, values);
        return result != -1;

    }

    public void insertGameResult(GameResult gameResult)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(COLUMN_GAME_NAME, gameResult.getGame());
        values.put(COLUMN_USERNAME, gameResult.getUsername());
        values.put(COLUMN_SCORE, gameResult.getScore());


        db.insert(TABLE_RESULT, null, values);
    }

    public void deleteAllGameResults() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete("GameResult", null, null);
    }


    public List<GameResult> getAllResults()
    {
        List<GameResult> list = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM GameResult", null);

        if (cursor.moveToFirst()) {
            do {
                GameResult result = new GameResult(
                        cursor.getInt(cursor.getColumnIndexOrThrow("id")),
                        cursor.getString(cursor.getColumnIndexOrThrow("username")),
                        cursor.getString(cursor.getColumnIndexOrThrow("game_name")),
                        cursor.getInt(cursor.getColumnIndexOrThrow("score"))
                );
                list.add(result);
            } while (cursor.moveToNext());
        }

        cursor.close();
        return list;
    }


    public List<GameResult> getResultsByUsernameOrGameName(String filter)
    {
        List<GameResult> list = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM GameResult WHERE username LIKE ? OR game_name LIKE ?",
                new String[]{"%" + filter + "%", "%" + filter + "%"});

        if (cursor.moveToFirst()) {
            do {
                GameResult result = new GameResult(
                        cursor.getInt(cursor.getColumnIndexOrThrow("id")),
                        cursor.getString(cursor.getColumnIndexOrThrow("username")),
                        cursor.getString(cursor.getColumnIndexOrThrow("game_name")),
                        cursor.getInt(cursor.getColumnIndexOrThrow("score"))
                );
                list.add(result);
            } while (cursor.moveToNext());
        }

        cursor.close();
        return list;
    }



/*
    public List<GameResult> getResultsByGameName(String gameName)
    {
        List<GameResult> list = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM GameResult WHERE gameName LIKE ?",
                new String[]{"%" + gameName + "%"});

        if (cursor.moveToFirst()) {
            do {
                GameResult result = new GameResult(
                        cursor.getInt(cursor.getColumnIndexOrThrow("id")),
                        cursor.getString(cursor.getColumnIndexOrThrow("username")),
                        cursor.getString(cursor.getColumnIndexOrThrow("game_name")),
                        cursor.getInt(cursor.getColumnIndexOrThrow("score"))
                );
                list.add(result);
            } while (cursor.moveToNext());
        }

        cursor.close();
        return list;
    }
*/


    public List<GameResult> getResultsByScore(int score)
    {
        List<GameResult> list = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM GameResult WHERE score = ?",
                new String[]{String.valueOf(score)});

        if (cursor.moveToFirst()) {
            do {
                GameResult result = new GameResult(
                        cursor.getInt(cursor.getColumnIndexOrThrow("id")),
                        cursor.getString(cursor.getColumnIndexOrThrow("username")),
                        cursor.getString(cursor.getColumnIndexOrThrow("game_name")),
                        cursor.getInt(cursor.getColumnIndexOrThrow("score"))
                );
                list.add(result);
            } while (cursor.moveToNext());
        }

        cursor.close();
        return list;
    }



















}
