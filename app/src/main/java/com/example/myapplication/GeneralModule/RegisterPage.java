package com.example.myapplication.GeneralModule;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

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

        String name = String.valueOf(nameEditText.getText());
        String email = String.valueOf(emailEditText.getText());
        String password = String.valueOf(passwordEditText.getText());

        if (name.isEmpty()){//check name is not empty
            Toast.makeText(this, "Name can't be empty", Toast.LENGTH_SHORT).show();
        }
        else if(!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            //check email is valid
            Toast.makeText(this, "Email invalid", Toast.LENGTH_SHORT).show();
        }
        else if(password.length() < 8){
            //check password is more than 8 char
            Toast.makeText(this, "Password must have min 8 characters", Toast.LENGTH_SHORT).show();
        }
        else {
            Intent first = new Intent(this, RegisterPage2.class);
            first.putExtra("role", role);
            first.putExtra("name", name);
            first.putExtra("email", email);
            first.putExtra("password", password);
            finish();
            startActivity(first);
        }
    }
}