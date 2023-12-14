package com.example.lifeorganizerapp.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.lifeorganizerapp.entities.User;

import java.util.List;

@Dao
public interface UserDAO {
    @Insert
    void insert(User user);


    @Query("SELECT * FROM user_table")
    LiveData<List<User>> getAllUsers();

    @Query("SELECT * FROM user_table WHERE username = :username AND password = :password")
    LiveData<User> getUserByUsernameAndPassword(String username, String password);

}
