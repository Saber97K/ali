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

 public class AdapterUser extends ArrayAdapter<UsersManage>
    {
        public AdapterUser(Context context, List<UsersManage> notes)
        {
            super(context, 0, notes);
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent)
        {
            UsersManage User = getItem(position);
            if(convertView == null)
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.user_cell, parent, false);

            TextView id = convertView.findViewById(R.id.UserIdCell);
            TextView email = convertView.findViewById(R.id.UserEmailCell);
            TextView rating = convertView.findViewById(R.id.UserRatingCell);

            id.setText(String.valueOf(User.getId()));
            email.setText(String.valueOf(User.getEmail()));

            return convertView;
        }
    }

