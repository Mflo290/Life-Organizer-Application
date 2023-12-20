package com.wgu.lifeorganizerapp.UI;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.wgu.lifeorganizerapp.R;
import com.wgu.lifeorganizerapp.database.Repository;
import com.wgu.lifeorganizerapp.entities.User;

public class MainActivity extends AppCompatActivity {

    Repository repository;
    EditText userNameInput;
    EditText passwordInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        repository = new Repository(getApplication());

//        // Create an instance of the User with admin credentials
//        User adminUser = new User(0,"Admin", "Password");
//
//        // Insert the admin user into the database
//        repository.insertUser(adminUser);

        //Initialize Variables
        Button loginButton = findViewById(R.id.loginButton);
        userNameInput = findViewById(R.id.usernameEditText);
        passwordInput  = findViewById(R.id.passwordEditText);

        //OnClickListener for login button
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String enteredUsername = userNameInput.getText().toString();
                String enteredPassword = passwordInput.getText().toString();

                LiveData<User> userLiveData = repository.getUserByUsernameAndPassword(enteredUsername, enteredPassword);

                userLiveData.observe(MainActivity.this, new Observer<User>() {
                    @Override
                    public void onChanged(User user) {
                        userLiveData.removeObserver(this);

                        if (user != null) {
                            Intent intent = new Intent(MainActivity.this, HomeScreen.class);
                            startActivity(intent);
                        }
                        else {
                            // Incorrect username or password, show an alert or toast message
                            Toast.makeText(MainActivity.this, "Incorrect username or password", Toast.LENGTH_SHORT).show();

                        }
                    }
                });

            }
        });



    }
}