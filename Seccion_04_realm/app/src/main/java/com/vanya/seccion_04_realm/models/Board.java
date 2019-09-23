package com.vanya.seccion_04_realm.models;

import com.vanya.seccion_04_realm.app.myApplication;

import java.util.Date;
import java.util.List;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.Required;

public class Board extends RealmObject {

    @PrimaryKey
    private int id ;
    @Required
    private String title ;
    @Required
    private Date createdAt ;

    private RealmList<Note> notes ;

    public Board(){}

    public Board(String title){
        this.id = myApplication.BoardID.incrementAndGet() ;
        this.title = title ;
        this.notes = new RealmList<>() ;
        this.createdAt = new Date() ;
    }

    public int getId() {
        return id;
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getCreatedAt() {
        return createdAt;
    }


    public RealmList<Note> getNotes() {
        return notes;
    }

}
