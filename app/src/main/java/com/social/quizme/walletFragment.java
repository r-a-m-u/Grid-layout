package com.social.quizme;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.social.quizme.databinding.FragmentWalletBinding;


public class  walletFragment extends Fragment {

    public walletFragment() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    FragmentWalletBinding binding;
    FirebaseFirestore database;
    User user;


    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding=FragmentWalletBinding.inflate(inflater,container,false);
        database= FirebaseFirestore.getInstance();
        database.collection("users").document(FirebaseAuth.getInstance().getUid()).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                 user=documentSnapshot.toObject(User.class);
                binding.currentCoins.setText(String.valueOf(user.getCoins()));

               }
        });


              binding.sendRequest.setOnClickListener(new View.OnClickListener() {
                  @Override
                  public void onClick(View v) {
                      User user = new User();
                      if (user.getCoins() >= 5000) {
                          String uid = FirebaseAuth.getInstance().getUid();
                          String paypal = binding.emailbox.getText().toString();
                          WithdrawRequest request = new WithdrawRequest(uid, paypal, user.getName());
                          database.collection("withdraws")
                                  .document(uid)
                                  .set(request).addOnSuccessListener(new OnSuccessListener<Void>() {
                              @Override
                              public void onSuccess(Void aVoid) {
                                  Toast.makeText(getContext(), "Request send sucessfully", Toast.LENGTH_SHORT).show();
                              }
                          });

                      } else {
                          Toast.makeText(getContext(), "you need more coins to get withdraw", Toast.LENGTH_SHORT).show();
                      }
                  }
              });


              return binding.getRoot();


    }
}