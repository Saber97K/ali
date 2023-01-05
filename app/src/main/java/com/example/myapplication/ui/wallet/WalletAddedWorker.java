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
import com.example.myapplication.databinding.WalletAddedWorkerBinding;
import com.example.myapplication.ui.Utils.ManageTopUps;
import com.example.myapplication.ui.Utils.ManageWallet;
import com.example.myapplication.ui.Utils.database.SQLiteManager;

public class WalletAddedWorker extends Fragment {

    private WalletAddedWorkerBinding binding;
    private int currentUser;
    private View view1;
    private ManageWallet manageWallet;
    private TextView customerBalanceNum;
    private SQLiteManager sqLiteManager;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        sqLiteManager = SQLiteManager.instanceOfDatabase(getActivity());
        sqLiteManager.populateWalletArray();



        currentUser = ((Session) this.getActivity().getApplication()).getSomeVariable();
        binding = WalletAddedWorkerBinding.inflate(inflater, container, false);
        view1 = binding.getRoot();
        customerBalanceNum = binding.WithdrawBalanceNum;


        for (int i = 0; i < ManageWallet.WalletList.size(); i++){
            if(ManageWallet.WalletList.get(i).getUser_id() == currentUser){
                manageWallet = ManageWallet.WalletList.get(i);
            }
        }


        customerBalanceNum.setText(manageWallet.getBalance() + "");

        Button btn2 = (Button) view1.findViewById(R.id.chooseToWithdraw);
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), WithdrawProcess.class);
                intent.putExtra("withdraw" , manageWallet.getId());
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

        for (int i = 0; i < ManageWallet.WalletList.size(); i++){
            if(ManageWallet.WalletList.get(i).getUser_id() == currentUser){
                manageWallet = ManageWallet.WalletList.get(i);
            }
        }

        customerBalanceNum.setText(manageWallet.getBalance() + "");

        super.onResume();


    }
}