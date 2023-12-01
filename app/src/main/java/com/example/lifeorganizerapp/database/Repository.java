package com.example.lifeorganizerapp.database;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.lifeorganizerapp.dao.ItemDAO;
import com.example.lifeorganizerapp.dao.ListDAO;
import com.example.lifeorganizerapp.entities.ToDoItem;
import com.example.lifeorganizerapp.entities.ToDoList;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Repository {

    private ListDAO listDAO;
    private ItemDAO itemDAO;
    private List<ToDoList> allToDoLists;
    private List<ToDoItem> allToDoItems;
    private static int NUMBER_OF_THREADS = 4;
    private LiveData<ToDoList> selectedList;
    static final ExecutorService databaseExecutor = Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    public Repository(Application application) {
        DatabaseBuilder db = DatabaseBuilder.getDatabase(application);
        listDAO = db.listDAO();
        itemDAO = db.itemDAO();
    }


    public LiveData<ToDoList> getToDoListById(int toDoListID) {
        return listDAO.getToDoListById(toDoListID);
    }

    public LiveData<ToDoItem> getToDoItemByID(int toDoItemID) {
        return itemDAO.getItemByID(toDoItemID);
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

    public LiveData<List<ToDoItem>> getAssociatedItems(int listID){
        return itemDAO.getAssociatedItems(listID);
    }

    public List<ToDoItem> getAllToDoItems() {
        databaseExecutor.execute(()->{
            allToDoItems = itemDAO.getAllToDoItems();
        });
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return allToDoItems;
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
            if (toDoList != null) {
                List<ToDoItem> associatedItems = itemDAO.getAssociatedItemsWithNullDateCompleted(toDoList.getListID());
                if(associatedItems != null && !associatedItems.isEmpty()) {
                    // Delete associated items with NULL dateCompleted
                    for (ToDoItem item : associatedItems) {
                        itemDAO.delete(item);
                    }
                }
                // Delete the list after deleting associated items
                listDAO.delete(toDoList);
            }
        });
    }



    public void insert(ToDoItem toDoItem) {
        databaseExecutor.execute(()->{
            itemDAO.insert(toDoItem);
        });
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }


    public void update(ToDoItem toDoItem) {
        databaseExecutor.execute(()->{
            itemDAO.update(toDoItem);
        });
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }


    public void delete(ToDoItem toDoItem) {
        databaseExecutor.execute(()->{
            itemDAO.delete(toDoItem);
        });
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

}
