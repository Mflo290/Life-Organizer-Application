package com.wgu.lifeorganizerapp.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.wgu.lifeorganizerapp.entities.ToDoList;

import java.util.List;

@Dao
public interface ListDAO {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(ToDoList toDoList);

    @Update
    void update(ToDoList toDoList);

    @Delete
    void delete(ToDoList toDoList);

    @Query("SELECT * FROM To_Do_Lists ORDER BY listID ASC")
    List<ToDoList> getAllToDoLists();

    @Query("SELECT * FROM To_Do_Lists WHERE listID = :listID")
    LiveData<ToDoList> getToDoListById(int listID);
}
