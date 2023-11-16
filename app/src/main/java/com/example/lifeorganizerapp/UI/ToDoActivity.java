package com.example.lifeorganizerapp.UI;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.lifeorganizerapp.R;
import com.example.lifeorganizerapp.database.Repository;

import org.w3c.dom.Text;

public class ToDoActivity extends AppCompatActivity {

    //Declare variables

    TextView listName;
    ImageView backArrow;
    ImageView editIcon;

    Repository repository;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_to_do);

        repository = new Repository(getApplication());

        listName = findViewById(R.id.heading_textview);
        backArrow = findViewById(R.id.back_arrow);
        editIcon = findViewById(R.id.edit_icon);



    }



}