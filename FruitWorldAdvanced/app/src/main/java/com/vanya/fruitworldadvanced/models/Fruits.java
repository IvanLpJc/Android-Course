package com.vanya.fruitworldadvanced.models;

public class Fruits {

    private String name ;
    private String description ;
    private int icon ;
    private int backgroundIcon ;
    private int quantity ;

    private static final int RESET_QUANTITY = 0 ;
    public static final int MAX_QUANTITY = 10 ;

    public Fruits(){}

    public Fruits(String name, String description, int backgroundIcon, int icon, int quantity) {
        this.name = name;
        this.description = description;
        this.icon = icon;
        this.backgroundIcon = backgroundIcon;
        this.quantity = quantity ;
    }

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

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    public int getBackgroundIcon() {
        return backgroundIcon;
    }

    public void setBackgroundIcon(int backgroundIcon) {
        this.backgroundIcon = backgroundIcon;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void reset(){
        this.quantity = RESET_QUANTITY;
    }

    public void addQuantity(){ if(this.quantity < MAX_QUANTITY) quantity++ ;}
}
