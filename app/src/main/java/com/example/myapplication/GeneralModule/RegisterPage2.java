package com.example.myapplication.GeneralModule;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatRadioButton;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.example.myapplication.R;
import com.example.myapplication.ui.Utils.UsersManage;
import com.example.myapplication.ui.Utils.database.SQLiteManager;

import java.util.Calendar;

public class RegisterPage2 extends AppCompatActivity {
    private EditText addressEdit, phoneEdit, GenderEdit;
    private Button dateButton;
    private DatePickerDialog datePickerDialog;
    private String role,name,email,password ,phone , address, gender, date;
    private AppCompatRadioButton rbMale, rbFemale;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_page2);
        Bundle bundle = getIntent().getExtras();

        role = bundle.getString("role");
        name = bundle.getString("name");
        email = bundle.getString("email");
        password = bundle.getString("password");
        initWidgets();
        date = getTodaysDate();
        gender = "Male";
    }

    private void initWidgets() {
        addressEdit = findViewById(R.id.AddressEDIT);
        phoneEdit = findViewById(R.id.Phone_NN);
        dateButton = findViewById(R.id.dateButton2);
        rbFemale = findViewById(R.id.femaleButton);
        rbMale = findViewById(R.id.maleButton);
        dateButton.setText(getTodaysDate());
        initDatePicker();

    }
    public void rdButton1(View view) {
        boolean isSelected = ((AppCompatRadioButton)view).isChecked();
        switch (view.getId()){
            case R.id.maleButton:
                if(isSelected){
                    rbMale.setTextColor(Color.WHITE);
                    rbFemale.setTextColor(Color.BLACK);
                    gender = "Male";
                }
                break;
            case R.id.femaleButton:
                if(isSelected){
                    rbFemale.setTextColor(Color.WHITE);
                    rbMale.setTextColor(Color.BLACK);
                    gender = "Female";
                }
                break;
        }
    }


    public void ContinueRegister(View view) {
        //check address
        //check phone number
        //check gender - do drop down
        //
        Intent intent = new Intent(this, UploadImage.class);
        address = String.valueOf(addressEdit.getText());
        phone = String.valueOf(phoneEdit.getText());

        if(address.isEmpty()){
            Toast.makeText(this, "Address can't be empty", Toast.LENGTH_SHORT).show();
        }
        else if(phone.isEmpty()){
            Toast.makeText(this, "Phone number can't be empty", Toast.LENGTH_SHORT).show();
        }

        else {
            intent.putExtra("address", address);
            intent.putExtra("phone", phone);
            intent.putExtra("gender", gender);
            intent.putExtra("date", date);
            intent.putExtra("role", role);
            intent.putExtra("name", name);
            intent.putExtra("email", email);
            intent.putExtra("password", password);
            finish();
            startActivity(intent);
        }
    }

        private String getTodaysDate() {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        return makeDateString(day,month,year);
    }


    private void initDatePicker() {
        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month + 1;
                String dateString = makeDateString(day, month,year);
                dateButton.setText(dateString);
                date = dateString;

            }
        };
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        int style = AlertDialog.THEME_HOLO_LIGHT;

        datePickerDialog = new DatePickerDialog(this , style , dateSetListener , year ,month , day);
    }

    private String makeDateString(int day, int month, int year) {
        return day + "/" + month + "/" + year;
    }

    public void DataChoose(View view) {
        datePickerDialog.show();
    }


    public void backButton4(View view) {
        Intent first = new Intent(this, RegisterPage.class);
        first.putExtra("text" , role);
        finish();
        startActivity(first);
    }
}