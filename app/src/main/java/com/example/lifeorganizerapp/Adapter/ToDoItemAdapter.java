package com.example.lifeorganizerapp.Adapter;

import android.content.Context;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
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

    public ToDoItemAdapter(Context context, Repository repository) {
        this.mInflater = LayoutInflater.from(context);
        this.context = context;
        this.repository = repository;
    }

    public Context getContext() {return context; }


    public class ItemViewHolder extends RecyclerView.ViewHolder {
        private EditText taskItemText;
        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);
            taskItemText = itemView.findViewById(R.id.task_item);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    final ToDoItem currentTaskItem = toDoItems.get(position);
                }
            });

        }
    }
    @NonNull
    @Override
    public ToDoItemAdapter.ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.task_item, parent, false);
        return new ItemViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ToDoItemAdapter.ItemViewHolder holder, int position) {
        ToDoItem currentTaskItem = toDoItems.get(position);
        String taskName = currentTaskItem.getTitle();
        holder.taskItemText.setText(taskName);
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
        this.toDoItems = toDoItems;
        notifyDataSetChanged();
    }



//    public static class ViewHolder extends RecyclerView.ViewHolder {
//        CheckBox checkBox;
//    }



}
