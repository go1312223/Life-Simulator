package com.waterball.life_simulator2;

/**
 * Created by AndroidWork on 2016/11/12.
 */

public class Memo extends Item {
    private String name;
    private String content;

    public Memo(String name , String content){
        this.name = name;
        this.content = content;
    }

    public String getContent() {
        return content;
    }

    public String getName() {
        return name;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setName(String name) {
        this.name = name;
    }

}
