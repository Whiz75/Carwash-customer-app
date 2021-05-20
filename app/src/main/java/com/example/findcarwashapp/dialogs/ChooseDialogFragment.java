package com.example.findcarwashapp.dialogs;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;

import androidx.core.view.GravityCompat;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.findcarwashapp.R;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;

import java.util.List;
import java.util.Objects;

public class ChooseDialogFragment extends DialogFragment {

    private Context context;
    private MaterialToolbar tool_bar;
    private MaterialButton upload_menu_btn;

    public ChooseDialogFragment() {
        // Required empty public constructor
    }

    List<String> stringList;
    public ChooseDialogFragment(List<String> stringList) {
        this.stringList = stringList;
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
        ViewGroup view = (ViewGroup) inflater.inflate(R.layout.fragment_choose_dialog, container, false);

        context = view.getContext();
        //call methods here
        init(view);
        setUpToolBAr(view);
        uploadMenu(view);

        return view;
    }

    private void init(ViewGroup view) {

        tool_bar = view.findViewById(R.id.menu_toolbar);
        upload_menu_btn = view.findViewById(R.id.upload_menu_btn);
    }

    private void setUpToolBAr(ViewGroup view)
    {
        tool_bar.setNavigationIcon(R.drawable.ic_close);
        tool_bar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dismiss();
            }
        });
    }

    private void uploadMenu(ViewGroup view)
    {
        upload_menu_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                @SuppressLint("ShowToast") Snackbar snackbar = Snackbar.make(v,"", Snackbar.LENGTH_LONG);
                View view = getLayoutInflater().inflate(R.layout.success_snack_bar, null);

                snackbar.getView().setBackgroundColor(Color.TRANSPARENT);
                Snackbar.SnackbarLayout snackBarView = (Snackbar.SnackbarLayout) snackbar.getView();
                snackBarView.setPadding(0, 0, 0, 0);

                snackBarView.addView(view, 0);
                snackbar.show();

                Toast.makeText(getActivity(), "it can here", Toast.LENGTH_LONG).show();
                dismiss();
            }
        });
    }
}