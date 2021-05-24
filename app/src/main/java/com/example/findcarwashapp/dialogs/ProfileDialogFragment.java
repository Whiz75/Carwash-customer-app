package com.example.findcarwashapp.dialogs;

import android.content.Context;
import android.os.Bundle;

import androidx.appcompat.widget.AppCompatImageView;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.findcarwashapp.R;

import java.util.Objects;


public class ProfileDialogFragment extends DialogFragment {

    private Context context;
    private AppCompatImageView close_dialog_img;

    public ProfileDialogFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onStart() {
        super.onStart();

        //set the dialog width and height
        Objects.requireNonNull(getDialog())
                .getWindow()
                .setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        ViewGroup view = (ViewGroup)inflater.inflate(R.layout.fragment_profile_dialog, container, false);
        context = view.getContext();
        //call methods here
        init(view);
        dialogControl(view);

        return view;
    }

    private void init(ViewGroup view)
    {
        close_dialog_img = view.findViewById(R.id.ProfileImgClose);
    }

    private void dialogControl(ViewGroup view)
    {
        context = view.getContext();
        close_dialog_img.setOnClickListener(v -> dismiss());
    }

}