package com.example.lifeorganizerapp.UI;

import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

import androidx.core.content.ContextCompat;
import androidx.lifecycle.Observer;

import com.example.lifeorganizerapp.R;
import com.example.lifeorganizerapp.database.Repository;
import com.example.lifeorganizerapp.entities.ToDoItem;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class EditTaskFragment extends BottomSheetDialogFragment {
    // Declare Variables
    public static final String TAG = "EditTaskFragment";
    private EditText taskEditText;
    private Button saveButton;
    private Repository repository;


    public EditTaskFragment(Repository repository) {
        this.repository = repository;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(STYLE_NORMAL, R.style.DialogStyle);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.bottom_dialog, container, false);
        getDialog().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        return view;
    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //Retrieve toDoListID from the intent
        int listID = getArguments().getInt("toDoListID", -1);

        taskEditText = getView().findViewById(R.id.item_edittext);
        saveButton = getView().findViewById(R.id.save_button);

        final Bundle bundle = getArguments();   //This is used to pass data in fragments

        if (bundle != null) {
            String taskName = bundle.getString("taskName");
            if (taskName != null) {
                taskEditText.setText(taskName);
                saveButton.setTextColor(taskName.isEmpty()
                        ? Color.GRAY
                        : ContextCompat.getColor(requireContext(), R.color.purple));
            }
        }

        taskEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            //This method checks if the text is empty or not to enable or disable the save button
            @Override
            public void onTextChanged(CharSequence sequence, int i, int i1, int i2) {
                saveButton.setEnabled(!sequence.toString().isEmpty());
                saveButton.setTextColor(sequence.toString().isEmpty()
                        ? Color.GRAY
                        : ContextCompat.getColor(requireContext(), R.color.purple));

            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });



        saveButton.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View view) {
             String newTaskName = taskEditText.getText().toString();
             int taskID = getArguments().getInt("taskID", -1);
             int listID = getArguments().getInt("toDoListID", -1);
             String listName = getArguments().getString("listName");

             if (!newTaskName.isEmpty() && listID != -1) {
                 repository.getToDoItemByID(taskID).observe(getViewLifecycleOwner(), new Observer<ToDoItem>() {
                     @Override
                     public void onChanged(ToDoItem existingTask) {
                         if(existingTask != null) {
                             existingTask.setListID(listID);
                             existingTask.setTitle(newTaskName);
                             existingTask.setListName(listName);

                             // Save the new task using your repository
                             repository.update(existingTask);

                             dismiss(); // Close the dialog after saving
                         }
                     }
                 });




             }
         }
     });
    }




}
