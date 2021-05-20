package com.example.findcarwashapp.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.findcarwashapp.R;
import com.example.findcarwashapp.interfaces.FragmentClickInterface;
import com.google.android.material.button.MaterialButton;


public class SignUpFragment extends Fragment {

    MaterialButton btn_backToLogin, btn_sign_up_user;

    public SignUpFragment() {
        // Required empty public constructor
    }

    private FragmentClickInterface clickInterface;

    public SignUpFragment(FragmentClickInterface clickInterface) {
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
        ViewGroup viewGroup = (ViewGroup) inflater.inflate(R.layout.fragment_sign_up, container, false);

        //call methods here
        init(viewGroup);
        GoToSignIn(viewGroup);

        return viewGroup;
    }

    private void init(ViewGroup view) {
        btn_backToLogin = view.findViewById(R.id.btn_have_account);
    }

    private void GoToSignIn(ViewGroup view)
    {
        btn_backToLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                clickInterface.BtnLoginClick();
            }
        });
    }
}