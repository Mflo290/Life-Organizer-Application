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

import com.example.lifeorganizerapp.Adapter.ToDoListAdapter;
import com.example.lifeorganizerapp.R;
import com.example.lifeorganizerapp.database.Repository;
import com.example.lifeorganizerapp.entities.ToDoItem;
import com.example.lifeorganizerapp.entities.ToDoList;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class AddNewTask extends BottomSheetDialogFragment {

    //Declare Variables
    public static final String TAG = "ActionBottomDialog";
    private EditText newTaskText;
    private Button newTaskSaveButton;
    Repository repository;


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

        newTaskText = getView().findViewById(R.id.item_edittext);
        newTaskSaveButton = getView().findViewById(R.id.save_button);

        boolean isUpdate = false;   //This checks if there will be a new task or an edit
        final Bundle bundle = getArguments();   //This is used to pass data in fragments

        if (bundle != null) {
            isUpdate = true;
            String taskName = bundle.getString("task_name");
            newTaskText.setText(taskName);
            newTaskSaveButton.setTextColor(taskName.isEmpty()
                    ? Color.GRAY
                    : ContextCompat.getColor(requireContext(), R.color.purple));
        }

        newTaskText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            //This method checks if the text is empty or not to enable or disable the save button
            @Override
            public void onTextChanged(CharSequence sequence, int i, int i1, int i2) {
                newTaskSaveButton.setEnabled(!sequence.toString().isEmpty());
                newTaskSaveButton.setTextColor(sequence.toString().isEmpty()
                        ? Color.GRAY
                        : ContextCompat.getColor(requireContext(), R.color.purple));

            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });

        final boolean finalIsUpdate = isUpdate;
//        newTaskSaveButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                String newTaskName = newTaskText.getText().toString();
//
//                if (finalIsUpdate) {
//                    int taskID = bundle.getInt("task_id");
//                    String taskTitle = bundle.getString("task_name");
//                    boolean checked = false;
//                    int toDoListID =
//                    ToDoItem updatedTask = new ToDoItem(taskID, newTaskName, checked, toDoListID);
//                    repository.update(updatedTask);
//                    notifyListUpdated(updatedTask);
//
//                } else {
//                    ToDoItem newTask = new ToDoItem();
//                    newTask.setTitle(newTaskName);
//                    repository.insert(newTask);
//                    notifyListUpdated(newTask);
//                }
//                dismiss(); // Close the dialog after saving
//            }
//        });
    }





}
