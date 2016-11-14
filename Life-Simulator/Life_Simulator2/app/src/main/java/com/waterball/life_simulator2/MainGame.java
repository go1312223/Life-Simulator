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

import com.waterball.life_simulator2.DB_Facades.DB_Facade;
import com.waterball.life_simulator2.DB_Facades.User_DB_Facade;
import com.waterball.life_simulator2.Memo.Memo_Activity;
import com.waterball.life_simulator2.User.User;

public class MainGame extends AppCompatActivity {
    public static int userId;  // the currently playing user's id
    private Cursor userCursor;  // the currently playing user's cursor in the Database

    private RelativeLayout parentRelative;  //背景
    private ImageButton memoBTN;
    private DB_Facade user_db_facade;

    private void initiate(){
        user_db_facade = User_DB_Facade.getFacade();
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


    private void processView(){
        parentRelative = (RelativeLayout)findViewById(R.id.relative_layout_activity_main_game);
        memoBTN = (ImageButton)findViewById(R.id.memoBTN_GAME);
    }
    private void processControl(){
        memoBTN.setOnClickListener(itemOnClick);
    }
    /*****  Item 按鈕點擊 → 到各Item頁面  *******/
    private ImageButton.OnClickListener itemOnClick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {

            ImageButton btn = (ImageButton)view;
            Intent goToItem = new Intent();
            goToItem.putExtra(User.ID_String,userId);

            if ( btn == memoBTN )
                goToItem.setClass(MainGame.this,Memo_Activity.class);

            startActivity(goToItem);
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_game);
        initiate();
        processView();
        processControl();
        loadUser();
    }
}
