package com.example.findcarwashapp.dialogs;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatSpinner;
import androidx.fragment.app.DialogFragment;

import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.example.findcarwashapp.R;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.chip.Chip;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ChooseDialogFragment extends DialogFragment {

    private Context context;
    private MaterialToolbar tool_bar;
    private AppCompatSpinner car_type_spinner;
    private MaterialButton upload_menu_btn;

    public ChooseDialogFragment() {
        // Required empty public constructor
    }

    List<String> stringList = new ArrayList<>();
    public ChooseDialogFragment(List<String> stringList) {
        this.stringList = stringList;
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
                .setLayout(ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.MATCH_PARENT);

        getDialog()
                .getWindow()
                .setGravity(Gravity.CENTER);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        ViewGroup view = (ViewGroup) inflater.inflate(R.layout.fragment_choose_dialog, container, false);

        context = view.getContext();
        //call methods here
        init(view);
        setUpToolBAr(view);
        setUpChips(view);
        //uploadMenu(view);
        getCar();

        return view;
    }

    private void init(ViewGroup view) {
        tool_bar = view.findViewById(R.id.menu_toolbar);
        car_type_spinner = view.findViewById(R.id.car_type_spinner);
        upload_menu_btn = view.findViewById(R.id.upload_menu_btn);
    }

    private void setUpToolBAr(ViewGroup view) {
        context = view.getContext();

        tool_bar.setNavigationIcon(R.drawable.ic_close);
        tool_bar.setOnClickListener(v -> dismiss());
    }

    private void setUpChips(ViewGroup view) {
        context = view.getContext();

        Chip full_chip = view.findViewById(R.id.full_wash_chip);
        Chip interior_chip = view.findViewById(R.id.interior_chip);
        Chip exterior_chip = view.findViewById(R.id.exterior_chip);
        Chip tyre_chip = view.findViewById(R.id.tyre_chip);

        List<String> optionsStrings = new ArrayList<>();

        exterior_chip.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (buttonView.isChecked()) {
                Toast.makeText(getContext(),exterior_chip.getText().toString(),Toast.LENGTH_SHORT).show();
                optionsStrings.add(exterior_chip.getText().toString());
            }else {
                Toast.makeText(getContext(),"unchecked",Toast.LENGTH_SHORT).show();
                for (int i = 0; i < optionsStrings.size(); i++){
                    String pos = optionsStrings.get(i);

                    if (TextUtils.equals(pos,exterior_chip.getText().toString())){
                        optionsStrings.remove(i);
                    }
                }
            }
        });

        full_chip.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (buttonView.isChecked()) {
                Toast.makeText(getContext(),full_chip.getText().toString(),Toast.LENGTH_SHORT).show();
                optionsStrings.add(full_chip.getText().toString());
            }else {
                Toast.makeText(getContext(),"unchecked",Toast.LENGTH_SHORT).show();
                for (int i = 0; i < optionsStrings.size(); i++){
                    String pos = optionsStrings.get(i);

                    if (TextUtils.equals(pos,full_chip.getText().toString())){
                        optionsStrings.remove(i);
                    }
                }
            }
        });

        interior_chip.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (buttonView.isChecked()) {
                Toast.makeText(getContext(),interior_chip.getText().toString(),Toast.LENGTH_SHORT).show();
                optionsStrings.add(interior_chip.getText().toString());
            }else {
                Toast.makeText(getContext(),"unchecked",Toast.LENGTH_SHORT).show();
                for (int i = 0; i < optionsStrings.size(); i++){
                    String pos = optionsStrings.get(i);

                    if (TextUtils.equals(pos,interior_chip.getText().toString())){
                        optionsStrings.remove(i);
                    }
                }
            }
        });

        tyre_chip.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (buttonView.isChecked()) {
                Toast.makeText(getContext(),tyre_chip.getText().toString(),Toast.LENGTH_SHORT).show();
                optionsStrings.add(tyre_chip.getText().toString());
            }else {
                Toast.makeText(getContext(),"unchecked",Toast.LENGTH_SHORT).show();
                for (int i = 0; i < optionsStrings.size(); i++){
                    String pos = optionsStrings.get(i);

                    if (TextUtils.equals(pos,tyre_chip.getText().toString())){
                        optionsStrings.remove(i);
                    }
                }
            }
        });

        //call upload method
        uploadMenu(view,optionsStrings);
    }

    private void uploadMenu(ViewGroup view, List<String> menuItems) {
        upload_menu_btn.setOnClickListener(v -> {
            context = view.getContext();

            try {
                if (menuItems.size() > 0) {
                    //Toast.makeText(getContext(),menuItems.toString(),Toast.LENGTH_LONG).show();
                    Bundle bundle = this.getArguments();

                    if(bundle != null) {
                        // handle your code here.
                        String lan = bundle.getString("lan");
                        String lat = bundle.getString("lat");
                        String place = bundle.getString("place");

                        Bundle bundle1 = new Bundle();
                        bundle1.putString("lan",lan);
                        bundle1.putString("lat",lat);
                        bundle1.putString("place",place);

                        ConfirmSelectionsDialogFragment fragment = new ConfirmSelectionsDialogFragment(menuItems);
                        fragment.setCancelable(false);
                        fragment.setArguments(bundle1);
                        fragment.show(getChildFragmentManager().beginTransaction(),"CONFIRMATION");
                    }else {
                        Toast toast = new Toast(context);
                        @SuppressLint("InflateParams")
                        View view1 = LayoutInflater.from(context).inflate(R.layout.unsuccess_toast_style,null);
                        toast.setDuration(Toast.LENGTH_LONG);
                        toast.setView(view1);
                        toast.show();
                    }
                }
            }catch (Exception e) {
                Toast.makeText(getContext(),e.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    private void getCar(){
        ArrayList<String> car_type = getCarTypes();

        //Create adapter
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, car_type);
        //Set adapter
        car_type_spinner.setAdapter(adapter);
    }

    private ArrayList<String> getCarTypes(){
        ArrayList<String> car = new ArrayList<>();

        car.add("Bike");
        car.add("Van");
        car.add("Truck");
        car.add("Taxi");
        car.add("Bus");

        return car;
    }
}