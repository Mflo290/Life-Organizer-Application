package com.example.lifeorganizerapp.database;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.lifeorganizerapp.dao.ListDAO;
import com.example.lifeorganizerapp.entities.ToDoList;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Repository {

    private ListDAO listDAO;
    private List<ToDoList> allToDoLists;
    private static int NUMBER_OF_THREADS = 4;
    static final ExecutorService databaseExecutor = Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    public Repository(Application application) {
        DatabaseBuilder db = DatabaseBuilder.getDatabase(application);
        listDAO = db.listDAO();
    }

    public LiveData<ToDoList> getToDoListById(int toDoListID) {
        return listDAO.getToDoListById(toDoListID);
    }


    public List<ToDoList> getAllToDoLists() {
        databaseExecutor.execute(() ->{
            allToDoLists = listDAO.getAllToDoLists();
        });
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return allToDoLists;
    }




    //Insert a toDoList into repository
    public void insert(ToDoList toDoList) {
        databaseExecutor.execute(()->{
            listDAO.insert(toDoList);
        });
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }


    public void update(ToDoList toDoList) {
        databaseExecutor.execute(()->{
            listDAO.update(toDoList);
        });
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }


    //Delete toDoList from repository
    public void delete(ToDoList toDoList) {
        databaseExecutor.execute(()->{
            listDAO.delete(toDoList);
        });
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }




}
