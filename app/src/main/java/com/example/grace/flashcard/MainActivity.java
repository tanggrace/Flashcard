package com.example.grace.flashcard;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;
import com.plattysoft.leonids.ParticleSystem;

import java.util.List;
import java.util.Random;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    TextView flashcard_question;
    TextView flashcard_answer;
    TextView answer1, answer2, answer3;
    ImageView toggle_choices_visibility, add_button, next_button, edit_button, deleteBtn;
    boolean isShowingAnswers;
    FlashcardDatabase flashcardDatabase;
    List<Flashcard> allFlashcards;
    int currentCardDisplayedIndex = 0;
    Flashcard cardToEdit;
    CountDownTimer countDownTimer;

    private static final int ADD_CARD_REQUEST_CODE = 100;
    private static final int EDIT_CARD_REQUEST_CODE = 101;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        flashcardDatabase = new FlashcardDatabase(getApplicationContext());
        allFlashcards = flashcardDatabase.getAllCards();

        flashcard_question = (TextView) findViewById(R.id.flashcard_question);
        flashcard_answer = (TextView) findViewById(R.id.flashcard_answer);
        answer1 = (TextView) findViewById(R.id.answer1);
        answer2 = (TextView) findViewById(R.id.answer2);
        answer3 = (TextView) findViewById(R.id.answer3);
        toggle_choices_visibility = (ImageView) findViewById(R.id.toggle_choices_visibility);
        add_button = (ImageView) findViewById(R.id.add_button);
        edit_button = (ImageView) findViewById(R.id.edit_button);
        next_button = (ImageView) findViewById(R.id.next_button);
        deleteBtn = (ImageView) findViewById(R.id.deleteBtn);

        if (allFlashcards != null && allFlashcards.size() > 0) {
            flashcard_question.setText(allFlashcards.get(0).getQuestion());
            flashcard_answer.setText(allFlashcards.get(0).getAnswer());
            answer1.setText(allFlashcards.get(0).getWrongAnswer1());
            answer2.setText(allFlashcards.get(0).getWrongAnswer2());
            answer3.setText(allFlashcards.get(0).getAnswer());
        } else {
            flashcardDatabase.insertCard(new Flashcard(flashcard_question.getText().toString(), flashcard_answer.getText().toString(), answer1.getText().toString(), answer2.getText().toString()));
            allFlashcards = flashcardDatabase.getAllCards();
        }

        flashcard_question.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                // get the center for the clipping circle
