package com.example.grace.flashcard;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class AddCardActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_card);

        String question = getIntent().getStringExtra("question");
        String answer = getIntent().getStringExtra("answer");
        String wrongAnswer = getIntent().getStringExtra("wrongAnswer");
        String wrongAnswer2 = getIntent().getStringExtra("wrongAnswer2");
        ((EditText) findViewById(R.id.questionTextField)).setText(question);
        ((EditText) findViewById(R.id.answerTextField)).setText(answer);
        ((EditText) findViewById(R.id.wrongAnswerTextField)).setText(wrongAnswer);
        ((EditText) findViewById(R.id.wrongAnswer2TextField)).setText(wrongAnswer2);

        findViewById(R.id.cancel_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        findViewById(R.id.save_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String question = ((EditText) findViewById(R.id.questionTextField)).getText().toString();
                String answer = ((EditText) findViewById(R.id.answerTextField)).getText().toString();
                String wrongAnswer = ((EditText) findViewById(R.id.wrongAnswerTextField)).getText().toString();
                String wrongAnswer2 = ((EditText) findViewById(R.id.wrongAnswer2TextField)).getText().toString();

                if (question.isEmpty() || answer.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Must enter both Question and Answer!", Toast.LENGTH_SHORT).show();
                } else {
                    Intent data = new Intent(); // create a new Intent, this is where we will put our data
                    data.putExtra("question", question); // puts one string into the Intent, with the key as 'question'
                    data.putExtra("answer", answer); // puts another string into the Intent, with the key as 'answer'
                    data.putExtra("wrongAnswer", wrongAnswer);
                    data.putExtra("wrongAnswer2", wrongAnswer2);
                    setResult(RESULT_OK, data); // set result code and bundle data for response
                    finish(); // closes this activity and pass data to the original activity that launched this activity
                }
            }
        });
    }
}
