package com.example.findcarwashapp.dialogs;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.fragment.app.DialogFragment;

import com.cazaea.sweetalert.SweetAlertDialog;
import com.example.findcarwashapp.R;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.chip.Chip;
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

            SweetAlertDialog pDialog = new SweetAlertDialog(getContext(), SweetAlertDialog.PROGRESS_TYPE);
            pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
            pDialog.setTitleText("Loading");
            pDialog.setCancelable(false);

            Bundle bundle = this.getArguments();
            Toast toast = new Toast(context);
            toast.setDuration(Toast.LENGTH_LONG);

            if (bundle != null) {
                String longitude = bundle.getString("lan");
                String latitude = bundle.getString("lat");
                String place = bundle.getString("place");

                View view1 = LayoutInflater.from(getContext()).inflate(R.layout.success_toast_style,null);
                MaterialTextView text = view1.findViewById(R.id.message);
                text.setText(R.string.toast_text);
                toast.setView(view1);
            }else {
                View view2 = LayoutInflater.from(getContext()).inflate(R.layout.unsuccess_toast_style,null);
                MaterialTextView text = view2.findViewById(R.id.message);
                text.setText(R.string.error_toast_text);
                toast.setView(view2);
            }

            new CountDownTimer(5000,1000){
                @Override
                public void onTick(long millisUntilFinished) {
                    //show the loading dialog when timer ticks
                    Toast.makeText(getContext(), String.valueOf(millisUntilFinished/1000), Toast.LENGTH_SHORT).show();
                    pDialog.show();
                }

                @Override
                public void onFinish() {
                    //display the toast when timer stops
                    pDialog.dismissWithAnimation();
                    toast.show();
                    dismiss();
                }
            }.start();
        });

        close_dialog.setOnClickListener(v -> dismiss());
    }

    private void setSelectedItems(ViewGroup view) {
        context = view.getContext();

        chipGroup.removeAllViews();

        for (int i = 0; i <selectionItems.size(); i++) {
            Chip chip = (Chip) LayoutInflater.from(context).inflate(R.layout.chip_item,null,false);
            chip.setText(selectionItems.get(i));
            chipGroup.addView(chip);
        }
    }
}