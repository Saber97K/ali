package com.example.myapplication.ui.order;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.MainActivity3;
import com.example.myapplication.MainActivity4;
import com.example.myapplication.R;
import com.example.myapplication.Session;
import com.example.myapplication.added_wallet.MainActivity3_wallet;
import com.example.myapplication.added_wallet.MainActivity4_wallet;
import com.example.myapplication.ui.Utils.OrdersManage;
import com.example.myapplication.ui.Utils.UsersManage;
import com.example.myapplication.ui.Utils.database.SQLiteManager;

public class OrderAccepted extends AppCompatActivity {
    private TextView typeTExtView, DateTextView, priceTextView , descTextView;
    private int currentUser , OrderId;
    private OrdersManage ordersManage;
    private SQLiteManager sqLiteManager;
    private UsersManage usersManage;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_accepted);
        currentUser = ((Session) this.getApplication()).getSomeVariable();
        Bundle bundle = getIntent().getExtras();
        OrderId = bundle.getInt("order");
        initWidgets();

        OrdersManage.orderArrayList.clear();
        sqLiteManager = SQLiteManager.instanceOfDatabase(this);
        sqLiteManager.populateOrderListArray();

        for (int i = 0 ; i < OrdersManage.orderArrayList.size(); i++){
            if(OrdersManage.orderArrayList.get(i).getId() == OrderId){
                ordersManage = OrdersManage.orderArrayList.get(i);
            }
        }
        for (int i = 0 ; i < UsersManage.UsersList.size(); i++){
            if(UsersManage.UsersList.get(i).getId() == currentUser){
                usersManage = UsersManage.UsersList.get(i);
            }
        }
        typeTExtView.setText(ordersManage.getType());
        DateTextView.setText(ordersManage.getDate());
        priceTextView.setText(ordersManage.getPrice() + "");
        descTextView.setText(ordersManage.getDescription());




    }

    private void initWidgets() {

        typeTExtView = findViewById(R.id.textView35);
        priceTextView = findViewById(R.id.textView36);
        DateTextView = findViewById(R.id.textView37);
        descTextView = findViewById(R.id.textView38);


    }

    public void cancelledOrder(View view) {
        for (int i = 0 ; i < OrdersManage.orderArrayList.size(); i++){
            if(OrdersManage.orderArrayList.get(i).getId() == OrderId){
                OrdersManage.orderArrayList.get(i).setAccepted_user_id(-1);
            }
        }

        ordersManage.setAccepted_user_id(-1);
        usersManage.setActiveOrder(-1);
        sqLiteManager.UpdateOrder(ordersManage);

        Intent intent = new Intent(getApplicationContext(), MainActivity3_wallet.class);
        finish();
        startActivity(intent);
    }

    public void backButtonFromActive(View view) {

        Intent intent = new Intent(getApplicationContext(), MainActivity4_wallet.class);
        finish();
        startActivity(intent);
    }
}