package com.example.myapplication.ui.Utils;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RatingBar;

import com.example.myapplication.R;
import com.example.myapplication.Session;
import com.example.myapplication.ui.Utils.database.SQLiteManager;

public class ReviewPage extends AppCompatActivity {

    private RatingBar ratingBar;
    private int currentUser, user;
    private String text;
    private EditText desciption;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review_page);
        Bundle bundle = getIntent().getExtras();
        user = bundle.getInt("user");
        text = bundle.getString("text");
        currentUser = ((Session) this.getApplication()).getSomeVariable();
        ratingBar = findViewById(R.id.ratingBar);
        desciption = findViewById(R.id.ReviewDesc);
        ratingBar.setStepSize(1);
        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {
                ratingBar.setOnRatingBarChangeListener(this);
            }
        });


    }

    public void MakeAReview(View view) {
        SQLiteManager sqLiteManager = SQLiteManager.instanceOfDatabase(this);
        sqLiteManager.populateRatingListArray();
        String desc = String.valueOf(desciption.getText());
        int id = RatingManage.ratingsArrayList.size();
        int rating = (int) ratingBar.getRating();
        RatingManage ratingManage = new RatingManage(id ,user,currentUser,rating, desc);
        sqLiteManager.addRatingToDatabase(ratingManage);
        Intent intent = new Intent(this, Success.class);
        finish();
        intent.putExtra("text" , text);
        startActivity(intent);
    }
}