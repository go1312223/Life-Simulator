package com.waterball.life_simulator2.DB_Facades;

import android.database.Cursor;
import android.database.SQLException;
import android.util.Log;

import com.waterball.life_simulator2.Items.Item;
import com.waterball.life_simulator2.Items.Schedule;


/**
 * Created by AndroidWork on 2016/11/19.
 */

public class Schedule_DB_Facade extends DB_Facade {
    private static DB_Facade facade;
    public static final int COLUMN_USERID = 1;
    public static final int COLUMN_SCH_GROUP_NAME = 2;
    public static String[] allScheNames = new String[24];  //24小時屬性字串
    public static String allAttrs_Command;  //24小時屬性字串的結合
    public static final String SCH_GROUP_NAME = "Schedule_group_name"; //行程表單名稱

    private Schedule_DB_Facade() {
        super("Schedule_Table",true); //不建表 所以多傳一個boolean
        StringBuilder string = new StringBuilder("");
        // 存放24小時行程的屬性名稱
        for ( int i = 0 ; i < 24 ; i ++ ) {
            allScheNames[i] = "Schedule_" + (i + 1);
            string.append("Schedule_" + (i + 1));
            if ( i != 23 )
                string.append(",");
        }
        allAttrs_Command = string.toString();
        Log.d("myLog",allAttrs_Command);
    }

    @Override
    public void createTable() throws SQLException {
        DB_Facade user_db_facade = User_DB_Facade.getFacade();
        StringBuilder attributes_Schs = new StringBuilder("");
        //用一個字串存放全部24小時行程屬性指令
        for ( int i = 0 ; i < 24 ; i ++ )
            attributes_Schs.append(allScheNames[i]).append(" TEXT, ");
        Log.d("myLog","創建行程表Table "+attributes_Schs);
        //然而放入create指令
        try{
            db.execSQL("CREATE TABLE IF NOT EXISTS " +
                    TABLE_NAME +
                    " (_id INTEGER PRIMARY KEY ," +
                    USER_ID + " INTEGER ," +
                    SCH_GROUP_NAME + " TEXT ," +
                    attributes_Schs.toString() +
                    "FOREIGN KEY ("+USER_ID+") REFERENCES "+ user_db_facade.TABLE_NAME+" (_id))" );
        }catch (SQLException err){
            Log.d("myLog",err.toString());
        }
    }


    @Override  //新增 傳入該資料的封裝物件
    public void InsertTuple(Item item) {
        Schedule schedule = (Schedule) item;
        StringBuilder schAttrs = new StringBuilder("");
        for ( int i = 0 ; i < 24 ; i ++ )
            schAttrs.append("'"+schedule.getSche_Name(i)+"',");
        Log.d("myLog","新增行程表單"+schAttrs);

        try {
            db.execSQL("INSERT INTO " + TABLE_NAME + " (" + allAttrs_Command + USER_ID+SCH_GROUP_NAME+ ") values ("
                    + schAttrs.toString()+schedule.getUserId()+",'"+schedule.getScheduleGroupName()+"')");
        }catch (SQLException err){
            Log.d("myLog",err.toString());
        }
    }

    @Override  //修改 傳入id 跟 修改之後的結果物件
    public void ModifyTuple(int id, Item item) throws SQLException {
        Schedule schedule = (Schedule) item;
        StringBuilder attrSche = new StringBuilder("");
        for ( int i = 0 ; i < 24 ; i ++ ) {
            attrSche.append(allScheNames[i] + "='" + schedule.getSche_Name(i) + "'");
            if ( i != 23 )
                attrSche.append(",");
        }
        Log.d("myLog","修改行程表單"+attrSche);

        try {
            db.execSQL("UPDATE " + TABLE_NAME + " SET " + attrSche.toString()+ " WHERE _id=" + id);
        }catch (SQLException err){
            Log.d("myLog",err.toString());
        }
    }

    public void ModifyTupleByPosition(int id, int position , String schedule)  {
        try {
            db.execSQL("UPDATE " + TABLE_NAME + " SET " + allScheNames[position]+"='"+schedule+"' WHERE _id=" + id);
        }catch (SQLException err){
            Log.d("myLog",err.toString());
        }
    }

    @Override //搜尋資料 傳入目標資料封裝物件
    public Cursor getSpecifiedTupleByName(Item item) throws SQLException {
        Schedule schedule = (Schedule) item;
        try {
            return db.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE " + SCH_GROUP_NAME + " = '" + schedule.getScheduleGroupName() + "'", null);
        }catch (SQLException err){
            Log.d("myLog",err.toString());
        }

        return null;
    }


    //建議使用  獨體模式 得到唯獨一個facade
    public static DB_Facade getFacade() {
        if ( facade == null )
            facade = new Schedule_DB_Facade();
        return facade;
    }
}
