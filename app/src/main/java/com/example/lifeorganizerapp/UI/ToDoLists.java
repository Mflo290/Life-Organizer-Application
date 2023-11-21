package com.example.lifeorganizerapp.UI;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.lifeorganizerapp.Adapter.ToDoListAdapter;
import com.example.lifeorganizerapp.R;
import com.example.lifeorganizerapp.database.Repository;
import com.example.lifeorganizerapp.entities.ToDoList;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class ToDoLists extends AppCompatActivity implements DialogCloseListener {



    //Declare variables
    TextView headingTextView;
    ImageView backArrow;
    FloatingActionButton fab;


    Repository repository;
    private ToDoListAdapter toDoListAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_to_do_lists);

        repository = new Repository(getApplication());

        //Initialize RecyclerView
        Repository repository = new Repository(getApplication());
        RecyclerView recyclerView = findViewById(R.id.lists_recyclerview);
        toDoListAdapter = new ToDoListAdapter(this, repository);
        recyclerView.setAdapter(toDoListAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        List<ToDoList> allToDoLists = repository.getAllToDoLists();
        toDoListAdapter.setToDoLists(allToDoLists);

//        ItemTouchHelper itemTouchHelper =
//
//        new ItemTouchHelper(new TouchHelper(toDoListAdapter));
//        itemTouchHelper.attachToRecyclerView(recyclerView);


        //Initialize variables
        headingTextView = findViewById(R.id.heading_textview);
        backArrow = findViewById(R.id.back_arrow);
        fab = findViewById(R.id.floating_add_button);

        //Change heading text on the toolbar
        headingTextView.setText("Lists");


        //Click on the back arrow
        backArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });



        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Repository repository = new Repository(getApplication());
                AddNewItem bottomFragment = new AddNewItem(repository);
                bottomFragment.show(getSupportFragmentManager(), AddNewItem.TAG);
            }
        });

    }


    @Override
    public void handleDialogClose(DialogInterface dialog){
        List<ToDoList> currentLists = repository.getAllToDoLists();
        toDoListAdapter.setToDoLists(currentLists);     //Adapter will be updated with the new list
        toDoListAdapter.notifyDataSetChanged();     //notifyDataSetChanged() will inform the RecyclerView to refresh its view.
    }


}