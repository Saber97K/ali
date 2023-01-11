package com.example.myapplication.ui.record;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
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
    private int choice = 0;
    private int currentUser;
    private UsersManage usersManage;



    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        for (int i = 0; i < UsersManage.UsersList.size(); i++){
            if(UsersManage.UsersList.get(i).getId() == currentUser){
                usersManage = UsersManage.UsersList.get(i);
            }
        }
        binding = FragmentRecordBinding.inflate(inflater, container, false);
        root = binding.getRoot();
        initWidgets();
        OrdersManage.orderArrayList.clear();
        noteListView = binding.orderListView;
        currentUser = ((Session) this.getActivity().getApplication()).getSomeVariable();
        loadFromDBToMemory();
        if(choice == 0) {
            setOrderOngoingAdapter();
        }


            AppCompatRadioButton btn1 = (AppCompatRadioButton) root.findViewById(R.id.ongoingRadio);
            btn1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                        setOrderOngoingAdapter();
                    choice = 0;
                    }
            });

            AppCompatRadioButton btn2 = (AppCompatRadioButton) root.findViewById(R.id.completedRadio);
            btn2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    setOrderCompletedAdapter();
                    choice = 1;
                }
            });

            AppCompatRadioButton btn3 = (AppCompatRadioButton) root.findViewById(R.id.cancelledRadio);
            btn3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    setOrderCancelledAdapter();
                   choice = 2 ;
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
    }


    /*  private void setOnClickListener() {
          noteListView.setOnItemClickListener(new AdapterView.OnItemClickListener()
          {
              @Override
              public void onItemClick(AdapterView<?> adapterView, View view, int position, long l)
              {
                  Node selectedNode = (Node) noteListView.getItemAtPosition(position);
                  Intent editNodeIntent = new Intent(getActivity().getApplicationContext(), OrdersActivity.class);
                  editNodeIntent.putExtra(Node.NOTE_EDIT_EXTRA, selectedNode.getId());
                  startActivity(editNodeIntent);
              }
          });
      }

     */
    @Override
    public void onResume()
    {
        super.onResume();
        if(choice == 0){
            setOrderOngoingAdapter();
        }else if(choice == 1){
            setOrderCompletedAdapter();
        }
        else {
            setOrderCancelledAdapter();
        }

    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}