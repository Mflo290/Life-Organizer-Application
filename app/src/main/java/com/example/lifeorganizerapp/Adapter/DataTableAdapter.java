package com.example.lifeorganizerapp.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.lifeorganizerapp.R;
import com.example.lifeorganizerapp.entities.ToDoItem;

import java.util.List;

public class DataTableAdapter extends RecyclerView.Adapter<DataTableAdapter.ViewHolder> {
    Context context;
    View view;
    List<ToDoItem> itemList;



    public DataTableAdapter(Context context, List<ToDoItem> itemList) {
        this.context = context;
        this.itemList = itemList;
    }


    @NonNull
    @Override
    public DataTableAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        view = LayoutInflater.from(context).inflate(R.layout.table_layout, parent, false);
        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull DataTableAdapter.ViewHolder holder, int position) {
        if (itemList != null && itemList.size() > 0) {
            ToDoItem itemTableList = itemList.get(position);
            holder.taskData.setText(itemTableList.getTitle());
            holder.listData.setText(itemTableList.getListName());
            holder.completedData.setText(String.valueOf(itemTableList.getDateCompleted()));
        }
    }


    @Override
    public int getItemCount() {
        if (itemList != null) {
            return itemList.size();
        } else {
            return 0;
        }
    }




    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView taskData, listData, completedData;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            taskData = itemView.findViewById(R.id.task_data);
            listData = itemView.findViewById(R.id.list_data);
            completedData = itemView.findViewById(R.id.completed_data);

        }

    } //ViewHolder Closing Brace





}
