package com.waterball.life_simulator2.Memo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.waterball.life_simulator2.DB_Facades.DB_Facade;
import com.waterball.life_simulator2.DB_Facades.Memo_DB_Facade;
import com.waterball.life_simulator2.Items.Memo;
import com.waterball.life_simulator2.R;

import java.util.List;

public class Memo_Item_Content extends AppCompatActivity {
    private TextView titleTX;
    private TextView contentTX;

    private static final int REQUEST_EDIT = 1;

    private List<Memo> memoList = Memo_Activity.memoList;
    private DB_Facade memo_db_facade;
    private Memo currentMemo;  //觀看中的Memo
    private int currentPosition;  //觀看中Memo的Position

    private void loadMemoToText(){
        Intent intent = getIntent();
        titleTX.setText(intent.getStringExtra(Memo.TITLE_STRING));
        contentTX.setText(intent.getStringExtra(Memo.CONTENT_STRING));
        currentPosition = intent.getIntExtra(Memo.POSITION_STRING,0);
        currentMemo = memoList.get(currentPosition);
    }

    private void processViews(){
        titleTX = (TextView)findViewById(R.id.memo_title_CONTENT);
        contentTX = (TextView)findViewById(R.id.memo_content_CONTENT);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_memo__item__content);

        memo_db_facade = Memo_DB_Facade.getFacade();
        processViews();
        loadMemoToText();

    }

    public void editOnClick(View v){
        int memoPosition = getIntent().getIntExtra(Memo.POSITION_STRING,-1);
        Intent goEditPage = new Intent(this,Memo_item_page.class);
        goEditPage.setAction("android.intent.action.Edit");
        goEditPage.putExtra(Memo.TITLE_STRING,currentMemo.getName());
        goEditPage.putExtra(Memo.CATEGORY_STRING,currentMemo.getCategory());
        goEditPage.putExtra(Memo.CONTENT_STRING,currentMemo.getContent());
        startActivityForResult(goEditPage,this.REQUEST_EDIT);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if ( requestCode == REQUEST_EDIT )
        {
            if ( resultCode == RESULT_OK )
            {
                String title = data.getStringExtra(Memo.TITLE_STRING);
                String content = data.getStringExtra(Memo.CONTENT_STRING);
                String category = data.getStringExtra(Memo.CATEGORY_STRING);
                titleTX.setText(title);
                contentTX.setText(content);
                Memo memo = new Memo( currentMemo.getId(),title,content,category);
                Log.d("myLog","收到編輯後Memo: title"+title+"\ncategory"+category+"\ncontent:"+content);
                Log.d("myLog","編輯至Memo.. id:"+currentMemo.getId()+"\nposition:"+currentPosition);
                memo_db_facade.ModifyTuple(currentMemo.getId(),memo);
                memoList.set(currentPosition,memo);

            }
        }
    }

}
