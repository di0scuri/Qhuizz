package com.example.Qhuizz;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.app.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class QuizActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String LOG_TAG = QuizActivity.class.getSimpleName();

    private TextView totalQuestionsTextView;
    private TextView questionTextView;
    private Button choiceA, choiceB, choiceC, choiceD;
    private Button button_submit;

    private List<Integer> allQuestionIndices = new ArrayList<>();

    private List<String> playerAnswer = new ArrayList<>();

    private List<String> playerAccuracy = new ArrayList<>();

    int score = 0;
    private int totalQuestion = QuestionsAndAnswers.question.length;
    private int currentQuestionIndex = 0;
    private String selectedAnswer = "";
    private List<Integer> usedQuestionIndices = new ArrayList<>();

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);

        // Save important data to the bundle
        savedInstanceState.putInt("score", score);
        savedInstanceState.putInt("currentQuestionIndex", currentQuestionIndex);
        savedInstanceState.putString("selectedAnswer", selectedAnswer);
        savedInstanceState.putIntegerArrayList("usedQuestionIndices", new ArrayList<>(usedQuestionIndices));
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        // Restore the saved data from the bundle
        score = savedInstanceState.getInt("score");
        currentQuestionIndex = savedInstanceState.getInt("currentQuestionIndex");
        selectedAnswer = savedInstanceState.getString("selectedAnswer");
        usedQuestionIndices = savedInstanceState.getIntegerArrayList("usedQuestionIndices");

        // Update the UI based on the restored data
        totalQuestionsTextView.setText("Total questions: " + totalQuestion);
        loadSavedQuestion();
    }

    // Modify this method to load the saved question
    public void loadSavedQuestion() {
        if (usedQuestionIndices.size() == totalQuestion) {
            endQuiz();
            return;
        }

        questionTextView.setText(QuestionsAndAnswers.question[currentQuestionIndex]);
        choiceA.setText(QuestionsAndAnswers.choices[currentQuestionIndex][0]);
        choiceB.setText(QuestionsAndAnswers.choices[currentQuestionIndex][1]);
        choiceC.setText(QuestionsAndAnswers.choices[currentQuestionIndex][2]);
        choiceD.setText(QuestionsAndAnswers.choices[currentQuestionIndex][3]);

        // Highlight the previously selected answer
        if (!selectedAnswer.isEmpty()) {
            switch (selectedAnswer) {
                case "A":
                    choiceA.setBackgroundColor(Color.MAGENTA);
                    break;
                case "B":
                    choiceB.setBackgroundColor(Color.MAGENTA);
                    break;
                case "C":
                    choiceC.setBackgroundColor(Color.MAGENTA);
                    break;
                case "D":
                    choiceD.setBackgroundColor(Color.MAGENTA);
                    break;
            }
        }

        // Enable or disable the submit button based on whether an answer is selected
        button_submit.setEnabled(!selectedAnswer.isEmpty());
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        totalQuestionsTextView = findViewById(R.id.total_questions);
        questionTextView = findViewById(R.id.question);
        choiceA = findViewById(R.id.choice_A);
        choiceB = findViewById(R.id.choice_B);
        choiceC = findViewById(R.id.choice_C);
        choiceD = findViewById(R.id.choice_D);
        button_submit = findViewById(R.id.submit_button);

        choiceA.setOnClickListener(this);
        choiceB.setOnClickListener(this);
        choiceC.setOnClickListener(this);
        choiceD.setOnClickListener(this);
        button_submit.setOnClickListener(this);

        button_submit.setEnabled(false);

        totalQuestionsTextView.setText("Question #1");
        loadNewRandomQuestion();
    }


    @Override
    public void onClick(View view) {

        choiceA.setBackgroundColor(Color.DKGRAY);
        choiceB.setBackgroundColor(Color.DKGRAY);
        choiceC.setBackgroundColor(Color.DKGRAY);
        choiceD.setBackgroundColor(Color.DKGRAY);


        Button clickedButton = (Button) view;
        if (clickedButton.getId() == R.id.submit_button) {
            if (selectedAnswer.equals(QuestionsAndAnswers.correctAnswers[currentQuestionIndex])) {
                score++;
                playerAccuracy.add("true");
            }else{
                playerAccuracy.add("false");
            }
            playerAnswer.add(selectedAnswer);
            currentQuestionIndex++;
            loadNewRandomQuestion();
            totalQuestionsTextView.setText("Question #"+usedQuestionIndices.size());

            Log.d(LOG_TAG, String.valueOf(playerAccuracy));
            Log.d(LOG_TAG, String.valueOf(playerAnswer));


        }else{
            selectedAnswer = clickedButton.getText().toString();
            clickedButton.setBackgroundColor(Color.MAGENTA);
            button_submit.setEnabled(true);
        }

    }

    public AlertDialog endQuiz() {
        AlertDialog dialog = savePrompt();
        dialog.show();
        return dialog;
    }
    public void loadNewRandomQuestion() {
        if (usedQuestionIndices.size() == totalQuestion) {
            endQuiz();
            return;
        }

        int randomIndex;
        do {
            randomIndex = new Random().nextInt(totalQuestion);
        } while (usedQuestionIndices.contains(randomIndex));

        usedQuestionIndices.add(randomIndex);
        currentQuestionIndex = randomIndex;

        allQuestionIndices.add(currentQuestionIndex);

        questionTextView.setText(QuestionsAndAnswers.question[currentQuestionIndex]);
        choiceA.setText(QuestionsAndAnswers.choices[currentQuestionIndex][0]);
        choiceB.setText(QuestionsAndAnswers.choices[currentQuestionIndex][1]);
        choiceC.setText(QuestionsAndAnswers.choices[currentQuestionIndex][2]);
        choiceD.setText(QuestionsAndAnswers.choices[currentQuestionIndex][3]);
        button_submit.setEnabled(false);
    }

    AlertDialog savePrompt() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Would you to see all the correct answers?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int which) {
                Intent intent = new Intent(QuizActivity.this, showAnswers.class);
                intent.putIntegerArrayListExtra("questionSequence", (ArrayList<Integer>) usedQuestionIndices);
                intent.putStringArrayListExtra("playerAnswer", (ArrayList<String>) playerAnswer);
                intent.putStringArrayListExtra("playerAccuracy", (ArrayList<String>) playerAccuracy);
                intent.putExtra("score", score);
                startActivity(intent);

            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int which) {
                Intent intent = new Intent(QuizActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        return builder.create();
    }
}