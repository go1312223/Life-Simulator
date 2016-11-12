package com.waterball.life_simulator2;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by AndroidWork on 2016/11/12.
 */

public class MyDBHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "LifeSimulator_Database.db";  // database name , don't edit!
    public static int version = 1;
    private static SQLiteDatabase database;

    private MyDBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        Log.d("myLog","資料庫版本:"+version);
    }
    //獨體模式 取得資料庫
    public static SQLiteDatabase getDatabase(Context context) {
        if (database == null || !database.isOpen()) {
            database = new MyDBHelper(context, DATABASE_NAME,
                    null, version).getWritableDatabase();
        }

        return database;
    }

    @Override  //更新資料表格!!! 所有facade都要實踐刪除~
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        version = i1;
        Log.d("myLog","資料庫版本:"+version);
        User_DB_Facade.getFacade().dropTable();
        onCreate(sqLiteDatabase);
    }
}
