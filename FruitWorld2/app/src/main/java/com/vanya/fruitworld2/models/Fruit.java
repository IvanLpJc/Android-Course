package com.vanya.fruitworld2.models;

public class Fruit {

    String name ;
    String origin ;
    int icon ;

    public Fruit(){}

    public Fruit(String n, int i, String origin) {
        this.name = n ;
        this.icon = i ;
        this.origin = origin ;
    }

    public String getOrigin() {
        return origin;
    }

    public String getName() {
        return name;
    }

    public int getIcon() {
        return icon;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }
}
