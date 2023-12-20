package com.wgu.lifeorganizerapp.UI;

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

import com.wgu.lifeorganizerapp.R;
import com.wgu.lifeorganizerapp.database.Repository;
import com.wgu.lifeorganizerapp.entities.ToDoList;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class AddNewItem  extends BottomSheetDialogFragment {

    //Declare Variables
    public static final String TAG = "ActionBottomDialog";
    private EditText newItemText;
    private Button newItemSaveButton;
    Repository repository;


    public interface OnListUpdateListener {
       void onListUpdated(ToDoList updatedList);
    }

    private OnListUpdateListener listener; // Listener instance variable

    public void setOnListUpdateListener(OnListUpdateListener listener) {
        this.listener = listener;
    }

    // Method to call when the list is updated
    private void notifyListUpdated(ToDoList updatedList) {
        if (listener != null) {
            listener.onListUpdated(updatedList);
        }
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(STYLE_NORMAL, R.style.DialogStyle);
    }


    public AddNewItem(Repository repository) {
        this.repository = repository;
    }


    @Override
    public void onDismiss (DialogInterface dialog){
        super.onDismiss(dialog);
        Bundle arguments = getArguments();
        if(arguments != null) {
            int toDoListID = arguments.getInt("todo_list_id");
            if (toDoListID != -1) {
                // Remove observer when the dialog is dismissed
                LiveData<ToDoList> updatedNameLiveData = repository.getToDoListById(getArguments().getInt("todo_list_id"));
                updatedNameLiveData.removeObservers(getViewLifecycleOwner());
            }
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

        newItemText = getView().findViewById(R.id.item_edittext);
        newItemSaveButton = getView().findViewById(R.id.save_button);

        boolean isUpdate = false;   //This checks if there will be a new item or an edit
        final Bundle bundle = getArguments();   //This is used to pass data in fragments

        if (bundle != null) {
            isUpdate = true;
            String listName = bundle.getString("list_name");
            if(listName != null) {
                newItemText.setText(listName);
                newItemSaveButton.setTextColor(listName.isEmpty()
                        ? Color.GRAY
                        : ContextCompat.getColor(requireContext(), R.color.dark_purple));
            }
        }

        newItemText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            //This method checks if the text is empty or not to enable or disable the save button
            @Override
            public void onTextChanged(CharSequence sequence, int i, int i1, int i2) {
                newItemSaveButton.setEnabled(!sequence.toString().isEmpty());
                newItemSaveButton.setTextColor(sequence.toString().isEmpty()
                        ? Color.GRAY
                        : ContextCompat.getColor(requireContext(), R.color.dark_purple));

            }
            @Override
            public void afterTextChanged(Editable editable) {
            }
        });

        final boolean finalIsUpdate = isUpdate;
        newItemSaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String newItemName = newItemText.getText().toString();

                if (finalIsUpdate) {
                    int toDoListID = bundle.getInt("todo_list_id");
                    if(toDoListID != -1) {
                        ToDoList updatedList = new ToDoList(toDoListID, newItemName);
                        repository.update(updatedList);
                        notifyListUpdated(updatedList);
                    }
                } else {
                    ToDoList newList = new ToDoList();
                    newList.setListName(newItemName);
                    repository.insert(newList);
                    notifyListUpdated(newList);
                }
                dismiss(); // Close the dialog after saving
            }
        });



    }





}


