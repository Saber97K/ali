package com.example.myapplication.ui.order;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.myapplication.MainActivity;
import com.example.myapplication.R;
import com.example.myapplication.Session;
import com.example.myapplication.databinding.FragmentCreatedOrderBinding;
import com.example.myapplication.ui.Utils.OrdersManage;
import com.example.myapplication.ui.Utils.UsersManage;
import com.example.myapplication.ui.Utils.database.SQLiteManager;

public class CreatedOrder extends Fragment {

    private FragmentCreatedOrderBinding binding;
    private View root;
    private int currentUser;
    private UsersManage usersManage;
    private OrdersManage ordersManage;
    Handler handler = new Handler();
    Runnable refresh;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentCreatedOrderBinding.inflate(inflater, container, false);


        SQLiteManager sqLiteManager = SQLiteManager.instanceOfDatabase(getActivity());
        sqLiteManager.populateUserListArray();
        sqLiteManager.populateOrderListArray();
        root = binding.getRoot();
        currentUser = ((Session) this.getActivity().getApplication()).getSomeVariable();
        for (int i = 0 ; i < UsersManage.UsersList.size(); i++){
            if(UsersManage.UsersList.get(i).getId() == currentUser){
                usersManage = UsersManage.UsersList.get(i);
                break;
            }
        }

        for (int i = 0; i < OrdersManage.orderArrayList.size(); i++){
            if(OrdersManage.orderArrayList.get(i).getUser_id() == currentUser){
                if(OrdersManage.orderArrayList.get(i).getStatus() == 0) {
                    ordersManage = OrdersManage.orderArrayList.get(i);
                    break;
                }
            }
        }



        Button btn2 = (Button) root.findViewById(R.id.cancelButton);
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                usersManage.setActiveOrder(-1);
                ordersManage.setStatus(2);
                Intent intent = new Intent(getActivity().getApplicationContext(), MainActivity.class);
                sqLiteManager.UpdateUser(usersManage);
                sqLiteManager.UpdateOrder(ordersManage);
                getActivity().finish();
                startActivity(intent);
            }
        });
        return root;
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

    private void loadFromDBToMemory() {
        SQLiteManager sqLiteManager = SQLiteManager.instanceOfDatabase(getActivity());
        sqLiteManager.populateUserListArray();
    }
}
