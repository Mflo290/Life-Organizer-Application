package com.example.lifeorganizerapp.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.lifeorganizerapp.database.Repository;
import com.example.lifeorganizerapp.entities.ToDoItem;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class TableAdapter extends RecyclerView.Adapter<TableAdapter.TableViewHolder>  {

    private List<ToDoItem> toDoItemList;
    private List<ToDoItem> filteredList; // For search functionality

    private Context context;
    private LayoutInflater mInflater;
    Repository repository;



    public TableAdapter(Context context, Repository repository) {
        this.mInflater = LayoutInflater.from(context);
        this.context = context;
        this.repository = repository;
        this.toDoItemList = toDoItemList;
        this.filteredList = new ArrayList<>(toDoItemList);
    }

    public class TableViewHolder extends RecyclerView.ViewHolder {


        public TableViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }

    @NonNull
    @Override
    public TableAdapter.TableViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull TableAdapter.TableViewHolder holder, int position) {
        ToDoItem currentItem = filteredList.get(position);

    }

    @Override
    public int getItemCount() {
        return filteredList.size();
    }

    public void filter(String keyword) {
        filteredList.clear();
        if (keyword.isEmpty()) {
            filteredList.addAll(toDoItemList);
        } else {
            keyword = keyword.toLowerCase(Locale.getDefault());
            for (ToDoItem item : toDoItemList) {
                // Modify this condition based on your search criteria
                if (item.getTitle().toLowerCase(Locale.getDefault()).contains(keyword)
                        || item.getListName().toLowerCase(Locale.getDefault()).contains(keyword)) {
                    filteredList.add(item);
                }
            }
        }
        notifyDataSetChanged();
    }

}
