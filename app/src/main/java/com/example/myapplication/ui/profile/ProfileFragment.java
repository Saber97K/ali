package com.example.myapplication.ui.profile;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.myapplication.GeneralModule.MainLobby;
import com.example.myapplication.R;
import com.example.myapplication.Session;
import com.example.myapplication.databinding.FragmentProfileBinding;
import com.example.myapplication.ui.Utils.RatingManage;
import com.example.myapplication.ui.Utils.UsersManage;
import com.example.myapplication.ui.Utils.database.SQLiteManager;
import com.example.myapplication.ui.record.RecordFragment;

import java.text.DecimalFormat;

public class ProfileFragment extends Fragment {

    private FragmentProfileBinding binding;
    private TextView dateEdit,phoneEdit,locEdit, userEdit, userRating;
    private ImageView imageView;
    private int currentUser ;
    private  float ratingValue;
    private UsersManage userProfile;
    private static final DecimalFormat df = new DecimalFormat("0.0");


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {


        binding = FragmentProfileBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        currentUser = ((Session) getActivity().getApplication()).getSomeVariable();
        initWidgets();
        loadFromDBToMemory();
        assignUser();
        userEdit.setText(userProfile.getName());
        dateEdit.setText(userProfile.getBirthday());
        phoneEdit.setText(userProfile.getPhoneNumber());
        locEdit.setText(userProfile.getLocation());
        userRating.setText(countRating());
        imageView.setImageBitmap(userProfile.getImage());
        Log.d("TAG", String.valueOf(currentUser));


        Button btnOut = (Button) root.findViewById(R.id.LogoutBtn);
        btnOut.setOnClickListener(new View.OnClickListener() {
                                       @Override
                                       public void onClick(View view) {
                                           Intent intent = new Intent(getActivity(), MainLobby.class);
                                           ((Session) getActivity().getApplication()).setSomeVariable(-1);
                                           ((Session) getActivity().getApplication()).setChoice(0);
                                           UsersManage.UsersList.clear();
                                           getActivity().finish();
                                           startActivity(intent);
                                       }
                                   }
        );

        Button btnDeleteUser = (Button) root.findViewById(R.id.DelAcc);
        btnDeleteUser.setOnClickListener(new View.OnClickListener() {
                                      @Override
                                      public void onClick(View view) {
                                          Intent intent = new Intent(getActivity(), MainLobby.class);
                                          getActivity().finish();
                                          deleteUserFromDB();
                                          ((Session) getActivity().getApplication()).setSomeVariable(-1);
                                          ((Session) getActivity().getApplication()).setChoice(0);
                                          UsersManage.UsersList.clear();
                                          startActivity(intent);
                                      }
                                  }
        );

        return root;
    }

    public String countRating() {
        ratingValue = 0;
        int counts = 0;
        for (int i =0 ; i < RatingManage.ratingsArrayList.size(); i++){
            if(RatingManage.ratingsArrayList.get(i).getTo_user() == currentUser){
                ratingValue = ratingValue + RatingManage.ratingsArrayList.get(i).getValue();
                counts++;
            }
        }
        if(counts != 0) {
            ratingValue = ratingValue / counts;
             return df.format(ratingValue);
        }else
        {
            return df.format(ratingValue);
        }

    }

    public void assignUser(){
        for (int i = 0 ; i < UsersManage.UsersList.size(); i++){
            if(UsersManage.UsersList.get(i).getId() == currentUser){
                userProfile = UsersManage.UsersList.get(i);
            }
        }
    }
    public void initWidgets(){
        imageView = binding.ProfilePic;
        userRating = binding.RatingEDIT;
        userEdit = binding.Name;
        dateEdit =binding.DOB;
        locEdit = binding.Location;
        phoneEdit = binding.PhoneNumberText;
    }
    private void loadFromDBToMemory() {
        UsersManage.UsersList.clear();
        RatingManage.ratingsArrayList.clear();
        SQLiteManager sqLiteManager = SQLiteManager.instanceOfDatabase(getActivity());
        sqLiteManager.populateUserListArray();
        sqLiteManager.populateRatingListArray();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private void deleteUserFromDB() {
        SQLiteManager sqLiteManager = SQLiteManager.instanceOfDatabase(getActivity());
        sqLiteManager.deleteUser(currentUser);
    }
}