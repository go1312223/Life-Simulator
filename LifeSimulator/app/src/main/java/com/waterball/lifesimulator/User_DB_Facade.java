package com.waterball.lifesimulator;

import android.database.Cursor;
import android.database.SQLException;
import android.util.Log;

/**
 * Created by Administrator on 2016/11/5.
 */

public class User_DB_Facade extends DB_Facade {
    private static final String USER_NAME = "userName";
    private static final String USER_EXP = "userexp";
    private static final String USER_LV = "userlv";
    private static final String USER_SEX = "usersex";

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
        db.execSQL("CREATE TABLE " + TABLE_NAME + " (_id INTEGER PRIMARY KEY ," + USER_NAME + " TEXT ," + USER_EXP + " INTEGER ," + USER_LV + " INTEGER ," + USER_SEX + " INTEGER )");
    }


    @Override
    public void deleteTuple(int index) throws SQLException {

    }

    @Override
    public void InsertTuple(Item item) throws SQLException {
        User user = (User)item;
        db.execSQL("INSERT INTO "+TABLE_NAME+" ("+USER_NAME+","+USER_EXP+","+USER_LV+","+USER_SEX+") values ('"+user.getName()+"',"
        +user.getExp()+","+user.getLv()+","+user.getSex()+")");
    }

    @Override
    public Cursor getSpecifiedTuple(Item item) throws SQLException {
        User user = (User)item;
        return db.rawQuery("SELECT * FROM "+TABLE_NAME+" WHERE "+USER_NAME+" LIKE '"+user.getName()+"'",null);
    }
}
