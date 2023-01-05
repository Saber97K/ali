package com.example.myapplication.ui.Utils;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.myapplication.R;

import java.text.DecimalFormat;
import java.util.List;

 public class AdapterUser extends ArrayAdapter<UsersManage>
    {
        private static final DecimalFormat df = new DecimalFormat("0.0");
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
            ImageView image = convertView.findViewById(R.id.ImageOfUser);

            id.setText(String.valueOf(User.getId()));
            email.setText(String.valueOf(User.getEmail()));
            image.setImageBitmap(User.getImage());
            rating.setText(countRating(User.getId()) + "");

            return convertView;
        }
        public String countRating(int id) {
            int ratingValue = 0;
            int counts = 0;
            for (int i =0 ; i < RatingManage.ratingsArrayList.size(); i++){
                if(RatingManage.ratingsArrayList.get(i).getTo_user() == id){
                    ratingValue = ratingValue + RatingManage.ratingsArrayList.get(i).getValue();
                    counts++;
                }
            }
            if(counts != 0) {
                ratingValue = ratingValue / counts;
                return df.format(ratingValue);
            }else
            {
                return df.format(ratingValue);
            }

        }
    }

