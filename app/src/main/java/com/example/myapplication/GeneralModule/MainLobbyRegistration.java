package com.example.myapplication.GeneralModule;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.myapplication.R;

public class MainLobbyRegistration extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_lobby_reg);

    }

    public void LobbyPageCustomer(View view) {
        Intent Customer = new Intent(this, RegisterPage.class);
        String strName = "Customer";
        Customer.putExtra( "text" , strName);
        finish();
        startActivity(Customer);
    }

    public void LobbyPageWorker(View view) {
        Intent Worker = new Intent(this, RegisterPage.class);
        String strName = "Worker";
        Worker.putExtra( "text" , strName);
        finish();
        startActivity(Worker);
    }

    public void ImageButton1(View view) {
        Intent intent = new Intent(this, MainLobby.class);
        finish();
        startActivity(intent);
    }
}