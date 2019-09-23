package models;

public class Fruit {

    private String name ;
    private String origin ;
    private int icon ;

    public Fruit(){}

    public Fruit(String name,int icon, String origin){
        this.name = name ;
        this.origin = origin ;
        this.icon = icon ;
    }

    public int getIcon() {
        return icon;
    }

    public String getName() {
        return name;
    }

    public String getOrigin() {
        return origin;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }
}
