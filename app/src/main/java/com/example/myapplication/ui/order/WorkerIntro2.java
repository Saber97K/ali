package com.example.myapplication.ui.order;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.myapplication.R;
import com.example.myapplication.Session;
import com.example.myapplication.added_wallet.MainActivity3_wallet;
import com.example.myapplication.databinding.WorkerIntro2Binding;
import com.example.myapplication.ui.Utils.OrdersManage;
import com.example.myapplication.ui.Utils.UsersManage;
import com.example.myapplication.ui.Utils.database.SQLiteManager;

public class WorkerIntro2 extends Fragment {

    private WorkerIntro2Binding binding;
    private View root;

    private TextView typeTExtView, DateTextView, priceTextView , descTextView,phoneTextView;
    private int currentUser , OrderId,anotherUser;
    private OrdersManage ordersManage;
    private SQLiteManager sqLiteManager;
    private UsersManage usersManage,usersManage1;
    private ImageView imageView;
    private ImageButton phone;



    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = WorkerIntro2Binding.inflate(inflater, container, false);
        root = binding.getRoot();
        currentUser = ((Session) getActivity().getApplication()).getSomeVariable();
        initWidgets();
        UsersManage.UsersList.clear();
        OrdersManage.orderArrayList.clear();
        sqLiteManager = SQLiteManager.instanceOfDatabase(getActivity());
        sqLiteManager.populateOrderListArray();
        sqLiteManager.populateUserListArray();

        for (int i =0 ; i < OrdersManage.orderArrayList.size(); i++){
            if(OrdersManage.orderArrayList.get(i).getAccepted_user_id() == currentUser){
               if(OrdersManage.orderArrayList.get(i).getStatus() == 0){
                   ordersManage  = OrdersManage.orderArrayList.get(i);

               }
            }
        }
        for (int i = 0 ; i < UsersManage.UsersList.size(); i++){
            if(UsersManage.UsersList.get(i).getId() == currentUser){
                usersManage = UsersManage.UsersList.get(i);

            }
        }
        for (int i = 0 ; i < UsersManage.UsersList.size(); i++){
            if(UsersManage.UsersList.get(i).getId() == anotherUser){
                usersManage1 = UsersManage.UsersList.get(i);

            }
        }

        Button cancel = (Button) root.findViewById(R.id.button50);
        cancel.setOnClickListener(new View.OnClickListener() {
                                      @Override
                                      public void onClick(View view) {
                                          Intent intent = new Intent(getActivity(), MainActivity3_wallet.class);
                                          ordersManage.setAccepted_user_id(-1);
                                          usersManage.setActiveOrder(-1);
                                          sqLiteManager.UpdateUser(usersManage);
                                          sqLiteManager.UpdateOrder(ordersManage);
                                          getActivity().finish();
                                          startActivity(intent);
                                      }
                                  }
        );




        phone = root.findViewById(R.id.imageView7);
        phone.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        String phone_number="0102799110";
                                        Intent intent = new Intent();
                                        intent.setAction(intent.ACTION_DIAL);
                                        Uri uri=Uri.parse("tel:"+phone_number);
                                        intent.setData(uri);
                                        startActivity(intent);
                                    }
                                }
        );
        return root;
    }

    private void initWidgets() {

        typeTExtView = getActivity().findViewById(R.id.textView15);
        priceTextView = getActivity().findViewById(R.id.textView37);
        DateTextView = getActivity().findViewById(R.id.textView36);
        descTextView = getActivity().findViewById(R.id.textView38);
        phoneTextView = getActivity().findViewById(R.id.textViewPhoneNumber1);
        imageView = getActivity().findViewById(R.id.imageView412);


    }




    @Override
    public void onResume()
    {
        super.onResume();
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}