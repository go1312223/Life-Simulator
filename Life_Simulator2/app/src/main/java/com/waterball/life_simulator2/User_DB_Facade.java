package com.waterball.life_simulator2;

import android.database.Cursor;
import android.database.SQLException;
import android.util.Log;

/**
 * Created by Administrator on 2016/11/6.
 */

public class User_DB_Facade extends DB_Facade {
    private static final String USER_NAME = "userName";
    private static final String USER_EXP = "userexp";
    private static final String USER_LV = "userlv";
    private static final String USER_SEX = "usersex";
    public static final int COLUMN_ID = 0;
    public  static final int COLUMN_TEXT_NAME = 1;
    public  static final int COLUMN_INT_EXP = 2;
    public  static final int COLUMN_INT_LV = 3;
    public  static final int COLUMN_INT_SEX = 4;

    public User_DB_Facade(){
        super("User_Table");
        try
        {
            createTable();
        }catch (SQLException err){
            Log.d("myLog",err.toString());
        }
    }
    @Override
    public void createTable() throws SQLException {
        try{
            db.execSQL("CREATE TABLE " + TABLE_NAME + " (_id INTEGER PRIMARY KEY ," + USER_NAME + " TEXT ," + USER_EXP + " INTEGER ," + USER_LV + " INTEGER ," + USER_SEX + " INTEGER )");
        }catch (SQLException err){
            Log.d("myLog",err.toString());
        }
    }


    @Override
    public void deleteTuple(int id)  {
        try {
            db.execSQL("DELETE FROM " + TABLE_NAME + " WHERE _id = " + id);
        }catch (SQLException err){
            Log.d("myLog",err.toString());
        }
    }

    @Override
    public void InsertTuple(Item item) {
        User user = (User)item;
        try {
            db.execSQL("INSERT INTO " + TABLE_NAME + " (" + USER_NAME + "," + USER_EXP + "," + USER_LV + "," + USER_SEX + ") values ('" + user.getName() + "',"
                    + user.getExp() + "," + user.getLv() + "," + user.getSex() + ")");
        }catch (SQLException err){
            Log.d("myLog",err.toString());
        }
    }

    @Override
    public void ModifyTuple(int id, Item item) throws SQLException {
        User user = (User)item;
        try {
            db.execSQL("UPDATE " + TABLE_NAME + " SET " + USER_EXP + "=" + ((User) item).getExp()
                    + "," + USER_LV + "=" + user.getLv() + "," + USER_SEX + "=" + user.getSex() + " WHERE _id=" + id);
        }catch (SQLException err){
            Log.d("myLog",err.toString());
        }
    }

    @Override
    public Cursor getSpecifiedTupleByName(Item item) throws SQLException {
        User user = (User)item;
        try {
            return db.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE " + USER_NAME + " LIKE '" + user.getName() + "'", null);
        }catch (SQLException err){
            Log.d("myLog",err.toString());
        }

        return null;
    }

    @Override
    public Cursor getSpecifiedTupleById(int id) throws SQLException {
        try {
            return db.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE _id = " + id, null);
        }catch (SQLException err){
            Log.d("myLog",err.toString());
        }

        return null;
    }
}
