package com.example.taskapp.ui.note.models;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity
public class Note implements Serializable {

    @PrimaryKey(autoGenerate = true)
    private long id;
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

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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
