package com.example.Qhuizz;

import static android.graphics.Color.BLACK;
import static android.graphics.Color.GREEN;
import static android.graphics.Color.RED;
import static android.graphics.Color.WHITE;
import static android.graphics.Color.green;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.app.R;

import java.util.ArrayList;

public class showAnswers extends AppCompatActivity {

    private static final String LOG_TAG = showAnswers.class.getSimpleName();

    int questionNumber = 1;

    private LinearLayout questionContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ArrayList<Integer> questionSequence = getIntent().getIntegerArrayListExtra("questionSequence");
        ArrayList<String> playerAnswer = getIntent().getStringArrayListExtra("playerAnswer");
        int score = getIntent().getIntExtra("score", 0);



        // Create a root LinearLayout for the entire layout
        LinearLayout rootView = new LinearLayout(this);
        rootView.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT));
        rootView.setOrientation(LinearLayout.VERTICAL);
        rootView.setBackgroundResource(R.drawable.background);

        // Create a header TextView
        TextView header = new TextView(this);
        LinearLayout.LayoutParams headerLayoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        headerLayoutParams.setMargins(20,230, 20, 20);
        header.setLayoutParams(headerLayoutParams);
        header.setTypeface(null, Typeface.BOLD);
        header.setGravity(Gravity.CENTER);
        header.setTextSize(30);
        if (score > 11) {
            header.setText("Congratulations");
            header.setTextColor(GREEN);
        } else {
            header.setText("Better Luck Next Time");
            header.setTextColor(RED);
        }
        rootView.addView(header);

        TextView secondary = new TextView(this);
        secondary.setLayoutParams(new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        ));
        secondary.setGravity(Gravity.CENTER);
        secondary.setTextSize(20);
        secondary.setText("Your score is: " + score);
        rootView.addView(secondary);

        // Create a ScrollView to hold the questions and answers
        ScrollView scrollView = new ScrollView(this);
        scrollView.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT, 1.0f));

        // Create a LinearLayout for questions and answers
        questionContainer = new LinearLayout(this);
        questionContainer.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT));
        questionContainer.setOrientation(LinearLayout.VERTICAL);

        // Add questions and answer options programmatically
        for (int i = 0; i < QuestionsAndAnswers.question.length; i++) {
            int index = questionSequence.get(i);
            addQuestionWithChoices(index, playerAnswer.get(i), questionNumber); // Pass the question number
            questionNumber++;
        }

        scrollView.addView(questionContainer);
        rootView.addView(scrollView);

        Button submitButton = new Button(this);
        submitButton.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT));
        submitButton.setText("Continue");

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (score > 11){
                Intent intent = new Intent(showAnswers.this, PrizeActivity.class);
                startActivity(intent);
                }
                else
                {
                    Intent intent = new Intent(showAnswers.this, MainActivity.class);
                    startActivity(intent);
                }
            }
        });

        rootView.addView(submitButton);

        setContentView(rootView);
        }


    private void addQuestionWithChoices(int questionIndex, String playerAnswer, int enumerator) {
        // Create a TextView for the enumerator
        TextView enumeratorText = new TextView(this);
        LinearLayout.LayoutParams enumeratorTextParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        enumeratorTextParams.setMargins(100, 0, 0, 0); // Adjust margin as needed
        enumeratorText.setLayoutParams(enumeratorTextParams);
        enumeratorText.setText(enumerator + ". "); // Add the enumerator
        enumeratorText.setTextSize(25);
        enumeratorText.setTextColor(WHITE);
        questionContainer.addView(enumeratorText);

        // Create a TextView for the question
        TextView questionText = new TextView(this);
        LinearLayout.LayoutParams questionTextParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        questionTextParams.setMargins(100, 0, 0, 0); // Adjust margin as needed
        questionText.setLayoutParams(questionTextParams);
        questionText.setText(QuestionsAndAnswers.question[questionIndex]);
        questionText.setTextSize(25);
        questionText.setTextColor(WHITE);
        questionContainer.addView(questionText);


        RadioGroup radioGroup = new RadioGroup(this);
        radioGroup.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT));
        radioGroup.setOrientation(LinearLayout.VERTICAL);

        // Add radio buttons for answer choices
        // Add radio buttons for answer choices
        for (int i = 0; i < QuestionsAndAnswers.choices[questionIndex].length; i++) {
            RadioButton radioButton = new RadioButton(this);
            RadioGroup.LayoutParams radioButtonParams = new RadioGroup.LayoutParams(
                    RadioGroup.LayoutParams.WRAP_CONTENT,
                    RadioGroup.LayoutParams.WRAP_CONTENT
            );

            // Set a 100dp margin on the left side
            radioButtonParams.setMargins(150, 0, 0, 0);
            radioButton.setLayoutParams(radioButtonParams);
            radioButton.setText(QuestionsAndAnswers.choices[questionIndex][i]);
            radioButton.setTextSize(18);

            radioButton.setEnabled(false);

            // Check if it's the correct answer and highlight it in green
            if (QuestionsAndAnswers.correctAnswers[questionIndex].equals(
                    QuestionsAndAnswers.choices[questionIndex][i])) {
                radioButton.setTextColor(GREEN);
            }// Check if the player's answer matches the correct answer and highlight it in red
            else if (playerAnswer != null && playerAnswer.equals(QuestionsAndAnswers.choices[questionIndex][i])) {
                if (!QuestionsAndAnswers.correctAnswers[questionIndex].equals(playerAnswer)) {
                    radioButton.setTextColor(Color.RED);
                }
            }else{
                radioButton.setTextColor(BLACK);
            }
            radioGroup.addView(radioButton);
            radioButton.setEnabled(false);
        }
        questionContainer.addView(radioGroup);
    }
}
