package com.example.lifeorganizerapp.UI;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;

import com.example.lifeorganizerapp.R;
import com.example.lifeorganizerapp.database.Repository;
import com.example.lifeorganizerapp.entities.ToDoList;

public class ToDoActivity extends AppCompatActivity implements AddNewItem.OnListUpdateListener{

    //Declare variables

    int toDoListID;
    TextView heading;
    String listName;
    ImageView backArrow;
    ImageView editIcon;

    Repository repository;



    @Override
    public void onListUpdated(ToDoList updatedList) {
        // Update the toolbar heading here with the new title
        heading.setText(updatedList.getListName());

        // Update the repository with the new list
        repository.update(updatedList);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_to_do);

        //Initialize Variables
        repository = new Repository(getApplication());

        //Find Views By ID
        heading = findViewById(R.id.heading_textview);
        backArrow = findViewById(R.id.back_arrow);
        editIcon = findViewById(R.id.edit_icon);


        //Get Intents from previous screen and repo
        toDoListID = getIntent().getIntExtra("toDoListID", -1);
        listName = getIntent().getStringExtra("toDoListName");

        //Set data
        heading.setText(listName);



        //Click on the back arrow
        backArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.putExtra("isBackButtonClicked", true);
                intent.putExtra("list_name", listName); // Pass the updated list name
                setResult(RESULT_OK, intent);
                finish();
            }
        });



        editIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopupMenu popupMenu = new PopupMenu(ToDoActivity.this, view);
                popupMenu.inflate(R.menu.edit_menu);

                // Handle menu item clicks
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        if (menuItem.getItemId() == R.id.edit_list) {
                            int currentID = getIntent().getIntExtra("toDoListID", -1);
                            LiveData<ToDoList> updatedNameLiveData = repository.getToDoListById(currentID);
                            updatedNameLiveData.observe(ToDoActivity.this, new Observer<ToDoList>() {
                                @Override
                                public void onChanged(ToDoList toDoList) {
                                    String title = toDoList.getListName();
                                    AddNewItem bottomFragment = new AddNewItem(repository);
                                    bottomFragment.setOnListUpdateListener(ToDoActivity.this); // Set the listener using ToDoActivity.this
                                    Bundle bundle = new Bundle();
                                    bundle.putInt("todo_list_id", toDoList.getListID());
                                    bundle.putString("list_name", title);
                                    bottomFragment.setArguments(bundle);
                                    bottomFragment.show(getSupportFragmentManager(), AddNewItem.TAG);

                                    // Remove the observer once the dialog is shown
                                    updatedNameLiveData.removeObserver(this);
                                }

                            });
                        } else if (menuItem.getItemId() == R.id.delete_list) {
                            // Handle delete list
                        }
                        return true;
                    }
                });
                popupMenu.show();
            }
        });

                    }








    }






