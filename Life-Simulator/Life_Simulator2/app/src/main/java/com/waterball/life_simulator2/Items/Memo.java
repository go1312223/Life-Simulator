package com.waterball.life_simulator2.Items;

/**
 * Created by AndroidWork on 2016/11/12.
 */

public class Memo extends Item {
    private String name;
    private String category;
    private String content;
    private int id;
    public static final String TITLE_STRING = "title_memo";
    public static final String CATEGORY_STRING = "category_memo";
    public static final String CONTENT_STRING = "content_memo";


    public Memo(String name , String content , String category){
        super();
        this.category = category;
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

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


}
