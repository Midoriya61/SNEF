package com.tinlm.snef.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.tinlm.snef.R;
import com.tinlm.snef.utilities.CustomerUtilities;

public class RegisterActivity extends AppCompatActivity {

    TextView notiRegister;
    EditText txtFirstName, txtLastName, txtUsername, txtPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        init();
    }

    private void init() {
        notiRegister = findViewById(R.id.notiRegister);
        txtFirstName = findViewById(R.id.txtFirstName);
        txtLastName = findViewById(R.id.txtLastName);
        txtUsername = findViewById(R.id.txtUsername);
        txtPassword = findViewById(R.id.txtPassword);
    }

    public void clickToCreateAccount(View view) {
        boolean checked = true;
        if(TextUtils.isEmpty(txtFirstName.getText())){
            txtFirstName.setError("Name is required");
            checked =false;
        }
        if(TextUtils.isEmpty(txtLastName.getText())){
            txtLastName.setError("Name is required");
            checked =false;
        }
        if(TextUtils.isEmpty(txtUsername.getText())){
            txtUsername.setError("Username is required");
            checked =false;
        }
        if(TextUtils.isEmpty(txtPassword.getText())){
            txtPassword.setError("Password is required");
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
    }

    public void clickToBackSignIn(View view) {
    }
}
