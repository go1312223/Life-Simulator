package com.waterball.life_simulator2.Memo;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;

import com.waterball.life_simulator2.DB_Facades.Memo_DB_Facade;
import com.waterball.life_simulator2.Items.Memo;
import com.waterball.life_simulator2.R;
import com.waterball.life_simulator2.User.LevelManager;
import com.waterball.life_simulator2.User.User;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static com.waterball.life_simulator2.MainGame.userId;

public class Memo_Activity extends AppCompatActivity {
    private static ListView memoListView;
    private static Spinner categorySpn;

    private static final int ADD_REQUEST = 0;
    private static final String CHOOSE_CATEGORY = "全部";

    public static List<Memo> memoList; // 全部的Memo儲存
    public static List<Memo> memoListWhenCategory;  // 分類選取時獲取的Memo清單
    public static Set<String> categorySet; // Memo分類儲存!!
    public static ArrayList<String> cateList; //Memo轉成List
    private static ArrayAdapter<String> categoryAdapter;
    private Memo_DB_Facade memo_db_facade;
    private LevelManager levelManager;

    private void initiate(){
        levelManager = LevelManager.getLevelManager();
        memo_db_facade = (Memo_DB_Facade) Memo_DB_Facade.getFacade();
        memoList = Collections.checkedList( new ArrayList<Memo>() , Memo.class);
        memoListWhenCategory = Collections.checkedList( new ArrayList<Memo>() , Memo.class);
        categorySet = Collections.checkedSet( new HashSet<String>() , String.class);
    }
    private void processViews(){
        memoListView = (ListView)findViewById(R.id.notebook_LIST_MEMO);
        categorySpn = (Spinner)findViewById(R.id.category_Spn_Memo);
    }
    private void processControl(){

        /***** 選擇Memo觀看內容 *****/
        memoListView.setOnItemClickListener(new ListView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {

                Memo memo;
                //如果目前是讀取分類的話 就要改變使用的容器為該分類ListView對應的容器
                if ( !categorySpn.getSelectedItem().toString().equals(CHOOSE_CATEGORY) )
                    memo = memoListWhenCategory.get(position);
                else
                     memo = memoList.get(position);
                Intent goToMemoItemPage = new Intent(Memo_Activity.this,Memo_Item_Content.class);
                goToMemoItemPage.putExtra(Memo.ID_STRING,memo.getId());
                goToMemoItemPage.putExtra(Memo.TITLE_STRING,memo.getName());
                goToMemoItemPage.putExtra(Memo.CATEGORY_STRING,memo.getCategory());
                goToMemoItemPage.putExtra(Memo.CONTENT_STRING,memo.getContent());
                startActivity(goToMemoItemPage);

            }
        });
        //將集合轉成arraylist 才能放進adapter
        cateList = new ArrayList<String>(categorySet);
        addFirstCategoryWatchAll();  //增加"全部"進分類
        notifyAdapterChange();  //通知adapter

        /***** 每次分類時，分類用memoListWhenCategory就更新 成 相對的ListView狀態 *****/
        categorySpn.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String categ;  //選擇的分類
                int id;
                String category,name,content;
                //搜尋分類
                if ( !adapterView.getSelectedItem().toString().equals(CHOOSE_CATEGORY))
                {
                    categ = adapterView.getSelectedItem().toString();
                    memoListWhenCategory.clear();  //先清空 再填入所有該分類的memo
                    Cursor cursor = memo_db_facade.getSpecifiedMemoByCategoryAndId(categ,userId);
                    cursor.moveToFirst();
                    //把目前的分類表更新到另一個arraylist
                    do {
                        id = cursor.getInt(0);
                        category = cursor.getString(Memo_DB_Facade.COLUMN_CATEGORY);
                        name = cursor.getString(Memo_DB_Facade.COLUMN_NAME);
                        content = cursor.getString(Memo_DB_Facade.COLUMN_CONTENT);
                        memoListWhenCategory.add(new Memo(id , name , content , category ));
                    } while ( cursor.moveToNext() );
                    updateListView(cursor);
                }
                //全部
                else
                    updateListView( memo_db_facade.getSpecifiedTupleByUserId(userId) );
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        updateCategorySpn();  //重載頁面時要更新分類
        updateListView( memo_db_facade.getSpecifiedTupleByUserId(userId) );  //更新ListView
    }

    /*******  載入Memos資料 *******/
    private void loadMemos(){
        int userId = getIntent().getIntExtra(User.ID_String,-1);
        String category;
        Memo memo;
        Log.d("myLog","取得user"+userId+"的所有memo....");
        Cursor memoCursor = memo_db_facade.getSpecifiedTupleByUserId(userId);
        if ( memoCursor.getCount() == 0 )
            Log.d("myLog","此使用者沒有memo資料");
        else {
            memoCursor.moveToFirst();

            do {
                Log.d("myLog","載入User"+memoCursor.getInt(Memo_DB_Facade.COLUMN_USERID)+" Memo"+memoCursor.getColumnName(0)+" "+memoCursor.getInt(0));
                category = memoCursor.getString(Memo_DB_Facade.COLUMN_CATEGORY);
                //增加快取分類集合
                categorySet.add(category);

                memo = new Memo(memoCursor.getInt(0),memoCursor.getString(Memo_DB_Facade.COLUMN_NAME),
                        memoCursor.getString(Memo_DB_Facade.COLUMN_CONTENT), category );
                //增加memo
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
        goEditPage.setAction("android.intent.action.Add");
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
                    //搜索該Memo Id
                    Cursor cursor = memo_db_facade.getSpecifiedTupleByName(memo);
                    cursor.moveToFirst();
                    int memoId = cursor.getInt(0);
                    Log.d("myLog","新增Memo , id = "+memoId);
                    memo.setId( memoId );
                    memoList.add(memo);

                    levelManager.updateEXP(25);
                }
            }
    }

    // 更新ListView
    private void updateListView(Cursor cursor) {
        SimpleCursorAdapter adapter = new SimpleCursorAdapter( this ,
                R.layout.memo_listview , cursor ,
                new String[]{  Memo_DB_Facade.MEMO_NAME , Memo_DB_Facade.MEMO_CATEGORY }
        , new int[]{ R.id.title_memo_item_layout , R.id.category_memo_item_layout } , 0 );
        memoListView.setAdapter(adapter);
    }

    //編輯後要更新分類清單
    private void updateCategorySpn() {
        categorySet.clear(); //清空分類
        for ( Memo s : memoList )  //從Memo單讀取分類
            categorySet.add(s.getCategory());

        cateList = new ArrayList<String>(categorySet);  //轉成List
        addFirstCategoryWatchAll();  //增加 分類
        notifyAdapterChange();  //通知adapter
    }

    //增加第一個分類 "全部"
    private void addFirstCategoryWatchAll(){
        if ( cateList.size() != 0 ) {
            String temp = cateList.get(0);
            cateList.set(0, CHOOSE_CATEGORY);
            cateList.add(temp);
        }
        else
            cateList.add(CHOOSE_CATEGORY);
    }

    //更新adapter
    public void notifyAdapterChange(){
        categoryAdapter = new ArrayAdapter<String>( this, android.R.layout.simple_spinner_item , cateList );
        categorySpn.setAdapter(categoryAdapter);
    }

}
