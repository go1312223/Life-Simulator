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
    public static final int COLUMN_ID = 0;  //方便該行的取得之常數
    public  static final int COLUMN_TEXT_NAME = 1;
    public  static final int COLUMN_INT_EXP = 2;
    public  static final int COLUMN_INT_LV = 3;
    public  static final int COLUMN_INT_SEX = 4;

    //使用私有建構子 !!
    private User_DB_Facade(){
        super("User_Table");  //設定表格名稱
        try
        {
            createTable();  //建表
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

    @Override  //刪除資料 傳入id
    public void deleteTuple(int id)  {
        try {
            db.execSQL("DELETE FROM " + TABLE_NAME + " WHERE _id = " + id);
        }catch (SQLException err){
            Log.d("myLog",err.toString());
        }
    }

    @Override  //新增 傳入該資料的封裝物件
    public void InsertTuple(Item item) {
        User user = (User)item;
        try {
            db.execSQL("INSERT INTO " + TABLE_NAME + " (" + USER_NAME + "," + USER_EXP + "," + USER_LV + "," + USER_SEX + ") values ('" + user.getName() + "',"
                    + user.getExp() + "," + user.getLv() + "," + user.getSex() + ")");
        }catch (SQLException err){
            Log.d("myLog",err.toString());
        }
    }

    @Override  //修改 傳入id 跟 修改之後的結果物件
    public void ModifyTuple(int id, Item item) throws SQLException {
        User user = (User)item;
        try {
            db.execSQL("UPDATE " + TABLE_NAME + " SET " + USER_EXP + "=" + ((User) item).getExp()
                    + "," + USER_LV + "=" + user.getLv() + "," + USER_SEX + "=" + user.getSex() + " WHERE _id=" + id);
        }catch (SQLException err){
            Log.d("myLog",err.toString());
        }
    }

    @Override //搜尋資料 傳入目標資料封裝物件
    public Cursor getSpecifiedTupleByName(Item item) throws SQLException {
        User user = (User)item;
        try {
            return db.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE " + USER_NAME + " LIKE '" + user.getName() + "'", null);
        }catch (SQLException err){
            Log.d("myLog",err.toString());
        }

        return null;
    }

    @Override  //用id搜尋資料
    public Cursor getSpecifiedTupleById(int id) throws SQLException {
        try {
            return db.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE _id = " + id, null);
        }catch (SQLException err){
            Log.d("myLog",err.toString());
        }

        return null;
    }

    //建議使用  獨體模式 得到唯獨一個facade
    public static DB_Facade getFacade() {
        if ( facade == null )
            facade = new User_DB_Facade();
        return facade;
    }
}
