package com.example.lifeorganizerapp.UI;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.lifeorganizerapp.R;

public class ToDoLists extends AppCompatActivity {

    //Declare variables
    TextView headingTextView;
    ImageView backArrow;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_to_do_lists);

        //Initialize variables
        headingTextView = findViewById(R.id.heading_textview);
        backArrow = findViewById(R.id.back_arrow);

        //Change heading text on the toolbar
        headingTextView.setText("Lists");

        //Click on the back arrow
        backArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
//                Intent intent = new Intent(ToDoLists.this, HomeScreen.class);
//                startActivity(intent);
            }
        });



    }
}