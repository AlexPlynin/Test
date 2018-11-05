package com.mytest.java.view;

import java.util.ArrayList;

public class Page {
    private  int width;
    private  int height;
    private  ArrayList<Column> column;

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public ArrayList<Column> getColumn() {
        return column;
    }

    public void setColumn(ArrayList<Column> column) {
        this.column = column;
    }

    @Override
    public String toString() {
        return "Page{" +
                "width=" + width +
                ", height=" + height +
                ", column=" + column +
                '}';
    }
}


