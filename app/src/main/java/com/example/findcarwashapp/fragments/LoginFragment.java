package com.example.findcarwashapp.fragments;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.findcarwashapp.R;
import com.example.findcarwashapp.activities.MapActivity;
import com.example.findcarwashapp.interfaces.FragmentClickInterface;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Objects;


public class LoginFragment extends Fragment {

    private TextInputEditText email_txt, password_txt;
    MaterialButton btn_sign_up, btn_login;
    Context context;

    private FirebaseAuth auth;

    public LoginFragment() {
        // Required empty public constructor
    }

    private FragmentClickInterface clickInterface;

    public LoginFragment(FragmentClickInterface clickInterface) {
        this.clickInterface = clickInterface;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        ViewGroup viewGroup = (ViewGroup) inflater.inflate(R.layout.fragment_login, container, false);
        context = viewGroup.getContext();

        //call methods here
        init(viewGroup);
        GoToSignUp(viewGroup);
        GoToMainActivity(viewGroup);

        return viewGroup;
    }

    private void init(ViewGroup view)
    {
        email_txt = view.findViewById(R.id.input_email);
        password_txt = view.findViewById(R.id.input_password);

        btn_sign_up = view.findViewById(R.id.btn_sign_up);
        btn_login = view.findViewById(R.id.login_button);
    }

    private void GoToSignUp(ViewGroup view)
    {
        btn_sign_up.setOnClickListener(v -> {
            context = view.getContext();
            clickInterface.BtnSignupClick();
        });
    }

    private void GoToMainActivity(ViewGroup view)
    {
        btn_login.setOnClickListener(v -> {

            auth = FirebaseAuth.getInstance();
            context = view.getContext();
            Toast.makeText(getActivity(), "You clicked this", Toast.LENGTH_LONG).show();

            String email = Objects.requireNonNull(email_txt.getText()).toString().trim();
            String password = Objects.requireNonNull(password_txt.getText()).toString().trim();

            if (TextUtils.isEmpty(email))
            {
                email_txt.setError( "Email can not be empty");
            }else if (TextUtils.isEmpty(password))
            {
                password_txt.setError( "Password can not be empty");
            }else
            {
                auth.signInWithEmailAndPassword(email, password)
                        .addOnSuccessListener(authResult -> {

                            Intent i = new Intent(getActivity(), MapActivity.class);
                            startActivity(i);
                            ((Activity) Objects.requireNonNull(getActivity())).overridePendingTransition(0, 0);

                        }).addOnFailureListener(e -> {
                            Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                        });
            }
        });
    }


}