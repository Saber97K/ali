package com.example.myapplication.ui.order;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.myapplication.Session;
import com.example.myapplication.databinding.FragmentWorkerOrderBinding;
import com.example.myapplication.ui.Utils.AdapterOrder;
import com.example.myapplication.ui.Utils.CategoryAdapter;
import com.example.myapplication.ui.Utils.CategoryManage;
import com.example.myapplication.ui.Utils.OrdersManage;
import com.example.myapplication.ui.Utils.database.SQLiteManager;


public class Worker_intro extends Fragment {

    private FragmentWorkerOrderBinding binding;
    private ListView noteListView , finished;
    private View root;
    private int currentUser;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentWorkerOrderBinding.inflate(inflater, container, false);
        root = binding.getRoot();
        currentUser = ((Session) this.getActivity().getApplication()).getSomeVariable();
        OrdersManage.orderArrayList.clear();
        noteListView = binding.workerOrderList;
        finished = binding.finishedList;
        loadFromDBToMemory();
        setOrderAdapter();
        setFinishedAdapter();
        setOnClickListener();
        return root;
    }


    private void setOrderAdapter( ) {
        AdapterOrder noteAdapter = new AdapterOrder(getActivity().getApplicationContext(), OrdersManage.OngoingOrderAvailable());
        noteListView.setAdapter(noteAdapter);
    }
    private void setFinishedAdapter( ) {
        AdapterOrder noteAdapter = new AdapterOrder(getActivity().getApplicationContext(), OrdersManage.CompletedOrderByThisPerson(currentUser));
        finished.setAdapter(noteAdapter);
    }

    private void loadFromDBToMemory() {
        SQLiteManager sqLiteManager = SQLiteManager.instanceOfDatabase(getActivity());
        sqLiteManager.populateOrderListArray();
    }
    private void setOnClickListener() {
        noteListView.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l)
            {
                OrdersManage selectedNode = (OrdersManage) noteListView.getItemAtPosition(position);
                Intent editNodeIntent = new Intent(getActivity().getApplicationContext(), OrderDetailsForWorker.class);
                editNodeIntent.putExtra("order", selectedNode.getId());
                getActivity().finish();
                startActivity(editNodeIntent);
            }
        });
    }

    @Override
    public void onResume()
    {
        super.onResume();
        setOrderAdapter();
        setFinishedAdapter();
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}