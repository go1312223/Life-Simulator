package com.waterball.lifesimulator;

import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    public static SQLiteDatabase db;
    public static User_DB_Facade user_db_facade;
    private EditText inputNameED;
    private FloatingActionButton fab;
    private Toolbar toolbar;
    public static final String DATABASE_NAME = "LifeSimulator_Database.db";
    private void initialize(){
        db = openOrCreateDatabase(DATABASE_NAME,MODE_PRIVATE,null);
        user_db_facade = new User_DB_Facade();
        user_db_facade.InsertTuple( new User("WaterBall"));
    }
    private void processViews(){
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        fab = (FloatingActionButton) findViewById(R.id.fab);
        inputNameED = (EditText)findViewById(R.id.nameEd_Main);
    }
    private void processControl(){
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }
    public void LogInOnClick(View v){
        String inputName = inputNameED.getText().toString();
        if ( inputName.length() == 0 )
            Toast.makeText(getApplicationContext(),"請輸入暱稱!",Toast.LENGTH_SHORT).show();
        try
        {
            Cursor cursor = user_db_facade.getSpecifiedTuple(new User(inputName));
            if ( cursor.getCount() <= 0 )
                Toast.makeText(getApplicationContext(),"找不到使用者暱稱", Toast.LENGTH_SHORT).show();
        }catch (SQLException err) {
            Log.d("myLog", err.toString());
        }
        Toast.makeText(getApplicationContext(),"登入成功,Hi"+inputName,Toast.LENGTH_LONG).show();

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
