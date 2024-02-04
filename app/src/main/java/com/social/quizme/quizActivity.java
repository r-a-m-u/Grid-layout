package com.social.quizme;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.social.quizme.databinding.ActivityQuizBinding;

import java.util.ArrayList;
import java.util.Random;

public class quizActivity extends AppCompatActivity {
    ActivityQuizBinding binding;
    ArrayList<Question> questions;
    int index = 0;
    Question question;
    CountDownTimer timer;
    FirebaseFirestore database;
    int correctAnswer;
    private AdView mAdView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityQuizBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        questions = new ArrayList<>();


        database = FirebaseFirestore.getInstance();
        final String catId = getIntent().getStringExtra("catId");
        final Random random = new Random();
        //random number generator
        int rand = random.nextInt(30);
        //joda iamgview click kariba tara id base question jaka asiba

        database.collection("categories")
                .document(catId)
                .collection("questions")
                //hamara query index jadi grated equal to achi rand sa
                .whereGreaterThanOrEqualTo("index", rand)
                .orderBy("index")
                .limit(21).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                if (queryDocumentSnapshots.getDocuments().size() < 22) {
                    database.collection("categories")
                            .document(catId)
                            .collection("questions")
                            //hamara query index jadi grated equal to achi rand sa
                            .whereLessThanOrEqualTo("index", rand)
                            .orderBy("index")
                            .limit(20).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                        @Override
                        public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                            if (queryDocumentSnapshots.getDocuments().size() < 20) {
                                for (DocumentSnapshot snapshot : queryDocumentSnapshots) {
                                    Question question = snapshot.toObject(Question.class);
                                    questions.add(question);
                                }
                                setNextQuestion();

                            }

                        }
                    });

                } else {
                    //jatiki bi question asila sita aku document snapshot ra rakhidaba  sita aku question bhitara object ku show kari question rupa anidalu
                    for (DocumentSnapshot snapshot : queryDocumentSnapshots) {
                        //object ra convert kari  questons add  karidala
                        Question question = snapshot.toObject(Question.class);
                        questions.add(question);
                    }
                    setNextQuestion();
                }

            }
        });


        resetTimer();

//        setNextQustion();

    }

    //time ko kasa start kara
    void resetTimer() {
        timer = new CountDownTimer(30000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                //jatiki second rahigala sita asiba
                binding.timmer.setText(String.valueOf(millisUntilFinished / 1000));

            }

            @Override
            public void onFinish() {

            }
        };

    }


    //wrong kahichu hala wrright answer kn sita haba a function ra

    void showAnswer() {
        if (question.getAnswer().equals(binding.option1.getText().toString()))
            binding.option1.setBackground(getResources().getDrawable(R.drawable.option_right));
        else if (question.getAnswer().equals(binding.option2.getText().toString()))
            binding.option2.setBackground(getResources().getDrawable(R.drawable.option_right));
        else if (question.getAnswer().equals(binding.option3.getText().toString()))
            binding.option3.setBackground(getResources().getDrawable(R.drawable.option_right));
        else if (question.getAnswer().equals(binding.option4.getText().toString()))
            binding.option4.setBackground(getResources().getDrawable(R.drawable.option_right));


    }

    void setNextQuestion() {
        if (timer != null) {
            timer.cancel();


            timer.start();
            if (index < questions.size()) {
                binding.questionCounter.setText(String.format("%d/%d", (index + 1), questions.size()));
                question = questions.get(index);
                binding.question.setText(question.getQuestion());
                binding.option1.setText(question.getOption1());
                binding.option2.setText(question.getOption2());
                binding.option3.setText(question.getOption3());
                binding.option4.setText(question.getOption4());
            }

        }
        binding.quit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(quizActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }

    //option correct option ku green aniba pampi new function baneba
    void checkAnswer(TextView textView) {
        String selectedAnswer = textView.getText().toString();
        if (selectedAnswer.equals(question.getAnswer())) {

            correctAnswer++;
            textView.setBackground(getResources().getDrawable(R.drawable.option_right));

        } else {
            showAnswer();
            textView.setBackground(getResources().getDrawable(R.drawable.qution_wrong));
        }

    }

    void reset() {
        binding.option1.setBackground(getResources().getDrawable(R.drawable.option_unselected));
        binding.option2.setBackground(getResources().getDrawable(R.drawable.option_unselected));
        binding.option3.setBackground(getResources().getDrawable(R.drawable.option_unselected));
        binding.option4.setBackground(getResources().getDrawable(R.drawable.option_unselected));
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.option1:
            case R.id.option2:
            case R.id.option3:
            case R.id.option4:
                if (timer != null) ;
                timer.cancel();
                TextView selected = (TextView) view;
                checkAnswer(selected);

                break;
            case R.id.nextbtn:
                reset();
                if (index <= questions.size()) {
                    index++;
                    setNextQuestion();
                } else {
                    Intent intent = new Intent(quizActivity.this, ResultActiviti.class);
                    intent.putExtra("correct", correctAnswer);
                    intent.putExtra("total", questions.size());

                    startActivity(intent);
                    Toast.makeText(this, "Quiz Finished", Toast.LENGTH_SHORT).show();
                }
                break;

        }


    }






}