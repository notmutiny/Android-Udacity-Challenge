package com.example.austin.austinsquiz;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    /* Welcome to my "challenge" app! The code is very simple
     * Start button calls startQuiz() -> iterates cycleQuiz()
     * cycleQuiz() is the mvp, listeners silently update data
     * strings are localized and referenced from strings.xml
     *
     * I had finished this before reading the grading rubric
     * as such you can see why I opted for an additional end slide
     * the code here is kept small and dynamic to easily add slides
     */

    // view references
    private Button startButton;
    private LinearLayout titleLayout;
    private LinearLayout questionLayout;
    private TextView questionText;
    private RadioGroup radioGroup;
    private RadioButton button1;
    private RadioButton button2;
    private RadioButton button3;
    private LinearLayout resultsLayout;
    private LinearLayout gameOverLayout;
    private TextView correctText;
    private TextView moreInfo;
    private TextView scoreCounter;
    private LinearLayout scoreLayout;
    private FloatingActionButton fab;

    // layout handlers
    private boolean resultsOpen;

    // layout variables
    private boolean correct;
    private String answer;
    private int index;
    private int score;

    // animations
    private Animation slideToLeft;
    private Animation slideFromRight;
    private Animation slideToBottom;
    private Animation slideFromBottom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // assign view references
        startButton = findViewById(R.id.StartButton);
        titleLayout = findViewById(R.id.TitleLayout);
        questionLayout = findViewById(R.id.Question);
        questionText = findViewById(R.id.QuestionText);
        radioGroup = findViewById(R.id.RadioGroup);
        button1 = findViewById(R.id.Button1);
        button2 = findViewById(R.id.Button2);
        button3 = findViewById(R.id.Button3);
        resultsLayout = findViewById(R.id.Results);
        gameOverLayout = findViewById(R.id.GameOverLayout);
        correctText = findViewById(R.id.Correct);
        moreInfo = findViewById(R.id.MoreInfo);
        scoreLayout = findViewById(R.id.Score);
        scoreCounter = findViewById(R.id.ScoreCounter);
        fab = findViewById(R.id.fab);

        // set animation references
        slideToLeft = AnimationUtils.loadAnimation(this, R.anim.exit_to_left);
        slideFromRight = AnimationUtils.loadAnimation(this, R.anim.enter_from_right);
        slideToBottom = AnimationUtils.loadAnimation(this, R.anim.slide_to_bottom);
        slideFromBottom = AnimationUtils.loadAnimation(this, R.anim.slide_from_bottom);

        // set animation durations
        slideToLeft.setDuration(200);
        slideFromRight.setDuration(200);
        slideToBottom.setDuration(200);
        slideFromBottom.setDuration(200);

        // assign view listeners
        if (fab != null) fab.setOnClickListener(cycleOnClick);
        if (startButton != null) startButton.setOnClickListener(startOnClick);
        if (radioGroup != null) radioGroup.setOnCheckedChangeListener(radioOnCheck);
    }

    // (re)starts quiz on call
    private void startQuiz() {
        // slide fab into view now that we use it to navigate
        if (fab != null && fab.getVisibility() != View.VISIBLE) {
            fab.startAnimation(slideFromBottom);
            fab.setVisibility(View.VISIBLE);
        }

        // hide start button because we're done with that now
        if (startButton != null && startButton.getVisibility() == View.VISIBLE) {
            startButton.setAnimation(slideToBottom);
            startButton.setVisibility(View.INVISIBLE);
        }

        index = 0; // initialize or reset index
        score = 0; // initialize or reset score
        scoreCounter.setText(Integer.toString(score));
        if (scoreLayout.getVisibility() != View.VISIBLE) {
            // show score now that we use it
            scoreLayout.setAnimation(slideFromBottom);
            scoreLayout.setVisibility(View.VISIBLE);
        }

        // call animation listener to smoothly transition between layout screens
        Animation.AnimationListener cycler = new Animation.AnimationListener() {
            @Override // if not using listener anims will overwrite each other
            public void onAnimationEnd(Animation animation) { cycleQuiz(); }

            // required but not used because Java is the best language ever
            @Override public void onAnimationRepeat(Animation animation) {}
            @Override public void onAnimationStart(Animation animation) {}
        };

        // hide onscreen views and then run the code above
        if (titleLayout.getVisibility() == View.VISIBLE) {
            slideToLeft.setAnimationListener(cycler);
            hideView(titleLayout, slideToLeft);
        } else if (gameOverLayout.getVisibility() == View.VISIBLE) {
            slideToLeft.setAnimationListener(cycler);
            hideView(gameOverLayout, slideToLeft);
        } else cycleQuiz(); // don't use listener
    }

    // updates layout for quiz
    private void cycleQuiz() {
        String[] choices = new String[3];
        resultsOpen = false;
        index++;

        /* This is the main method in my application
         * Add more cases to add more question slides
         * If you do make localized string values too
         */

        switch (index) {
            case 1: {
                // set radio text to localized string
                choices[0] = getString(R.string.yes);
                choices[1] = getString(R.string.no);
                choices[2] = getString(R.string.maybe);
                answer = choices[0];

                // set question text and stealth update results before they're actually needed
                if (questionText != null) questionText.setText(getString(R.string.question1));
                if (moreInfo != null) moreInfo.setText(getString(R.string.result1));
                break;
            }
            case 2: {
                choices[0] = getString(R.string.very);
                choices[1] = getString(R.string.a_little);
                choices[2] = getString(R.string.never_programmed);
                answer = choices[0];

                if (questionText != null) questionText.setText(getString(R.string.question2));
                if (moreInfo != null) moreInfo.setText(getString(R.string.result2));
                break;
            }
            case 3: {
                choices[0] = getString(R.string.a_lot);
                choices[1] = getString(R.string.a_little);
                choices[2] = getString(R.string.none);
                answer = choices[2];

                if (questionText != null) questionText.setText(getString(R.string.question3));
                if (moreInfo != null) moreInfo.setText(getString(R.string.result3));
                break;
            }
            case 4: {
                choices[0] = getString(R.string.ezpz);
                choices[1] = getString(R.string.terribly);
                choices[2] = getString(R.string.crybaby);
                answer = choices[0];

                if (questionText != null) questionText.setText(getString(R.string.question4));
                if (moreInfo != null) moreInfo.setText(getString(R.string.result4));
                break;
            }
            case 5: {
                choices[0] = getString(R.string.very);
                choices[1] = getString(R.string.not);
                choices[2] = getString(R.string.what_course);
                answer = choices[0];

                if (questionText != null) questionText.setText(getString(R.string.question5));
                if (moreInfo != null) moreInfo.setText(getString(R.string.result5));
                break;
            }
            case 6: {
                choices[0] = getString(R.string.yes_noteify);
                choices[1] = getString(R.string.no_unheard_of);
                choices[2] = getString(R.string.ofc_not);
                answer = choices[0];

                if (questionText != null) questionText.setText(getString(R.string.question6));
                if (moreInfo != null) moreInfo.setText(getString(R.string.result6));
                break;
            }
            case 7: {
                choices[0] = getString(R.string.thank_you);
                choices[1] = choices[0];
                choices[2] = choices[0];
                answer = choices[0];
                // all are correct!

                if (questionText != null) questionText.setText(getString(R.string.question7));
                if (moreInfo != null) moreInfo.setText(getString(R.string.result7));
                break;
            }

            default: {
                // show (re)start button because we're done with the quiz now
                if (startButton != null && startButton.getVisibility() == View.INVISIBLE) {
                    startButton.setText(getString(R.string.restart));
                    startButton.setAnimation(slideFromBottom);
                    startButton.setVisibility(View.VISIBLE);
                }

                // manually set invisible because hideView() sets to GONE
                if (fab != null && fab.getVisibility() == View.VISIBLE) {
                    fab.startAnimation(slideToBottom);
                    fab.setVisibility(View.INVISIBLE);
                }

                // I added these two lines at the end, show the game over page with more info
                if (gameOverLayout.getVisibility() != View.VISIBLE) showView(gameOverLayout);
                Toast.makeText(this, "Required score toast message: " + Integer.toString(score), Toast.LENGTH_LONG).show();
                // score is already persistently shown in the corner but I don't want points docked per the grading rubric
                return; // don't run code below
            }
        }

        // update button text values
        button1.setText(choices[0]);
        button2.setText(choices[1]);
        button3.setText(choices[2]);

        // slide in question and radio buttons (same layout)
        if (questionLayout.getVisibility() == View.VISIBLE) {
            slideToLeft.setAnimationListener(new Animation.AnimationListener() {
                @Override // wait for animation end before showing second anim
                public void onAnimationEnd(Animation animation) {
                    questionLayout.setVisibility(View.GONE);
                    showView(questionLayout);
                }

                @Override public void onAnimationRepeat(Animation animation) {}
                @Override public void onAnimationStart(Animation animation) {}
            });

            // run above code after animation finishes
            questionLayout.startAnimation(slideToLeft);
        } else showView(questionLayout);
    }

    // slide view onscreen
    public void showView(View v) {
        v.setVisibility(View.VISIBLE);
        v.startAnimation(slideFromRight);
    }

    // slide view offscreen
    private void hideView(View v, Animation anim) {
        v.setVisibility(View.GONE);
        v.startAnimation(anim);
    }

    // slide results onscreen
    private void showResults() {
        if (correct) {
            if (correctText != null) { // set correct text format
                correctText.setText(getString(R.string.correct));
                correctText.setTextColor(Color.parseColor("#66CD00"));
            }
            score++; // increment score and update view
            scoreCounter.setText(Integer.toString(score));
        } else if (correctText != null) {
            correctText.setText(getString(R.string.wrong));
            correctText.setTextColor(Color.parseColor("#FF0000"));
        }

        // show results layout with updated information
        if (resultsLayout != null && resultsLayout.getVisibility() != View.VISIBLE) {
            resultsLayout.startAnimation(slideFromRight);
            resultsLayout.setVisibility(View.VISIBLE);
            resultsOpen = true;
        }
    }

    // slide question results offscreen
    private void hideResults(Animation anim) {
        if (resultsLayout != null && resultsLayout.getVisibility() == View.VISIBLE) {
            resultsLayout.startAnimation(anim);
            resultsLayout.setVisibility(View.GONE);
            radioGroup.clearCheck();
            resultsOpen = false;
        }
    }

    // static methods (there's only one here but I normally have more)
    public static void showSnackbar(View view, String message, int duration) {
        Snackbar.make(view, message, duration).show();
    }

    // listeners (I can put some of these in the xml but I don't want to)
    private View.OnClickListener startOnClick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            startQuiz();
        }
    };

    private View.OnClickListener cycleOnClick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if (resultsOpen) {
                // hide the result layout and show the next question
                slideToLeft.setAnimationListener(new Animation.AnimationListener() {
                    public void onAnimationEnd(Animation animation) {
                        cycleQuiz();
                    }

                    // required but not used because Java is the best language ever
                    @Override public void onAnimationRepeat(Animation animation) {}
                    @Override public void onAnimationStart(Animation animation) {}
                });

                hideResults(slideToLeft); // pass anim ref so we can listen
                fab.setImageResource(R.drawable.ic_check_black_24dp);
            }

            else if (radioGroup != null && radioGroup.getCheckedRadioButtonId() > -1) {
                // hide the question and show the answer with its results
                slideToLeft.setAnimationListener(new Animation.AnimationListener() {
                    public void onAnimationEnd(Animation animation) {
                        showResults();
                    }

                    @Override public void onAnimationRepeat(Animation animation) {}
                    @Override public void onAnimationStart(Animation animation) {}
                });

                hideView(questionLayout, slideToLeft);
                fab.setImageResource(R.drawable.ic_arrow_forward_black_24dp);
            }

            else { // throw error because the user is trying to break my app wtf
                String error = getResources().getString(R.string.error_no_check);
                showSnackbar(findViewById(R.id.Parent), error + "  Σ(°ロ°)", Snackbar.LENGTH_SHORT);
            }
        }
    };

    // updates boolean that determines if selected answer is correct
    private RadioGroup.OnCheckedChangeListener radioOnCheck = new RadioGroup.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(RadioGroup radioGroup, int i) {
            RadioButton selected = findViewById(radioGroup.getCheckedRadioButtonId());
            String a = selected != null ? selected.getText().toString() : null;
            if (a != null) correct = a.equals(answer);
        }
    };
}
