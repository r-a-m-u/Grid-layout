package com.social.quizme;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.social.quizme.databinding.FragmentProfileBinding;
import com.social.quizme.model.profileModel;

import java.net.URL;
import java.util.ArrayList;
import java.util.Objects;


public class profileFragment extends Fragment {


    private Object profileModel;

    public profileFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    FragmentProfileBinding binding;

    FirebaseAuth auth;
    FirebaseFirestore database;
    FirebaseStorage storage;
    StorageReference storageReference;

    private  static  final int PICK_IMAGE=1;
    UploadTask UploadTask;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentProfileBinding.inflate(inflater, container, false);
        database = FirebaseFirestore.getInstance();
        storage = FirebaseStorage.getInstance();





        binding.addd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_GET_CONTENT);
                intent.setType("image/");
                startActivityForResult(intent, -33);

            }
        });


        // Inflate the layout for this fragment
        return binding.getRoot();
    }

        @Override
        public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
            super.onActivityResult(requestCode, resultCode, data);
            if (requestCode == -33) {
                if (data.getData() != null) {
                    Uri uri = data.getData();
                    binding.profileImage.setImageURI(uri);
                    final StorageReference reference = storage.getReference().child("cover_photo").child(FirebaseAuth.getInstance().getUid());
                    //data ku store kariba
                    reference.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            Toast.makeText(getContext(), "Cover photo saved", Toast.LENGTH_SHORT).show();
                            reference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    reference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                        @Override
                                        public void onSuccess(Uri uri) {
                                            FirebaseFirestore.getInstance().collection("users").document(FirebaseAuth.getInstance().getUid()).set(uri.toString());

                                        }
                                    });

                                }
                            });


                        }
                    });

                }


            }
        }

}
