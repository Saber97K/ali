package com.example.myapplication.ui.order;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.myapplication.R;
import com.example.myapplication.Session;
import com.example.myapplication.added_wallet.MainActivity4_wallet;
import com.example.myapplication.added_wallet.MainActivity8_wallet;
import com.example.myapplication.ui.Utils.ManageWallet;
import com.example.myapplication.ui.Utils.OrdersManage;
import com.example.myapplication.ui.Utils.UsersManage;
import com.example.myapplication.ui.Utils.database.SQLiteManager;

public class OrderDetailsForWorker extends AppCompatActivity {
    private TextView typeTExtView, DateTextView, priceTextView , descTextView;
    private int currentUser , OrderId;
    private OrdersManage ordersManage;
    private SQLiteManager sqLiteManager;
    private UsersManage usersManage;
    private ManageWallet manageWallet;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_details_for_worker);
        currentUser = ((Session) this.getApplication()).getSomeVariable();
        Bundle bundle = getIntent().getExtras();
        OrderId = bundle.getInt("order");
        initWidgets();

        OrdersManage.orderArrayList.clear();
        UsersManage.UsersList.clear();
        ManageWallet.WalletList.clear();
        loadFromDBToMemory();



        for (int i = 0 ; i < OrdersManage.orderArrayList.size(); i++){
            if(OrdersManage.orderArrayList.get(i).getId() == OrderId){
                ordersManage = OrdersManage.orderArrayList.get(i);
            }
        }
        for (int i = 0 ; i < ManageWallet.WalletList.size(); i++){
            if(ManageWallet.WalletList.get(i).getUser_id() == currentUser){
                manageWallet = ManageWallet.WalletList.get(i);
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

    private void loadFromDBToMemory() {
        sqLiteManager = SQLiteManager.instanceOfDatabase(this);
        sqLiteManager.populateOrderListArray();
        sqLiteManager.populateWalletArray();
        sqLiteManager.populateUserListArray();
    }
    private void initWidgets() {

        typeTExtView = findViewById(R.id.textView15);
        priceTextView = findViewById(R.id.textView17);
        DateTextView = findViewById(R.id.textView16);
        descTextView = findViewById(R.id.textView14);


    }

    public void Accept(View view) {
        if (manageWallet != null) {
            for (int i = 0; i < OrdersManage.orderArrayList.size(); i++) {
                if (OrdersManage.orderArrayList.get(i).getId() == OrderId) {
                    OrdersManage.orderArrayList.get(i).setAccepted_user_id(currentUser);
                }
            }
            ordersManage.setAccepted_user_id(currentUser);
            usersManage.setActiveOrder(OrderId);
            sqLiteManager.UpdateOrder(ordersManage);
            sqLiteManager.UpdateUser(usersManage);


            Intent intent = new Intent(getApplicationContext(), MainActivity4_wallet.class);
            finish();
            startActivity(intent);
        } else {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Message");
            builder.setMessage("No wallet pls add it");
            // add buttons and their events
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    // do something when the "OK" button is clicked
                    Intent intent = new Intent(getApplicationContext(), MainActivity8_wallet.class);
                    finish();
                    startActivity(intent);
                }
            });
            builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    // do something when the "Cancel" button is clicked
                }
            });
            // create and show the Alert Dialog
            AlertDialog dialog = builder.create();
            dialog.show();
        }
    }
}