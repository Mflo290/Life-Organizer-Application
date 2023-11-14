package com.example.lifeorganizerapp.Adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.lifeorganizerapp.R;
import com.example.lifeorganizerapp.UI.AddNewItem;
import com.example.lifeorganizerapp.database.Repository;
import com.example.lifeorganizerapp.entities.ToDoList;

import java.util.List;

public class ToDoListAdapter extends RecyclerView.Adapter<ToDoListAdapter.ListViewHolder> {

    private List<ToDoList> mToDoList;
    private final Context context;
    private final LayoutInflater mInflater;
    Repository repository;



    public ToDoListAdapter(Context context, Repository repository) {
        this.mInflater = LayoutInflater.from(context);
        this.context = context;
        this.repository = repository;
    }



    // Method to get the context
    public Context getContext() {
        return context;
    }



    public class ListViewHolder extends RecyclerView.ViewHolder {
        private final TextView toDoListTextView;
        public ListViewHolder(@NonNull View itemView) {
            super(itemView);
            toDoListTextView = itemView.findViewById(R.id.list_item);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    final ToDoList currentToDoList = mToDoList.get(position);
                    Intent intent = new Intent(context, ToDoList.class);
                    intent.putExtra("toDoListID", currentToDoList.getListID());

                    context.startActivity(intent);
                }
            });
        }
        public void bind(ToDoList toDoList) {
            toDoListTextView.setText(toDoList.getListName());
        }
    }



    @NonNull
    @Override
    public ToDoListAdapter.ListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.list_item, parent, false);
        return new ListViewHolder(itemView);
    }



    //This is what is displayed on the list
    @Override
    public void onBindViewHolder(@NonNull ToDoListAdapter.ListViewHolder holder, int position) {
        ToDoList currentToDoList = mToDoList.get(position);
        holder.bind(currentToDoList);
    }



    public void deleteToDoList(int position) {
        ToDoList listItem = mToDoList.get(position);
        repository.getToDoListById(listItem.getListID());
        mToDoList.remove(position);
        notifyItemRemoved(position);
    }



    public void editToDoList(int position) {
        ToDoList listItem = mToDoList.get(position);
        Bundle bundle = new Bundle();
        bundle.putInt("todo_list_id", listItem.getListID());
        bundle.putString("list_name", listItem.getListName());
        AddNewItem fragment = new AddNewItem(repository);    //Pass the repository to AddNewItem
        fragment.setArguments(bundle);
        fragment.show(fragment.getChildFragmentManager(), AddNewItem.TAG);      //Use getChildFragmentManager() If you're inside a Fragment. Instead of getSupportFragmentManager()
    }



    @Override
    public int getItemCount() {
        if (mToDoList != null) {
            return mToDoList.size();
        } else {
            return 0;
        }
    }



    public void setToDoLists(List<ToDoList> toDoLists){
        mToDoList = toDoLists;
        notifyDataSetChanged();
    }



//    public static class ViewHolder extends RecyclerView.ViewHolder {
//        CheckBox checkBox;
//    }



}