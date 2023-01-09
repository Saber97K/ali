package com.example.myapplication.GeneralModule;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.myapplication.R;
import com.example.myapplication.ui.Utils.UsersManage;
import com.example.myapplication.ui.Utils.database.SQLiteManager;

public class UploadImage extends AppCompatActivity {
    private ImageView imageView;
    private Button button1, button2;
    private static final int pickImage = 100;
    private Uri imageFilePath;
    private Bitmap imageToScore;
    private SQLiteManager sqLiteManager;
    private String role,name,email,password,gender,phone,address,date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_image);
        Bundle bundle = getIntent().getExtras();
        imageView = findViewById(R.id.image);
        button2 = findViewById(R.id.moveNext);
        sqLiteManager = SQLiteManager.instanceOfDatabase(this);
        sqLiteManager.populateUserListArray();
        role = bundle.getString("role");
        name = bundle.getString("name");
        email = bundle.getString("email");
        password = bundle.getString("password");
        gender = bundle.getString("gender");
        phone = bundle.getString("phone");
        address = bundle.getString("address");
        date = bundle.getString("date");
    }


    public void chooseImage(View view ){
        try {
            Intent obj = new Intent();
            obj.setType("image/*");
            obj.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(obj, pickImage);

        }catch (Exception e){//Permission not allowed etc
            Toast.makeText(this, "An Error Occurred, Try Again Later  ", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        try {
            super.onActivityResult(requestCode, resultCode, data);
            if(requestCode == pickImage && resultCode == RESULT_OK && data!= null && data.getData() != null){
                imageFilePath = data.getData();
                imageToScore = MediaStore.Images.Media.getBitmap(getContentResolver(), imageFilePath);

                imageView.setImageBitmap(imageToScore);
            }

        }catch (Exception e)
        {

        }

    }

    public void Register2PageGo(View view) {
        try {
            Intent first = new Intent(this, LoginPage.class);
            int id = UsersManage.UsersList.size();
            if (imageView.getDrawable() != null && imageToScore!= null) {
                UsersManage user = new UsersManage(id, role, email, password, date, name, address, gender, phone, -1, imageToScore);
                UsersManage.UsersList.add(user);
                sqLiteManager.addUserToDatabase(user);
                finish();
                startActivity(first);
            }else {
                Toast.makeText(this, "Please add an image" , Toast.LENGTH_LONG).show();
            }
        }
        catch (Exception e){

        }

    }
}