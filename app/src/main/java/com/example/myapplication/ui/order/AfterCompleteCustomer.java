package com.example.myapplication.ui.order;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatRadioButton;

import com.example.myapplication.MainActivity;
import com.example.myapplication.MainActivity2;
import com.example.myapplication.R;
import com.example.myapplication.Session;
import com.example.myapplication.ui.Utils.AdapterUser;
import com.example.myapplication.ui.Utils.CategoryManage;
import com.example.myapplication.ui.Utils.OrdersManage;
import com.example.myapplication.ui.Utils.UsersManage;
import com.example.myapplication.ui.Utils.database.SQLiteManager;

import java.util.Calendar;

public class AfterCompleteCustomer extends AppCompatActivity {

    private ListView noteListView;

    private int currentUser, acceptedUser;
    private UsersManage acceptedUsersManage;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_after_complete_customer);
        Bundle bundle = getIntent().getExtras();
        acceptedUser = bundle.getInt("id");
        noteListView = findViewById(R.id.UserListViewCell);
        currentUser = ((Session) this.getApplication()).getSomeVariable();
        SQLiteManager sqLiteManager = SQLiteManager.instanceOfDatabase(this);
        sqLiteManager.populateUserListArray();
        for (int i = 0 ; i < UsersManage.UsersList.size(); i++){
            if(UsersManage.UsersList.get(i).getId() == acceptedUser){
                acceptedUsersManage = UsersManage.UsersList.get(i);
            }
        }
        UsersManage.UsersList.clear();
        UsersManage.UsersList.add(acceptedUsersManage);
        setAcceptedUserAdapter();
    }

    private void setAcceptedUserAdapter( ) {
        AdapterUser noteAdapter = new AdapterUser(getApplicationContext(), UsersManage.UsersList);
        noteListView.setAdapter(noteAdapter);
    }


    public void ReviewButton(View view) {
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        finish();
        startActivity(intent);
    }

    public void CompleteCustomerAfter(View view) {
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        finish();
        startActivity(intent);
    }
}