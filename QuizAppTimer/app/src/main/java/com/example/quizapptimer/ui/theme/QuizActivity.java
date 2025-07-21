package com.example.quizapptimer.ui.theme;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.quizapptimer.R;

public class QuizActivity extends AppCompatActivity {

    TextView questionTextView, timerTextView;
    Button option1, option2, option3, option4;

    CountDownTimer countDownTimer;
    int currentQuestionIndex = 0;
    int score = 0;
    int timeLeft = 10; // seconds


    String[] questions;
    String[][] options;
    int[] correctAnswers = {2, 3, 2, 2, 3};

    @Override
    protected void onCreate(Bundle savedInstanceState){

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        questionTextView = findViewById(R.id.questionText);
        timerTextView = findViewById(R.id.timerText);
        option1 = findViewById(R.id.option1);
        option2 = findViewById(R.id.option2);
        option3 = findViewById(R.id.option3);
        option4 = findViewById(R.id.option4);

        // Initialize questions and options here (safe to use getString now)
        questions = new String[] {
                getString(R.string.question_1),
                getString(R.string.question_2),
                getString(R.string.question_3),
                getString(R.string.question_4),
                getString(R.string.question_5)
        };

        options = new String[][]{
                {
                        getString(R.string.q1_op1),
                        getString(R.string.q1_op2),
                        getString(R.string.q1_op3),
                        getString(R.string.q1_op4)
                },
                {
                        getString(R.string.q2_op1),
                        getString(R.string.q2_op2),
                        getString(R.string.q2_op3),
                        getString(R.string.q2_op4)
                },
                {
                        getString(R.string.q3_op1),
                        getString(R.string.q3_op2),
                        getString(R.string.q3_op3),
                        getString(R.string.q3_op4)
                },
                {
                        getString(R.string.q4_op1),
                        getString(R.string.q4_op2),
                        getString(R.string.q4_op3),
                        getString(R.string.q4_op4)
                },
                {
                        getString(R.string.q5_op1),
                        getString(R.string.q5_op2),
                        getString(R.string.q5_op3),
                        getString(R.string.q5_op4)
                }
        };

        loadQuestion();
    }

    void loadQuestion(){
        questionTextView.setText(questions[currentQuestionIndex]);
        option1.setText(options[currentQuestionIndex][0]);
        option2.setText(options[currentQuestionIndex][1]);
        option3.setText(options[currentQuestionIndex][2]);
        option4.setText(options[currentQuestionIndex][3]);

        startTimer();

        option1.setOnClickListener(v -> checkAnswer(0));
        option2.setOnClickListener(v -> checkAnswer(1));
        option3.setOnClickListener(v -> checkAnswer(2));
        option4.setOnClickListener(v -> checkAnswer(3));
    }

    void startTimer(){
        timerTextView.setText("Time left: 10s");
        countDownTimer = new CountDownTimer(10000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                timerTextView.setText("Time left:" + millisUntilFinished/1000 + " s");
            }

            @Override
            public void onFinish() {
                Toast.makeText(QuizActivity.this, "Time's up!", Toast.LENGTH_SHORT).show();
                nextQuestion();
            }
        }.start();
    }

    void checkAnswer(int selectedOption){
        countDownTimer.cancel();

        if (selectedOption == correctAnswers[currentQuestionIndex]){
            score++;
            Toast.makeText(this, "Correct!", Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(this, "Incorrect!", Toast.LENGTH_SHORT).show();
        }

        nextQuestion();
    }

    void nextQuestion(){
        currentQuestionIndex++;

        if (currentQuestionIndex < questions.length) {
            loadQuestion();
        } else {
            // Quiz finished, go to ResultActivity
            Intent intent = new Intent(QuizActivity.this, ResultActivity.class);
            intent.putExtra("score", score);
            intent.putExtra("total", questions.length);
            startActivity(intent);
            finish();
        }

    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (countDownTimer != null) {
            countDownTimer.cancel();
        }
    }
}
