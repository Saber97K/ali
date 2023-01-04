package com.example.myapplication.ui.wallet;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.myapplication.MainActivity;
import com.example.myapplication.R;
import com.example.myapplication.Session;
import com.example.myapplication.ui.Utils.ManageTopUps;
import com.example.myapplication.ui.Utils.ManageWallet;
import com.example.myapplication.ui.Utils.database.SQLiteManager;

public class WalletAddingActivity extends AppCompatActivity {

    private int currentUser;
    private EditText pass , bank;
    private SQLiteManager sqLiteManager;
    private ManageWallet manageWallet;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wallet_adding);
        currentUser = ((Session) getApplication()).getSomeVariable();
        sqLiteManager = SQLiteManager.instanceOfDatabase(this);
        sqLiteManager.populateWalletArray();
        bank = findViewById(R.id.editTextBankAccount);
        pass = findViewById(R.id.editTextPassword);



    }


    public void ConfirmBank(View view) {

        String bank2 = String.valueOf(bank.getText());
        String pass2 = String.valueOf(pass.getText());
        int id = ManageWallet.WalletList.size();
        manageWallet = new ManageWallet(id, bank2 , currentUser, 100 , pass2);
        sqLiteManager.addWallet(manageWallet);
        finish();
    }
}