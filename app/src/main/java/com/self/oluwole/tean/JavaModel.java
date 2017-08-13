package com.self.oluwole.tean;

/**
 * Created by yung on 8/13/17.
 */

public class JavaModel {
    private String name;
    private int num_pages;

    public JavaModel(){

    }

    public JavaModel(String name, int num_pages){
        this.name = name;
        this.num_pages = num_pages;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getNum_pages() {
        return num_pages;
    }

    public void setNum_pages(int num_pages) {
        this.num_pages = num_pages;
    }
}
