package com.waterball.lifesimulator;

public class User extends Item {
    private static final int BOY = 0;
    private static final int GIRL = 1;
    private String name = "";
    private int exp = 0;
    private int lv = 0;
    private int sex = 0;
    public User(String name,int exp,int lv,int sex){
        this.name = name;
        this.exp = exp;
        this.lv = lv;
        if (sex != BOY && sex != GIRL )
            throw new IllegalArgumentException("性別參數設定錯誤");
        this.sex = sex;
    }
    public User(String name){
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
