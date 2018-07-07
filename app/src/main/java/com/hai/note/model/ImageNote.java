package com.hai.note.model;

/**
 * Created by Hai on 06/07/2018.
 */

public class ImageNote {
    private int id;
    private String path;

    public ImageNote() {

    }

    public ImageNote(String path) {
        this.path = path;
    }

    public ImageNote(int id, String path) {
        this.id = id;
        this.path = path;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
