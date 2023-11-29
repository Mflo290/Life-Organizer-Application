package com.example.lifeorganizerapp.UI;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;

import com.example.lifeorganizerapp.Adapter.ToDoItemAdapter;
import com.example.lifeorganizerapp.Adapter.ToDoListAdapter;
import com.example.lifeorganizerapp.R;
import com.example.lifeorganizerapp.database.Repository;
import com.example.lifeorganizerapp.entities.ToDoItem;
import com.example.lifeorganizerapp.entities.ToDoList;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class ToDoActivity extends AppCompatActivity implements AddNewItem.OnListUpdateListener{

    //Declare variables
//
    int toDoListID;
    TextView heading;
    String listName;
    ImageView backArrow;
    ImageView editIcon;

    Repository repository;
    FloatingActionButton fab;
    private ToDoItemAdapter toDoItemAdapter;
    private ToDoListAdapter toDoListAdapter;




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

        repository = new Repository(getApplication());

        //RecyclerView
        RecyclerView recyclerView = findViewById(R.id.tasks_recyclerview);
        toDoItemAdapter = new ToDoItemAdapter(this, repository);
        recyclerView.setAdapter(toDoItemAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        List<ToDoItem> allTaskItems = repository.getAllToDoItems();
        toDoItemAdapter.setToDoItems(allTaskItems);

        toDoListAdapter = new ToDoListAdapter(this, repository);

        //Find Views By ID
        heading = findViewById(R.id.heading_textview);
        backArrow = findViewById(R.id.back_arrow);
        editIcon = findViewById(R.id.edit_icon);
        fab = findViewById(R.id.floating_add_button);

        //Get Intents from previous screen and repo
        toDoListID = getIntent().getIntExtra("toDoListID", -1);
        listName = getIntent().getStringExtra("toDoListName");

        //Set data
        heading.setText(listName);


        repository.getAssociatedItems(toDoListID).observe(this, new Observer<List<ToDoItem>>() {
            @Override
            public void onChanged(List<ToDoItem> toDoItems) {
                toDoItemAdapter.setToDoItems(toDoItems);
            }
        });


        //Set FAB visibility when keyboard is out
        // Get the root layout of your activity
        final View activityRootView = findViewById(R.id.constraint_layout); // Replace with your actual root layout ID
        activityRootView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                Rect r = new Rect();
                activityRootView.getWindowVisibleDisplayFrame(r);
                int screenHeight = activityRootView.getRootView().getHeight();

                // Calculate the height difference between the screen height and visible window height
                int heightDifference = screenHeight - (r.bottom - r.top);

                // If the height difference is more than a threshold (e.g., 200dp), the keyboard is probably visible
                if (heightDifference > screenHeight * 0.15) {
                    // Hide your FloatingActionButton
                    fab.setVisibility(View.GONE);
                } else {
                    // Show your FloatingActionButton
                    fab.setVisibility(View.VISIBLE);
                }
            }
        });



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
                            repository.getAssociatedItems(toDoListID).observe(ToDoActivity.this, new Observer<List<ToDoItem>>() {
                                @Override
                                public void onChanged(List<ToDoItem> associatedItems) {
                                    if(associatedItems != null && !associatedItems.isEmpty()) {
                                        // Display AlertDialog for associated items
                                        AlertDialog.Builder builder = new AlertDialog.Builder(ToDoActivity.this);
                                        builder.setTitle("Alert")
                                                .setMessage("This list has associated items. Are you sure you want to delete it?")
                                                .setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                                                    @Override
                                                    public void onClick(DialogInterface dialog, int which) {
                                                        // Handle deletion when associated items exist
                                                        repository.getToDoListById(toDoListID).observe(ToDoActivity.this, new Observer<ToDoList>() {
                                                            @Override
                                                            public void onChanged(ToDoList currentList) {
                                                                if(currentList != null) {
                                                                    toDoListAdapter.deleteToDoList(currentList);
                                                                    finish(); // Finish the activity after deletion
                                                                }
                                                            }
                                                        });


                                                    }
                                                })
                                                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                                    @Override
                                                    public void onClick(DialogInterface dialog, int which) {
                                                        // Dismiss the dialog or perform any other action upon cancellation
                                                        dialog.dismiss();
                                                    }
                                                })
                                                .show();
                                    }
                                    else {
                                        repository.getToDoListById(toDoListID).observe(ToDoActivity.this, new Observer<ToDoList>() {
                                            @Override
                                            public void onChanged(ToDoList currentList) {
                                                if(currentList != null) {
                                                    toDoListAdapter.deleteToDoList(currentList);
                                                    finish();
                                                }
                                            }
                                        });
                                    }
                                }
                            });

                        }
                        return true;
                    }
                });
                popupMenu.show();
            }
        });


        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Repository repository = new Repository(getApplication());

                Intent intent = new Intent(ToDoActivity.this,AddNewTask.class);
                intent.putExtra("toDoListID", toDoListID);

                AddNewTask bottomFragment = new AddNewTask(repository);
                bottomFragment.setArguments(intent.getExtras()); // Pass the intent extras as fragment arguments
                bottomFragment.show(getSupportFragmentManager(), AddNewTask.TAG);
            }
        });




    }








}






