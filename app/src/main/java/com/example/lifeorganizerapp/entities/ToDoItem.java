package com.example.lifeorganizerapp.entities;

public class ToDoItem {

    int todoID;
    int status;
    String title;

    public int getTodoID() {
        return todoID;
    }

    public void setTodoID(int id) {
        this.todoID = id;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
