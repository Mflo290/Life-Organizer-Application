package com.example.lifeorganizerapp.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.lifeorganizerapp.R;
import com.example.lifeorganizerapp.database.Repository;
import com.example.lifeorganizerapp.entities.ToDoItem;

import java.util.List;

public class ToDoItemAdapter extends RecyclerView.Adapter<ToDoItemAdapter.ItemViewHolder> {

    private List<ToDoItem> toDoItems;
    private final Context context;
    private final LayoutInflater mInflater;
    Repository repository;

    public ToDoItemAdapter(List<ToDoItem> toDoItems, Context context, LayoutInflater mInflater) {
        this.toDoItems = toDoItems;
        this.context = context;
        this.mInflater = mInflater;
    }

    public Context getContext() {return context; }


    public class ItemViewHolder extends RecyclerView.ViewHolder {
        private final CheckBox toDoItemView;
        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);
            toDoItemView = itemView.findViewById(R.id.todo_item_view);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    final ToDoItem currentItem = toDoItems.get(position);
                }
            });

        }
    }
    @NonNull
    @Override
    public ToDoItemAdapter.ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.todo_item_view, parent, false);
        return new ItemViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ToDoItemAdapter.ItemViewHolder holder, int position) {
        ToDoItem currentItem = toDoItems.get(position);
        String description = currentItem.getTitle();

        holder.toDoItemView.setText(description);
    }

    @Override
    public int getItemCount() {
        if (toDoItems != null) {
            return toDoItems.size();
        } else {
            return 0;
        }
    }


    public void setToDoItems(List<ToDoItem> toDoItems) {
        toDoItems = toDoItems;
        notifyDataSetChanged();
    }



//    public static class ViewHolder extends RecyclerView.ViewHolder {
//        CheckBox checkBox;
//    }



}
