package com.example.lifeorganizerapp.UI;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.lifeorganizerapp.R;
import com.example.lifeorganizerapp.database.Repository;
import com.example.lifeorganizerapp.entities.ToDoItem;


public class HomeScreen extends AppCompatActivity {



        //Declared Navigation Widgets
        DrawerLayout drawerLayout;
        ImageView hamIcon;
        LinearLayout homeButton, searchButton, reportsButton, logoutButton;
        Button toDoButton;
        Repository repository;



        @Override
        protected void onCreate (Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);

        repository = new Repository(getApplication());


        //Initialized menu-clickable buttons
        drawerLayout = findViewById(R.id.drawer_layout);
        hamIcon = findViewById(R.id.ham_menu);
        homeButton = findViewById(R.id.home_button);
        searchButton = findViewById(R.id.search_button);
        reportsButton = findViewById(R.id.reports_button);
        logoutButton = findViewById(R.id.logout_button);
        toDoButton = findViewById(R.id.todo_button);



        //Click on the logout button in the menu
            toDoButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(HomeScreen.this, ToDoLists.class);
                    startActivity(intent);
                }
            });


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
                  closeDrawer(drawerLayout);
                }
        });

            //Click search button in the menu
            searchButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(HomeScreen.this, SearchKeywords.class);
                    startActivity(intent);
                }
            });

            //Click reports button in the menu
            reportsButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(HomeScreen.this,"Reports Button", Toast.LENGTH_SHORT).show();
                }
            });

            //Click logout button in the menu
            logoutButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(HomeScreen.this, MainActivity.class);
                    startActivity(intent);
                }
            });



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
