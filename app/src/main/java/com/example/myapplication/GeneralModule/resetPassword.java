package com.example.myapplication.GeneralModule;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.myapplication.MainActivity;
import com.example.myapplication.R;
import com.example.myapplication.ui.Utils.ManageVisited;
import com.example.myapplication.ui.Utils.ManageWallet;
import com.example.myapplication.ui.Utils.OrdersManage;
import com.example.myapplication.ui.Utils.UsersManage;
import com.example.myapplication.ui.Utils.database.SQLiteManager;

public class resetPassword extends AppCompatActivity {
    private String email, otp, password, retypepassword;
    private EditText PasswordEditText, RetypePasswordEditText;
    private OrdersManage ordersManage;
    private ManageWallet manageWallet;
    private ManageVisited manageVisited;
    private SQLiteManager sqLiteManager;
    private UsersManage usersManage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);
        Bundle bundle = getIntent().getExtras();

        email = bundle.getString("email");
        otp = bundle.getString("OTP");
        loadFromDBToMemory();

        PasswordEditText = findViewById(R.id.editTextTextPassword2);
        RetypePasswordEditText = findViewById(R.id.editTextTextPassword3);

    }
    private void loadFromDBToMemory() {
        sqLiteManager = SQLiteManager.instanceOfDatabase(this);
        UsersManage.UsersList.clear();
        sqLiteManager.populateUserListArray();
        sqLiteManager.populateOrderListArray();
        sqLiteManager.populateWalletArray();
        sqLiteManager.populateVisitsListArray();
    }

    public void resetButtonClicked(View view) {
        password = String.valueOf(PasswordEditText.getText());
        retypepassword = String.valueOf(RetypePasswordEditText.getText());

        //check if they are the same
        if(password.length() < 8){
            //check password is more than 8 char
            Toast.makeText(this, "Password must have min 8 characters", Toast.LENGTH_SHORT).show();
        }
        else if(password.equals(retypepassword)) {//same
            Toast.makeText(this, "Password changed successfully", Toast.LENGTH_SHORT).show();
            //update new password in database
            for (UsersManage note : UsersManage.UsersList) {
                if ((note.getEmail().equals(email) && (note.getOtp().equals(otp)))) {//correct otp

                    note.setPassword(password); //password in this case is the new password
                    usersManage = note;// you can see the alt + tab, it's in the 1st monitor, change monitor to alt tab
                    break;
                }
            }
            sqLiteManager.UpdateUser(usersManage);
            Intent Main = new Intent(this, LoginPage.class);
            finish();
            startActivity(Main);
        }
        else{//not same
            Toast.makeText(this, "Password doesn't match", Toast.LENGTH_SHORT).show();
        }


    }

    public void backFromReset(View view) {
        Intent back = new Intent(this, ForgotPassword.class);
        finish();
        startActivity(back);
    }
}