package com.example.myapplication.ui.wallet;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.myapplication.R;
import com.example.myapplication.Session;
import com.example.myapplication.databinding.WalletAddedBinding;
import com.example.myapplication.ui.Utils.ManageTopUps;
import com.example.myapplication.ui.Utils.ManageWallet;
import com.example.myapplication.ui.Utils.database.SQLiteManager;

public class WalletAdded extends Fragment {

    private WalletAddedBinding binding;
    private int currentUser;
    private View view1;
    private ManageWallet manageWallet;
    private TextView customerBalanceNum;
    private float valueOfTopUp = 0;
    private SQLiteManager sqLiteManager;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        sqLiteManager = SQLiteManager.instanceOfDatabase(getActivity());
        ManageTopUps.TopUpsList.clear();

        sqLiteManager.populateWalletArray();
        sqLiteManager.populateTopUps();



        currentUser = ((Session) this.getActivity().getApplication()).getSomeVariable();
        binding = WalletAddedBinding.inflate(inflater, container, false);
        view1 = binding.getRoot();
        customerBalanceNum = binding.customerBalanceNum;


        for (int i = 0; i < ManageWallet.WalletList.size(); i++){
            if(ManageWallet.WalletList.get(i).getUser_id() == currentUser){
                manageWallet = ManageWallet.WalletList.get(i);
            }
        }

        for (int i = 0 ; i < ManageTopUps.TopUpsList.size(); i++){
            if (ManageTopUps.TopUpsList.get(i).getWallet_id() == manageWallet.getId()) {
                valueOfTopUp += ManageTopUps.TopUpsList.get(i).getValue();
            }
        }

        customerBalanceNum.setText(valueOfTopUp + "");

        Button btn2 = (Button) view1.findViewById(R.id.chooseTopup);
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), TopUpProcess.class);
                intent.putExtra("topup" , manageWallet.getId());
                startActivity(intent);
            }
        });
        Button btn3 = (Button) view1.findViewById(R.id.customerClickBankAcount);
        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), WalletAddingActivity.class);
                startActivity(intent);
            }
        });
        return  view1;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

    }

    @Override
    public void onResume()
    {
        ManageTopUps.TopUpsList.clear();
        sqLiteManager.populateTopUps();
        valueOfTopUp = 0;
        for (int i = 0 ; i < ManageTopUps.TopUpsList.size(); i++){
            if (ManageTopUps.TopUpsList.get(i).getWallet_id() == manageWallet.getId()) {
                valueOfTopUp += ManageTopUps.TopUpsList.get(i).getValue();
            }
        }
        customerBalanceNum.setText(valueOfTopUp + "");

        super.onResume();


    }
}