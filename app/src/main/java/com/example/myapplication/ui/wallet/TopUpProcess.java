package com.example.myapplication.ui.wallet;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.myapplication.R;
import com.example.myapplication.Session;
import com.example.myapplication.ui.Utils.ManageTopUps;
import com.example.myapplication.ui.Utils.ManageWallet;
import com.example.myapplication.ui.Utils.Success;
import com.example.myapplication.ui.Utils.database.SQLiteManager;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class TopUpProcess extends AppCompatActivity  implements View.OnClickListener {
    private Button mBtn1;
    private Button mBtn2;
    private Button mBtn3;
    private Button mBtn4;
    private Button mBtn5;
    private Button mBtn6;
    private Button confirmTop;

    private ImageButton backFromTop1;
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
        mBtn1 = findViewById(R.id.btn1);
        mBtn2 = findViewById(R.id.btn2);
        mBtn3 = findViewById(R.id.btn3);
        mBtn4 = findViewById(R.id.btn4);
        mBtn5 = findViewById(R.id.btn5);
        mBtn6 = findViewById(R.id.btn6);
        backFromTop1 = findViewById(R.id.backFromTop);
        enterTop = findViewById(R.id.enterTopUp);
        confirmTop = findViewById(R.id.confirmTopup);

        mBtn1.setOnClickListener(this);
        mBtn2.setOnClickListener(this);
        mBtn3.setOnClickListener(this);
        mBtn4.setOnClickListener(this);
        mBtn5.setOnClickListener(this);
        mBtn6.setOnClickListener(this);
        backFromTop1.setOnClickListener(this);
        confirmTop.setOnClickListener(this);

        Bundle bundle = getIntent().getExtras();
        wallet_id = bundle.getInt("topup");



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


    public void onClick(View view) {
        String inputNum;
        inputNum=((TextView) view).getText().toString();
        switch (view.getId()){
            case R.id.btn1:
                /*setEnable(mBtn1);*/
                refreshNum(inputNum);
                break;
            case R.id.btn2:
                /*setEnable(mBtn2);*/
                refreshNum(inputNum);
                break;
            case R.id.btn3:
                /*setEnable(mBtn3);*/
                refreshNum(inputNum);
                break;
            case R.id.btn4:
               /* setEnable(mBtn4);*/
                refreshNum(inputNum);
                break;
            case R.id.btn5:
               /* setEnable(mBtn5);*/
                refreshNum(inputNum);
                break;
            case R.id.btn6:
                /*setEnable(mBtn6);*/
                refreshNum(inputNum);
                break;
            case R.id.backFromTop:
                Intent intent= new Intent();
                intent.setClass(TopUpProcess.this,WalletAdded.class);
                startActivity(intent);
                break;

            case R.id.confirmTopup:
                int id = ManageTopUps.TopUpsList.size();
                float value = Float.parseFloat(String.valueOf(enterTop.getText()));
                ManageTopUps manageTopUps = new ManageTopUps(id , manageWallet.getId(), value,date );
                sqLiteManager.addTopUPS(manageTopUps);
                manageWallet.setBalance(manageWallet.getBalance() + value);
                sqLiteManager.UpdateWallet(manageWallet);
                ManageTopUps.TopUpsList.add(manageTopUps);
                finish();
                startActivity(new Intent(this, Success.class));
                break;
        }

    }
    private void refreshNum(String num){
        enterTop.setText(num);
    }
    private void setEnable(Button btn) {
        List<Button> buttonList=new ArrayList<>();
        if (buttonList.size()==0){
            buttonList.add(mBtn1);
            buttonList.add(mBtn2);
            buttonList.add(mBtn3);
            buttonList.add(mBtn4);
            buttonList.add(mBtn5);
            buttonList.add(mBtn6);
        }
        for (int i = 0; i <buttonList.size() ; i++) {
            buttonList.get(i).setEnabled(true);
        }
        btn.setEnabled(false);
    }
}