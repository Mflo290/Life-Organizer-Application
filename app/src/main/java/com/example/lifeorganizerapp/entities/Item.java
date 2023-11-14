package com.example.lifeorganizerapp.entities;

public abstract class Item {

    private int ID;
    private String title;


    public Item(int ID, String title) {
        this.ID = ID;
        this.title = title;
    }

    public Item() {
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
