package com.example.myapplication.ui.Utils;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.R;
import com.example.myapplication.Session;
import com.example.myapplication.added_wallet.MainActivity3_wallet;
import com.example.myapplication.added_wallet.MainActivity_wallet;


public class Success extends AppCompatActivity {
    private String caseCustomer;
    private int currentUser;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_success);
        if( getIntent().hasExtra("text")) {
            Bundle bundle = getIntent().getExtras();
            caseCustomer = bundle.getString("text");

        }
        currentUser = ((Session) this.getApplication()).getSomeVariable();

}

    public void SuccessfullyTopUp(View view) {
        if (caseCustomer != null) {
            if (caseCustomer.equals("cust")) {
                Intent intent = new Intent(getApplicationContext(), MainActivity_wallet.class);
                finish();
                startActivity(intent);
            } else if (caseCustomer.equals("worker")) {
                Intent intent = new Intent(getApplicationContext(), MainActivity3_wallet.class);
                finish();
                startActivity(intent);
            }
        }
        else {
            finish();
        }
    }
}