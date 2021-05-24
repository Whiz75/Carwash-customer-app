package com.example.findcarwashapp.dialogs;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.DialogFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.findcarwashapp.R;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textview.MaterialTextView;

import java.util.Objects;

public class ConfirmLocationDialogFragment extends DialogFragment {

    private MaterialButton close_btn;
    private ImageView close_dialog;
    private MaterialTextView location_tv;

    Context context;

    public ConfirmLocationDialogFragment() {
        // Required empty public constructor
    }

    String location;
    public ConfirmLocationDialogFragment(String location) {
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
        ViewGroup viewGroup = (ViewGroup) inflater.inflate(R.layout.fragment_confirm_location_dialog, container, false);
        inti(viewGroup);
        CloseDialog(viewGroup);
        setLocation(viewGroup);

        return viewGroup;
    }

    private void inti(ViewGroup view)
    {
        close_dialog = view.findViewById(R.id.confirm_location_dialog_cancel);
        close_btn = view.findViewById(R.id.close_btn);
        location_tv = view.findViewById(R.id.confirmLocationAddress_tv);
    }

    private void CloseDialog(ViewGroup view)
    {
        context = view.getContext();

        close_btn.setOnClickListener(v -> {

            ChooseDialogFragment fragment = new ChooseDialogFragment();
            fragment.show(getChildFragmentManager().beginTransaction()
                    .setCustomAnimations(R.anim.left_in, R.anim.left_out), "ADD MENU");
        });

        close_dialog.setOnClickListener(v -> dismiss());
    }

    private void setLocation(ViewGroup view)
    {
        context = view.getContext();
        location_tv.setText(location);
    }
}