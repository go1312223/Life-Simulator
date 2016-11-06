package com.waterball.life_simulator2;

/**
 * Created by Administrator on 2016/11/6.
 */

public class User extends Item {
    public static final int BOY = 0;  //性別常數
    public static final int GIRL = 1;
    public static final String ID_String = "userId";
    private String name = "";  //user's name
    private int exp = 0;  //user's experiences
    private int lv = 0;  //user's level
    private int sex = 0;  //user's sex -> only a boy or a girl
    public User(String name,int exp,int lv,int sex){
        this.name = name;
        this.exp = exp;
        this.lv = lv;
        if (sex != BOY && sex != GIRL )
            throw new IllegalArgumentException("性別參數設定錯誤");
        this.sex = sex;
    }
    public User(String name ,  int sex){
        if (sex != BOY && sex != GIRL )
            throw new IllegalArgumentException("性別參數設定錯誤");
        this.sex = sex;
        this.name = name;
    }
    public String getName() {
        return name;
    }

    public int getExp() {
        return exp;
    }

    public int getLv() {
        return lv;
    }

    public int getSex() {
        return sex;
    }

    public void setExp(int exp) {
        this.exp = exp;
    }

    public void setLv(int lv) {
        this.lv = lv;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }
}
