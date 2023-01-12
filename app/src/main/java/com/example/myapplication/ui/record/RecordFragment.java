package com.example.myapplication.ui.record;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatRadioButton;
import androidx.fragment.app.Fragment;

import com.example.myapplication.R;
import com.example.myapplication.Session;
import com.example.myapplication.databinding.FragmentRecordBinding;
import com.example.myapplication.ui.Utils.AdapterOrder;
import com.example.myapplication.ui.Utils.OrdersManage;
import com.example.myapplication.ui.Utils.UsersManage;
import com.example.myapplication.ui.Utils.database.SQLiteManager;

public class RecordFragment extends Fragment {

    private FragmentRecordBinding binding;
    private ListView noteListView;
    private AppCompatRadioButton ongoingButton, completedButton, cancelledButton;
    private View root;
    private int currentUser;
    private UsersManage usersManage;



    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        UsersManage.UsersList.clear();
        OrdersManage.orderArrayList.clear();
        loadFromDBToMemory();
        currentUser = ((Session) getActivity().getApplication()).getSomeVariable();
        Log.d("TAG", "onCreateView: " + currentUser);
        for (int i = 0; i < UsersManage.UsersList.size(); i++){
            if(UsersManage.UsersList.get(i).getId() == currentUser){
                usersManage = UsersManage.UsersList.get(i);
            }
        }
        binding = FragmentRecordBinding.inflate(inflater, container, false);
        root = binding.getRoot();
        initWidgets();
        noteListView = binding.orderListView;
        setOrderOngoingAdapter();


            AppCompatRadioButton btn1 = (AppCompatRadioButton) root.findViewById(R.id.ongoingRadio);
            btn1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                        setOrderOngoingAdapter();
                    ((Session) getActivity().getApplication()).setChoice(0);
                    }
            });

            AppCompatRadioButton btn2 = (AppCompatRadioButton) root.findViewById(R.id.completedRadio);
            btn2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    setOrderCompletedAdapter();
                    ((Session) getActivity().getApplication()).setChoice(1);
                }
            });

            AppCompatRadioButton btn3 = (AppCompatRadioButton) root.findViewById(R.id.cancelledRadio);
            btn3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    setOrderCancelledAdapter();
                    ((Session) getActivity().getApplication()).setChoice(2);
                }
            });

        return root;
    }

    private void initWidgets() {
        ongoingButton = getActivity().findViewById(R.id.ongoingRadio);
        completedButton = getActivity().findViewById(R.id.completedRadio);
        cancelledButton = getActivity().findViewById(R.id.cancelledRadio);
    }


    private void setOrderOngoingAdapter( ) {
        AdapterOrder noteAdapter;
        if(usersManage.getRole().equals("Customer")) {
            noteAdapter = new AdapterOrder(getActivity().getApplicationContext(), OrdersManage.OngoingOrder(currentUser));
        }else{
            noteAdapter = new AdapterOrder(getActivity().getApplicationContext(), OrdersManage.OngoingOrderByThisPerson(currentUser));
        }
        noteListView.setAdapter(noteAdapter);
    }
    private void setOrderCompletedAdapter( ) {
        AdapterOrder noteAdapter;
        if(usersManage.getRole().equals("Customer")) {
            noteAdapter = new AdapterOrder(getActivity().getApplicationContext(), OrdersManage.CompletedOrder(currentUser));
        }else{
            noteAdapter = new AdapterOrder(getActivity().getApplicationContext(), OrdersManage.CompletedOrderByThisPerson(currentUser));
        }
        noteListView.setAdapter(noteAdapter);
    }
    private void setOrderCancelledAdapter( ) {
        AdapterOrder noteAdapter;
        if(usersManage.getRole().equals("Customer")) {
            noteAdapter = new AdapterOrder(getActivity().getApplicationContext(), OrdersManage.CancelledOrder(currentUser));
        }else{
            noteAdapter = new AdapterOrder(getActivity().getApplicationContext(), OrdersManage.CancelledOrderByThisPerson(currentUser));
        }
        noteListView.setAdapter(noteAdapter);
    }
    private void loadFromDBToMemory() {
        SQLiteManager sqLiteManager = SQLiteManager.instanceOfDatabase(getActivity());
        sqLiteManager.populateOrderListArray();
        sqLiteManager.populateUserListArray();
    }


    @Override
    public void onResume()
    {

        super.onResume();
        if(((Session) getActivity().getApplication()).getChoice() == 0){
            setOrderOngoingAdapter();
        }else if(((Session) getActivity().getApplication()).getChoice() == 1){
            setOrderCompletedAdapter();
        }
        else {
            setOrderCancelledAdapter();
        }

    }
    @Override
    public void onPause()
    {
        super.onPause();


    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;

    }

}