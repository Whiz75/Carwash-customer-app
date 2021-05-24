package com.example.findcarwashapp.dialogs;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;

import androidx.appcompat.widget.AppCompatImageView;
import androidx.fragment.app.DialogFragment;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.findcarwashapp.R;
import com.example.findcarwashapp.activities.MapActivity;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputLayout;

import java.util.List;
import java.util.Locale;
import java.util.Objects;

public class EnterLocationDialogFragment extends DialogFragment { ;

    private MaterialButton find_location_btn;
    private AppCompatImageView close_dialog_iv;
    private TextInputLayout search_location;

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
        search_location = viewGroup.findViewById(R.id.search_location_til);
    }

    private void findLocation(ViewGroup view)
    {
        context = view.getContext();

        String location_txt = search_location.getEditText().toString().trim();

        find_location_btn.setOnClickListener(v -> {

            if (TextUtils.isEmpty(location_txt))
            {
                Toast.makeText(context.getApplicationContext(),"Please enter location",Toast.LENGTH_LONG).show();
            }else
            {
                geoLocate(location_txt);
                dismiss();
            }

        });

        close_dialog_iv.setOnClickListener(v -> dismiss());
    }

    private void geoLocate(String search_txt)
    {
        try
        {
            Geocoder geocoder = new Geocoder(getActivity(), Locale.getDefault());
            List<Address> addresses = geocoder.getFromLocationName(search_txt, 1);
            final String address = addresses.get(0).getAddressLine(0);

            Toast.makeText(getActivity(), address, Toast.LENGTH_SHORT).show();
            //Objects.requireNonNull(search_location.getEditText()).setText(location);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}