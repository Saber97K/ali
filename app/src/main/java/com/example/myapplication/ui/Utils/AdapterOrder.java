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

    public class AdapterOrder extends ArrayAdapter<OrdersManage>
    {
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

            TextView title = convertView.findViewById(R.id.cellOrderTitle);
            TextView date = convertView.findViewById(R.id.cellOrderDate);
            TextView id = convertView.findViewById(R.id.orderId);
            TextView userId = convertView.findViewById(R.id.userId3);
            TextView acceptedUser = convertView.findViewById(R.id.orderAccepted2);
            TextView price = convertView.findViewById(R.id.cellOrderPrice);
            TextView type = convertView.findViewById(R.id.cellOrderType);
            TextView desc = convertView.findViewById(R.id.cellOrderDesc);

            title.setText(order.getTitle());
            date.setText(order.getDate());
            id.setText(String.valueOf(order.getId()));
            price.setText(String.valueOf(order.getPrice()));
            type.setText(order.getType());
            desc.setText(order.getDescription());
            userId.setText(String.valueOf(order.getUser_id()));
            acceptedUser.setText(String.valueOf(order.getAccepted_user_id()));

            return convertView;
        }
    }
