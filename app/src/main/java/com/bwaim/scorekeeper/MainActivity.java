package com.bwaim.scorekeeper;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    // Initial number of bugs
    final int NUMBER_BUGS_AT_BEGINING = 25;

    // Initial time to correct the program (in milliseconds)
    final long TIME_TO_CORRECT = 90000;
    final long TIMER_INTERVAL = 1000;

    // Keys for saving the state of the instance
    final String SCORE_A = "scoreA";
    final String SCORE_B = "scoreB";
    final String MILLIS_UNTIL_FINISHED = "millisUntilFinished";

    // The TextView of the timer
    private TextView timerTV;

    // current number of bugs of the team A
    private int numberBugsTeamA = NUMBER_BUGS_AT_BEGINING;

    // current number of bugs of the team B
    private int numberBugsTeamB = NUMBER_BUGS_AT_BEGINING;

    // The count down timer
    private CountDownTimer countDownTimer;

    // The save of the millis until the countDown finish
    private long savedMillisUntilFinished;

    // boolean to know if the debugging session is started
    private boolean started = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Get the TextView of the timer
        timerTV = findViewById(R.id.timer);

        // Initialize the countdown
        savedMillisUntilFinished = TIME_TO_CORRECT;
        countDownTimer = initCountDownTimer();
        // Trick for displaying the count down
        countDownTimer.onTick(TIME_TO_CORRECT);
    }

    /**
     * Initialise a countDownTimer
     *
     * @return the countDownTimer created
     */
    private CountDownTimer initCountDownTimer() {
        // The savedMillisUntilFinished variable is used to restore the state
        // and initialise the countdown with the right remaining time
        return new CountDownTimer(savedMillisUntilFinished, TIMER_INTERVAL) {

            public void onTick(long millisUntilFinished) {
                // Here the remaining time is saved
                savedMillisUntilFinished = millisUntilFinished;
                long minutes = millisUntilFinished / 1000 / 60;
                timerTV.setText(String.format(Locale.getDefault(), "%02d:%02d", minutes,
                        (millisUntilFinished - (minutes * 60 * 1000)) / 1000));
            }

            public void onFinish() {
                displayWinner();
            }
        };
    }

    /**
     * display the score for the team A
     */
    private void refreshScoreTeamA() {
        TextView scoreTV = findViewById(R.id.scoreTeamA);
        scoreTV.setText(String.valueOf(numberBugsTeamA));
    }

    /**
     * display the score for the team B
     */
    private void refreshScoreTeamB() {
        TextView scoreTV = findViewById(R.id.scoreTeamB);
        scoreTV.setText(String.valueOf(numberBugsTeamB));
    }

    /**
     * Display the result of the competition
     */
    private void displayWinner() {
        String winner = (numberBugsTeamA < numberBugsTeamB) ?
                getString(R.string.teamA) + " wins" :
                (numberBugsTeamB < numberBugsTeamA) ? getString(R.string.teamB) + " wins" : getString(R.string.resultEgality);
        timerTV.setText(winner);
    }

    /**
     * Check if a coder resolved all bugs and display the winner if necessary
     */
    private void checkResult() {
        if (numberBugsTeamA == 0 || numberBugsTeamB == 0) {
            // To reinit the countdown at the initial value
            savedMillisUntilFinished = TIME_TO_CORRECT;
            Button startPauseButton = findViewById(R.id.startPauseButton);
            // Stop the countdown
            startPauseDebugging(startPauseButton);
            displayWinner();
            // Disable the button, it's a mandatory to start a new program
            startPauseButton.setEnabled(false);
        }
    }

    /**
     * this function decrease the number of bugs of the team A by 1
     *
     * @param view the calling button
     */
    public void correctSyntaxErrorTeamA(View view) {
        // To avoid the score to go under 0
        if (started && numberBugsTeamA > 0) {
            numberBugsTeamA--;
            refreshScoreTeamA();
            checkResult();
        }
    }

    /**
     * this function decrease the number of bugs of the team A by 3
     *
     * @param view the calling button
     */
    public void correctLogicErrorTeamA(View view) {
        // To avoid the score to go under 0
        if (started && numberBugsTeamA > 2) {
            numberBugsTeamA -= 3;
            refreshScoreTeamA();
            checkResult();
        }
    }

    /**
     * This function increase the number of bugs of the team B by 5
     *
     * @param view the calling button
     */
    public void virusAttackTeamA(View view) {
        if (started) {
            numberBugsTeamB += 5;
            refreshScoreTeamB();
        }
    }

    /**
     * this function decrease the number of bugs of the team B by 1
     *
     * @param view the calling button
     */
    public void correctSyntaxErrorTeamB(View view) {
        // To avoid the score to go under 0
        if (started && numberBugsTeamB > 0) {
            numberBugsTeamB--;
            refreshScoreTeamB();
            checkResult();
        }
    }

    /**
     * this function decrease the number of bugs of the team B by 3
     *
     * @param view the calling button
     */
    public void correctLogicErrorTeamB(View view) {
        // To avoid the score to go under 0
        if (started && numberBugsTeamB > 2) {
            numberBugsTeamB -= 3;
            refreshScoreTeamB();
            checkResult();
        }
    }

    /**
     * This function increase the number of bugs of the team A by 5
     *
     * @param view the calling button
     */
    public void virusAttackTeamB(View view) {
        if (started) {
            numberBugsTeamA += 5;
            refreshScoreTeamA();
        }
    }

    /**
     * Start the debugging session
     *
     * @param view the calling button
     */
    public void startPauseDebugging(View view) {
        Button startPauseButton = (Button) view;

        // If not started, the countdown starts and button label change
        if (!started) {
            countDownTimer = initCountDownTimer();
            countDownTimer.start();
            startPauseButton.setText(R.string.pause);
        } else { // if started, the countdown stops and button label change
            countDownTimer.cancel();
            startPauseButton.setText(R.string.start);
        }
        started = !started;
    }

    /**
     * Start a new program to debug
     *
     * @param view the calling button
     */
    public void startNewProgram(View view) {
        // Reinit the score of both teams
        numberBugsTeamA = NUMBER_BUGS_AT_BEGINING;
        numberBugsTeamB = NUMBER_BUGS_AT_BEGINING;
        refreshScoreTeamA();
        refreshScoreTeamB();

        // Reinit the timer
        countDownTimer.cancel();
        // Trick to refresh the display
        countDownTimer.onTick(TIME_TO_CORRECT);
        started = false;

        // Change the label of the button
        Button startPauseButton = findViewById(R.id.startPauseButton);
        startPauseButton.setText(R.string.start);
        // Enable the button in case it was disable
        startPauseButton.setEnabled(true);
    }

    /**
     * Save the state of the instance
     *
     * @param outState the bundle to save the state
     */
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putInt(SCORE_A, numberBugsTeamA);
        outState.putInt(SCORE_B, numberBugsTeamB);
        outState.putLong(MILLIS_UNTIL_FINISHED, savedMillisUntilFinished);

        // Pause the timer
        countDownTimer.cancel();
    }

    /**
     * restore the state of the instance
     *
     * @param savedInstanceState the bundle to restore the state
     */
    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        numberBugsTeamA = savedInstanceState.getInt(SCORE_A);
        numberBugsTeamB = savedInstanceState.getInt(SCORE_B);
        savedMillisUntilFinished = savedInstanceState.getLong(MILLIS_UNTIL_FINISHED);

        refreshScoreTeamA();
        refreshScoreTeamB();

        countDownTimer = initCountDownTimer();
        // Trick to refresh the display of the countdown
        countDownTimer.onTick(savedMillisUntilFinished);
    }
}
