package com.waterball.life_simulator2;

import android.database.Cursor;

/**
 * Created by AndroidWork on 2016/11/12.
 */

public class Memo_DB_Facade extends DB_Facade {
    public Memo_DB_Facade() {
        super("Memo_Table");
    }

    @Override
    public void createTable() {
5
    }

    @Override
    public void deleteTuple(int id) {

    }

    @Override
    public void InsertTuple(Item item) {

    }

    @Override
    public void ModifyTuple(int id, Item item) {

    }

    @Override
    public Cursor getSpecifiedTupleByName(Item item) {
        return null;
    }

    @Override
    public Cursor getSpecifiedTupleById(int id) {
        return null;
    }
}
