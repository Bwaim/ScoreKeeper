/*
 *    Copyright 2018 Fabien Boismoreau
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

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
    private TextView mTimerTV;

    // The TextView of the team A score
    private TextView mScoreATV;

    // The TextView of the team B score
    private TextView mScoreBTV;

    // The startPauseButton
    private Button mStartPauseB;

    // current number of bugs of the team A
    private int mNumberBugsTeamA = NUMBER_BUGS_AT_BEGINING;

    // current number of bugs of the team B
    private int mNumberBugsTeamB = NUMBER_BUGS_AT_BEGINING;

    // The count down timer
    private CountDownTimer mCountDownTimer;

    // The save of the millis until the countDown finish
    private long mSavedMillisUntilFinished = TIME_TO_CORRECT;

    // boolean to know if the debugging session is started
    private boolean mStarted = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Get the TextView of the timer, the score for team A and B, the start - pause button
        mTimerTV = findViewById(R.id.timer);
        mScoreATV = findViewById(R.id.scoreTeamA);
        mScoreBTV = findViewById(R.id.scoreTeamB);
        mStartPauseB = findViewById(R.id.startPauseButton);

        // Restore the state if it's not the first launch
        if (savedInstanceState != null) {
            mNumberBugsTeamA = savedInstanceState.getInt(SCORE_A);
            mNumberBugsTeamB = savedInstanceState.getInt(SCORE_B);
            mSavedMillisUntilFinished = savedInstanceState.getLong(MILLIS_UNTIL_FINISHED);
        }

        // Initialize the countdown
        mCountDownTimer = initCountDownTimer();
        // Trick for displaying the count down
        mCountDownTimer.onTick(mSavedMillisUntilFinished);

        refreshScoreTeamA();
        refreshScoreTeamB();
    }

    /**
     * Initialise a countDownTimer
     *
     * @return the countDownTimer created
     */
    private CountDownTimer initCountDownTimer() {
        // The mSavedMillisUntilFinished variable is used to restore the state
        // and initialise the countdown with the right remaining time
        return new CountDownTimer(mSavedMillisUntilFinished, TIMER_INTERVAL) {

            public void onTick(long millisUntilFinished) {
                // Here the remaining time is saved
                mSavedMillisUntilFinished = millisUntilFinished;
                long minutes = millisUntilFinished / 1000 / 60;
                mTimerTV.setText(String.format(Locale.getDefault(), "%02d:%02d", minutes,
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
        mScoreATV.setText(String.valueOf(mNumberBugsTeamA));
    }

    /**
     * display the score for the team B
     */
    private void refreshScoreTeamB() {
        mScoreBTV.setText(String.valueOf(mNumberBugsTeamB));
    }

    /**
     * Display the result of the competition
     */
    private void displayWinner() {
        String winner = (mNumberBugsTeamA < mNumberBugsTeamB) ?
                getString(R.string.teamA) + " wins" :
                (mNumberBugsTeamB < mNumberBugsTeamA) ? getString(R.string.teamB) + " wins" : getString(R.string.resultEgality);
        mTimerTV.setText(winner);
    }

    /**
     * Check if a coder resolved all bugs and display the winner if necessary
     */
    private void checkResult() {
        if (mNumberBugsTeamA == 0 || mNumberBugsTeamB == 0) {
            // To reinit the countdown at the initial value
            mSavedMillisUntilFinished = TIME_TO_CORRECT;
            // Stop the countdown
            startPauseDebugging(mStartPauseB);
            displayWinner();
            // Disable the button, it's a mandatory to start a new program
            mStartPauseB.setEnabled(false);
        }
    }

    /**
     * this function decrease the number of bugs of the team A by 1
     *
     * @param view the calling button
     */
    public void correctSyntaxErrorTeamA(View view) {
        // To avoid the score to go under 0
        if (mStarted && mNumberBugsTeamA > 0) {
            mNumberBugsTeamA--;
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
        if (mStarted && mNumberBugsTeamA > 2) {
            mNumberBugsTeamA -= 3;
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
        if (mStarted) {
            mNumberBugsTeamB += 5;
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
        if (mStarted && mNumberBugsTeamB > 0) {
            mNumberBugsTeamB--;
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
        if (mStarted && mNumberBugsTeamB > 2) {
            mNumberBugsTeamB -= 3;
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
        if (mStarted) {
            mNumberBugsTeamA += 5;
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
        if (!mStarted) {
            mCountDownTimer = initCountDownTimer();
            mCountDownTimer.start();
            startPauseButton.setText(R.string.pause);
        } else { // if started, the countdown stops and button label change
            mCountDownTimer.cancel();
            startPauseButton.setText(R.string.start);
        }
        mStarted = !mStarted;
    }

    /**
     * Start a new program to debug
     *
     * @param view the calling button
     */
    public void startNewProgram(View view) {
        // Reinit the score of both teams
        mNumberBugsTeamA = NUMBER_BUGS_AT_BEGINING;
        mNumberBugsTeamB = NUMBER_BUGS_AT_BEGINING;
        refreshScoreTeamA();
        refreshScoreTeamB();

        // Reinit the timer
        mCountDownTimer.cancel();
        // Trick to refresh the display
        mCountDownTimer.onTick(TIME_TO_CORRECT);
        mStarted = false;

        // Change the label of the button
        mStartPauseB.setText(R.string.start);
        // Enable the button in case it was disable
        mStartPauseB.setEnabled(true);
    }

    /**
     * Save the state of the instance
     *
     * @param outState the bundle to save the state
     */
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putInt(SCORE_A, mNumberBugsTeamA);
        outState.putInt(SCORE_B, mNumberBugsTeamB);
        outState.putLong(MILLIS_UNTIL_FINISHED, mSavedMillisUntilFinished);

        // Pause the timer
        mCountDownTimer.cancel();
    }

}
