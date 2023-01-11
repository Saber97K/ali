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

    public class AdapterOrder extends ArrayAdapter<OrdersManage>
    {
        private static UsersManage usersManage;
        private int ratingValue;
        private static final DecimalFormat df = new DecimalFormat("0.0");
        public AdapterOrder(Context context, List<OrdersManage> notes)
        {
            super(context, 0, notes);
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent)
        {

            OrdersManage order = getItem(position);
            if(convertView == null)
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.order_cell, parent, false);

            for (int i = 0 ; i < UsersManage.UsersList.size();i++){
                if(UsersManage.UsersList.get(i).getId() == order.getUser_id()){
                    usersManage = UsersManage.UsersList.get(i);
                }
            }

            TextView title = convertView.findViewById(R.id.cellOrderTitle);
            TextView date = convertView.findViewById(R.id.cellOrderDate);

            TextView username = convertView.findViewById(R.id.cellOrderUserId);
            TextView price = convertView.findViewById(R.id.cellOrderPrice);
            TextView type = convertView.findViewById(R.id.cellOrderType);
            TextView desc = convertView.findViewById(R.id.cellOrderDesc);
            TextView rating = convertView.findViewById(R.id.cellOrderRating);
            ImageView image = convertView.findViewById(R.id.cellImage1);

            title.setText(order.getTitle());
            date.setText(order.getDate());
            username.setText(usersManage.getName());
            price.setText(String.valueOf(order.getPrice()));
            type.setText(order.getType());
            desc.setText(order.getDescription());
            rating.setText(countRating(order.getUser_id()));
            image.setImageBitmap(usersManage.getImage());

            return convertView;
        }
        public String countRating(int usedId) {
            ratingValue = 0;
            int counts = 0;
            for (int i =0 ; i < RatingManage.ratingsArrayList.size(); i++){
                if(RatingManage.ratingsArrayList.get(i).getTo_user() == usedId){
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