//                int cx = flashcard_question.getWidth() / 2;
//                int cy = flashcard_answer.getHeight() / 2;
//
//                // get the final radius for the clipping circle
//                float finalRadius = (float) Math.hypot(cx, cy);
//
//                // create the animator for this view (the start radius is zero)
//                Animator anim = ViewAnimationUtils.createCircularReveal(flashcard_answer, cx, cy, 0f, finalRadius);
//
//                // hide the question and show the answer to prepare for playing the animation!
//                flashcard_question.setVisibility(View.INVISIBLE);
//                flashcard_answer.setVisibility(View.VISIBLE);
//
//                anim.setDuration(1000);
//                anim.start();

                flashcard_question.setCameraDistance(25000);
                flashcard_answer.setCameraDistance(25000);
                flashcard_question.animate()
                        .rotationY(90)
                        .setDuration(200)
                        .withEndAction(
                                new Runnable() {
                                    @Override
                                    public void run() {
                                        flashcard_question.setVisibility(View.INVISIBLE);
                                        flashcard_answer.setVisibility(View.VISIBLE);
                                        // second quarter turn
                                        flashcard_answer.setRotationY(-90);
                                        flashcard_answer.animate()
                                                .rotationY(0)
                                                .setDuration(200)
                                                .start();
                                    }
                                }
                        ).start();
            }
        });

        // Optional
        flashcard_answer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                flashcard_answer.animate()
                        .rotationY(90)
                        .setDuration(200)
                        .withEndAction(
                                new Runnable() {
                                    @Override
                                    public void run() {
                                        flashcard_answer.setVisibility(View.INVISIBLE);
                                        flashcard_question.setVisibility(View.VISIBLE);
                                        // second quarter turn
                                        flashcard_question.setRotationY(-90);
                                        flashcard_question.animate()
                                                .rotationY(0)
                                                .setDuration(200)
                                                .start();
                                    }
                                }
                        ).start();
            }
        });

        answer1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                answer1.setBackgroundColor(getResources().getColor(R.color.salmon, null));
                answer2.setBackgroundColor(getResources().getColor(R.color.lightOrange, null));
                answer3.setBackgroundColor(getResources().getColor(R.color.limeGreen, null));
            }
        });

        answer2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                answer1.setBackgroundColor(getResources().getColor(R.color.lightOrange, null));
                answer2.setBackgroundColor(getResources().getColor(R.color.salmon, null));
                answer3.setBackgroundColor(getResources().getColor(R.color.limeGreen, null));
            }
        });

        answer3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                answer1.setBackgroundColor(getResources().getColor(R.color.lightOrange, null));
                answer2.setBackgroundColor(getResources().getColor(R.color.lightOrange, null));
                answer3.setBackgroundColor(getResources().getColor(R.color.limeGreen, null));
                new ParticleSystem(MainActivity.this, 100, R.drawable.confetti, 3000)
                        .setSpeedRange(0.2f, 0.5f)
                        .oneShot(findViewById(R.id.answer3), 100);
            }
        });

        isShowingAnswers = true;
        toggle_choices_visibility.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isShowingAnswers) {
                    toggle_choices_visibility.setImageResource(R.drawable.show_icon);
                    answer1.setVisibility(View.INVISIBLE);
                    answer2.setVisibility(View.INVISIBLE);
                    answer3.setVisibility(View.INVISIBLE);
                    isShowingAnswers = false;
                } else {
                    toggle_choices_visibility.setImageResource(R.drawable.hide_icon);
                    answer1.setVisibility(View.VISIBLE);
                    answer2.setVisibility(View.VISIBLE);
                    answer3.setVisibility(View.VISIBLE);
                    isShowingAnswers = true;
                }
            }
        });

        // Week 2
        add_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddCardActivity.class);
                MainActivity.this.startActivityForResult(intent, ADD_CARD_REQUEST_CODE);
                overridePendingTransition(R.anim.right_in, R.anim.left_out);
            }
        });

        edit_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String question = flashcard_question.getText().toString();
                String answer = flashcard_answer.getText().toString();
                String wrongAnswer1 = answer1.getText().toString();
                String wrongAnswer2 = answer2.getText().toString();

                int i = 0;
                while (!allFlashcards.get(i).getQuestion().equals(question)) {
                    i++;
                }
                cardToEdit = allFlashcards.get(i);

                Intent myIntent = new Intent(MainActivity.this, AddCardActivity.class);
                myIntent.putExtra("question", question);
                myIntent.putExtra("answer", answer);
                myIntent.putExtra("wrongAnswer1", wrongAnswer1);
                myIntent.putExtra("wrongAnswer2", wrongAnswer2);
                MainActivity.this.startActivityForResult(myIntent, EDIT_CARD_REQUEST_CODE);
            }
        });

        next_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // advance our pointer index so we can show the next card
                currentCardDisplayedIndex++;

                // make sure we don't get an IndexOutOfBoundsError if we are viewing the last indexed card in our list
                if (currentCardDisplayedIndex > allFlashcards.size() - 1) {
                    currentCardDisplayedIndex = 0;
                }

                final Animation leftOutAnim = AnimationUtils.loadAnimation(v.getContext(), R.anim.left_out);
                final Animation rightInAnim = AnimationUtils.loadAnimation(v.getContext(), R.anim.right_in);

                leftOutAnim.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {
                        // this method is called when the animation first starts
                        flashcard_question.setVisibility(View.VISIBLE);
                        flashcard_answer.setVisibility(View.INVISIBLE);
                        ((TextView) findViewById(R.id.timer)).setVisibility(View.INVISIBLE);
                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        // this method is called when the animation is finished playing
                        flashcard_question.startAnimation(rightInAnim);

                        int randomCardIndex = getRandomNumber(0, allFlashcards.size() - 1);

                        // set the question and answer TextViews with data from the database
                        flashcard_question.setText(allFlashcards.get(randomCardIndex).getQuestion());
                        flashcard_answer.setText(allFlashcards.get(randomCardIndex).getAnswer());
                        answer1.setText(allFlashcards.get(randomCardIndex).getWrongAnswer1());
                        answer2.setText(allFlashcards.get(randomCardIndex).getWrongAnswer2());
                        answer3.setText(allFlashcards.get(randomCardIndex).getAnswer());
                        answer1.setBackgroundColor(getResources().getColor(R.color.lightOrange, null));
                        answer2.setBackgroundColor(getResources().getColor(R.color.lightOrange, null));
                        answer3.setBackgroundColor(getResources().getColor(R.color.lightOrange, null));
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {
                        // we don't need to worry about this method
                    }
                });
                rightInAnim.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {
                        // this method is called when the animation first starts
                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        // this method is called when the animation is finished playing
                        ((TextView) findViewById(R.id.timer)).setVisibility(View.VISIBLE);
                        startTimer();
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {
                        // we don't need to worry about this method
                    }
                });

                flashcard_question.startAnimation(leftOutAnim);
            }
        });

        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                flashcardDatabase.deleteCard(flashcard_question.getText().toString());
                allFlashcards = flashcardDatabase.getAllCards();
                if (allFlashcards.size() == 0) {
                    flashcard_question.setText("Add a card!");
                    flashcard_answer.setText("Add a card!");
                    answer1.setVisibility(View.INVISIBLE);
                    answer2.setVisibility(View.INVISIBLE);
                    answer3.setVisibility(View.INVISIBLE);
                    isShowingAnswers = false;
                    toggle_choices_visibility.setVisibility(View.INVISIBLE);
                    next_button.setVisibility(View.INVISIBLE);
                    edit_button.setVisibility(View.INVISIBLE);
                    deleteBtn.setVisibility(View.INVISIBLE);
                    ((TextView) findViewById(R.id.timer)).setVisibility(View.INVISIBLE);
                } else {
                    currentCardDisplayedIndex--;
                    if (currentCardDisplayedIndex < 0) {
                        currentCardDisplayedIndex = allFlashcards.size() - 1;
                    }
                    flashcard_question.setText(allFlashcards.get(currentCardDisplayedIndex).getQuestion());
                    flashcard_answer.setText(allFlashcards.get(currentCardDisplayedIndex).getAnswer());
                    answer1.setText(allFlashcards.get(currentCardDisplayedIndex).getWrongAnswer1());
                    answer2.setText(allFlashcards.get(currentCardDisplayedIndex).getWrongAnswer2());
                    answer3.setText(allFlashcards.get(currentCardDisplayedIndex).getAnswer());
                    flashcard_question.setVisibility(View.VISIBLE);
                    flashcard_answer.setVisibility(View.INVISIBLE);
                    answer1.setBackgroundColor(getResources().getColor(R.color.lightOrange, null));
                    answer2.setBackgroundColor(getResources().getColor(R.color.lightOrange, null));
                    answer3.setBackgroundColor(getResources().getColor(R.color.lightOrange, null));
                    startTimer();
                }
            }
        });

        countDownTimer = new CountDownTimer(16000, 1000) {
            public void onTick(long millisUntilFinished) {
                ((TextView) findViewById(R.id.timer)).setText("" + millisUntilFinished / 1000);
            }

            public void onFinish() {
            }
        };

        startTimer();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ADD_CARD_REQUEST_CODE && resultCode == RESULT_OK) {
            // grab the data passed from AddCardActivity
            String question = data.getExtras().getString("question"); // 'question' needs to match the key we used when we put the string in the Intent
            String answer = data.getExtras().getString("answer");
            String wrongAnswer1 = data.getExtras().getString("wrongAnswer1");
            String wrongAnswer2 = data.getExtras().getString("wrongAnswer2");
            // set the TextViews to show the new question and answer
            flashcard_question.setText(question);
            flashcard_answer.setText(answer);
            answer1.setText(wrongAnswer1);
            answer2.setText(wrongAnswer2);
            answer3.setText(answer);
            answer1.setBackgroundColor(getResources().getColor(R.color.lightOrange, null));
            answer2.setBackgroundColor(getResources().getColor(R.color.lightOrange, null));
            answer3.setBackgroundColor(getResources().getColor(R.color.lightOrange, null));

            toggle_choices_visibility.setVisibility(View.VISIBLE);
            next_button.setVisibility(View.VISIBLE);
            edit_button.setVisibility(View.VISIBLE);
            deleteBtn.setVisibility(View.VISIBLE);
            ((TextView) findViewById(R.id.timer)).setVisibility(View.VISIBLE);

            Snackbar.make(flashcard_question,
                    "Card successfully created",
                    Snackbar.LENGTH_SHORT)
                    .show();

            flashcardDatabase.insertCard(new Flashcard(question, answer, wrongAnswer1, wrongAnswer2));
            allFlashcards = flashcardDatabase.getAllCards();

        } else if (requestCode == EDIT_CARD_REQUEST_CODE && resultCode == RESULT_OK) {
            // grab the data passed from AddCardActivity
            String question = data.getExtras().getString("question"); // 'question' needs to match the key we used when we put the string in the Intent
            String answer = data.getExtras().getString("answer");
            String wrongAnswer1 = data.getExtras().getString("wrongAnswer1");
            String wrongAnswer2 = data.getExtras().getString("wrongAnswer2");
            // set the TextViews to show the EDITED question and answer
            flashcard_question.setText(question);
            flashcard_answer.setText(answer);
            answer1.setText(wrongAnswer1);
            answer2.setText(wrongAnswer2);
            answer3.setText(answer);
            answer1.setBackgroundColor(getResources().getColor(R.color.lightOrange, null));
            answer2.setBackgroundColor(getResources().getColor(R.color.lightOrange, null));
            answer3.setBackgroundColor(getResources().getColor(R.color.lightOrange, null));

            cardToEdit.setQuestion(question);
            cardToEdit.setAnswer(answer);
            cardToEdit.setWrongAnswer1(wrongAnswer1);
            cardToEdit.setWrongAnswer2(wrongAnswer2);

            flashcardDatabase.updateCard(cardToEdit);
        }
        startTimer();
    }

    // returns a random number between minNumber and maxNumber, inclusive.
    // for example, if i called getRandomNumber(1, 3), there's an equal chance of it returning either 1, 2, or 3.
    public int getRandomNumber(int minNumber, int maxNumber) {
        Random rand = new Random();
        return rand.nextInt((maxNumber - minNumber) + 1) + minNumber;
    }

    private void startTimer() {
        countDownTimer.cancel();
        countDownTimer.start();
    }
}
