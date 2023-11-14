package com.example.lifeorganizerapp.entities;

import androidx.room.Entity;

import java.time.LocalDate;

@Entity(tableName = "To_Do_Items")
public class ToDoItem extends Item{

    private LocalDate dateCompleted;
    private boolean checked;



    public ToDoItem(int ID, String title) {
        super(ID, title);
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



}
