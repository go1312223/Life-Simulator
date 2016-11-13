package com.waterball.life_simulator2.Memo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.waterball.life_simulator2.Items.Memo;
import com.waterball.life_simulator2.R;

public class Memo_Item_Content extends AppCompatActivity {
    private TextView titleTX;
    private TextView contentTX;

    private void loadMemoToText(){
        Intent intent = getIntent();
        titleTX.setText(intent.getStringExtra(Memo.TITLE_STRING));
        contentTX.setText(intent.getStringExtra(Memo.CONTENT_STRING));
    }

    private void processViews(){
        titleTX = (TextView)findViewById(R.id.memo_title_CONTENT);
        contentTX = (TextView)findViewById(R.id.memo_content_CONTENT);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_memo__item__content);
        processViews();
        loadMemoToText();
    }
}
