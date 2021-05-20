package com.example.findcarwashapp.fragments;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.findcarwashapp.R;
import com.example.findcarwashapp.activities.MapActivity;
import com.example.findcarwashapp.interfaces.FragmentClickInterface;
import com.google.android.material.button.MaterialButton;


public class LoginFragment extends Fragment {

    MaterialButton btn_sign_up, btn_login;
    Context context;

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
        btn_sign_up = view.findViewById(R.id.btn_sign_up);
        btn_login = view.findViewById(R.id.login_button);
    }

    private void GoToSignUp(ViewGroup view)
    {
        btn_sign_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                context = view.getContext();
                clickInterface.BtnSignupClick();
            }
        });
    }

    private void GoToMainActivity(ViewGroup view)
    {
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                context = view.getContext();
                Toast.makeText(getActivity(), "You clicked this", Toast.LENGTH_LONG).show();

                Intent i = new Intent(getActivity(), MapActivity.class);
                startActivity(i);
                ((Activity) getActivity()).overridePendingTransition(0, 0);
            }
        });
    }


}