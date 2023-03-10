package com.example.myapplication.ui.wallet;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;

import com.example.myapplication.R;
import com.example.myapplication.Session;
import com.example.myapplication.added_wallet.MainActivity6_wallet;
import com.example.myapplication.added_wallet.MainActivity9_wallet;
import com.example.myapplication.ui.Utils.ManageWallet;
import com.example.myapplication.ui.Utils.UsersManage;
import com.example.myapplication.ui.Utils.database.SQLiteManager;

public class WalletAddingActivity extends AppCompatActivity {

    private int currentUser;
    private EditText pass , bank;
    private SQLiteManager sqLiteManager;
    private ManageWallet manageWallet;
    private UsersManage usersManage;
    private EditText editText;
    private CheckBox checkBox;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wallet_adding);
        currentUser = ((Session) getApplication()).getSomeVariable();


        UsersManage.UsersList.clear();
        ManageWallet.WalletList.clear();
        sqLiteManager = SQLiteManager.instanceOfDatabase(this);
        sqLiteManager.populateUserListArray();
        sqLiteManager.populateWalletArray();


        bank = findViewById(R.id.editTextBankAccount);
        pass = findViewById(R.id.editTextPassword);

        for (int i =  0 ; i < UsersManage.UsersList.size(); i++){
            if(UsersManage.UsersList.get(i).getId() == currentUser){
                usersManage = UsersManage.UsersList.get(i);
            }
        }
        editText = findViewById(R.id.editTextPassword);
        checkBox = findViewById(R.id.checkBoxShowPassword);
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    editText.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                } else {
                    editText.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
            }
        });



    }


    public void ConfirmBank(View view) {

        //Change the bank and password to string for storing purpose
        String bank2 = String.valueOf(bank.getText());
        String pass2 = String.valueOf(pass.getText());
        int id = ManageWallet.WalletList.size();

        //Create a wallet bank account
        manageWallet = new ManageWallet(id, bank2 , currentUser, 0 , pass2);
        sqLiteManager.addWallet(manageWallet);
        Intent intent;
        if(usersManage.getRole().equals("Customer")) {
             intent = new Intent(this, MainActivity6_wallet.class);
        }else {
             intent = new Intent(this, MainActivity9_wallet.class);
        }
        finish();
        startActivity(intent);
    }

    public void backFromBankInfo(View view) {
        Intent intent= new Intent();
        intent.setClass(this,WalletFragment.class);
        startActivity(intent);
    }


}