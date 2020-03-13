package com.example.grace.flashcard;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class AddCardActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_card);

        final EditText questionTextField = (EditText) findViewById(R.id.questionTextField);
        final EditText answerTextField = (EditText) findViewById(R.id.answerTextField);
        final EditText wrongAnswer1TextField = (EditText) findViewById(R.id.wrongAnswer1TextField);
        final EditText wrongAnswer2TextField = (EditText) findViewById(R.id.wrongAnswer2TextField);

        String question = getIntent().getStringExtra("question");
        String answer = getIntent().getStringExtra("answer");
        String wrongAnswer1 = getIntent().getStringExtra("wrongAnswer1");
        String wrongAnswer2 = getIntent().getStringExtra("wrongAnswer2");
        questionTextField.setText(question);
        answerTextField.setText(answer);
        wrongAnswer1TextField.setText(wrongAnswer1);
        wrongAnswer2TextField.setText(wrongAnswer2);

        final ImageView cancel_button = (ImageView) findViewById(R.id.cancel_button);
        cancel_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        final ImageView save_button = findViewById(R.id.save_button);
        save_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String question = questionTextField.getText().toString();
                String answer = answerTextField.getText().toString();
                String wrongAnswer1 = wrongAnswer1TextField.getText().toString();
                String wrongAnswer2 = wrongAnswer2TextField.getText().toString();
                if (question.isEmpty() || answer.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Must enter both Question and Answer!", Toast.LENGTH_SHORT).show();
                } else {
                    Intent data = new Intent(); // create a new Intent, this is where we will put our data
                    data.putExtra("question", questionTextField.getText().toString()); // puts one string into the Intent, with the key as 'question'
                    data.putExtra("answer", answerTextField.getText().toString()); // puts another string into the Intent, with the key as 'answer'
                    data.putExtra("wrongAnswer1", wrongAnswer1TextField.getText().toString());
                    data.putExtra("wrongAnswer2", wrongAnswer2TextField.getText().toString());
                    setResult(RESULT_OK, data); // set result code and bundle data for response
                    finish(); // closes this activity and pass data to the original activity that launched this activity
                }
            }
        });
    }
}
