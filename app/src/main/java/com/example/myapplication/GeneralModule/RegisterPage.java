package com.example.myapplication.GeneralModule;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.example.myapplication.R;
import com.example.myapplication.ui.Utils.UsersManage;

public class RegisterPage extends AppCompatActivity {
    private EditText nameEditText, emailEditText , passwordEditText;
    private static String role ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_page);
        Bundle bundle = getIntent().getExtras();
        role = bundle.getString("text");
        nameEditText = findViewById(R.id.editTextTextPersonName);
        emailEditText = findViewById(R.id.editTextTextEmailAddress);
        passwordEditText = findViewById(R.id.editTextTextPassword);

    }

    public void SignUp(View view) {

        //check name is not empty
        //check email is valid
        //check password is more than 8 char
        String name = String.valueOf(nameEditText.getText());
        String email = String.valueOf(emailEditText.getText());
        String password = String.valueOf(passwordEditText.getText());
        Intent first = new Intent(this, RegisterPage2.class);
        first.putExtra( "role" , role);
        first.putExtra( "name" , name);
        first.putExtra( "email" , email);
        first.putExtra( "password" , password);
        finish();
        startActivity(first);
    }
}