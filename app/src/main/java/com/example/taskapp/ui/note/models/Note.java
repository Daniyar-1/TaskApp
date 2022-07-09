package com.example.taskapp.ui.note.models;

import java.io.Serializable;

public class Note {

    private String imgURL;
    private String title, description;


    public Note(String imgURL, String title, String description) {
        this.imgURL = imgURL;
        this.title = title;
        this.description = description;
    }

    public Note(String title, String desc) {
        this.title = title;
        this.description = desc;

    }

    public String getImgURL() {
        return imgURL;
    }

    public void setImgURL(String imgURL) {
        this.imgURL = imgURL;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
