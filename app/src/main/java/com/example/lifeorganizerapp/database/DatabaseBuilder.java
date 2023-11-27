package com.example.lifeorganizerapp.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverter;
import androidx.room.TypeConverters;

import com.example.lifeorganizerapp.dao.ItemDAO;
import com.example.lifeorganizerapp.dao.ListDAO;
import com.example.lifeorganizerapp.entities.ToDoItem;
import com.example.lifeorganizerapp.entities.ToDoList;

@Database(entities = {ToDoList.class, ToDoItem.class}, version = 3, exportSchema = false)
@TypeConverters(Converters.class)
public abstract class DatabaseBuilder extends RoomDatabase {
    public abstract ListDAO listDAO();     //List Interface - CRUD
    public abstract ItemDAO itemDAO();

    private static volatile DatabaseBuilder INSTANCE;     //Database Instance

    //This is where you choose between a synchronous or asynchronous database
    static DatabaseBuilder getDatabase(final Context context) {
        if(INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(), DatabaseBuilder.class,"MyDatabase.db") //<-- Name of database
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return INSTANCE;
    }
}
