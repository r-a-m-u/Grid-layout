package com.social.quizme;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Instrumentation;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.social.quizme.databinding.ActivityResultActivitiBinding;

public class ResultActiviti extends AppCompatActivity {
    ActivityResultActivitiBinding binding;
    int POINTS=10;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityResultActivitiBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        int correctAnswer=getIntent().getIntExtra("correct",0);
        int totalQuestion=getIntent().getIntExtra("total",0);



        //points bi khibara achi gota answer ku 10 point
        long points=correctAnswer*POINTS;
        binding.score.setText(String.format("%d/%d",correctAnswer,totalQuestion));
        binding.earnCoins.setText(String.valueOf(points));

        FirebaseFirestore database=FirebaseFirestore.getInstance();

        database.collection("users")
                //tara user id niba
                //jia bi login haithib atara unique id
                .document(FirebaseAuth.getInstance().getUid())
                //coins ku increment kariba
                .update("coins", FieldValue.increment(points));

        binding.restartBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(ResultActiviti.this,quizActivity.class);
                startActivity(intent);
            }
        });

        binding.shareBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Intent.ACTION_SEND);
                intent.setType("text/plain");
                intent.putExtra(Intent.EXTRA_SUBJECT,"CLICK OUT OF THE COOL APPLICATION");
                intent.putExtra(Intent.EXTRA_TEXT,"your application link");
                //different option show
                startActivity(Intent.createChooser(intent,"Share via"));


            }
        });
    }
}