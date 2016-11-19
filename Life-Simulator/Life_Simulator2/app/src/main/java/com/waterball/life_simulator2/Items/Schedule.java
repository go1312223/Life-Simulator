package com.waterball.life_simulator2.Items;

import android.util.Log;

/**
 * Created by AndroidWork on 2016/11/19.
 */

public class Schedule extends Item {
    /**********
     * 存放24小時行程欄位
     * 索引0 → 從8:00AM~9:00AM 開始存放相關行程名稱
     * 索引23 → 7:00AM~8:00AM 完成循環
     * ***********/
    private String[] sche_Names = new String[24];
    private String ScheduleGroupName;

    public String getScheduleGroupName() {
        return ScheduleGroupName;
    }

    public void setScheduleGroupName(String scheduleGroupName) {
        ScheduleGroupName = scheduleGroupName;
    }

    public String[] getSche_Names() {
        return sche_Names;
    }

    public String getSche_Name(int idx) {
        if ( idx >= sche_Names.length || idx < 0 ) {
            IndexOutOfBoundsException err = new IndexOutOfBoundsException("行程表索引不能超跨越0~23");
            Log.d("myLog",err.toString());
            throw err;
        }
        return sche_Names[idx];
    }

    public void setSche_Names(String[] sche_Names) {
        this.sche_Names = sche_Names;
    }

    public void setSche_Name(int idx ,String sche_Name) {
        if ( idx >= sche_Names.length || idx < 0 ) {
            IndexOutOfBoundsException err = new IndexOutOfBoundsException("行程表索引不能超跨越0~23");
            Log.d("myLog",err.toString());
            throw err;
        }
        this.sche_Names[idx] = sche_Name;
    }
}
