package com.example.lifeorganizerapp;

import static org.junit.Assert.assertTrue;

import androidx.room.Room;
import androidx.test.core.app.ApplicationProvider;

import com.example.lifeorganizerapp.dao.ItemDAO;
import com.example.lifeorganizerapp.database.DatabaseBuilder;
import com.example.lifeorganizerapp.entities.ToDoItem;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;
import java.util.List;

public class ToDoItemTests {


    private DatabaseBuilder itemDatabase;
    private ItemDAO itemDAO;



    @Before
    public void setUp() throws Exception {

        itemDatabase = Room.inMemoryDatabaseBuilder(
                ApplicationProvider.getApplicationContext(),
                DatabaseBuilder.class
        ).build();
        itemDAO = itemDatabase.itemDAO();
    }

//    @Test
//    public void testGetCompletedTasks() throws InterruptedException {
//        // Insert some test items into the database
//        LocalDate setDate = LocalDate.now();
//        ToDoItem testItem1 = new ToDoItem(0, "TestItem1", false, 1, "TestList");
//        ToDoItem testItem2 = new ToDoItem(0, "TestItem2", true, 1, "TestList");
//        ToDoItem testItem3 = new ToDoItem(0, "TestItem3", true, 2, "AnotherList");
//        testItem1.setDateCompleted(setDate);
//        testItem2.setDateCompleted(setDate);
//        testItem3.setDateCompleted(setDate);
//
//        itemDAO.insert(testItem1);
//        itemDAO.insert(testItem2);
//        itemDAO.insert(testItem3);
//
//        // Get completed items from the DAO
//        List<ToDoItem> completedItems = itemDAO.getCompletedItems();
//        // Check if the retrieved list contains the inserted completed items
//        boolean found = false;
//        for (ToDoItem item : completedItems) {
//            if (item.isChecked()) {
//                found = true;
//                break;
//            }
//        }
//        assertTrue(found);
//
//    }
//


    @Test
    public void testGetAssociatedItemsWithNullDate() throws InterruptedException {
        // Insert some test items into the database
        ToDoItem testItem1 = new ToDoItem(0, "TestItem1", false, 1, "TestList");
        ToDoItem testItem2 = new ToDoItem(0, "TestItem2", false, 1, "TestList");
        ToDoItem testItem3 = new ToDoItem(0, "TestItem3", false, 2, "AnotherList");
        itemDAO.insert(testItem1);
        itemDAO.insert(testItem2);
        itemDAO.insert(testItem3);


        // Get associated items with a null date completed for list ID 1
        List<ToDoItem> associatedItems = itemDAO.getAssociatedItemsWithNullDateCompleted(1);

        // Ensure the retrieved list contains items associated with list ID 1 and have null date completed
        boolean found = false;
        for (ToDoItem item : associatedItems) {
            if (item.getListID() == 1 && item.getDateCompleted() == null) {
                found = true;
                break;
            }
        }
        assertTrue(found);
    }


    @Test
    public void afterInsertItem() {
        ToDoItem testItem = new ToDoItem(0, "TestItem", false, 1, "TestList");
        itemDAO.insert(testItem);

        // Get all items from the DAO
        List<ToDoItem> allToDoItems = itemDAO.getAllToDoItems();

        // Ensure the item is inserted by checking if an equivalent item is in the list of returned items
        boolean found = false;
        for (ToDoItem item: allToDoItems) {
            if(item.getTitle().equals(testItem.getTitle()) &&
                    item.isChecked() == testItem.isChecked() &&
                    item.getListID() == testItem.getListID() &&
                    item.getListName().equals(testItem.getListName())) {
                found = true;
                break;
            }
        }
        assertTrue(found);
    }

    @After
    public void tearDown() throws Exception {
        itemDatabase.close();
    }




}




