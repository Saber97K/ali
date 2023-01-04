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
import androidx.lifecycle.ViewModelProvider;

import com.example.myapplication.MainActivity;
import com.example.myapplication.R;
import com.example.myapplication.Session;
import com.example.myapplication.databinding.FragmentRecordBinding;
import com.example.myapplication.databinding.FragmentWalletBinding;
import com.example.myapplication.ui.Utils.ManageWallet;
import com.example.myapplication.ui.Utils.UsersManage;
import com.example.myapplication.ui.Utils.database.SQLiteManager;
import com.example.myapplication.ui.profile.ProfileFragment;

public class WalletFragment extends Fragment {

    private FragmentWalletBinding binding;
    private int currentUser;
    private View view1;
    private ManageWallet manageWallet;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        SQLiteManager sqLiteManager = SQLiteManager.instanceOfDatabase(getActivity());
        sqLiteManager.populateWalletArray();
        currentUser = ((Session) this.getActivity().getApplication()).getSomeVariable();
        binding = FragmentWalletBinding.inflate(inflater, container, false);
        view1 = binding.getRoot();
        for (int i = 0; i < ManageWallet.WalletList.size(); i++){
            if(ManageWallet.WalletList.get(i).getUser_id() == currentUser){
                manageWallet = ManageWallet.WalletList.get(i);
            }
        }

            Button btn2 = (Button) view1.findViewById(R.id.clickAddBankAcount);
            btn2.setOnClickListener(new View.OnClickListener() {
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
        super.onResume();

    }
}