package com.waterball.lifesimulator;

import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by Administrator on 2016/11/5.
 */

public abstract class DB_Facade {
    protected static String TABLE_NAME;
    protected SQLiteDatabase db;
    public DB_Facade(String tableName){
        this.db = MainActivity.db;
        this.TABLE_NAME = tableName;
    }
    public abstract void createTable() throws SQLException;
    public abstract void deleteTuple(int index) throws SQLException;
    public abstract void InsertTuple(Item item) throws SQLException;
    public Cursor getAll() throws SQLException{
        return db.rawQuery("SELECT * FROM "+TABLE_NAME,null);
    }
    public abstract Cursor getSpecifiedTuple(Item item) throws SQLException ;
}
