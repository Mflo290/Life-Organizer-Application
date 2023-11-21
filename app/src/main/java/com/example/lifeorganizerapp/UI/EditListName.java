package com.example.lifeorganizerapp.UI;

import android.app.Activity;
import android.content.DialogInterface;
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
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;

import com.example.lifeorganizerapp.R;
import com.example.lifeorganizerapp.database.Repository;
import com.example.lifeorganizerapp.entities.ToDoList;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class EditListName extends BottomSheetDialogFragment {

    public static final String TAG = "ActionBottomDialog";
    private EditText currentTitle;
    private Button saveNewTitleButton;
    Repository repository;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(STYLE_NORMAL, R.style.DialogStyle);
    }

    public EditListName(Repository repository) {
        this.repository = repository;
    }

    @Override
    public void onDismiss (DialogInterface dialog){
        Activity activity = getActivity();
        if(activity instanceof DialogCloseListener) {
            ((DialogCloseListener)activity).handleDialogClose(dialog);
        }
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

        currentTitle = getView().findViewById(R.id.item_edittext);
        saveNewTitleButton = getView().findViewById(R.id.save_button);

        boolean isUpdate = false;   //This checks if there will be a new item or an edit

        final Bundle bundle = getArguments();   //This is used to pass data in fragments

        if (bundle != null) {     //If the bundle is not empty, then the existing listName will be passed onto the fragment
            isUpdate = true;
            String listName = bundle.getString("list_name");

            currentTitle.setText(listName);
            if (listName.length() > 0) {        //If there is text, the save button color will change
                saveNewTitleButton.setTextColor(ContextCompat.getColor(getContext(), R.color.purple));
            }
        }
        currentTitle.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            //This method checks if the text is empty or not to enable or disable the save button
            @Override
            public void onTextChanged(CharSequence sequence, int i, int i1, int i2) {
                if (sequence.toString().equals("")) {
                    saveNewTitleButton.setEnabled(false);
                    saveNewTitleButton.setTextColor(Color.GRAY);
                } else {
                    saveNewTitleButton.setEnabled(true);
                    saveNewTitleButton.setTextColor(ContextCompat.getColor(getContext(), R.color.purple));
                }
            }
            @Override
            public void afterTextChanged(Editable editable) {
            }
        });

        final boolean finalIsUpdate = isUpdate;
        saveNewTitleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String newItemName = currentTitle.getText().toString();

                if (finalIsUpdate) {
                    int toDoListID = bundle.getInt("todo_list_id");
                    LiveData<ToDoList> updatedNameLiveData = repository.getToDoListById(toDoListID);
                    // Observe the LiveData for changes AND THEN go into the onChanged method to up
                    updatedNameLiveData.observe(getViewLifecycleOwner(), new Observer<ToDoList>() {
                        @Override
                        public void onChanged(ToDoList toDoList) {
                            ToDoList newList = new ToDoList();
                            newList.setListName(newItemName);
                            repository.update(newList);
                        }
                    });
                } else {
                    ToDoList newList = new ToDoList();
                    newList.setListName(newItemName);
                    repository.insert(newList);
                }
                dismiss(); // Close the dialog after saving
            }
        });



    }









}
