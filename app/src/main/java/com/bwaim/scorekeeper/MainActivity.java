package com.bwaim.scorekeeper;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    // Initial number of bugs
    final int NUMBER_BUGS_AT_BEGINING = 25;

    // Initial time to correct the program (in milliseconds)
    final long TIME_TO_CORRECT = 90000;
    final long TIMER_INTERVAL = 1000;

    private TextView timerTV;

    // current number of bugs of the team A
    private int numberBugsTeamA = NUMBER_BUGS_AT_BEGINING;

    // current number of bugs of the team B
    private int numberBugsTeamB = NUMBER_BUGS_AT_BEGINING;

    // The count down timer
    private CountDownTimer countDownTimer;

    // boolean to know if the debugging session is started
    private boolean started = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Get the TextView of the timer
        timerTV = findViewById(R.id.timer);
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
        return new CountDownTimer(TIME_TO_CORRECT, TIMER_INTERVAL) {

            public void onTick(long millisUntilFinished) {
                long minutes = millisUntilFinished / 1000 / 60;
                timerTV.setText(String.format(Locale.getDefault(), "%02d:%02d", minutes,
                        (millisUntilFinished - (minutes * 60 * 1000)) / 1000));
            }

            public void onFinish() {
                String winner = (numberBugsTeamA < numberBugsTeamB) ?
                        getString(R.string.teamA) + " wins" :
                        (numberBugsTeamB < numberBugsTeamA) ? getString(R.string.teamB) + " wins" : getString(R.string.resultEgality);
                timerTV.setText(winner);
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
     * this function decrease the number of bugs of the team A by 1
     *
     * @param view the calling button
     */
    public void correctSyntaxErrorTeamA(View view) {
        // To avoid the score to go under 0
        if (started && numberBugsTeamA > 0) {
            numberBugsTeamA--;
            refreshScoreTeamA();
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
    public void startDebugging(View view) {
        if (!started) {
            countDownTimer.start();
            started = true;
        }
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
    }

}
