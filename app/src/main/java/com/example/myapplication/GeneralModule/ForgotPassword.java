package com.example.myapplication.GeneralModule;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.audiofx.PresetReverb;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.myapplication.R;
import com.example.myapplication.ui.Utils.ManageVisited;
import com.example.myapplication.ui.Utils.ManageWallet;
import com.example.myapplication.ui.Utils.OrdersManage;
import com.example.myapplication.ui.Utils.UsersManage;
import com.example.myapplication.ui.Utils.database.SQLiteManager;

public class ForgotPassword extends AppCompatActivity {
    private EditText emailEditText, otpEditText;
    private OrdersManage ordersManage;
    private ManageWallet manageWallet;
    private ManageVisited manageVisited;
    private SQLiteManager sqLiteManager;
    private boolean wrongOTP = true; //if "true" means OTP is wrong, else OTP is correct

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        emailEditText = findViewById(R.id.editTextTextPersonName2);
        otpEditText = findViewById(R.id.otp);
    }

    private void loadFromDBToMemory() {
        sqLiteManager = SQLiteManager.instanceOfDatabase(this);
        sqLiteManager.populateUserListArray();
        sqLiteManager.populateOrderListArray();
        sqLiteManager.populateWalletArray();
        sqLiteManager.populateVisitsListArray();
    }

    public void RestoreAccount(View view) {
        String email = String.valueOf(emailEditText.getText());
        String otp = String.valueOf(otpEditText.getText());
        Intent Main;
        wrongOTP = true;
        if (email.isEmpty()){//check name is not empty
            Toast.makeText(this, "Email can't be empty", Toast.LENGTH_SHORT).show();
        }
        else if(!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            //check email is valid
            Toast.makeText(this, "Email invalid", Toast.LENGTH_SHORT).show();
        }
        else if(otp.isEmpty()){
            //check otp is not empty
            Toast.makeText(this, "OTP can't be empty", Toast.LENGTH_SHORT).show();
        }
        else{
            loadFromDBToMemory();
            for (UsersManage note : UsersManage.UsersList) {
                if ((note.getEmail().equals(email) && (note.getOtp().equals(otp)))) {//correct otp
                //reset password and update the database
                    wrongOTP = false;
                    //reset password logic
                    Main = new Intent(this, resetPassword.class);
                    Main.putExtra("email", email);
                    Main.putExtra("OTP", otp);
                    startActivity(Main);
                    break;
                
                }
                    
                }
            
            if(wrongOTP){
                Toast.makeText(this, "Wrong OTP or Email", Toast.LENGTH_SHORT).show();
            }

        }



    }
    public void backFromForgot(View view) {
        Intent back = new Intent(this, LoginPage.class);
        finish();
        startActivity(back);
    }
}