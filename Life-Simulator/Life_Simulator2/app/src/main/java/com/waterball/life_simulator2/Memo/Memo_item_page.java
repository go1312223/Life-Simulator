package com.waterball.life_simulator2.Memo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.waterball.life_simulator2.Items.Memo;
import com.waterball.life_simulator2.R;

public class Memo_item_page extends AppCompatActivity {
    private Button okBTN;
    private EditText titleED;
    private EditText categoryED;
    private EditText contentED;

    private void processViews(){
        okBTN = (Button)findViewById(R.id.ok_teim_NoteITEM);
        titleED = (EditText)findViewById(R.id.title_text_NoteEdit);
        categoryED = (EditText)findViewById(R.id.category_text_NoteEdit);
        contentED = (EditText)findViewById(R.id.content_text_NoteEdit);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_memo_item_page);
        processViews();
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
