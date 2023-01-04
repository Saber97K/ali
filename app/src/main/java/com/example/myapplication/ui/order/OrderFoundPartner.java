package com.example.myapplication.ui.order;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.myapplication.GeneralModule.MainLobby;
import com.example.myapplication.MainActivity;
import com.example.myapplication.MainActivity2;
import com.example.myapplication.MainActivity3;
import com.example.myapplication.R;
import com.example.myapplication.Session;
import com.example.myapplication.databinding.FoundPartnerBinding;
import com.example.myapplication.ui.Utils.CategoryAdapter;
import com.example.myapplication.ui.Utils.CategoryManage;
import com.example.myapplication.ui.Utils.OrdersManage;
import com.example.myapplication.ui.Utils.UsersManage;
import com.example.myapplication.ui.Utils.database.SQLiteManager;

public class OrderFoundPartner extends Fragment {

    private FoundPartnerBinding binding;
    private View root;
    private OrdersManage ordersManage;
    private int currentUser;
    private SQLiteManager sqLiteManager;
    private UsersManage usersManage, acceptedUserManage;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FoundPartnerBinding.inflate(inflater, container, false);
        root = binding.getRoot();
        CategoryManage.categoryManages.clear();
        currentUser = ((Session) getActivity().getApplication()).getSomeVariable();
        loadFromDBToMemory();

        for (int i = 0 ; i < OrdersManage.orderArrayList.size(); i++){
            if(OrdersManage.orderArrayList.get(i).getUser_id() == currentUser) {
                if (OrdersManage.orderArrayList.get(i).getStatus() == 0  &&
                        OrdersManage.orderArrayList.get(i).getAccepted_user_id() != -1) {
                    ordersManage = OrdersManage.orderArrayList.get(i);
                }
            }
        }
        for (int i = 0 ; i < UsersManage.UsersList.size(); i++){
            if(UsersManage.UsersList.get(i).getId() == currentUser){
                usersManage = UsersManage.UsersList.get(i);
            }
        }
        for (int i = 0 ; i < UsersManage.UsersList.size(); i++){
            if(UsersManage.UsersList.get(i).getId() == ordersManage.getAccepted_user_id()){
                acceptedUserManage = UsersManage.UsersList.get(i);
            }
        }

        Button btnOut = (Button) root.findViewById(R.id.buttonCompleteOrder);
        btnOut.setOnClickListener(new View.OnClickListener() {
                                      @Override
                                      public void onClick(View view) {
                                          ordersManage.setStatus(1);
                                          sqLiteManager.UpdateOrder(ordersManage);
                                          usersManage.setActiveOrder(-1);
                                          acceptedUserManage.setActiveOrder(-1);
                                          sqLiteManager.UpdateUser(usersManage);
                                          sqLiteManager.UpdateUser(acceptedUserManage);
                                          Intent intent = new Intent(getActivity(), AfterCompleteCustomer.class);
                                          intent.putExtra("id" , acceptedUserManage.getId());
                                          getActivity().finish();
                                          startActivity(intent);
                                      }
                                  }
        );

        Button btn2 = (Button) root.findViewById(R.id.buttonRejectOrder2);
        btn2.setOnClickListener(new View.OnClickListener() {
                                      @Override
                                      public void onClick(View view) {
                                          ordersManage.setAccepted_user_id(-1);
                                          sqLiteManager.UpdateOrder(ordersManage);
                                          acceptedUserManage.setActiveOrder(-1);
                                          sqLiteManager.UpdateUser(acceptedUserManage);
                                          Intent intent = new Intent(getActivity(), AfterCompleteCustomer.class);
                                          intent.putExtra("id" , acceptedUserManage.getId());
                                          getActivity().finish();
                                          startActivity(intent);
                                      }
                                  }
        );
        return root;
    }


    private void loadFromDBToMemory() {
        sqLiteManager = SQLiteManager.instanceOfDatabase(getActivity());
        sqLiteManager.populateOrderListArray();
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