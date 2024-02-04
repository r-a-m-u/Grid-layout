package com.social.quizme;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.social.quizme.Adapter.LeaderboardAdapter;
import com.social.quizme.databinding.FragmentLeaderboardBinding;

import java.util.ArrayList;


public class leaderboardFragment extends Fragment {


    public leaderboardFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
    FragmentLeaderboardBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding=FragmentLeaderboardBinding.inflate(inflater,container,false);
        FirebaseFirestore database=FirebaseFirestore.getInstance();

        final ArrayList<User> users=new ArrayList<>();
        LeaderboardAdapter adapter=new LeaderboardAdapter(getContext(),users);


        //recyclerview ra add karidaba users ku
        binding.recyclerview.setAdapter(adapter);
        binding.recyclerview.setLayoutManager(new LinearLayoutManager(getContext()));

        database.collection("users")
                //coin ra basee ra ama order karibara achi users ku
                .orderBy("coins", Query.Direction.DESCENDING).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                for (DocumentSnapshot snapshot:queryDocumentSnapshots){
                    User user=snapshot.toObject(User.class);
                    users.add(user);
                }
                adapter.notifyDataSetChanged();

            }
        });
        // Inflate the layout for this fragment
        return binding.getRoot();
    }


                    }