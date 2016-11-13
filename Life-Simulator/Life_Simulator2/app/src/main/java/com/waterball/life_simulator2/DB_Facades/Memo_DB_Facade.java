package com.waterball.life_simulator2.DB_Facades;

import android.database.Cursor;
import android.database.SQLException;
import android.util.Log;

import com.waterball.life_simulator2.Items.Item;
import com.waterball.life_simulator2.Items.Memo;

/**
 * Created by AndroidWork on 2016/11/12.
 */

public class Memo_DB_Facade extends DB_Facade {
    private static DB_Facade facade;
    private static final String MEMO_NAME = "memo_name";
    private static final String MEMO_CONTENT = "memo_content";
    private static final String MEMO_CATEGORY = "memo_category";
    public static final int COLUMN_NAME = 2;
    public static final int COLUMN_CONTENT = 3;
    public static final int COLUMN_CATEGORY = 4;
    private Memo_DB_Facade() {
        super("Memo_Table");
    }

    @Override
    public void createTable() throws SQLException {
        try{
            db.execSQL("CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " (_id INTEGER  ," + USER_ID + " INTEGER ," +
                    MEMO_NAME + " TEXT ," + MEMO_CONTENT + " TEXT ,"+MEMO_CATEGORY+" TEXT,"+ " PRIMARY KEY (_id ," + USER_ID +"))" );
        }catch (SQLException err){
            Log.d("myLog",err.toString());
        }
    }


    @Override  //新增 傳入該資料的封裝物件
    public void InsertTuple(Item item) {
        Memo memo = (Memo)item;
        try {
            db.execSQL("INSERT INTO " + TABLE_NAME + " (" + MEMO_NAME + "," + MEMO_CONTENT +","+MEMO_CATEGORY+","+USER_ID+") values ('"
                    + memo.getName() + "','" + memo.getContent() +"','"+memo.getCategory()+"',"+memo.getUserId()+")");
        }catch (SQLException err){
            Log.d("myLog",err.toString());
        }
    }

    @Override  //修改 傳入id 跟 修改之後的結果物件
    public void ModifyTuple(int id, Item item) throws SQLException {
        Memo memo = (Memo)item;
        try {
            db.execSQL("UPDATE " + TABLE_NAME + " SET " + MEMO_NAME + "='" + memo.getName()
                    + "'," + MEMO_CONTENT + "='" + memo.getContent() + "',"+ MEMO_CATEGORY + "='" + memo.getCategory() + "' WHERE _id=" + id);
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


    //建議使用  獨體模式 得到唯獨一個facade
    public static DB_Facade getFacade() {
        if ( facade == null )
            facade = new Memo_DB_Facade();
        return facade;
    }
}
