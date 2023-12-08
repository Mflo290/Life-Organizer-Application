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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lifeorganizerapp.Adapter.DataTableAdapter;
import com.example.lifeorganizerapp.R;
import com.example.lifeorganizerapp.database.Repository;
import com.example.lifeorganizerapp.entities.ToDoItem;

import java.util.List;

public class SearchKeywords extends AppCompatActivity {

    //Declared Navigation Widgets
    DrawerLayout drawerLayout;
    TextView headingTextView;
    ImageView hamIcon;
    LinearLayout homeButton, searchButton, reportsButton, logoutButton;
    Button submitSearchButton;
    Button clearDataButton;
    Repository repository;
    RecyclerView recyclerView;
    DataTableAdapter dataTableAdapter;
    EditText searchInputText;
    List<ToDoItem> itemList;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_keywords);

        repository = new Repository(getApplication());

//      //Initialized menu-clickable buttons
        drawerLayout = findViewById(R.id.drawer_layout);
        hamIcon = findViewById(R.id.ham_menu);
        homeButton = findViewById(R.id.home_button);
        searchButton = findViewById(R.id.search_button);
        reportsButton = findViewById(R.id.reports_button);
        logoutButton = findViewById(R.id.logout_button);
        submitSearchButton = findViewById(R.id.submit_search_button);
        clearDataButton = findViewById(R.id.clear_button);
        searchInputText = findViewById(R.id.search_input_editText);
        headingTextView = findViewById(R.id.heading_textview);

        //RecyclerView
        recyclerView = findViewById(R.id.data_table_recyclerview);

        headingTextView.setText("Search Keywords");



        //Click on the hamburger menu
        hamIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openDrawer(drawerLayout);
            }
        });

        //Click on the home button in the menu
        homeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SearchKeywords.this, HomeScreen.class);
                startActivity(intent);            }
        });

        //Click search button in the menu
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { closeDrawer(drawerLayout); }
        });



        //Click reports button in the menu
        reportsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SearchKeywords.this, SearchReports.class);
                startActivity(intent);
            }
        });

        //Click logout button in the menu
        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SearchKeywords.this, MainActivity.class);
                startActivity(intent);
            }
        });

        submitSearchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String userInput = searchInputText.getText().toString().trim();
                if(!userInput.isEmpty()) {
                    itemList = repository.searchTaskName(userInput);
                    setRecyclerView(itemList, userInput);
                } else {
                    recyclerView.setVisibility(View.GONE); // Hide RecyclerView
                    findViewById(R.id.no_items_text_view).setVisibility(View.VISIBLE);
                }
            }
        });

        clearDataButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                itemList = null;
                setRecyclerView(itemList, "");
            }
        });


    } //create method

    public void setRecyclerView(List<ToDoItem> itemList, String searchText) {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        if(itemList != null && itemList.size() > 0) {
            dataTableAdapter = new DataTableAdapter(this, itemList, searchText);
            recyclerView.setAdapter(dataTableAdapter);
            recyclerView.setVisibility(View.VISIBLE);
            findViewById(R.id.no_items_text_view).setVisibility(View.GONE); // Hide TextView
        } else {
            recyclerView.setVisibility(View.GONE); // Hide RecyclerView
            findViewById(R.id.no_items_text_view).setVisibility(View.VISIBLE); // Show TextView
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