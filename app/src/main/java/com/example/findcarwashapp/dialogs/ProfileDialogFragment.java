package com.example.findcarwashapp.dialogs;

import static android.app.Activity.RESULT_OK;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.Toast;

import com.example.findcarwashapp.R;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.StorageReference;

import java.util.Objects;

import de.hdodenhof.circleimageview.CircleImageView;


public class ProfileDialogFragment extends DialogFragment {

    private Context context;
    private MaterialToolbar tool_bar;
    private TextInputLayout username_il, email_il, surname_il;
    private CircleImageView profile_img;
    private FloatingActionButton addImage;

    private StorageReference storageReference;
    private static final int GALLERY_REQUEST = 1;
    private Uri imageUri = null;

    public ProfileDialogFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onStart() {
        super.onStart();

        //set the dialog width and height
        Objects.requireNonNull(getDialog())
                .getWindow()
                .setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        ViewGroup view = (ViewGroup)inflater.inflate(R.layout.fragment_profile_dialog, container, false);
        context = view.getContext();
        //call methods here
        init(view);
        dialogControl(view);
        getUserDetails(view);
        selectImage(view);

        return view;
    }

    private void init(ViewGroup view) {
        tool_bar = view.findViewById(R.id.password_reset_toolbar);
        //close_dialog_img = view.findViewById(R.id.ProfileImgClose);
        username_il = view.findViewById(R.id.profile_username);
        surname_il = view.findViewById(R.id.profile_lastname);
        email_il = view.findViewById(R.id.profile_email);

        profile_img = view.findViewById(R.id.profileImg);
        addImage = view.findViewById(R.id.select_img);
    }

    private void dialogControl(ViewGroup view) {
        context = view.getContext();
        tool_bar.setNavigationIcon(R.drawable.ic_close);

        tool_bar.setOnClickListener(v -> dismiss());
    }

    private void selectImage(ViewGroup view) {

        Context context = view.getContext();

        addImage.setOnClickListener(v -> {
            Intent galleryIntent = new Intent(Intent.ACTION_GET_CONTENT);
            galleryIntent.setType("image/*");
            startActivityForResult(galleryIntent,GALLERY_REQUEST);
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == GALLERY_REQUEST && resultCode == RESULT_OK && data != null) {
            imageUri = data.getData();
            profile_img.setImageURI(imageUri);
            //update user image
            update(imageUri);
        }
    }

    private void getUserDetails(ViewGroup view) {
        context = view.getContext();

        FirebaseFirestore
                .getInstance()
                .collection("Users")
                .document(Objects.requireNonNull(FirebaseAuth.getInstance().getUid()))
                .addSnapshotListener((value, error) -> {
                    if (value != null) {
                        String name = Objects.requireNonNull(value.get("name")).toString();
                        String email = Objects.requireNonNull(value.get("email")).toString();

                        Objects.requireNonNull(username_il.getEditText()).setText(name);
                        Objects.requireNonNull(email_il.getEditText()).setText(email);
                        Objects.requireNonNull(surname_il.getEditText()).setText(value.get("lastName").toString());
                    }else {
                        Toast.makeText(getContext(), "User info doesn't exist", Toast.LENGTH_LONG).show();
                    }
                });
    }

    private void update(Uri uri) {

        try {
            final StorageReference filepath = storageReference
                    .child("Profile_images")
                    .child(FirebaseAuth.getInstance().getUid())
                    .child(System.currentTimeMillis()+"."+getFileExtention(uri));

            if (uri != null) {
                filepath
                        .putFile(uri)
                        .addOnSuccessListener(taskSnapshot -> filepath
                                .getDownloadUrl()
                                .addOnSuccessListener(uri1 -> {
                                    try
                                    {
                                        try {
                                            FirebaseFirestore
                                                    .getInstance()
                                                    .collection("Users")
                                                    .document(FirebaseAuth.getInstance().getUid())
                                                    .update("uri", uri1.toString())
                                                    .addOnSuccessListener(unused ->
                                                            Toast.makeText(getContext(),"image",Toast.LENGTH_LONG).show());

                                        }catch(Exception e)
                                        {
                                            Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                                        }

                                    } catch (Exception e) {
                                        Toast.makeText(getContext(),e.getMessage(),Toast.LENGTH_LONG).show();
                                    }
                                })).addOnFailureListener(e ->
                        Toast.makeText(getContext(),e.getMessage(),Toast.LENGTH_SHORT).show());
            }else {
                Toast.makeText(getContext(),"URL is empty!!!",Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String getFileExtention(Uri mUri) {
        ContentResolver cr = getContext().getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cr.getType(mUri));
    }

}