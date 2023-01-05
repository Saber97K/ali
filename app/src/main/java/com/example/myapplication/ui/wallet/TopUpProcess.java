package com.example.myapplication.ui.wallet;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.example.myapplication.R;
import com.example.myapplication.Session;
import com.example.myapplication.ui.Utils.ManageTopUps;
import com.example.myapplication.ui.Utils.ManageWallet;
import com.example.myapplication.ui.Utils.Success;
import com.example.myapplication.ui.Utils.database.SQLiteManager;

import java.util.Calendar;

public class TopUpProcess extends AppCompatActivity {

    private int currentUser;
    private EditText enterTop;
    private int wallet_id;
    private SQLiteManager sqLiteManager;
    private ManageWallet manageWallet;
    private String date;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_top_up_process);
        Bundle bundle = getIntent().getExtras();
        wallet_id = bundle.getInt("topup");


        enterTop = findViewById(R.id.enterTopUp);
        currentUser = ((Session) getApplication()).getSomeVariable();
        date = getTodayDate();


        ManageWallet.WalletList.clear();


        sqLiteManager = SQLiteManager.instanceOfDatabase(this);
        sqLiteManager.populateWalletArray();

        for (ManageWallet manageWallet1 : ManageWallet.WalletList){
            if(manageWallet1.getId() == wallet_id){
                manageWallet = manageWallet1;
            }
        }

    }
    public void button1btn(View view) {
        enterTop.setText("10");

    }
    public void button2btn(View view) {
        enterTop.setText("30");
    }
    public void button3btn(View view) {
        enterTop.setText("50");
    }
    public void button4btn(View view) {
        enterTop.setText("100");
    }
    public void button5btn(View view) {
        enterTop.setText("200");
    }

    public void button6btn(View view) {
        enterTop.setText("500");
    }


    private String getTodayDate() {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int time = calendar.get(Calendar.HOUR);
        int minute = calendar.get(Calendar.MINUTE);
        int second = calendar.get(Calendar.SECOND);
        return makeDateString(day,month,year , time , minute , second);
    }
    private String makeDateString(int day, int month, int year, int time , int minute , int second) {
        return day + "/" + month + "/" + year  + " " + time + ":" + minute + ":" + second;
    }
    public void confirmTopupOn(View view) {

        int id = ManageTopUps.TopUpsList.size();
        float value = Float.parseFloat(String.valueOf(enterTop.getText()));
        ManageTopUps manageTopUps = new ManageTopUps(id , manageWallet.getId(), value,date );
        sqLiteManager.addTopUPS(manageTopUps);
        manageWallet.setBalance(manageWallet.getBalance() + value);
        sqLiteManager.UpdateWallet(manageWallet);
        ManageTopUps.TopUpsList.add(manageTopUps);
        finish();
        Intent intent = new Intent(this, Success.class);
        startActivity(intent);
    }
}