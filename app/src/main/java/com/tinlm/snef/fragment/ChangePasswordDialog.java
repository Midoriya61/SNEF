package com.tinlm.snef.fragment;

import android.app.Dialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.tinlm.snef.R;
import com.tinlm.snef.activity.EditAccountActivity;
import com.tinlm.snef.constain.ConstainApp;
import com.tinlm.snef.utilities.CustomerUtilities;

public class ChangePasswordDialog extends AppCompatDialogFragment {
    private EditText edtOldPassword;
    private EditText edtNewPassword;
    private EditText edtConfirmPassword;
    private Button btnCancel, btnChange;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        final View view = inflater.inflate(R.layout.dialog_editpassword, null);
        builder.setView(view);
        edtOldPassword = view.findViewById(R.id.edtOldPassword);
        edtNewPassword = view.findViewById(R.id.edtNewPassword);
        edtConfirmPassword = view.findViewById(R.id.edtConfirmPassword);
        btnCancel = view.findViewById(R.id.btnCancel);
        btnChange = view.findViewById(R.id.btnChange);
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        btnChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean checkNewPassword = true;
                if (edtConfirmPassword.getText().toString().length() == 0) {
                    checkNewPassword = false;
                    edtOldPassword.setError("Nhập mật khẩu củ");
                }
                if (edtNewPassword.getText().toString().length() == 0) {
                    checkNewPassword = false;
                    edtNewPassword.setError("Nhập mật khẩu mới");
                }
                if (edtConfirmPassword.getText().toString().length() == 0) {
                    checkNewPassword = false;
                    edtConfirmPassword.setError("Xác nhận mật khẩu mới");
                } else if( !edtConfirmPassword.getText().toString().equals(edtNewPassword.getText().toString()) ) {
                    checkNewPassword = false;
                    edtConfirmPassword.setError("Xác nhận mật khẩu mới");
                }
                SharedPreferences sharedPreferences = view.getContext().getSharedPreferences(ConstainApp.login_Prefer, v.getContext().MODE_PRIVATE);
                String passowrd = sharedPreferences.getString(ConstainApp.PASSWORD, "");
                if (!edtOldPassword.getText().toString().equals(passowrd)) {
                    edtOldPassword.setError("Mật khẩu sai");
                    checkNewPassword = false;
                }
                if (checkNewPassword) {
                    CustomerUtilities customerUtilities = new CustomerUtilities();
                    int accountId = sharedPreferences.getInt(ConstainApp.ACCOUNTID, 0);
                    boolean result = customerUtilities.updatePassword(accountId, edtNewPassword.getText().toString());
                    if (result) {
                        Toast.makeText(view.getContext(), "Thay đổi thành công", Toast.LENGTH_SHORT).show();
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString(ConstainApp.PASSWORD , passowrd);
                        editor.apply();
                    }else {
                        Toast.makeText(view.getContext(), "Thay đổi thất bại", Toast.LENGTH_SHORT).show();
                    }
                    dismiss();
                }
            }
        });
        return builder.create();
    }
}
