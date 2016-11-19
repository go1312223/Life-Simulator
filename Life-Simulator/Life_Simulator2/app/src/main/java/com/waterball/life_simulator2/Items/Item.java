package com.waterball.life_simulator2.Items;

import com.waterball.life_simulator2.MainGame;

/**
 * Created by Administrator on 2016/11/6.
 */

public abstract class Item {
    /*
    * 想要新增一個Item ~
    * 直接宣告一個類別繼承他就好了
    * 然後在Game Activity中
    * 宣告放置所有該Item的ArrayList
    * 記得userId也是item的Primary key
    * */
    protected Item(){  //建構子直接存入MainGame"當前登入的UserId"
        this.userId = MainGame.userId;
    }
    private static int userId;  //紀錄userId
    protected int id;  //PK
    public static final String ID_STRING = "id";

    public static int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

}
