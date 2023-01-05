package com.example.myapplication.ui.order;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatRadioButton;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import com.example.myapplication.MainActivity;
import com.example.myapplication.MainActivity2;
import com.example.myapplication.R;
import com.example.myapplication.Session;
import com.example.myapplication.added_wallet.MainActivity2_wallet;
import com.example.myapplication.added_wallet.MainActivity6_wallet;
import com.example.myapplication.added_wallet.MainActivity7_wallet;
import com.example.myapplication.added_wallet.MainActivity_wallet;
import com.example.myapplication.ui.Utils.ManageTopUps;
import com.example.myapplication.ui.Utils.ManageWallet;
import com.example.myapplication.ui.Utils.OrdersManage;
import com.example.myapplication.ui.Utils.UsersManage;
import com.example.myapplication.ui.Utils.database.SQLiteManager;

import java.util.Calendar;

public class OrderCreate extends AppCompatActivity {
    private AppCompatRadioButton rbLeft, rbRight;
    private EditText priceEditText, descEditText, titleEditText;
    private Button dateButton;
    private DatePickerDialog datePickerDialog;
    private String date, type,category;
    private int currentUser;
    private UsersManage usersManage;
    private ManageWallet manageWallet;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_order);
        Bundle bundle = getIntent().getExtras();
        category = bundle.getString("category");
        currentUser = ((Session) this.getApplication()).getSomeVariable();
        initWidgets();
        initDatePicker();
        dateButton.setText(getTodayDate());
        date = getTodayDate();
        OrdersManage.orderArrayList.clear();
        type = "face-to-face";


    }

    private String getTodayDate() {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        return makeDateString(day,month,year);
    }

    private void initDatePicker() {
        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month + 1;
                String date1 = makeDateString(day, month,year);
                dateButton.setText(date1);
                date = date1;
                
            }
        };
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        int style = AlertDialog.THEME_HOLO_LIGHT;

        datePickerDialog = new DatePickerDialog(this , style , dateSetListener , year ,month , day);
    }

    private String makeDateString(int day, int month, int year) {
        return day + "/" + month + "/" + year;
    }

    private void initWidgets() {
        rbLeft = findViewById(R.id.FaceToFace);
        rbRight = findViewById(R.id.online);
        dateButton = findViewById(R.id.dateButton);
        priceEditText = findViewById(R.id.PriceEditText);
        descEditText = findViewById(R.id.descriptionEditText);
        titleEditText = findViewById(R.id.titleEditText);


    }

    public void rdButton(View view) {
        boolean isSelected = ((AppCompatRadioButton)view).isChecked();
        switch (view.getId()){
            case R.id.FaceToFace:
                if(isSelected){
                    rbLeft.setTextColor(Color.WHITE);
                    rbRight.setTextColor(Color.BLACK);
                    type = "face-to-face";
                }
                break;
            case R.id.online:
                if(isSelected){
                    rbRight.setTextColor(Color.WHITE);
                    rbLeft.setTextColor(Color.BLACK);
                    type = "online";
                }
                break;
        }
    }

    public void DataChoose(View view) {
        datePickerDialog.show();
    }

    public void Publish(View view) {
        SQLiteManager sqLiteManager = SQLiteManager.instanceOfDatabase(this);
        ManageWallet.WalletList.clear();
        OrdersManage.orderArrayList.clear();
        sqLiteManager.populateOrderListArray();
        sqLiteManager.populateUserListArray();
        sqLiteManager.populateWalletArray();
        for (int i = 0 ; i < UsersManage.UsersList.size(); i++){
            if(UsersManage.UsersList.get(i).getId() == currentUser){
                usersManage = UsersManage.UsersList.get(i);
            }
        }

        for (int i = 0 ; i < ManageWallet.WalletList.size(); i++){
            if(ManageWallet.WalletList.get(i).getUser_id() == currentUser){
                manageWallet = ManageWallet.WalletList.get(i);
            }
        }

        String desc = String.valueOf(descEditText.getText());
        String title = String.valueOf(titleEditText.getText());
        double price = Double.parseDouble(String.valueOf(priceEditText.getText()));
        int id = OrdersManage.orderArrayList.size() + 1;
        if(manageWallet == null){
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Message");
            builder.setMessage("No wallet pls add it");
            // add buttons and their events
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    // do something when the "OK" button is clicked
                    Intent intent = new Intent(getApplicationContext(), MainActivity7_wallet.class);
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

        }else {
            if (price <= manageWallet.getBalance()) {


                usersManage.setActiveOrder(id);
                OrdersManage order = new OrdersManage(id, currentUser, -1, type, title, desc, price, date, 0, category);
                OrdersManage.orderArrayList.add(order);
                sqLiteManager.addOrderToDatabase(order);
                sqLiteManager.UpdateUser(usersManage);
                Intent intent = new Intent(getApplicationContext(), MainActivity2_wallet.class);
                finish();
                startActivity(intent);
            } else {
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("Message");
                builder.setMessage("Not have enough balance. Please topUp.");
                // add buttons and their events
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // do something when the "OK" button is clicked
                        Intent intent = new Intent(getApplicationContext(), MainActivity6_wallet.class);
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
    public void backOrder(View view) {
        finish();
    }
}