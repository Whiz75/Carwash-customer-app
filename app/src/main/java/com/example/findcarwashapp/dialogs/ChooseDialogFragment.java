package com.example.findcarwashapp.dialogs;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import androidx.fragment.app.DialogFragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.CompoundButton;
import android.widget.Toast;
import com.example.findcarwashapp.R;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.checkbox.MaterialCheckBox;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipDrawable;
import com.google.android.material.chip.ChipGroup;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ChooseDialogFragment extends DialogFragment {

    private Context context;
    private MaterialToolbar tool_bar;

    private MaterialCheckBox item_full, item_exterior, item_interior,item_extra;
    private MaterialButton upload_menu_btn;

    public ChooseDialogFragment() {
        // Required empty public constructor
    }

    List<String> stringList = new ArrayList<String>();
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
        //chooseItems(view, stringList);
        uploadMenu(view);

        return view;
    }

    private void init(ViewGroup view)
    {
        tool_bar = view.findViewById(R.id.menu_toolbar);

        item_full = view.findViewById(R.id.item_full_wash);
        item_exterior = view.findViewById(R.id.item_exterior_wash);
        item_interior = view.findViewById(R.id.item_interior_wash);
        item_extra = view.findViewById(R.id.item_extra_wash);

        upload_menu_btn = view.findViewById(R.id.upload_menu_btn);
    }

    private void setUpToolBAr(ViewGroup view)
    {
        context = view.getContext();

        tool_bar.setNavigationIcon(R.drawable.ic_close);
        tool_bar.setOnClickListener(v -> dismiss());
    }

    private void chooseItems(ViewGroup view, List<String> stringList)
    {
        isCheckedBox(view,item_full, item_exterior, item_interior, item_extra);
    }

    private void isCheckedBox(ViewGroup view,MaterialCheckBox box, MaterialCheckBox box1, MaterialCheckBox box2, MaterialCheckBox box3)
    {
        context = view.getContext();
        ChipGroup chipGroup = view.findViewById(R.id.chip_group);

                box.setOnCheckedChangeListener((buttonView, isChecked) -> {
                    if (isChecked)
                    {
                        box1.setEnabled(true);
                        box2.setEnabled(true);
                        box3.setEnabled(true);

                        //Chip chip = new Chip(view.getContext());
                        Chip chip = view.findViewById(R.id.full_wash_chip);
                        chip.setCheckable(true);
                        //create chip drawable
                        ChipDrawable drawable = ChipDrawable.createFromAttributes(view.getContext(),
                                null, 0, R.style.Widget_MaterialComponents_Chip_Choice);
                        //set chip drawable
                        chip.setChipDrawable(drawable);
                        //chip.setText(input);

                        chip.setOnCloseIconClickListener(v -> chipGroup.removeView(chip));
                        chipGroup.addView(chip);

                    }else
                    {
                        box1.setEnabled(false);
                        box2.setEnabled(false);
                        box3.setEnabled(false);
                    }
                });

        box1.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked)
            {
                stringList.add(box1.getText().toString());
            }
        });

        box2.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked)
            {
                stringList.add(box2.getText().toString());
            }
        });

        box3.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked)
            {
                stringList.add(box3.getText().toString());
            }
        });

        Toast.makeText(getContext(), stringList.toString(), Toast.LENGTH_SHORT).show();
    }

    private void uploadMenu(ViewGroup view)
    {
        upload_menu_btn.setOnClickListener(v -> {

            context = view.getContext();

            @SuppressLint("ShowToast") Snackbar snackbar = Snackbar.make(v,"", Snackbar.LENGTH_LONG);
            View view1 = getLayoutInflater().inflate(R.layout.success_snack_bar, null);

            snackbar.getView().setBackgroundColor(Color.TRANSPARENT);
            Snackbar.SnackbarLayout snackBarView = (Snackbar.SnackbarLayout) snackbar.getView();
            snackBarView.setPadding(0, 0, 0, 50);

            snackBarView.addView(view1, 0);
            snackbar.show();

            chooseItems(view, stringList);
            //dismiss();
        });
    }
}