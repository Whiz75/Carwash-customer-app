package com.example.findcarwashapp.dialogs;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.DialogFragment;

import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.cazaea.sweetalert.SweetAlertDialog;
import com.example.findcarwashapp.R;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.chip.Chip;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class ChooseDialogFragment extends DialogFragment {

    private Context context;
    private MaterialToolbar tool_bar;
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

        return view;
    }

    private void init(ViewGroup view) {
        tool_bar = view.findViewById(R.id.menu_toolbar);
        upload_menu_btn = view.findViewById(R.id.upload_menu_btn);
    }

    private void setUpToolBAr(ViewGroup view) {
        context = view.getContext();

        tool_bar.setNavigationIcon(R.drawable.ic_close);
        tool_bar.setOnClickListener(v -> dismiss());
    }

    private void setUpChips(ViewGroup view) {
        context = view.getContext();
        //ChipGroup chipGroup = view.findViewById(R.id.chip_group);

        Chip full_chip = view.findViewById(R.id.full_wash_chip);
        Chip interior_chip = view.findViewById(R.id.interior_chip);
        Chip exterior_chip = view.findViewById(R.id.exterior_chip);
        Chip tyre_chip = view.findViewById(R.id.tyre_chip);

        List<String> optionsStrings = new ArrayList<>();

        exterior_chip.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (buttonView.isChecked()) {
                Toast.makeText(getContext(),exterior_chip.getText().toString(),Toast.LENGTH_LONG).show();
                optionsStrings.add(exterior_chip.getText().toString());
            }else {
                Toast.makeText(getContext(),"unchecked",Toast.LENGTH_LONG).show();
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
                Toast.makeText(getContext(),full_chip.getText().toString(),Toast.LENGTH_LONG).show();
                optionsStrings.add(full_chip.getText().toString());
            }else {
                Toast.makeText(getContext(),"unchecked",Toast.LENGTH_LONG).show();
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
                Toast.makeText(getContext(),interior_chip.getText().toString(),Toast.LENGTH_LONG).show();
                optionsStrings.add(interior_chip.getText().toString());
            }else {
                Toast.makeText(getContext(),"unchecked",Toast.LENGTH_LONG).show();
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
                Toast.makeText(getContext(),tyre_chip.getText().toString(),Toast.LENGTH_LONG).show();
                optionsStrings.add(tyre_chip.getText().toString());
            }else {
                Toast.makeText(getContext(),"unchecked",Toast.LENGTH_LONG).show();
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
                    Toast.makeText(getContext(),menuItems.toString(),Toast.LENGTH_LONG).show();

                    HashMap<String,Object> request = new HashMap<>();
                    request.put("preference",menuItems);


                    final SweetAlertDialog dlg = new SweetAlertDialog(view.getContext(), SweetAlertDialog.PROGRESS_TYPE);
                    dlg.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
                    dlg.setTitleText("Loading");
                    dlg.setCancelable(false);
                    dlg.show();

                    FirebaseFirestore
                            .getInstance()
                            .collection("Requests")
                            .document(Objects.requireNonNull(FirebaseAuth.getInstance().getUid()))
                            .set(request)
                            .addOnSuccessListener(unused -> {

                                dlg.cancel();
                                //dialog to display selected item
                                ConfirmSelectionsDialogFragment fragment = new ConfirmSelectionsDialogFragment(menuItems);
                                fragment.show(getChildFragmentManager().beginTransaction(),"CONFIRM SELECTION");

                                @SuppressLint("ShowToast")
                                Snackbar snackbar = Snackbar.make(v,"", Snackbar.LENGTH_LONG);
                                @SuppressLint("InflateParams")
                                View view1 = getLayoutInflater().inflate(R.layout.success_snack_bar, null);

                                snackbar.getView().setBackgroundColor(Color.TRANSPARENT);
                                Snackbar.SnackbarLayout snackBarView = (Snackbar.SnackbarLayout) snackbar.getView();
                                snackBarView.setPadding(0, 0, 0, 50);

                                snackBarView.addView(view1, 0);
                                snackbar.show();
                            }).addOnFailureListener(e ->
                            Toast.makeText(getContext(),e.getMessage(),Toast.LENGTH_LONG).show());
                }
            }catch (Exception e) {
                Toast.makeText(getContext(),e.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
}