package com.mytest.java.view;

import java.util.Objects;

public class Column {
    private String title;
    private int width;
    public Column(){

    }

    public Column(String title, int width) {
        this.title = title;
        this.width = width;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Column)) return false;
        Column column = (Column) o;
        return getWidth() == column.getWidth() &&
                Objects.equals(getTitle(), column.getTitle());
    }

    @Override
    public int hashCode() {

        return Objects.hash(getTitle(), getWidth());
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public String getTitle() {
        return title;
    }

    public int getWidth() {
        return width;
    }

    @Override
    public String toString() {
        return "Column{" +
                "title='" + title + '\'' +
                ", width=" + width +
                '}';
    }
}
