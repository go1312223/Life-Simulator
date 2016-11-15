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

import com.waterball.life_simulator2.DB_Facades.DB_Facade;
import com.waterball.life_simulator2.DB_Facades.User_DB_Facade;
import com.waterball.life_simulator2.User.User;

public class MainActivity extends AppCompatActivity {
    public static SQLiteDatabase db;
    public static DB_Facade user_db_facade;
    private RadioButton boy_RD;  //boy radiobutton
    private EditText inputNameED;  //edit : input the name
    private FloatingActionButton fab;  // send email to me
    private Toolbar toolbar; // nothing you should care about this

    private void initialize(){
        db = MyDBHelper.getDatabase(getApplicationContext());
        user_db_facade = User_DB_Facade.getFacade();
    }
    private void processViews(){
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        fab = (FloatingActionButton) findViewById(R.id.fab);
        inputNameED = (EditText)findViewById(R.id.nameEd_Main);
        boy_RD = (RadioButton)findViewById(R.id.boy_RD);
    }
    private void processControl(){
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }
    // Click the Log In Button
    public void LogInOnClick(View v){
        Cursor cursor;
        String inputName = inputNameED.getText().toString();  //取得使用者名稱
        int sex = ( boy_RD.isChecked() ? User.BOY : User.GIRL);  //取得性別
        User user = new User(inputName,sex); // 封裝使用者資料

        if ( inputName.length() == 0 )  //判斷欄位輸入
            Toast.makeText(getApplicationContext(),"請輸入暱稱!",Toast.LENGTH_SHORT).show();
        else {
            //判斷是否搜尋到該使用者資料
            cursor = user_db_facade.getSpecifiedTupleByName(user);
            if (cursor.getCount() <= 0 || cursor == null)  //if can't find
            {
                if (cursor == null)
                    throw new SQLException("Cursor Null error");
                //增新增為新使用者
                user_db_facade.InsertTuple(user);
            }

            //取得該登入使用者資料
            cursor = user_db_facade.getSpecifiedTupleByName(user);
            cursor.moveToFirst();

            //傳遞該使用者id到第二個頁面
            Intent logIn = new Intent(this, MainGame.class);
            logIn.putExtra(User.ID_String, cursor.getInt(User_DB_Facade.COLUMN_ID));

            //log in successfully and load user's datas
            Toast.makeText(getApplicationContext(), "登入成功,歡迎 " + inputName, Toast.LENGTH_LONG).show();
            startActivity(logIn);
        }

    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setSupportActionBar(toolbar);
        initialize();  //初始化資料
        processViews();  //初始化元件
        processControl();  //元件控制註冊
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
