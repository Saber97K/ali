package com.example.myapplication.GeneralModule;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.MainActivity;
import com.example.myapplication.MainActivity3;
import com.example.myapplication.R;
import com.example.myapplication.Session;
import com.example.myapplication.added_wallet.MainActivity10_wallet;
import com.example.myapplication.added_wallet.MainActivity2_wallet;
import com.example.myapplication.added_wallet.MainActivity3_wallet;
import com.example.myapplication.added_wallet.MainActivity4_wallet;
import com.example.myapplication.added_wallet.MainActivity5_wallet;
import com.example.myapplication.added_wallet.MainActivity_wallet;
import com.example.myapplication.ui.Utils.ManageVisited;
import com.example.myapplication.ui.Utils.ManageWallet;
import com.example.myapplication.ui.Utils.OrdersManage;
import com.example.myapplication.ui.Utils.UsersManage;
import com.example.myapplication.ui.Utils.database.SQLiteManager;

public class LoginPage extends AppCompatActivity {
    private EditText emailText, passwordText;
    private OrdersManage ordersManage;
    private ManageWallet manageWallet;
    private ManageVisited manageVisited;
    private SQLiteManager sqLiteManager;
    private boolean wrongPassword = true; //if "true" means password is wrong, else password is correct

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
        sqLiteManager = SQLiteManager.instanceOfDatabase(this);
        sqLiteManager.populateUserListArray();
        sqLiteManager.populateOrderListArray();
        sqLiteManager.populateWalletArray();
        sqLiteManager.populateVisitsListArray();
    }
    public void EnterTheSystem(View view) {
        loadFromDBToMemory();
        String email1 = String.valueOf(emailText.getText());
        String password1 = String.valueOf(passwordText.getText());

        //check email format before procedding
        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email1).matches()) {//invalid email
            Toast.makeText(this, "Email Invalid!", Toast.LENGTH_SHORT).show();
        }
        else {
            //correct email format
            for (UsersManage note : UsersManage.UsersList) {
                if ((note.getEmail().equals(email1) && (note.getPassword().equals(password1)))) {//correct password
                    wrongPassword = false;
                    Toast.makeText(this, "Login Successful", Toast.LENGTH_SHORT).show();
                    for (int i = 0; i < ManageWallet.WalletList.size(); i++) {
                        if (ManageWallet.WalletList.get(i).getUser_id() == note.getId()) {
                            manageWallet = ManageWallet.WalletList.get(i);
                        }
                    }
                    for (int i = 0; i < ManageVisited.VisitsList.size(); i++) {
                        if (ManageVisited.VisitsList.get(i).getUser_id() == note.getId() &&
                                ManageVisited.VisitsList.get(i).getStatus() == 0) {
                            manageVisited = ManageVisited.VisitsList.get(i);
                        }
                    }
                    Intent Main;
                    if (manageWallet == null) {
                        if (note.getRole().equals("Customer")) {
                            Main = new Intent(this, MainActivity.class);
                        } else {
                            Main = new Intent(this, MainActivity3.class);
                        }
                    } else {
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

                                if (manageVisited == null) {
                                    Main = new Intent(this, MainActivity3_wallet.class);
                                } else {
                                    Main = new Intent(this, MainActivity10_wallet.class);
                                    manageVisited.setStatus(1);
                                    sqLiteManager.UpdateVisit(manageVisited);

                                }


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
            if(wrongPassword){
                Toast.makeText(this, "Login Failed, Please Try Again", Toast.LENGTH_SHORT).show();
            }
        }

    }

    public void ForgotPassword(View view) {
        Intent NewPassword = new Intent(this, ForgotPassword.class);
        finish();
        startActivity(NewPassword);
    }
}