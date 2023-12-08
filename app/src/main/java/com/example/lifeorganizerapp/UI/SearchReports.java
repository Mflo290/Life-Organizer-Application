package com.example.lifeorganizerapp.UI;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.lifeorganizerapp.R;
import com.example.lifeorganizerapp.database.Repository;

public class SearchReports extends AppCompatActivity {
    DrawerLayout drawerLayout;
    TextView headingTextView;
    ImageView hamIcon;
    LinearLayout homeButton, searchButton, reportsButton, logoutButton;
    Repository repository;

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


        headingTextView.setText("Reports");




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