package com.bwaim.scorekeeper;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    // Initial number of bugs
    final int NUMBER_BUGS_AT_BEGINING = 25;

    // current number of bugs of the team A
    private int numberBugsTeamA = NUMBER_BUGS_AT_BEGINING;

    // current number of bugs of the team B
    private int numberBugsTeamB = NUMBER_BUGS_AT_BEGINING;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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
        if (numberBugsTeamA > 0) {
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
        if (numberBugsTeamA > 2) {
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
        numberBugsTeamB += 5;
        refreshScoreTeamB();
    }

    /**
     * this function decrease the number of bugs of the team B by 1
     *
     * @param view the calling button
     */
    public void correctSyntaxErrorTeamB(View view) {
        // To avoid the score to go under 0
        if (numberBugsTeamB > 0) {
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
        if (numberBugsTeamB > 2) {
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
        numberBugsTeamA += 5;
        refreshScoreTeamA();
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

        // TODO Reinit the timer
    }
}
