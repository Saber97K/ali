package com.example.myapplication.ui.order;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.Session;
import com.example.myapplication.databinding.FragmentWorkerOrderBinding;
import com.example.myapplication.ui.Utils.AdapterCard;
import com.example.myapplication.ui.Utils.AdapterOrder;
import com.example.myapplication.ui.Utils.CategoryManage;
import com.example.myapplication.ui.Utils.OrdersManage;
import com.example.myapplication.ui.Utils.database.SQLiteManager;

public class Worker_intro extends Fragment implements OnItemClick {

    private FragmentWorkerOrderBinding binding;
    public ListView noteListView;
    private View root;
    private int currentUser;
    private RecyclerView courseRV;
    private AdapterCard dataRVAdapter;
    private SQLiteManager sqLiteManager;
    private int cat_id = -1;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentWorkerOrderBinding.inflate(inflater, container, false);
        root = binding.getRoot();
        currentUser = ((Session) this.getActivity().getApplication()).getSomeVariable();

        OrdersManage.orderArrayList.clear();
        CategoryManage.categoryManages.clear();


        noteListView = binding.workerOrderList;
        courseRV = binding.idRVItems;

        loadFromDBToMemory();
        courseRV.setHasFixedSize(true);
        courseRV.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        loadRecyclerViewData();
        if (cat_id == -1) {
            setOrderAdapter();
        } else {
            setOrderAdapter2();
        }

        setOnClickListener();
        return root;
    }

    private void loadRecyclerViewData() {
        dataRVAdapter = new AdapterCard(CategoryManage.categoryManages, getActivity(), this);
        courseRV.setAdapter(dataRVAdapter);
    }

    private void setOrderAdapter() {
        AdapterOrder noteAdapter;
        noteAdapter = new AdapterOrder(getActivity().getApplicationContext(), OrdersManage.OngoingOrderAvailable());
        noteListView.setAdapter(noteAdapter);

    }

    private void setOrderAdapter2() {
        AdapterOrder noteAdapter;
        noteAdapter = new AdapterOrder(getActivity().getApplicationContext(), OrdersManage.OngoingOrderAvailable(cat_id));
        noteListView.setAdapter(noteAdapter);

    }


    private void loadFromDBToMemory() {
        SQLiteManager sqLiteManager = SQLiteManager.instanceOfDatabase(getActivity());
        sqLiteManager.populateOrderListArray();
        sqLiteManager.populateCategoryListArray();
    }

    private void setOnClickListener() {
        noteListView.setOnItemClickListener((adapterView, view, position, l) -> {
            OrdersManage selectedNode = (OrdersManage) noteListView.getItemAtPosition(position);
            Intent editNodeIntent = new Intent(getActivity().getApplicationContext(), OrderDetailsForWorker.class);
            editNodeIntent.putExtra("order", selectedNode.getId());
            getActivity().finish();
            startActivity(editNodeIntent);
        });
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void onClick(String value) {
        cat_id = Integer.parseInt(value);
        loadRecyclerViewData();
        if (cat_id == -1) {
            setOrderAdapter();
        } else {
            setOrderAdapter2();
        }
    }
}