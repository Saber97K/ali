package com.example.myapplication.ui.order;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.myapplication.GeneralModule.MainLobby;
import com.example.myapplication.MainActivity3;
import com.example.myapplication.R;
import com.example.myapplication.Session;
import com.example.myapplication.added_wallet.MainActivity3_wallet;
import com.example.myapplication.databinding.WorkerIntro2Binding;
import com.example.myapplication.ui.Utils.CategoryAdapter;
import com.example.myapplication.ui.Utils.CategoryManage;
import com.example.myapplication.ui.Utils.OrdersManage;
import com.example.myapplication.ui.Utils.UsersManage;
import com.example.myapplication.ui.Utils.database.SQLiteManager;

public class WorkerIntro2 extends Fragment {

    private WorkerIntro2Binding binding;
    private View root;

    private TextView typeTExtView, DateTextView, priceTextView , descTextView;
    private int currentUser , OrderId;
    private OrdersManage ordersManage;
    private SQLiteManager sqLiteManager;
    private UsersManage usersManage;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = WorkerIntro2Binding.inflate(inflater, container, false);
        root = binding.getRoot();
        sqLiteManager = SQLiteManager.instanceOfDatabase(getActivity());
        sqLiteManager.populateOrderListArray();

        currentUser = ((Session) getActivity().getApplication()).getSomeVariable();


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
                break;
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
        return root;
    }

    private void initWidgets() {

        typeTExtView = getActivity().findViewById(R.id.textView35);
        priceTextView = getActivity().findViewById(R.id.textView36);
        DateTextView = getActivity().findViewById(R.id.textView37);
        descTextView = getActivity().findViewById(R.id.textView38);


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