package com.example.myapplication.ui.order;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.R;
import com.example.myapplication.Session;
import com.example.myapplication.added_wallet.MainActivity3_wallet;
import com.example.myapplication.added_wallet.MainActivity_wallet;
import com.example.myapplication.ui.Utils.AdapterUser;
import com.example.myapplication.ui.Utils.ReviewPage;
import com.example.myapplication.ui.Utils.UsersManage;
import com.example.myapplication.ui.Utils.database.SQLiteManager;

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
        Intent intent = new Intent(getApplicationContext(), ReviewPage.class);
        intent.putExtra("user" , acceptedUser);
        intent.putExtra("text" , "cust");
        finish();
        startActivity(intent);
    }

    public void CompleteCustomerAfter(View view) {
        Intent intent = new Intent(getApplicationContext(), MainActivity_wallet.class);
        finish();
        startActivity(intent);
    }
}