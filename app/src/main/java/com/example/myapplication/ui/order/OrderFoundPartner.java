package com.example.myapplication.ui.order;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.myapplication.R;
import com.example.myapplication.Session;
import com.example.myapplication.databinding.FoundPartnerBinding;
import com.example.myapplication.ui.Utils.CategoryManage;
import com.example.myapplication.ui.Utils.ManageVisited;
import com.example.myapplication.ui.Utils.ManageWallet;
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
    private ManageWallet manageWallet , manageWalletAccepted;


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
        for (int i = 0; i < ManageWallet.WalletList.size(); i++){
            if(ManageWallet.WalletList.get(i).getUser_id() == currentUser){
                manageWallet = ManageWallet.WalletList.get(i);
            }
        }
        for (int i = 0; i < ManageWallet.WalletList.size(); i++){
            if(ManageWallet.WalletList.get(i).getUser_id() == ordersManage.getAccepted_user_id()){
                manageWalletAccepted = ManageWallet.WalletList.get(i);
            }
        }

        Button btnOut = (Button) root.findViewById(R.id.buttonCompleteOrder);
        btnOut.setOnClickListener(new View.OnClickListener() {
                                      @Override
                                      public void onClick(View view) {
                                          int visitsSize = ManageVisited.VisitsList.size();
                                          ManageVisited manageVisited = new ManageVisited(visitsSize, acceptedUserManage.getId(),ordersManage.getId(),0);
                                          sqLiteManager.addVisits(manageVisited);
                                          ordersManage.setStatus(1);
                                          sqLiteManager.UpdateOrder(ordersManage);
                                          usersManage.setActiveOrder(-1);
                                          manageWallet.setBalance((float) (manageWallet.getBalance() - ordersManage.getPrice()));
                                          manageWalletAccepted.setBalance((float) (manageWalletAccepted.getBalance() + ordersManage.getPrice()));
                                          acceptedUserManage.setActiveOrder(-1);
                                          sqLiteManager.UpdateUser(usersManage);
                                          sqLiteManager.UpdateWallet(manageWallet);
                                          sqLiteManager.UpdateWallet(manageWalletAccepted);
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
                                          int visitsSize = ManageVisited.VisitsList.size();
                                          ManageVisited manageVisited = new ManageVisited(visitsSize, acceptedUserManage.getId(),ordersManage.getId(),0);
                                          sqLiteManager.addVisits(manageVisited);
                                          ordersManage.setStatus(2);
                                          sqLiteManager.UpdateOrder(ordersManage);
                                          acceptedUserManage.setActiveOrder(-1); // worker no more task
                                          usersManage.setActiveOrder(-1);   // customer no more order for worker
                                          sqLiteManager.UpdateUser(acceptedUserManage);
                                          sqLiteManager.UpdateUser(usersManage);

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
        sqLiteManager.populateWalletArray();
        sqLiteManager.populateVisitsListArray();
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