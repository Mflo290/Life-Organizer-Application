package com.example.lifeorganizerapp.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.lifeorganizerapp.dao.ListDAO;
import com.example.lifeorganizerapp.entities.ToDoList;

@Database(entities = {ToDoList.class}, version = 1, exportSchema = false)
public abstract class DatabaseBuilder extends RoomDatabase {
    public abstract ListDAO listDAO();     //Vacation Interface - CRUD

    private static volatile DatabaseBuilder INSTANCE;     //Database Instance


    //getDatabase. This is where you choose between a synchronous or asynchronous database
    static DatabaseBuilder getVacationDatabase(final Context context) {
        if(INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(), DatabaseBuilder.class,"MyDatabase.db") //<-- Name of database
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return INSTANCE;
    }
}
