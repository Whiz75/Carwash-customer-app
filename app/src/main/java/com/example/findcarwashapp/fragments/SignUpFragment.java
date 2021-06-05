package com.example.findcarwashapp.fragments;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.cazaea.sweetalert.SweetAlertDialog;
import com.example.findcarwashapp.R;
import com.example.findcarwashapp.activities.MapActivity;
import com.example.findcarwashapp.interfaces.FragmentClickInterface;
import com.example.findcarwashapp.model.User;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Objects;


public class SignUpFragment extends Fragment {

    private MaterialButton btn_backToLogin, btn_sign_up_user;
    private TextInputEditText name_txt, surname_txt, email_txt, password_txt, confirm_pass_txt;

    private FirebaseAuth auth;

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
        SignUpUser(viewGroup);

        return viewGroup;
    }

    private void init(ViewGroup view) {

        name_txt = view.findViewById(R.id.InputName);
        surname_txt = view.findViewById(R.id.InputLastname);
        email_txt = view.findViewById(R.id.InputEmail);
        password_txt = view.findViewById(R.id.InputPassword);
        confirm_pass_txt = view.findViewById(R.id.InputConfirmPassword);

        btn_sign_up_user = view.findViewById(R.id.sign_up_button);
        btn_backToLogin = view.findViewById(R.id.btn_have_account);
    }

    private void GoToSignIn(ViewGroup view)
    {
        btn_backToLogin.setOnClickListener(v -> clickInterface.BtnLoginClick());
    }

    private void SignUpUser(ViewGroup view)
    {
        //initialize firebase auth object
        auth = FirebaseAuth.getInstance();

        btn_sign_up_user.setOnClickListener(v -> {

            final String name = name_txt.getText().toString().trim();
            final String lastName = surname_txt.getText().toString().trim();
            final String email = email_txt.getText().toString().trim();
            String password = password_txt.getText().toString().trim();
            String confirmPassword = confirm_pass_txt.getText().toString().trim();

            try
            {
                if (TextUtils.isEmpty(name))
                {
                    name_txt.setError("Cannot be empty");
                }
                else if (TextUtils.isEmpty(lastName))
                {
                    surname_txt.setError("Cannot be empty");
                }
                else if (TextUtils.isEmpty(email))
                {
                    email_txt.setError("Cannot be empty");
                }
                else if (TextUtils.isEmpty(password))
                {
                    password_txt.setError("Cannot be empty");
                } else if (!(TextUtils.equals(password, confirmPassword)))
                {
                    confirm_pass_txt.setError("Password does not match");
                }else
                {
                    //show dialog
                    final SweetAlertDialog pDialog = new SweetAlertDialog(view.getContext(), SweetAlertDialog.PROGRESS_TYPE);
                    pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
                    pDialog.setTitleText("Loading");
                    pDialog.setCancelable(false);
                    pDialog.show();

                    auth.createUserWithEmailAndPassword(email, password)
                            .addOnSuccessListener(authResult -> {

                                //get current user's id
                                String uid = auth.getUid();

                                //User model instance
                                User user = new User();
                                user.setId(uid);
                                user.setName(name);
                                user.setLastName(lastName);
                                user.setEmail(email);

                                FirebaseFirestore
                                        .getInstance()
                                        .collection("Users")
                                        .document(uid)
                                        .set(user);

                                pDialog.dismissWithAnimation();
                                //upload user's info to database
                                Toast.makeText(view.getContext(), "User registration was successful...", Toast.LENGTH_LONG).show();
                                startActivity(new Intent(getActivity(), MapActivity.class));

                            }).addOnFailureListener(e ->
                            Toast.makeText(view.getContext(), e.getMessage(), Toast.LENGTH_SHORT).show());
                }
            } catch (Exception e)
            {
                e.printStackTrace();
            }
        });
    }
}