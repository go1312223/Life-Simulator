package com.waterball.life_simulator2;

import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    public static SQLiteDatabase db;
    public static User_DB_Facade user_db_facade;
    private RadioButton boy_RD;  //boy radiobutton
    private EditText inputNameED;  //edit : input the name
    private FloatingActionButton fab;  // send email to me
    private Toolbar toolbar; // nothing you should care about this
    public static final String DATABASE_NAME = "LifeSimulator_Database.db";  // database name , don't edit!
    private void initialize(){
        db = openOrCreateDatabase(DATABASE_NAME,MODE_PRIVATE,null);
        user_db_facade = new User_DB_Facade();
    }
    private void processViews(){
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        fab = (FloatingActionButton) findViewById(R.id.fab);
        inputNameED = (EditText)findViewById(R.id.nameEd_Main);
        boy_RD = (RadioButton)findViewById(R.id.boy_RD);
    }
    private void processControl(){
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // to write the feedback function allowing user to send email to us
            }
        });
    }
    // Click the Log In Button
    public void LogInOnClick(View v){
        Cursor cursor;
        String inputName = inputNameED.getText().toString();  //get user's name
        int sex = ( boy_RD.isChecked() ? User.BOY : User.GIRL);  //get user's sex
        User user = new User(inputName,sex); // encapsulate user's datas

        if ( inputName.length() == 0 )  //judge if the user hasn't input
            Toast.makeText(getApplicationContext(),"請輸入暱稱!",Toast.LENGTH_SHORT).show();

        //search if the name exists
        cursor = user_db_facade.getSpecifiedTupleByName(user);
        if ( cursor.getCount() <= 0 || cursor == null )  //if can't find
        {
            if (cursor == null)
                throw new SQLException("Cursor Null error");
            //insert as an new tuple
            user_db_facade.InsertTuple(user);
        }
        // Log In the game to the next activity
        Intent logIn = new Intent(this,MainGame.class);

        cursor = user_db_facade.getSpecifiedTupleByName(user);
        cursor.moveToFirst();

        //put user's id in the extra
        logIn.putExtra(User.ID_String,cursor.getInt(User_DB_Facade.COLUMN_ID));

        //log in successfully and load user's datas
        Toast.makeText(getApplicationContext(),"登入成功,歡迎 "+inputName,Toast.LENGTH_LONG).show();
        startActivity(logIn);


    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setSupportActionBar(toolbar);
        initialize();
        processViews();
        processControl();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
