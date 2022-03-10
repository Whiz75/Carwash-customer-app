package com.example.findcarwashapp.dialogs;

import android.annotation.SuppressLint;
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
import com.google.android.material.textview.MaterialTextView;

import java.util.Objects;

public class RatingDialogFragment extends DialogFragment {

    private MaterialButton rate_btn;
    private ImageView rating_close_dialog;
    Context context;

    public RatingDialogFragment() {
        // Required empty public constructor
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
        ViewGroup viewGroup = (ViewGroup) inflater.inflate(R.layout.fragment_rating_dialog, container, false);
        inti(viewGroup);
        rateDialog(viewGroup);

        return viewGroup;
    }

    private void inti(ViewGroup view) {
        rating_close_dialog = view.findViewById(R.id.rating_dialog_cancel);
        rate_btn = view.findViewById(R.id.rate_btn);
    }

    private void rateDialog(ViewGroup view) {
        context = view.getContext();

        rate_btn.setOnClickListener(v -> {

            Toast toast = new Toast(context);
            toast.setDuration(Toast.LENGTH_LONG);

            @SuppressLint("InflateParams")
            View view1 = LayoutInflater.from(getContext()).inflate(R.layout.success_toast_style,null);
            MaterialTextView text = view1.findViewById(R.id.message);
            text.setText(R.string.toast_text);
            toast.setView(view1);
        });

        rating_close_dialog.setOnClickListener(v -> dismiss());
    }
}