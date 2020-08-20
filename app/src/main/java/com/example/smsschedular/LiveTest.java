package com.example.smsschedular;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "tasks")
public class LiveTest {

    @PrimaryKey(autoGenerate = true)
    private int id;
    private String title;
    private String body;
    private String time;
    private Long timeinmillis;

    @Ignore
    public LiveTest(String title, String body,String time,Long timeinmillis) {
        this.title = title;
        this.body = body;
        this.time=time;
        this.timeinmillis=timeinmillis;
    }

    public LiveTest(int id, String title, String body,String time,Long timeinmillis) {
        this.id = id;
        this.title = title;
        this.body = body;
        this.time=time;
        this.timeinmillis=timeinmillis;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getTime() {
        return time;
    }

    public Long getTimeinmillis() {
        return timeinmillis;
    }

    public void setTimeinmillis(Long timeinmillis) {
        this.timeinmillis = timeinmillis;
    }

    public void setTime(String time) {
        this.time = time;
    }
}

