package com.waterball.life_simulator2;

import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.waterball.life_simulator2.DB_Facades.DB_Facade;
import com.waterball.life_simulator2.DB_Facades.User_DB_Facade;
import com.waterball.life_simulator2.Memo.Memo_Activity;
import com.waterball.life_simulator2.Schedule.ScheduleActivity;
import com.waterball.life_simulator2.User.LevelManager;
import com.waterball.life_simulator2.User.User;

public class MainGame extends AppCompatActivity {
    public static User curUser;
    public static int userId;  // the currently playing user's id
    private Cursor userCursor;  // the currently playing user's cursor in the Database

    private RelativeLayout parentRelative;  //背景
    private DB_Facade user_db_facade;
    private ProgressBar expBar;
    private TextView userLvTxt;
    private TextView userNameTxt;

    private void initiate(){
        user_db_facade = User_DB_Facade.getFacade();
    }

    /*******  載入使用者資料 *******/
    private void loadUser(){
        //取得傳來的id
        Intent intent = getIntent();
        userId = intent.getIntExtra(User.ID_String,-1);
        Log.d("myLog","User id got : " + userId);
        if ( userId <= 0 )
            throw new SQLException("Error !! User's Id  == -1 ");

        // 尋找登入的User資料並存入Model
        userCursor = user_db_facade.getSpecifiedTupleById(userId);
        Log.d("myLog","是否找到user"+userId+","+(userCursor.getCount()==0?" 否 ":" 是 "));

        if (userCursor.getCount() == 0)
            throw new SQLException("找不到該user資料!!");
        userCursor.moveToFirst();

        // 判斷性別更換背景
        int userSex = userCursor.getInt(User_DB_Facade.COLUMN_INT_SEX);
        if ( userSex == User.BOY )
            parentRelative.setBackgroundResource(R.drawable.boy_room2);
        else
            parentRelative.setBackgroundResource(R.drawable.girl_room);

        //儲存使用者資料
        String userName = userCursor.getString(User_DB_Facade.COLUMN_TEXT_NAME);
        int userEXP = userCursor.getInt(User_DB_Facade.COLUMN_INT_EXP);
        int userLV = userCursor.getInt(User_DB_Facade.COLUMN_INT_LV);
        userNameTxt.setText(userName);
        userLvTxt.setText((userLV+1)+"");
        curUser = new User(userName,userEXP,userLV,userSex);


        //建立LevelManager來管理等級經驗 作為第一次使用需要傳入User
        LevelManager lvManager = LevelManager.getLevelManager(curUser);

        //登入獎勵
        lvManager.updateEXP(25);

        //更新經驗條
        expBar.setMax(lvManager.getMaxExpNeed());
        expBar.setProgress(curUser.getExp());
    }


    private void processView(){
        userNameTxt = (TextView)findViewById(R.id.userNameShowOnCard_MainGame);
        expBar = (ProgressBar)findViewById(R.id.userExpBar_MainGame);
        userLvTxt = (TextView)findViewById(R.id.userLevel_MainGame);
        parentRelative = (RelativeLayout)findViewById(R.id.relative_layout_activity_main_game);
    }

    /*****  Item 按鈕點擊 → 到各Item頁面  *******/
    public void itemOnClick(View v) {
        int id = v.getId();
        Intent goToItem = new Intent();
        goToItem.putExtra(User.ID_String,userId);
        switch(id){
            case R.id.memoBTN_GAME:
                goToItem.setClass(MainGame.this,Memo_Activity.class);
                break;
            case R.id.scheduleBTN_GAME:
                goToItem.setClass(MainGame.this, ScheduleActivity.class);
                break;
        }

        startActivity(goToItem);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_game);
        initiate();
        processView();
        loadUser();
    }
}
