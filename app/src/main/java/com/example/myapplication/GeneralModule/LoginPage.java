package com.example.myapplication.GeneralModule;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.MainActivity;
import com.example.myapplication.MainActivity3;
import com.example.myapplication.R;
import com.example.myapplication.Session;
import com.example.myapplication.added_wallet.MainActivity2_wallet;
import com.example.myapplication.added_wallet.MainActivity3_wallet;
import com.example.myapplication.added_wallet.MainActivity4_wallet;
import com.example.myapplication.added_wallet.MainActivity5_wallet;
import com.example.myapplication.added_wallet.MainActivity_wallet;
import com.example.myapplication.ui.Utils.ManageWallet;
import com.example.myapplication.ui.Utils.OrdersManage;
import com.example.myapplication.ui.Utils.UsersManage;
import com.example.myapplication.ui.Utils.database.SQLiteManager;

public class LoginPage extends AppCompatActivity {
    private EditText emailText, passwordText;
    private OrdersManage ordersManage;
    private ManageWallet manageWallet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);
        initWidgets();
    }

    private void initWidgets() {
        emailText = findViewById(R.id.editEmail);
        passwordText = findViewById(R.id.editPassword);
    }
    private void loadFromDBToMemory() {
        SQLiteManager sqLiteManager = SQLiteManager.instanceOfDatabase(this);
        sqLiteManager.populateUserListArray();
        sqLiteManager.populateOrderListArray();
        sqLiteManager.populateWalletArray();
    }
    public void EnterTheSystem(View view) {
        loadFromDBToMemory();
        String email1 = String.valueOf(emailText.getText());
        String password1 = String.valueOf(passwordText.getText());
        for (UsersManage note : UsersManage.UsersList)
        {
            if((note.getEmail().equals(email1) && (note.getPassword().equals(password1)))) {
                for (int i = 0; i < ManageWallet.WalletList.size(); i++) {
                    if (ManageWallet.WalletList.get(i).getUser_id() == note.getId()) {
                        manageWallet = ManageWallet.WalletList.get(i);
                    }
                }
                Intent Main;
                if (manageWallet == null) {
                    if (note.getRole().equals("Customer")) {
                            Main = new Intent(this, MainActivity.class);
                        } else {
                            Main = new Intent(this, MainActivity3.class);
                        }
                    }


                else {
                    if (note.getRole().equals("Customer")) {
                        if (note.getActiveOrder() != -1) {
                            for (int i = 0; i < OrdersManage.orderArrayList.size(); i++) {
                                if (OrdersManage.orderArrayList.get(i).getId() == note.getActiveOrder()) {
                                    ordersManage = OrdersManage.orderArrayList.get(i);
                                }
                            }
                            if (ordersManage.getAccepted_user_id() != -1) {
                                Main = new Intent(this, MainActivity5_wallet.class);
                            } else {
                                Main = new Intent(this, MainActivity2_wallet.class);
                            }
                        } else {
                            Main = new Intent(this, MainActivity_wallet.class);
                        }


                    } else {
                        if (note.getActiveOrder() == -1) {
                            Main = new Intent(this, MainActivity3_wallet.class);

                        } else {
                            Main = new Intent(this, MainActivity4_wallet.class);

                        }
                    }

                }
                ((Session) this.getApplication()).setSomeVariable(note.getId());
                finish();
                startActivity(Main);
            }
        }

    }

    public void ForgotPassword(View view) {
        Intent NewPassword = new Intent(this, ForgotPassword.class);
        finish();
        startActivity(NewPassword);
    }
}