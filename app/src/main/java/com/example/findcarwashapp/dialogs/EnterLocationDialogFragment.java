package com.example.findcarwashapp.dialogs;

import android.content.Context;
import android.os.Bundle;

import androidx.appcompat.widget.AppCompatImageView;
import androidx.fragment.app.DialogFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.findcarwashapp.R;
import com.google.android.material.button.MaterialButton;

import java.util.Objects;

public class EnterLocationDialogFragment extends DialogFragment { ;

    private MaterialButton find_location_btn;
    private AppCompatImageView close_dialog_iv;

    Context context;

    public EnterLocationDialogFragment() {
        // Required empty public constructor
    }

    String location;

    public EnterLocationDialogFragment(String location) {
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
        ViewGroup viewGroup = (ViewGroup) inflater.inflate(R.layout.fragment_enter_location_dialog, container, false);
        //call methods here
        init(viewGroup);
        findLocation(viewGroup);

        return viewGroup;
    }

    private void init(ViewGroup viewGroup) {
        find_location_btn = viewGroup.findViewById(R.id.find_location_btn);
        close_dialog_iv = viewGroup.findViewById(R.id.dialog_cancel_iv);
    }

    private void findLocation(ViewGroup view)
    {
        context = view.getContext();

        find_location_btn.setOnClickListener(v -> {

            Toast.makeText(context.getApplicationContext(),"Find location clicked",Toast.LENGTH_LONG).show();
            dismiss();
        });

        close_dialog_iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }
}