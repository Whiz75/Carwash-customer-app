package com.example.findcarwashapp.dialogs;

import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.DialogFragment;

import com.example.findcarwashapp.R;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.button.MaterialButton;

import java.util.Objects;

public class ResetPasswordDialogFragment extends DialogFragment {

    private Context context;
    private MaterialToolbar tool_bar;
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
                        ViewGroup.LayoutParams.MATCH_PARENT);

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

        return view;
    }

    private void init(ViewGroup view) {
        tool_bar = view.findViewById(R.id.password_reset_toolbar);
        password_reset_btn = view.findViewById(R.id.reset_password_btn);
    }

    private void setUpToolBAr(ViewGroup view) {
        context = view.getContext();

        tool_bar.setNavigationIcon(R.drawable.ic_close);
        tool_bar.setOnClickListener(v -> dismiss());
    }
}