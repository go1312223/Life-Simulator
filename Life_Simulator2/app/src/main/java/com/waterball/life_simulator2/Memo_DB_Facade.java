package com.waterball.life_simulator2;

import android.database.Cursor;
import android.database.SQLException;
import android.util.Log;

/**
 * Created by AndroidWork on 2016/11/12.
 */

public class Memo_DB_Facade extends DB_Facade {
    private static DB_Facade facade;
    private static final String MEMO_NAME = "memo_name";
    private static final String MEMO_CONTENT = "memo_content";
    public static final int COLUMN_NAME = 2;
    public static final int COLUMN_CONTENT = 3;
    private Memo_DB_Facade() {
        super("Memo_Table");
    }

    @Override
    public void createTable() throws SQLException {
        try{
            db.execSQL("CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " (_id INTEGER  ," + USER_ID + " INTEGER ," + MEMO_NAME + " TEXT ," + MEMO_CONTENT + " TEXT , PRIMARY KEY (_id ," + USER_ID +"))" );
        }catch (SQLException err){
            Log.d("myLog",err.toString());
        }
    }


    @Override  //新增 傳入該資料的封裝物件
    public void InsertTuple(Item item) {
        Memo memo = (Memo)item;
        try {
            db.execSQL("INSERT INTO " + TABLE_NAME + " (" + MEMO_NAME + "," + MEMO_CONTENT +") values ('" + memo.getName() + "','"
                    + memo.getContent() +"'");
        }catch (SQLException err){
            Log.d("myLog",err.toString());
        }
    }

    @Override  //修改 傳入id 跟 修改之後的結果物件
    public void ModifyTuple(int id, Item item) throws SQLException {
        Memo memo = (Memo)item;
        try {
            db.execSQL("UPDATE " + TABLE_NAME + " SET " + MEMO_NAME + "='" + memo.getName()
                    + "'," + MEMO_CONTENT + "='" + memo.getContent() + "',   WHERE _id=" + id);
        }catch (SQLException err){
            Log.d("myLog",err.toString());
        }
    }

    @Override //搜尋資料 傳入目標資料封裝物件
    public Cursor getSpecifiedTupleByName(Item item) throws SQLException {
        Memo memo = (Memo)item;
        try {
            return db.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE " + MEMO_NAME + " LIKE '" + memo.getName() + "'", null);
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
            facade = new Memo_DB_Facade();
        return facade;
    }
}
