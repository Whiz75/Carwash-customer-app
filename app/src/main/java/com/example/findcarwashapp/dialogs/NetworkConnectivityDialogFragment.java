package com.example.findcarwashapp.dialogs;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.DialogFragment;

import com.example.findcarwashapp.R;
import com.google.android.material.button.MaterialButton;

import java.util.List;
import java.util.Locale;
import java.util.Objects;

public class NetworkConnectivityDialogFragment extends DialogFragment { ;

    private MaterialButton okay_btn;
    Context context;

    public NetworkConnectivityDialogFragment() {
        // Required empty public constructor
    }

    String location;
    public NetworkConnectivityDialogFragment(String location) {
        this.location = location;
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
                .setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        getDialog()
                .getWindow()
                .setAllowEnterTransitionOverlap(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        ViewGroup viewGroup = (ViewGroup) inflater.inflate(R.layout.fragment_network_connectivity_dialog, container, false);
        //call methods here
        init(viewGroup);
        checkNetwork(viewGroup);

        return viewGroup;
    }

    private void init(ViewGroup viewGroup) {
        okay_btn = viewGroup.findViewById(R.id.okay_btn);
    }

    private void checkNetwork(ViewGroup view) {
        context = view.getContext();

        okay_btn.setOnClickListener(v -> {
            dismiss();
        });
    }
}