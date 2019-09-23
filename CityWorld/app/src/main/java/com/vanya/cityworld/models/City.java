package com.vanya.cityworld.models;

import com.vanya.cityworld.app.MyApplication;

import java.security.PublicKey;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.Required;

public class City extends RealmObject {

    @PrimaryKey
    private int id ;
    @Required
    private String name ;
    @Required
    private String description ;
    @Required
    private String backgroundImage ;

    private float rate ;

    public City(){}

    public City(String name, String description, String backgroundImage, float rate) {
        this.id = MyApplication.CityID.incrementAndGet() ;
        this.name = name;
        this.description = description;
        this.backgroundImage = backgroundImage;
        this.rate = rate;
    }

    public int getId() {
        return id;
    }
    public void setId(int id){ this.id = id ;}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getBackgroundImage() {
        return backgroundImage;
    }

    public void setBackgroundImage(String backgroundImage) {
        this.backgroundImage = backgroundImage;
    }

    public float getRate() {
        return rate;
    }

    public void setRate(float rate) {
        this.rate = rate;
    }
}
