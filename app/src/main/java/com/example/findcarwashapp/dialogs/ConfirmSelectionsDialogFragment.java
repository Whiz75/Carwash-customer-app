package com.example.findcarwashapp.dialogs;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.fragment.app.DialogFragment;

import com.example.findcarwashapp.R;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipDrawable;
import com.google.android.material.chip.ChipGroup;
import com.google.android.material.textview.MaterialTextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ConfirmSelectionsDialogFragment extends DialogFragment {

    private MaterialButton close_btn;
    private ImageView close_dialog;
    private ChipGroup chipGroup;
    Context context;

    public ConfirmSelectionsDialogFragment() {
        // Required empty public constructor
    }

    List<String> selectionItems = new ArrayList<>();
    public ConfirmSelectionsDialogFragment(List<String> selectionItems) {
        this.selectionItems = selectionItems;
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
        ViewGroup viewGroup = (ViewGroup) inflater.inflate(R.layout.fragment_confirm_selections_dialog, container, false);
        inti(viewGroup);
        CloseDialog(viewGroup);
        setSelectedItems(viewGroup);

        return viewGroup;
    }

    private void inti(ViewGroup view) {
        close_dialog = view.findViewById(R.id.confirm_location_dialog_cancel);
        close_btn = view.findViewById(R.id.close_btn);
        chipGroup = view.findViewById(R.id.confirm_selections_cg);
    }

    private void CloseDialog(ViewGroup view) {
        context = view.getContext();

        close_btn.setOnClickListener(v -> {

            ChooseDialogFragment fragment = new ChooseDialogFragment();
            fragment.show(getChildFragmentManager().beginTransaction()
                    .setCustomAnimations(R.anim.left_in, R.anim.left_out), "ADD MENU");
        });

        close_dialog.setOnClickListener(v -> dismiss());
    }

    private void setSelectedItems(ViewGroup view) {
        context = view.getContext();

        Chip chip = new Chip(Objects.requireNonNull(getContext()));
                ChipDrawable drawable = ChipDrawable.createFromAttributes(getContext(),
                        null, 0, R.style.Widget_MaterialComponents_Chip_Entry);

        chip.setChipDrawable(drawable);

        for (int i = 0; i <selectionItems.size(); i++) {
            chip.setText(selectionItems.get(i));
        }
        chipGroup.addView(chip);

        Toast.makeText(getContext(), selectionItems.toString(), Toast.LENGTH_SHORT).show();
    }
}