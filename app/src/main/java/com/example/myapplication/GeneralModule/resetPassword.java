package com.example.myapplication.GeneralModule;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.myapplication.R;

public class resetPassword extends AppCompatActivity {
    private String email, otp, password, retypepassword;
    private EditText PasswordEditText, RetypePasswordEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);
        Bundle bundle = getIntent().getExtras();

        email = bundle.getString("email");
        otp = bundle.getString("otp");

        PasswordEditText = findViewById(R.id.editTextTextPassword2);
        RetypePasswordEditText = findViewById(R.id.editTextTextPassword3);

    }

    public void resetButtonClicked(View view) {
        password = String.valueOf(PasswordEditText.getText());
        retypepassword = String.valueOf(RetypePasswordEditText.getText());

        //check if they are the same

        if(password.equals(retypepassword)) {//same
            Toast.makeText(this, "", Toast.LENGTH_SHORT).show();
        }
        else{//not same
            Toast.makeText(this, "Password Doesn't Match", Toast.LENGTH_SHORT).show();
        }


    }
}