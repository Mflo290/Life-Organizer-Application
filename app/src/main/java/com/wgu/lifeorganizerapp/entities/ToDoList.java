package com.wgu.lifeorganizerapp.entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "To_Do_Lists")
public class ToDoList {
    @PrimaryKey(autoGenerate = true)
    int listID;
    String listName;

    public ToDoList(int listID, String listName) {
        this.listID = listID;
        this.listName = listName;
    }

    public ToDoList() {

    }

    public int getListID() {
        return listID;
    }

    public void setListID(int listID) {
        this.listID = listID;
    }

    public String getListName() {
        return listName;
    }

    public void setListName(String listName) {
        this.listName = listName;
    }
}
