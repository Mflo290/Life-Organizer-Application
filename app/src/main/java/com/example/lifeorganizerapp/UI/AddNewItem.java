package com.example.lifeorganizerapp.UI;

import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;

import androidx.core.content.ContextCompat;

import com.example.lifeorganizerapp.R;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class AddNewItem  extends BottomSheetDialogFragment {

    public static final String TAG = "ActionBottomDialog";

    private EditText newItemText;

    //Define database handler

    public static AddNewItem newInstance() {
        return new AddNewItem();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(STYLE_NORMAL, R.style.DialogStyle);
    }

//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//        View view = inflater.inflate(R.layout.new_item, container, false);
//        getDialog().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
//        return view;
//    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
//        super.onViewCreated(view, savedInstanceState);
//        newItemText = getView().findViewById(R.id.newItemButton);

        //database handler
        //db.openDatabase();

        boolean isUpdate = false;
        final Bundle bundle = getArguments();

        if(bundle != null) {
            isUpdate = true;
            String item = bundle.getString("task");
            newItemText.setText(item);
            if(item.length() > 0) {
 //               newItemSaveButton.setTextColor(ContextCompat.getColor(getContext(), com.google.android.material.R.color.design_default_color_primary_dark));
            }
            newItemText.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence sequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence sequence, int i, int i1, int i2) {
                    if(sequence.toString().equals("")) {
  //                      newItemSaveButton.setEnabled(false);
  //                      newItemDaveButton.setTextColor(Color.GRAY);
                    }
                    else{
 //                       newItemSaveButton.setEnabled(true);
 //                       newItemDaveButton.setTextColor(ContextCompat.getColor(getContext(), R.color.colorPrimaryDark));
                    }
                }

                @Override
                public void afterTextChanged(Editable sequence) {

                }
            });




        }

    }





}
