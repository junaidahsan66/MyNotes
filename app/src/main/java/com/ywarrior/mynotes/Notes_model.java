package com.ywarrior.mynotes;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.util.Date;

@Entity(tableName = "notes")
public class Notes_model {

    @PrimaryKey(autoGenerate = true)
    private int id;
    private String text;
    private  String url;
    @Ignore
    public Notes_model(String text, Date date,String url) {
        this.text = text;
        this.date = date;
        this.url=url;
    }

    private Date date;

    @Ignore
    public Notes_model() {

    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Notes_model(int id, String text, Date date,String url) {
        this.id = id;
        this.text = text;
        this.date = date;
        this.url=url;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
