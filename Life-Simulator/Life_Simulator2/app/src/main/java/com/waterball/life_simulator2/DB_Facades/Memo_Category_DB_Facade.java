package com.waterball.life_simulator2.DB_Facades;

import android.database.Cursor;
import android.database.SQLException;
import android.util.Log;

import com.waterball.life_simulator2.Items.Item;
import com.waterball.life_simulator2.Items.Memo;

import static com.waterball.life_simulator2.DB_Facades.Memo_DB_Facade.MEMO_CATEGORY;

/**
 * Created by AndroidWork on 2016/11/16.
 */

public class Memo_Category_DB_Facade extends DB_Facade {
    private static DB_Facade facade;
    public static final String MEMO_CATEGORY_NAME = "memo_category_name";
    public static final int COLUMN_NAME = 2;
    public static final int COLUMN_USERID = 1;
    private Memo_Category_DB_Facade() {
        super("Memo_Category_Table");
    }

    @Override
    public void createTable() throws SQLException {
        DB_Facade user_db_facade = User_DB_Facade.getFacade();
        try{
            db.execSQL("CREATE TABLE IF NOT EXISTS " +
                    TABLE_NAME +
                    " (_id INTEGER PRIMARY KEY ," +
                    USER_ID + " INTEGER ," +
                    MEMO_CATEGORY_NAME + " TEXT ," +
                    "FOREIGN KEY ("+USER_ID+") REFERENCES "+ user_db_facade.TABLE_NAME+" ( _id ))" );
        }catch (SQLException err){
            Log.d("myLog",err.toString());
        }
    }


    @Override  //新增 傳入該資料的封裝物件
    public void InsertTuple(Item item) {
        Memo memo = (Memo)item;
        try {
            db.execSQL("INSERT INTO " + TABLE_NAME + " (" + MEMO_CATEGORY_NAME+","+USER_ID+") values ('"
                    +memo.getCategory()+"',"+memo.getUserId()+")");
        }catch (SQLException err){
            Log.d("myLog",err.toString());
        }
    }

    @Override  //修改 傳入id 跟 修改之後的結果物件
    public void ModifyTuple(int id, Item item) throws SQLException {
        Memo memo = (Memo)item;
        try {
            db.execSQL("UPDATE " + TABLE_NAME + " SET " + MEMO_CATEGORY + "='" + memo.getCategory() + "' WHERE _id=" + id);
        }catch (SQLException err){
            Log.d("myLog",err.toString());
        }
    }

    @Override //搜尋資料 傳入目標資料封裝物件
    public Cursor getSpecifiedTupleByName(Item item) throws SQLException {
        Memo memo = (Memo)item;
        try {
            return db.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE " + MEMO_CATEGORY_NAME + " = '" + memo.getCategory() + "'", null);
        }catch (SQLException err){
            Log.d("myLog",err.toString());
        }

        return null;
    }


    //建議使用  獨體模式 得到唯獨一個facade
    public static DB_Facade getFacade() {
        if ( facade == null )
            facade = new Memo_Category_DB_Facade();
        return facade;
    }
}
