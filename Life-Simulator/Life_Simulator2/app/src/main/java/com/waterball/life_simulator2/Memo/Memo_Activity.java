package com.waterball.life_simulator2.Memo;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.waterball.life_simulator2.DB_Facades.DB_Facade;
import com.waterball.life_simulator2.DB_Facades.Memo_DB_Facade;
import com.waterball.life_simulator2.Items.Memo;
import com.waterball.life_simulator2.R;
import com.waterball.life_simulator2.User.User;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Memo_Activity extends AppCompatActivity {
    private ListView memoListView;
    private static final int ADD_REQUEST = 0;

    public static List<Memo> memoList; // Memo使用!!
    private MemoAdapter memoAdapter;
    private DB_Facade memo_db_facade;
    class MemoAdapter extends BaseAdapter{
        private Context context;
        public MemoAdapter(Context c){
            context = c;
        }
        @Override
        public int getCount() {
            return memoList.size();
        }
        @Override
        public Object getItem(int i) {
            return memoList.get(i);
        }
        @Override
        public long getItemId(int i) {
            return memoList.get(i).getId();
        }
        @Override
        public View getView(int position, View convertView, ViewGroup viewGroup) {
            Memo memo;
            TextView text = new TextView(context);
            if(convertView == null ){
                memo = (Memo)getItem(position);
                text.setText(memo.getName());
                convertView = text;
                text.setTextColor(Color.BLACK);
                text.setTextSize(25);
                convertView.setTag(text);
            }else{
                text = (TextView) convertView.getTag();
            }
            return text;
        }
    }
    private void initiate(){
        memo_db_facade = Memo_DB_Facade.getFacade();
        memoAdapter = new MemoAdapter(this);
        memoList = Collections.checkedList( new ArrayList<Memo>() , Memo.class);
    }
    private void processViews(){
        memoListView = (ListView)findViewById(R.id.notebook_LIST_MEMO);
    }
    private void processControl(){
        memoListView.setAdapter(memoAdapter);
        /***** 選擇Memo觀看內容 *****/
        memoListView.setOnItemClickListener(new ListView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {

                Memo memo = memoList.get(position);
                Intent goToMemoItemPage = new Intent(Memo_Activity.this,Memo_Item_Content.class);
                goToMemoItemPage.putExtra(Memo.POSITION_STRING,position);
                Log.d("myLog","傳遞Memo訊息,position="+position);
                goToMemoItemPage.putExtra(Memo.TITLE_STRING,memo.getName());
                goToMemoItemPage.putExtra(Memo.CATEGORY_STRING,memo.getCategory());
                goToMemoItemPage.putExtra(Memo.CONTENT_STRING,memo.getContent());
                startActivity(goToMemoItemPage);

            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        memoAdapter.notifyDataSetChanged();
    }

    /*******  載入Memos資料 *******/
    private void loadMemos(){
        int userId = getIntent().getIntExtra(User.ID_String,-1);
        Memo memo;
        Log.d("myLog","取得user"+userId+"的所有memo....");
        Cursor memoCursor = memo_db_facade.getSpecifiedTupleByUserId(userId);
        if ( memoCursor.getCount() == 0 )
            Log.d("myLog","此使用者沒有memo資料");
        else {
            memoCursor.moveToFirst();

            do {
                Log.d("myLog","載入User"+memoCursor.getInt(Memo_DB_Facade.COLUMN_USERID)+" Memo"+memoCursor.getColumnName(0)+" "+memoCursor.getInt(0));
                memo = new Memo(memoCursor.getInt(0),memoCursor.getString(Memo_DB_Facade.COLUMN_NAME),
                        memoCursor.getString(Memo_DB_Facade.COLUMN_CONTENT), memoCursor.getString(Memo_DB_Facade.COLUMN_CATEGORY));
                memoList.add(memo);
            } while (memoCursor.moveToNext());
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_memo_);
        initiate();
        loadMemos();
        processViews();
        processControl();
    }

    public void addMemoOnClick (View v){
        Intent goEditPage = new Intent(this,Memo_item_page.class);
        goEditPage.setAction("android.intent.action.Edit");
        startActivityForResult(goEditPage,ADD_REQUEST);
    }

    @Override
        protected void onActivityResult(int requestCode, int resultCode, Intent data) {
            super.onActivityResult(requestCode, resultCode, data);
            if ( requestCode == ADD_REQUEST )
            {
                /****** 載入使用者剛填好的資料
                 * 填入資料庫
                 * 增加到memoList
                 * 通知adapter
                 * *****/
                if ( resultCode == RESULT_OK )
                {
                    Memo memo = new Memo( data.getStringExtra(Memo.TITLE_STRING)
                            , data.getStringExtra(Memo.CONTENT_STRING) , data.getStringExtra(Memo.CATEGORY_STRING) );
                    memo_db_facade.InsertTuple(memo);

                    memoList.add(memo);
                    memoAdapter.notifyDataSetChanged();
                }
            }
    }


}
