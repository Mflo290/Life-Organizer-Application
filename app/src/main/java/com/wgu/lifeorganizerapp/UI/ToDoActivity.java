package com.wgu.lifeorganizerapp.UI;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.wgu.lifeorganizerapp.Adapter.ToDoItemAdapter;
import com.wgu.lifeorganizerapp.Adapter.ToDoListAdapter;
import com.wgu.lifeorganizerapp.R;
import com.wgu.lifeorganizerapp.database.Repository;
import com.wgu.lifeorganizerapp.entities.ToDoItem;
import com.wgu.lifeorganizerapp.entities.ToDoList;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator;

public class ToDoActivity extends AppCompatActivity implements AddNewItem.OnListUpdateListener{

    //Declare variables

    int toDoListID;
    TextView heading;
    String listName;
    ImageView backArrow;
    ImageView editIcon;

    Repository repository;
    FloatingActionButton fab;
    private ToDoItemAdapter toDoItemAdapter;
    private ToDoListAdapter toDoListAdapter;
    private ArrayList<ToDoItem> completedTasksList = new ArrayList<>();




    ItemTouchHelper.SimpleCallback simpleCallback= new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {

        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            return false;

        }




        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
            int position = viewHolder.getAdapterPosition();

            if (direction == ItemTouchHelper.LEFT) {
                AlertDialog.Builder builder = new AlertDialog.Builder(ToDoActivity.this);
                builder.setTitle("Delete Task");
                builder.setMessage("Are you sure you want to delete this Task? ");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        // Retrieve the task item to be deleted
                        ToDoItem itemToDelete = toDoItemAdapter.getItem(position);

                        // Check if itemToDelete is not null before performing deletion
                        if (itemToDelete != null) {
                            // Delete the task from the repository
                            repository.delete(itemToDelete);

                            // Update the UI after deletion (if needed)
                            toDoItemAdapter.notifyItemRemoved(position);

                            // Show a toast message indicating successful deletion
                            Toast.makeText(ToDoActivity.this, "Task deleted", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // Clear the view's state to revert the swipe
                        toDoItemAdapter.notifyItemChanged(position);
                    }
                });
                // Show the delete confirmation dialog
                builder.show();

            } else if (direction == ItemTouchHelper.RIGHT){
                AlertDialog.Builder builder = new AlertDialog.Builder(ToDoActivity.this);
                builder.setTitle("Complete Task");
                builder.setMessage("Are you sure you want to complete this Task? ");

                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        ToDoItem completedTask = toDoItemAdapter.getItem(position);

                        if (completedTask != null) {
                            // Set the completion date as today's date
                            completedTask.setDateCompleted(LocalDate.now());

                            // Update the completion date in the repository
                            repository.update(completedTask);

                            // Add the completed task to the completedTasksList (ArrayList)
                            completedTasksList.add(completedTask);

                            toDoItemAdapter.notifyItemRemoved(position);

                            Toast.makeText(ToDoActivity.this, "Task completed", Toast.LENGTH_SHORT).show();

                        }
                    }
                });

                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // Clear the view's state to revert the swipe
                        toDoItemAdapter.notifyItemChanged(position);
                    }
                });
                // Show the delete confirmation dialog
                builder.show();

            }


        }

        @Override
        public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
            new RecyclerViewSwipeDecorator.Builder(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
                    .addSwipeLeftBackgroundColor(ContextCompat.getColor(ToDoActivity.this, R.color.red))
                    .addSwipeLeftActionIcon(R.drawable.delete_icon_24)
                    .addSwipeRightBackgroundColor(ContextCompat.getColor(ToDoActivity.this,R.color.green))
                    .addSwipeRightActionIcon(R.drawable.check_icon_24)
                    .create()
                    .decorate();


            super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);


        }


    };


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

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleCallback);
        itemTouchHelper.attachToRecyclerView(recyclerView);

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


        repository.getAssociatedTaskNullDate(toDoListID).observe(this, new Observer<List<ToDoItem>>() {
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
                            repository.getAssociatedTaskNullDate(toDoListID).observe(ToDoActivity.this, new Observer<List<ToDoItem>>() {
                                @Override
                                public void onChanged(List<ToDoItem> associatedItems) {
                                    if(associatedItems != null && !associatedItems.isEmpty()) {
                                        // Display AlertDialog for associated items
                                        AlertDialog.Builder builder = new AlertDialog.Builder(ToDoActivity.this);
                                        builder.setTitle("Alert")
                                                .setMessage("This list has associated tasks. Are you sure you want to delete this list?")
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
                                    } else {
                                        AlertDialog.Builder builder = new AlertDialog.Builder(ToDoActivity.this);
                                        builder.setTitle("Alert")
                                                .setMessage("Are you sure you want to delete this list?")
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

                                                                    Toast.makeText(ToDoActivity.this, "List deleted", Toast.LENGTH_SHORT).show();
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
                intent.putExtra("listName", listName);

                AddNewTask bottomFragment = new AddNewTask(repository);
                bottomFragment.setArguments(intent.getExtras()); // Pass the intent extras as fragment arguments
                bottomFragment.show(getSupportFragmentManager(), AddNewTask.TAG);
            }
        });




    }







}






