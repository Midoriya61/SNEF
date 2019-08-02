package com.tinlm.snef.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.tinlm.snef.R;
import com.tinlm.snef.utilities.CustomerUtilities;

public class RegisterActivity extends AppCompatActivity {

    private TextView notiRegister;
    private EditText txtFirstName, txtLastName, txtRegisterUsername, txtRegisterPassword, txtConfirmPassword;
    private CardView btnCreateAccount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        init();
        btnCreateAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickToCreateAccount();
            }
        });
    }

    private void init() {
        notiRegister = findViewById(R.id.notiRegister);
        txtFirstName = findViewById(R.id.txtFirstName);
        txtLastName = findViewById(R.id.txtLastName);
        txtRegisterUsername = findViewById(R.id.txtRegisterUsername);
        txtRegisterPassword = findViewById(R.id.txtRegisterPassword);
        btnCreateAccount = findViewById(R.id.btnCreateAccount);
        txtConfirmPassword = findViewById(R.id.txtConfirmPassword);
    }

    private void clickToCreateAccount() {
        boolean checked = true;
        if(TextUtils.isEmpty(txtFirstName.getText())){
            txtFirstName.setError("Bạn cần nhập tên");
            checked =false;
        }
        String lastName = txtLastName.getText().toString();
        if(TextUtils.isEmpty(txtLastName.getText())){
            txtLastName.setError("Bạn cần nhập họ");
            checked =false;
        }
        if(TextUtils.isEmpty(txtRegisterUsername.getText())){
            txtRegisterUsername.setError("Bạn cần nhập tên người dùng");
            checked =false;
        }
        if(TextUtils.isEmpty(txtRegisterPassword.getText())){
            txtRegisterPassword.setError("Bạn cần nhập mật khẩu");
            checked =false;
        }
        if(TextUtils.isEmpty(txtConfirmPassword.getText())){
            txtConfirmPassword.setError("Bạn cần nhập lại mật khẩu");
            checked =false;
        }
//        if( checked ){
//            CustomerUtilities utilities = new CustomerUtilities();
//            String username = txtUsername.getText().toString();
//            String password = txtPassword.getText().toString();
//            String firstName = txtFirstName.getText().toString();
//            String lastName = txtLastName.getText().toString();
//            boolean resultAddUser = utilities.addUser(username, password, fullname);
//            if( resultAddUser ) {
//                notiRegister.setTextColor(getResources().getColor(R.color.colorPrimary));
//                notiRegister.setText("Account is created successfully");
//            } else {
//                notiRegister.setTextColor(getResources().getColor(R.color.colorDanger));
//                notiRegister.setText("Username is existed");
//            }
//        } else {
//            notiRegister.setTextColor(getResources().getColor(R.color.colorDanger));
//            notiRegister.setText("Please confirm all information");
//        }
        if( checked ){
            if( !txtRegisterPassword.getText().toString().equals(txtConfirmPassword.getText().toString()) ) {
                txtConfirmPassword.setError("Mật khẩu không trùng nhau");
            } else {
                CustomerUtilities customerUtilities = new CustomerUtilities();
                boolean result = customerUtilities.register(txtRegisterUsername.getText().toString(), txtRegisterPassword.getText().toString(),
                        txtFirstName.getText().toString(), txtLastName.getText().toString());
                if( result == true ) {

                } else {

                }
            }
        } else {
            notiRegister.setTextColor(getResources().getColor(R.color.colorDanger));
            notiRegister.setText("Bạn cần điền đầy đủ thông tin");
        }
    }

    public void clickToBackSignIn(View view) {
    }
}
