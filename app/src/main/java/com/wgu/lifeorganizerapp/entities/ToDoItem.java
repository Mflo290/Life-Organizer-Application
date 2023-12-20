package com.wgu.lifeorganizerapp.entities;

import androidx.room.Entity;

import java.time.LocalDate;

@Entity(tableName = "To_Do_Items")
public class ToDoItem extends Item {

    private LocalDate dateCompleted;
    private boolean checked;
    private int listID;
    private String listName;



    public ToDoItem(int ID, String title, boolean checked, int listID, String listName) {
        super(ID, title);
        this.checked = checked;
        this.listID = listID;
        this.listName = listName;
    }

    public String getListName() {
        return listName;
    }

    public void setListName(String listName) {
        this.listName = listName;
    }

    public ToDoItem() {
    }


    public LocalDate getDateCompleted() {
        return dateCompleted;
    }

    public void setDateCompleted(LocalDate dateCompleted) {
        this.dateCompleted = dateCompleted;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    public int getListID() {
        return listID;
    }

    public void setListID(int listID) {
        this.listID = listID;
    }


}
