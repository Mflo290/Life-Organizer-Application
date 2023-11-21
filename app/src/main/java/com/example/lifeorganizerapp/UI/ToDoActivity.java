package com.example.lifeorganizerapp.UI;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;

import com.example.lifeorganizerapp.R;
import com.example.lifeorganizerapp.database.Repository;

import org.w3c.dom.Text;

public class ToDoActivity extends AppCompatActivity {

    //Declare variables

    int toDoListID;
    TextView heading;
    String listName;
    ImageView backArrow;
    ImageView editIcon;

    Repository repository;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_to_do);

        //Initialize Variables
        repository = new Repository(getApplication());

        //Find Views By ID
        heading = findViewById(R.id.heading_textview);
        backArrow = findViewById(R.id.back_arrow);
        editIcon = findViewById(R.id.edit_icon);


        //Get Intents from previous screen and repo
        toDoListID = getIntent().getIntExtra("toDoListID", -1);
        listName = getIntent().getStringExtra("toDoListName");

        //Set data
        heading.setText(listName);



        //Click on the back arrow
        backArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
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
                        Repository repository = new Repository(getApplication());
                        if (menuItem.getItemId() == R.id.edit_list) {
                            AddNewItem bottomFragment = new AddNewItem(repository);
                            bottomFragment.show(getSupportFragmentManager(), AddNewItem.TAG);
                        } else if (menuItem.getItemId() == R.id.delete_list) {


                        }
                        return true;
                    }
                });
                popupMenu.show();
            }
        });





    }






}