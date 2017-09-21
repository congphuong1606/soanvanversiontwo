package vn.congphuong.ominext.soanvanthpt.data;

import java.io.Serializable;

/**
 * Created by Ominext on 9/20/2017.
 */

public class Lesson implements Serializable{
    int id;
    String name;
    String path;
    int chapter;


    public Lesson(int id, String name, String path, int chapter) {
        this.id = id;
        this.name = name;
        this.path = path;
        this.chapter = chapter;
    }

    public Lesson(String name, String path, int chapter) {
        this.name = name;
        this.path = path;
        this.chapter = chapter;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public int getChapter() {
        return chapter;
    }

    public void setChapter(int chapter) {
        this.chapter = chapter;
    }
}
