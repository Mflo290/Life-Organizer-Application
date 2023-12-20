package com.wgu.lifeorganizerapp.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.wgu.lifeorganizerapp.entities.ToDoItem;

import java.util.List;

@Dao
public interface ItemDAO {


    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(ToDoItem toDoItem);

    @Update
    void update(ToDoItem toDoItem);

    @Delete
    void delete(ToDoItem toDoItem);

    @Query("SELECT * FROM To_Do_Items ORDER BY ID DESC")
    List<ToDoItem> getAllToDoItems();

    @Query("SELECT * FROM To_Do_Items WHERE listID = :listID AND dateCompleted IS NULL")
    LiveData<List<ToDoItem>> getAssociatedTasksWithNulDateCompleted(int listID);


    @Query("SELECT * FROM To_Do_Items WHERE ID = :ID")
    LiveData<ToDoItem> getItemByID(int ID);

    @Query("SELECT * FROM To_Do_Items WHERE listID = :listID AND dateCompleted IS NULL")
    List<ToDoItem> getAssociatedItemsWithNullDateCompleted(int listID);

    @Query("SELECT * FROM To_Do_Items WHERE dateCompleted IS NULL ORDER BY ID DESC")
    LiveData<List<ToDoItem>> getUncompletedTasks();

    @Query("SELECT * FROM To_Do_Items WHERE dateCompleted IS NOT NULL ORDER BY dateCompleted DESC")
    LiveData<List<ToDoItem>> getCompletedTasks();

    @Query("SELECT * FROM To_Do_Items WHERE checked = 1 AND dateCompleted IS NULL")
    LiveData<List<ToDoItem>> getCheckedUncompletedTasks();


}
