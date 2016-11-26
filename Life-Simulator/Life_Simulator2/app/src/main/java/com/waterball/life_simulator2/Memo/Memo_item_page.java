package com.waterball.life_simulator2.Memo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.waterball.life_simulator2.Items.Memo;
import com.waterball.life_simulator2.R;
import com.waterball.life_simulator2.User.LevelManager;

import java.util.ArrayList;

import static com.waterball.life_simulator2.Memo.Memo_Activity.categorySet;

public class Memo_item_page extends AppCompatActivity {
    private Button okBTN;
    private EditText titleED;
    private AutoCompleteTextView categoryED;
    private EditText contentED;
    private LevelManager levelManager = LevelManager.getLevelManager();

    private void processControl(){
        ArrayList<String> list = new ArrayList<>(categorySet);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this , android.R.layout.simple_spinner_item , list );
        categoryED.setAdapter(adapter);
        categoryED.setThreshold(1);
    }
    private void processViews(){
        okBTN = (Button)findViewById(R.id.ok_teim_NoteITEM);
        titleED = (EditText)findViewById(R.id.title_text_NoteEdit);
        categoryED = (AutoCompleteTextView)findViewById(R.id.category_text_NoteEdit);
        contentED = (EditText)findViewById(R.id.content_text_NoteEdit);
    }
    /***** 如果是編輯狀態就載入資料 *****/
    private void loadDataIfEditing(){
        Intent intent = getIntent();
        if ( intent.getAction() == "android.intent.action.Edit" )
        {
            titleED.setText(intent.getStringExtra(Memo.TITLE_STRING));
            categoryED.setText(intent.getStringExtra(Memo.CATEGORY_STRING));
            contentED.setText(intent.getStringExtra(Memo.CONTENT_STRING));
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_memo_item_page);
        processViews();
        processControl();
        loadDataIfEditing();
    }

    public void onSubmit(View view) {
        Button btn = (Button)view;
        if ( btn == okBTN)
        {
            Intent result = getIntent();
            String title = titleED.getText().toString();
            String category = categoryED.getText().toString();
            String content = contentED.getText().toString();
            //判斷欄位
            if ( title.length() == 0 || category.length() == 0 || content.length() == 0 )
                Toast.makeText(this, "輸入欄位不能為空~", Toast.LENGTH_SHORT).show();
            else
            {
                result.putExtra(Memo.TITLE_STRING,title);
                result.putExtra(Memo.CATEGORY_STRING,category);
                result.putExtra(Memo.CONTENT_STRING,content);
                setResult(Memo_Activity.RESULT_OK,result);

                levelManager.updateEXP((int)(content.length()*0.3));
                finish();
            }
        }
        else  // == cancelBTN
        {
            setResult(1000);  //1000代表什麼都不做 因為什麼屁都不是
            finish();
        }
    }

}
