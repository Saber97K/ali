package com.example.myapplication.ui.wallet;

import android.app.AlertDialog;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.R;
import com.example.myapplication.Session;
import com.example.myapplication.added_wallet.MainActivity6_wallet;
import com.example.myapplication.added_wallet.MainActivity8_wallet;
import com.example.myapplication.added_wallet.MainActivity9_wallet;
import com.example.myapplication.ui.Utils.ManageTopUps;
import com.example.myapplication.ui.Utils.ManageWallet;
import com.example.myapplication.ui.Utils.Success;
import com.example.myapplication.ui.Utils.database.SQLiteManager;

import java.util.Calendar;

public class WithdrawProcess extends AppCompatActivity {

    private int currentUser;
    private EditText WithdrawOn;
    private int wallet_id;
    private SQLiteManager sqLiteManager;
    private ManageWallet manageWallet;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_withdraw_process);
        Bundle bundle = getIntent().getExtras();
        wallet_id = bundle.getInt("withdraw");


        WithdrawOn = findViewById(R.id.enterWithdraw);
        currentUser = ((Session) getApplication()).getSomeVariable();

        ManageTopUps.TopUpsList.clear();
        ManageWallet.WalletList.clear();


        sqLiteManager = SQLiteManager.instanceOfDatabase(this);
        sqLiteManager.populateWalletArray();
        sqLiteManager.populateTopUps();
        for (ManageWallet manageWallet1 : ManageWallet.WalletList){
            if(manageWallet1.getId() == wallet_id){
                manageWallet = manageWallet1;
            }
        }

    }

    public void button11btn(View view) {
        WithdrawOn.setText("10");

    }
    public void button21btn(View view) {
        WithdrawOn.setText("30");
    }
    public void button31btn(View view) {
        WithdrawOn.setText("50");
    }
    public void button41btn(View view) {
        WithdrawOn.setText("100");
    }
    public void button51btn(View view) {
        WithdrawOn.setText("200");
    }
    public void button61btn(View view) {
        WithdrawOn.setText("500");
    }




    public void confirmWithdrawOn(View view) {
        float value = Float.parseFloat(String.valueOf(WithdrawOn.getText()));
        if(value < manageWallet.getBalance()) {
            manageWallet.setBalance(manageWallet.getBalance() - value);
            sqLiteManager.UpdateWallet(manageWallet);
            finish();
            Intent intent = new Intent(this, Success.class);
            startActivity(intent);
        }else{
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Message");
            builder.setMessage("Not have enough balance to withdraw");
            // add buttons and their events
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    // do something when the "OK" button is clicked
                    Intent intent = new Intent(getApplicationContext(), MainActivity9_wallet.class);
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
        // show notification after withdraw
        String message = "You have withdrawn " + value + " from your wallet. Your current balance is " + manageWallet.getBalance();
        // Create a notification builder

        String CHANNEL_ID="MYCHANNEL";
        NotificationChannel notificationChannel= null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            notificationChannel = new NotificationChannel(CHANNEL_ID,"name", NotificationManager.IMPORTANCE_LOW);
        }
        Notification notification= null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            notification = new Notification.Builder(getApplicationContext(),CHANNEL_ID)
                    .setContentTitle("Withdraw")
                    .setContentText(message)
                    .setChannelId(CHANNEL_ID)
                    .setSmallIcon(android.R.drawable.sym_action_chat)
                    .build();
        }

        NotificationManager notificationManager=(NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            notificationManager.createNotificationChannel(notificationChannel);
        }
        notificationManager.notify(1,notification);


    }
}