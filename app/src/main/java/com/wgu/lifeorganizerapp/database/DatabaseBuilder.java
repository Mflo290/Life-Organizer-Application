package com.wgu.lifeorganizerapp.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.wgu.lifeorganizerapp.dao.ItemDAO;
import com.wgu.lifeorganizerapp.dao.ListDAO;
import com.wgu.lifeorganizerapp.dao.UserDAO;
import com.wgu.lifeorganizerapp.entities.ToDoItem;
import com.wgu.lifeorganizerapp.entities.ToDoList;
import com.wgu.lifeorganizerapp.entities.User;

@Database(entities = {ToDoList.class, ToDoItem.class, User.class}, version = 8, exportSchema = false)
@TypeConverters(Converters.class)
public abstract class DatabaseBuilder extends RoomDatabase {
    public abstract ListDAO listDAO();     //List Interface - CRUD
    public abstract ItemDAO itemDAO();
    public abstract UserDAO userDAO();

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
