package com.waterball.life_simulator2;

import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.RelativeLayout;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MainGame extends AppCompatActivity {
    private int userId;  // the currently playing user's id
    private Cursor userCursor;  // the currently playing user's cursor in the Database

    private List<Memo> memoList; // memos

    private RelativeLayout parentRelative;  //背景
    private ImageButton memoBTN;
    private DB_Facade memo_db_facade;
    private DB_Facade user_db_facade;

    private void initiate(){
        user_db_facade = User_DB_Facade.getFacade();
        memo_db_facade = Memo_DB_Facade.getFacade();

        memoList = Collections.checkedList( new ArrayList<Memo>() , Memo.class);
    }
    private void loadAllDatas(){
        loadUser();
      //  loadMemos();
        loadCheckList();
        loadSchedule();
    }
    /*******  載入使用者資料 *******/
    private void loadUser(){
        Intent intent = getIntent();
        userId = intent.getIntExtra(User.ID_String,-1);
        Log.d("myLog","User id got : " + userId);
        if ( userId <= 0 )
            throw new SQLException("Error !! User's Id  == -1 ");

        // 選擇背景為 男 或 女 房間
        userCursor = user_db_facade.getSpecifiedTupleById(userId);
        Log.d("myLog","是否找到user"+userId+","+(userCursor.getCount()==0?" 否 ":" 是 "));
        if (userCursor.getCount() == 0)
            throw new SQLException("找不到該user資料!!");
        userCursor.moveToFirst();
        if ( userCursor.getInt(User_DB_Facade.COLUMN_INT_SEX) == User.BOY )
            parentRelative.setBackgroundResource(R.drawable.boy_room2);
        else
            parentRelative.setBackgroundResource(R.drawable.girl_room);
    }

    /*******  載入Memos資料 *******/
    private void loadMemos(){
        Cursor memoCursor = memo_db_facade.getSpecifiedTupleByUserId(userId);
        if ( memoCursor.getCount() == 0 )
            Log.d("myLog","此使用者沒有memo資料");
        else
            memoCursor.moveToFirst();
        Memo memo;
        do {
            memo = new Memo ( memoCursor.getString(Memo_DB_Facade.COLUMN_NAME) ,
                    memoCursor.getString(Memo_DB_Facade.COLUMN_CONTENT));
            memoList.add(memo);
        } while ( memoCursor.moveToNext() );
    }
    /*******  載入CheckList資料  參考這個↑↑↑   *******/
    private void loadCheckList(){
        //你得先宣告一個CheckList物件繼承Item
        //然後在定義一個ArrayList讀取該使用者所有的Item
    }

    private void loadSchedule(){

    }

    private void processView(){
        parentRelative = (RelativeLayout)findViewById(R.id.relative_layout_activity_main_game);
        memoBTN = (ImageButton)findViewById(R.id.memoBTN_GAME);
    }
    private void processControl(){
        memoBTN.setOnClickListener(itemOnClick);
    }
    //Item 按鈕點擊 → 到各Item頁面
    private ImageButton.OnClickListener itemOnClick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            ImageButton btn = (ImageButton)view;
            Intent goToItem = new Intent();
            if ( btn == memoBTN )
            {

            }

        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_game);
        initiate();
        processView();
        processControl();
        loadAllDatas();
    }
}
