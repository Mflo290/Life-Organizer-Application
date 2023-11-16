package com.example.lifeorganizerapp.entities;

import androidx.room.Entity;

import java.time.LocalDate;

@Entity(tableName = "To_Do_Items")
public class ToDoItem extends Item {

    private LocalDate dateCompleted;
    private boolean checked;
    private int listID;



    public ToDoItem(int ID, String title, boolean checked, int listID) {
        super(ID, title);
        this.checked = checked;
        this.listID = listID;
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
