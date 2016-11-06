package com.waterball.life_simulator2;

import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by Administrator on 2016/11/6.
 */

public abstract class DB_Facade {
    protected static String TABLE_NAME;  //table's name
    protected SQLiteDatabase db;  // 資料庫本體 由 MainActivity 創建
    public DB_Facade(String tableName){
        //子類別需呼叫建構式，並且傳入table名稱 EX : super("userName");
        this.db = MainActivity.db;
        this.TABLE_NAME = tableName;
    }
    public abstract void createTable() ;  //創建table
    public abstract void deleteTuple(int id) ;  //刪除資料 傳入該資料id
    public abstract void InsertTuple(Item item) ;  //新增資料 傳入資料封裝好的item
    public abstract void ModifyTuple(int id , Item item) ;  //修改資料 傳入id 及 修改結果之Item
    public Cursor getAll() throws SQLException{  //顯示所有資料
        return db.rawQuery("SELECT * FROM "+TABLE_NAME,null);
    }
    public abstract Cursor getSpecifiedTupleByName(Item item)  ;  //搜尋特定資料by Name
    public abstract Cursor getSpecifiedTupleById(int id) ;  //搜尋特定資料by Id
}
