package com.example.lifeorganizerapp.UI;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.lifeorganizerapp.Adapter.DataTableAdapter;
import com.example.lifeorganizerapp.R;
import com.example.lifeorganizerapp.database.Repository;
import com.example.lifeorganizerapp.entities.ToDoItem;

import java.util.ArrayList;
import java.util.List;

public class SearchReports extends AppCompatActivity {
    DrawerLayout drawerLayout;
    TextView headingTextView;
    ImageView hamIcon;
    LinearLayout homeButton, searchButton, reportsButton, logoutButton;
    Repository repository;
    RecyclerView recyclerView;
    DataTableAdapter dataTableAdapter;
    RadioGroup radioGroup;
    Button submitSearchButton;
    Button clearDataButton;

    RecyclerView emptyList;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_reports);

        repository = new Repository(getApplication());
        drawerLayout = findViewById(R.id.drawer_layout);
        hamIcon = findViewById(R.id.ham_menu);
        homeButton = findViewById(R.id.home_button);
        searchButton = findViewById(R.id.search_button);
        reportsButton = findViewById(R.id.reports_button);
        logoutButton = findViewById(R.id.logout_button);
        headingTextView = findViewById(R.id.heading_textview);
        radioGroup = findViewById(R.id.radioGroup);
        recyclerView = findViewById(R.id.data_recyclerView);
        submitSearchButton = findViewById(R.id.submit_search_button);
        clearDataButton = findViewById(R.id.clear_button);

        headingTextView.setText("Reports");

        submitSearchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int checkRadioID = radioGroup.getCheckedRadioButtonId();

                if (checkRadioID == R.id.radioAll) {
                    showAllToDoItems();
                } else if (checkRadioID == R.id.radioCompleted) {
                    showCompletedToDoItems();
                } else {
                    showUncompletedToDoItems();
                }
            }
        });

        clearDataButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                List<ToDoItem> emptyList = new ArrayList<>(); // Create an empty list
                setRecyclerView(recyclerView, emptyList);

            }
        });



        //onClick, hamburger menu icon
        hamIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openDrawer(drawerLayout);
            }
        });

        //onClick, home button in menu
        homeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SearchReports.this, HomeScreen.class);
                startActivity(intent);            }
        });

        //Click search button in the menu
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SearchReports.this, SearchKeywords.class);
                startActivity(intent);
            }
        });

        //Click reports button in the menu
        reportsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                closeDrawer(drawerLayout);
            }
        });

        //Click logout button in the menu
        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SearchReports.this, MainActivity.class);
                startActivity(intent);
            }
        });



    } //onCreate closing brace



    private void showUncompletedToDoItems() {
        repository.getUncompletedToDoItems().observe(this, uncompletedItems -> {
            if(uncompletedItems != null) {
                setRecyclerView(recyclerView, uncompletedItems);
            }
        });
    }

    private void showCompletedToDoItems() {
        repository.getCompletedToDoItems().observe(this, completedItems -> {
            if(completedItems != null) {
                setRecyclerView(recyclerView, completedItems);
            }
        });
    }

    private void showAllToDoItems() {
        List<ToDoItem> allItems = repository.getAllToDoItems();
        setRecyclerView(recyclerView, allItems);
    }


    private void setRecyclerView(RecyclerView recyclerView, List<ToDoItem> itemList) {
        // Set up RecyclerView adapter and display itemList in RecyclerView
        dataTableAdapter = new DataTableAdapter(this, itemList);
        recyclerView.setAdapter(dataTableAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Set the visibility of the "No items found" TextView based on the itemList
        TextView noItemsTextView = findViewById(R.id.no_items_text_view);
        if (itemList != null && itemList.isEmpty()) {
            noItemsTextView.setVisibility(View.VISIBLE); // Show the TextView
        } else {
            noItemsTextView.setVisibility(View.GONE); // Hide the TextView
        }
    }


    //Method to open the hamburger menu when clicked
    public static void openDrawer(DrawerLayout drawerLayout) {
        drawerLayout.openDrawer(GravityCompat.START);
    }


    //Method to close the hamburger menu when clicked
    public static void closeDrawer(DrawerLayout drawerLayout) {
        if(drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        }
    }


    @Override
    protected void onPause() {super.onPause();
        closeDrawer(drawerLayout);
    }



}