package com.example.findcarwashapp.dialogs;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.DialogFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.findcarwashapp.R;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;

import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Objects;


public class ProfileDialogFragment extends DialogFragment {

    private Context context;
    private MaterialToolbar tool_bar;
    private TextInputLayout username_input_layout, email_input_layout;

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
        getUserDetails(view);

        return view;
    }

    private void init(ViewGroup view)
    {
        tool_bar = view.findViewById(R.id.menu_toolbar);
        //close_dialog_img = view.findViewById(R.id.ProfileImgClose);
        username_input_layout = view.findViewById(R.id.profile_username);
    }

    private void dialogControl(ViewGroup view)
    {
        context = view.getContext();
        tool_bar.setNavigationIcon(R.drawable.ic_close);

        tool_bar.setOnClickListener(v -> dismiss());
    }

    private void getUserDetails(ViewGroup view)
    {
        context = view.getContext();

        FirebaseFirestore
                .getInstance()
                .collection("Users")
                .document(Objects.requireNonNull(FirebaseAuth.getInstance().getUid()))
                .addSnapshotListener((value, error) -> {
                    if (value != null)
                    {
                        String name = Objects.requireNonNull(value.get("name")).toString();
                        String email = Objects.requireNonNull(value.get("email")).toString();

                        Objects.requireNonNull(username_input_layout.getEditText()).setText(name);
                        //Objects.requireNonNull(email_input_layout.getEditText()).setText(email);
                    }else
                    {
                        Toast.makeText(getContext(), "User info doesn't exist", Toast.LENGTH_LONG).show();
                    }
                });
    }

}