package com.example.lifeorganizerapp.tochhelper;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.view.View;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.example.lifeorganizerapp.Adapter.ToDoItemAdapter;
import com.example.lifeorganizerapp.Adapter.ToDoListAdapter;
import com.example.lifeorganizerapp.R;

public class TouchHelper extends ItemTouchHelper.SimpleCallback {

    private ToDoItemAdapter adapter;

    // This class will now support the swipe left and write functions
    public TouchHelper(ToDoItemAdapter adapter) {
        super(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT);
        this.adapter = adapter;
    }




    @Override
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
        return false;
    }



    //This method contains all the swipe functions
    @Override
    public void onSwiped(final RecyclerView.ViewHolder viewHolder, int direction) {
        final int position = viewHolder.getAdapterPosition();
        if(direction == ItemTouchHelper.LEFT) {
            AlertDialog.Builder builder = new AlertDialog.Builder(adapter.getContext());
            builder.setTitle("Delete Task");
            builder.setMessage("Are you sure you want to delete this Item? ");
            builder.setPositiveButton("Confirm",
                    new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
            //        adapter.deleteToDoList(position);
                 }
            });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                adapter.notifyItemChanged(viewHolder.getAdapterPosition());
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
        }

    }

    @Override
    public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
        super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);

        Drawable deleteIcon = ContextCompat.getDrawable(adapter.getContext(), R.drawable.delete_icon_24);
        ColorDrawable background = new ColorDrawable(Color.RED);

        View itemView = viewHolder.itemView;
        int backgroundCornerOffset = 20;

        int iconMargin = (itemView.getHeight() - deleteIcon.getIntrinsicHeight()) / 2;
        int iconTop = itemView.getTop() + (itemView.getHeight() - deleteIcon.getIntrinsicHeight()) / 2;
        int iconBottom = iconTop + deleteIcon.getIntrinsicHeight();

        if(dY > 0) {
            // Swiping to The right
        }
         else if(dX < 0) { //Swiping to the Left
            int iconLeft = itemView.getRight() + iconMargin - deleteIcon.getIntrinsicWidth() - 100;
            int iconRight = itemView.getRight() - iconMargin;    //These two
            deleteIcon.setBounds(iconLeft, iconTop, iconRight, iconBottom);

            // Ensure the background stops at the normal size of the delete icon
            int backgroundLeft = Math.min(itemView.getRight() + ((int) dX) - backgroundCornerOffset, iconLeft);
            int backgroundRight = iconLeft + deleteIcon.getIntrinsicWidth(); // Adjusted width of the icon here
            background.setBounds(backgroundLeft, itemView.getTop(), backgroundRight, itemView.getBottom());
        }
        else {
            background.setBounds(0,0,0,0);
        }
        background.draw(c);
        deleteIcon.draw(c);
    }




}
