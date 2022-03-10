package com.example.findcarwashapp.dialogs;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

import com.example.findcarwashapp.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textview.MaterialTextView;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Objects;

public class ResetPasswordDialogFragment extends DialogFragment {

    private Context context;
    private MaterialToolbar tool_bar;
    private TextInputEditText resetPassword;
    private MaterialButton password_reset_btn;

    public ResetPasswordDialogFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onStart() {
        super.onStart();

        Objects.requireNonNull(getDialog())
                .getWindow()
                .setLayout(ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT);

        getDialog()
                .getWindow()
                .setGravity(Gravity.CENTER);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        ViewGroup view = (ViewGroup) inflater.inflate(R.layout.fragment_reset_password_dialog, container, false);

        context = view.getContext();
        init(view);
        setUpToolBar(view);
        resetPassword(view);

        return view;
    }

    private void init(ViewGroup view) {
        tool_bar = view.findViewById(R.id.password_reset_toolbar);
        resetPassword = view.findViewById(R.id.InputResetPassword);
        password_reset_btn = view.findViewById(R.id.reset_password_btn);
    }

    private void resetPassword(ViewGroup view){

        String password = resetPassword.getText().toString().trim();

        Toast toast = new Toast(context);
        toast.setDuration(Toast.LENGTH_LONG);

        View view1 = LayoutInflater.from(getContext()).inflate(R.layout.success_toast_style, null);
        MaterialTextView text = view1.findViewById(R.id.message);
        text.setText(R.string.toast_text);
        toast.setView(view1);

        password_reset_btn.setOnClickListener(v -> {
            if (password.isEmpty()) {
                toast.show();
                dismiss();
            }
        });
    }

    private void setUpToolBar(ViewGroup view) {
        context = view.getContext();

        tool_bar.setNavigationIcon(R.drawable.ic_close);
        tool_bar.setOnClickListener(v -> dismiss());
    }
}