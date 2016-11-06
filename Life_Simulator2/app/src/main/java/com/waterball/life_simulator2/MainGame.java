package com.waterball.life_simulator2;

import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.RelativeLayout;

import static com.waterball.life_simulator2.MainActivity.user_db_facade;

public class MainGame extends AppCompatActivity {
    private int userId;  // the currently playing user's id
    private Cursor userCursor;  // the currently playing user's cursor in the Database

    private RelativeLayout parentRelative;
    private void loadAllDatas(){
        Intent intent = getIntent();
        userId = intent.getIntExtra(User.ID_String,-1);
        Log.d("myLog","User id got : " + userId);
        if ( userId <= 0 )
            throw new SQLException("Error !! User's Id  == -1 ");
        // put the background
        userCursor = user_db_facade.getSpecifiedTupleById(userId);
        userCursor.moveToFirst();
        if ( userCursor.getInt(User_DB_Facade.COLUMN_INT_SEX) == User.BOY )
            parentRelative.setBackgroundResource(R.drawable.boy_room2);
        else
            parentRelative.setBackgroundResource(R.drawable.girl_room);
    }
    private void initiate(){


    }
    private void processView(){
        parentRelative = (RelativeLayout)findViewById(R.id.relative_layout_activity_main_game);
    }
    private void processControl(){

    }
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
