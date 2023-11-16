package com.example.lifeorganizerapp.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.lifeorganizerapp.entities.ToDoItem;

import java.util.List;

@Dao
public interface ItemDAO {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(ToDoItem toDoItem);

    @Update
    void update(ToDoItem toDoItem);

    @Delete
    void delete(ToDoItem toDoItem);

    @Query("SELECT * FROM To_Do_Items ORDER BY ID ASC")
    List<ToDoItem> getAllToDoItems();

    @Query("SELECT * FROM To_Do_Items WHERE listID = :listID")
    LiveData<List<ToDoItem>> getAssociatedItems(int listID);

    @Query("SELECT * FROM To_Do_Items WHERE ID = :ID")
    LiveData<ToDoItem> getItemByID(int ID);

}
