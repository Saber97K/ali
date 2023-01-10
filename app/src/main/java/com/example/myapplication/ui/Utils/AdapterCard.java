package com.example.myapplication.ui.Utils;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.MainActivity3;
import com.example.myapplication.R;
import com.example.myapplication.Session;
import com.example.myapplication.ui.order.OnItemClick;

import java.util.ArrayList;

public class AdapterCard extends RecyclerView.Adapter<AdapterCard.ViewHolder> {
    private ArrayList<CategoryManage> dataModalArrayList;
    private Context context;
    private OnItemClick mCallback;


    // constructor class for our Adapter
    public AdapterCard(ArrayList<CategoryManage> dataModalArrayList, Context context , OnItemClick listener) {
        this.dataModalArrayList = dataModalArrayList;
        this.context = context;
        this.mCallback = listener;
    }

    @NonNull
    @Override
    public AdapterCard.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // passing our layout file for displaying our card item
        return new AdapterCard.ViewHolder(LayoutInflater.from(context).inflate(R.layout.worker_cat_cell, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterCard.ViewHolder holder, int position) {
        // setting data to our views in Recycler view items.
        final CategoryManage modal = dataModalArrayList.get(position);
        holder.courseNameTV.setText(modal.getText());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    mCallback.onClick(String.valueOf(modal.getId()));
            }
        });
    }

    @Override
    public int getItemCount() {
        // returning the size of array list.
        return dataModalArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        // creating variables for our
        // views of recycler items.
        private TextView courseNameTV;
        private ImageView courseIV;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            // initializing the views of recycler views.
            courseNameTV = itemView.findViewById(R.id.idTVtext);
        }
    }
}
