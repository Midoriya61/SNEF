package com.tinlm.snef.fragment;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.tinlm.snef.R;

public class CustomDialogFragment extends DialogFragment {

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        Dialog dialog = new Dialog(getActivity());
        dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN);
        dialog.setContentView(R.layout.dialog_custom);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        // OK

        dialog.findViewById(R.id.positive_button).setOnClickListener(new View.OnClickListener() {
            @Override            public void onClick(View v) {
                dismiss();
            }
        });
        // Close
        dialog.findViewById(R.id.close_button).setOnClickListener(new View.OnClickListener() {
            @Override            public void onClick(View v) {
                dismiss();
            }

        });
        return dialog;
    }
}