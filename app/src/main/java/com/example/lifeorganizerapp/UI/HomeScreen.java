package com.example.lifeorganizerapp.UI;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.lifecycle.Observer;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lifeorganizerapp.R;
import com.example.lifeorganizerapp.database.Repository;
import com.example.lifeorganizerapp.entities.ToDoItem;

import java.util.List;


public class HomeScreen extends AppCompatActivity {



        //Declared Navigation Widgets
        DrawerLayout drawerLayout;
        ImageView hamIcon;
        LinearLayout homeButton, searchButton, reportsButton, logoutButton;
        Button toDoButton;
        Repository repository;
        List<ToDoItem> checkedTasks;
        int currentPosition = 0;
        ImageView backwardArrow;
        ImageView forwardArrow;


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
        backwardArrow = findViewById(R.id.arrow_backward);
        forwardArrow = findViewById(R.id.arrow_forward);


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
                    Intent intent = new Intent(HomeScreen.this, SearchReports.class);
                    startActivity(intent);
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


            repository.getCheckedUncompletedToDoItems().observe(this, new Observer<List<ToDoItem>>() {
                @Override
                public void onChanged(List<ToDoItem> itemList) {
                    checkedTasks = itemList;
                    displayToDoItems();
                }
            });


            backwardArrow.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    showPreviousToDoItems();
                }
            });

            forwardArrow.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showNextToDoItems();
                }
            });


        }   //onCreate closing brace


    private void showPreviousToDoItems() {
        if (currentPosition > 0) {
            currentPosition--;
            displayToDoItems();
        }
    }

    private void showNextToDoItems() {
        if (checkedTasks != null && currentPosition < checkedTasks.size() - 1) {
            currentPosition++;
            displayToDoItems();
        }
    }

    private void displayToDoItems() {
        ConstraintLayout reminderBottomLayout = findViewById(R.id.reminder_bottom_layout);

        if (checkedTasks != null && !checkedTasks.isEmpty()) {
            ToDoItem currentItem = checkedTasks.get(currentPosition);
            TextView reminderTextView = findViewById(R.id.reminder_textView);
            ImageView backwardArrow = findViewById(R.id.arrow_backward);
            ImageView forwardArrow = findViewById(R.id.arrow_forward);

            if (reminderTextView != null) {
                String message = "Reminder: " + currentItem.getTitle();
                reminderTextView.setText(message); // Update with task title or relevant data

                if (checkedTasks.size() > 1) {
                    backwardArrow.setVisibility(View.VISIBLE);
                    forwardArrow.setVisibility(View.VISIBLE);

                    if (currentPosition == 0) {
                        backwardArrow.setVisibility(View.INVISIBLE);
                    } else if (currentPosition == checkedTasks.size() - 1) {
                        forwardArrow.setVisibility(View.INVISIBLE);
                    }
                } else {
                    backwardArrow.setVisibility(View.INVISIBLE);
                    forwardArrow.setVisibility(View.INVISIBLE);
                }
            }
            // Set reminder_bottom_layout visible
            reminderBottomLayout.setVisibility(View.VISIBLE);
            } else {
            // Hide reminder_bottom_layout if no tasks
            reminderBottomLayout.setVisibility(View.INVISIBLE);
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
