package com.example.myapplication.ui.Utils;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.myapplication.R;

import java.util.List;


public class CategoryAdapter extends ArrayAdapter<CategoryManage>
{
    public CategoryAdapter(Context context, List<CategoryManage> notes)
    {
        super(context, 0, notes);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent)
    {
        CategoryManage order = getItem(position);
        if(convertView == null)
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.category_cell, parent, false);


//        TextView id = convertView.findViewById(R.id.categoryIdTextView);
        TextView title = convertView.findViewById(R.id.categoryNameTextView);

        title.setText(order.getText());
//        id.setText(String.valueOf(order.getId()));



        return convertView;
    }
}
