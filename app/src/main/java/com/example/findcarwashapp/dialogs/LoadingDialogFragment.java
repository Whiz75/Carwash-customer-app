package com.example.findcarwashapp.dialogs;

import android.os.Bundle;

import androidx.fragment.app.DialogFragment;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.findcarwashapp.R;
import com.google.android.material.textview.MaterialTextView;

import java.util.Objects;


public class LoadingDialogFragment extends DialogFragment {

    public LoadingDialogFragment() {
        // Required empty public constructor
    }

    private String caption;

    public LoadingDialogFragment(String caption) {
        this.caption = caption;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onStart() {
        super.onStart();

        //set dialog width and height
        Objects.requireNonNull(getDialog())
                .getWindow()
                .setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        //set dialog gravity
        getDialog()
                .getWindow()
                .setGravity(Gravity.BOTTOM);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        ViewGroup viewGroup = (ViewGroup) inflater.inflate(R.layout.fragment_loading_dialog, container, false);
        init(viewGroup);

        return viewGroup;
    }

    private void init(ViewGroup view)
    {
        MaterialTextView caption_tv = view.findViewById(R.id.dialog_caption);
        caption_tv.setText(caption);
    }
}