package com.example.lifeorganizerapp.UI;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.lifeorganizerapp.R;



public class HomeScreen extends AppCompatActivity {



        //Declared Navigation Widgets
        DrawerLayout drawerLayout;
        ImageView hamIcon;
        LinearLayout homeButton, searchButton, reportsButton, logoutButton;



        @Override
        protected void onCreate (Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);


        //Initialized menu-clickable buttons
        drawerLayout = findViewById(R.id.drawer_layout);
        hamIcon = findViewById(R.id.ham_menu);
        homeButton = findViewById(R.id.home_button);
        searchButton = findViewById(R.id.search_button);
        reportsButton = findViewById(R.id.reports_button);
        logoutButton = findViewById(R.id.logout_button);


        //Set click listener for the hamburger menu
        hamIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openDrawer(drawerLayout);
            }
        });


        //Set click listener for home button in the menu
            homeButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(HomeScreen.this, "Home Button", Toast.LENGTH_SHORT).show();
                }
        });
            //Set click listener for the search button in the menu
            searchButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(HomeScreen.this,"Search Button", Toast.LENGTH_SHORT).show();
                }
            });
            //Set click listener for the reports button in the menu
            reportsButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(HomeScreen.this,"Reports Button", Toast.LENGTH_SHORT).show();
                }
            });
            //Set click listener for the logout button in the menu
            logoutButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(HomeScreen.this,"Logout Button", Toast.LENGTH_SHORT).show();
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

        //This method is used to redirect to the selected menu screen
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
