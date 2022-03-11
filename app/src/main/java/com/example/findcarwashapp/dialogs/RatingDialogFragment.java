package com.example.findcarwashapp.dialogs;

import static android.widget.Toast.LENGTH_LONG;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.widget.AppCompatRatingBar;
import androidx.fragment.app.DialogFragment;
import com.example.findcarwashapp.R;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textview.MaterialTextView;

import java.io.CharArrayWriter;
import java.util.Objects;

public class RatingDialogFragment extends DialogFragment {

    private MaterialButton rate_btn;
    private ImageView rating_close_dialog;
    private AppCompatRatingBar ratingBar;
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

    //initialize dialog components
    private void inti(ViewGroup view) {
        rating_close_dialog = view.findViewById(R.id.rating_dialog_cancel);
        rate_btn = view.findViewById(R.id.rate_btn);
        ratingBar = view.findViewById(R.id.rating_bar);
    }

    //dialog functionality
    private void rateDialog(ViewGroup view) {
        context = view.getContext();

        rate_btn.setOnClickListener(v -> {

            ratingBar.setFocusable(false);
            float ratings = ratingBar.getRating();

            try {
                Toast.makeText(context,"Thank you for rating us!!!" , LENGTH_LONG).show();
                getDialog().dismiss();
            } catch (Resources.NotFoundException e) {
                e.printStackTrace();
            }
        });

        rating_close_dialog.setOnClickListener(v -> dismiss());
    }
}