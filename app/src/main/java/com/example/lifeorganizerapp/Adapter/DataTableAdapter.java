package com.example.lifeorganizerapp.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.BackgroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.lifeorganizerapp.R;
import com.example.lifeorganizerapp.entities.ToDoItem;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DataTableAdapter extends RecyclerView.Adapter<DataTableAdapter.ViewHolder> {
    Context context;
    View view;
    List<ToDoItem> itemList;
    private String searchText; // Add this line




    public DataTableAdapter(Context context, List<ToDoItem> itemList, String searchText) {
        this.context = context;
        this.itemList = itemList;
        this.searchText = searchText;
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
            holder.highlightText(itemTableList.getTitle(), searchText); // Use searchText for highlighting
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

        public void highlightText(String text, String searchText) {
            if (!searchText.isEmpty() && text.toLowerCase().contains(searchText.toLowerCase())) {
                SpannableStringBuilder spannable = new SpannableStringBuilder(text);
                Pattern pattern = Pattern.compile(searchText, Pattern.CASE_INSENSITIVE);
                Matcher matcher = pattern.matcher(text);

                while (matcher.find()) {
                    spannable.setSpan(new BackgroundColorSpan(Color.YELLOW), matcher.start(), matcher.end(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                }
                taskData.setText(spannable, TextView.BufferType.SPANNABLE); // Set the spannable text
            } else {
                taskData.setText(text);
            }
        }



    } //ViewHolder Closing Brace





}
