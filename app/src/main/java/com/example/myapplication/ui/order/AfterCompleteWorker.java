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

import com.example.myapplication.MainActivity;
import com.example.myapplication.R;
import com.example.myapplication.Session;
import com.example.myapplication.added_wallet.MainActivity3_wallet;
import com.example.myapplication.databinding.ActivityAfterCompleteWorkerBinding;
import com.example.myapplication.ui.Utils.AdapterUser;
import com.example.myapplication.ui.Utils.CategoryAdapter;
import com.example.myapplication.ui.Utils.CategoryManage;
import com.example.myapplication.ui.Utils.OrdersManage;
import com.example.myapplication.ui.Utils.ReviewPage;
import com.example.myapplication.ui.Utils.UsersManage;
import com.example.myapplication.ui.Utils.database.SQLiteManager;

public class AfterCompleteWorker extends Fragment {

    private ActivityAfterCompleteWorkerBinding binding;
    private ListView noteListView;

    private int currentUser;
    private OrdersManage acceptedOrdersManage;
    private UsersManage acceptedUsersManage;
    private View root;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = ActivityAfterCompleteWorkerBinding.inflate(inflater, container, false);
        root = binding.getRoot();

        noteListView = binding.UserListViewCell;

        currentUser = ((Session) getActivity().getApplication()).getSomeVariable();
        OrdersManage.orderArrayList.clear();
        UsersManage.UsersList.clear();

        SQLiteManager sqLiteManager = SQLiteManager.instanceOfDatabase(getActivity());
        sqLiteManager.populateOrderListArray();
        sqLiteManager.populateUserListArray();
        for (int i = 0 ; i < OrdersManage.orderArrayList.size(); i++){
            if(OrdersManage.orderArrayList.get(i).getAccepted_user_id() == currentUser){
                acceptedOrdersManage = OrdersManage.orderArrayList.get(i);
            }
        }
        for (int i = 0 ; i < UsersManage.UsersList.size(); i++){
            if(UsersManage.UsersList.get(i).getId() == acceptedOrdersManage.getUser_id()){
                acceptedUsersManage = UsersManage.UsersList.get(i);
            }
        }

        Button btn2 = (Button) root.findViewById(R.id.buttong);
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity().getApplicationContext(), ReviewPage.class);
                intent.putExtra("text" , "worker");
                intent.putExtra("user" , acceptedUsersManage.getId());
                getActivity().finish();
                startActivity(intent);
            }
        });

        Button btn3 = (Button) root.findViewById(R.id.buttongg);
        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity().getApplicationContext(), MainActivity3_wallet.class);
                getActivity().finish();
                startActivity(intent);
            }
        });

        UsersManage.UsersList.clear();
        UsersManage.UsersList.add(acceptedUsersManage);
        setAcceptedUserAdapter();
        return root;
    }
    private void setAcceptedUserAdapter( ) {
        AdapterUser noteAdapter = new AdapterUser(getActivity().getApplicationContext(), UsersManage.UsersList);
        noteListView.setAdapter(noteAdapter);
    }

    @Override
    public void onResume()
    {
        super.onResume();
        setAcceptedUserAdapter();
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}