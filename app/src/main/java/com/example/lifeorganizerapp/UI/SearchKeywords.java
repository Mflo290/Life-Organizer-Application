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
import android.widget.Toast;

import com.example.lifeorganizerapp.Adapter.DataTableAdapter;
import com.example.lifeorganizerapp.R;
import com.example.lifeorganizerapp.database.Repository;

public class SearchKeywords extends AppCompatActivity {

    //Declared Navigation Widgets
    DrawerLayout drawerLayout;
    ImageView hamIcon;
    LinearLayout homeButton, searchButton, reportsButton, logoutButton;
    Button submitSearchButton;
    Repository repository;
    RecyclerView recyclerView;
    DataTableAdapter dataTableAdapter;


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

        //RecyclerView
        recyclerView = findViewById(R.id.data_table_recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        dataTableAdapter = new DataTableAdapter(this);
        recyclerView.setAdapter(dataTableAdapter);



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

            }
        });


    } //create method


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

    //Method to redirect to selected menu button's activity
    public static void redirectActivity(Activity activity, Class secondActivity) {
        Intent intent = new Intent(activity, secondActivity);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        activity.startActivity(intent);
        activity.finish();
    }


    @Override
    protected void onPause() {super.onPause();
        closeDrawer(drawerLayout);
    }





}