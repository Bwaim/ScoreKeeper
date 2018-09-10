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

package com.bwaim.scorekeeper.configuration;

import android.os.SystemClock;
import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.widget.TextView;

import com.bwaim.scorekeeper.R;
import com.bwaim.scorekeeper.ViewModelFactory;
import com.bwaim.scorekeeper.data.source.ConfigurationRepository;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.text.SimpleDateFormat;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.junit.Assert.assertTrue;

/**
 * Created by Fabien Boismoreau on 04/09/2018.
 * Test for the Score screen, the main screen
 */
@RunWith(AndroidJUnit4.class)
@LargeTest
public class ScoreScreenTest {

    /**
     * Check default value in {@link com.bwaim.scorekeeper.data.Configuration}
     */
    private final static String NAME_A = "Toto";
    private final static String NAME_B = "Coder B";
    private final static String INITIAL_SCORE = "25";
    private final static String INITIAL_TIME = "01:30";

    @Rule
    public ActivityTestRule<ScoreActivity> mScoreActivityTestRule =
            new ActivityTestRule<>(ScoreActivity.class);

    @Before
    public void resetState() {
        ViewModelFactory.destroyInstance();
        ConfigurationRepository.destroyInstance();
    }

    @Test
    public void initialState() {
        onView(withId(R.id.teamA)).check(matches(withText(NAME_A)));
        onView(withId(R.id.teamB)).check(matches(withText(NAME_B)));
        onView(withId(R.id.scoreTeamA)).check(matches(withText(INITIAL_SCORE)));
        onView(withId(R.id.scoreTeamB)).check(matches(withText(INITIAL_SCORE)));
        onView(withId(R.id.timer)).check(matches(withText(INITIAL_TIME)));
        onView(withId(R.id.startPauseButton)).check(matches(withText(R.string.start)));
    }

    @Test
    public void startTimer_click() throws Exception {

        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");

        onView(withId(R.id.startPauseButton)).check(matches(withText(R.string.start)));

        onView(withId(R.id.startPauseButton)).perform(click());

        TextView timerTV = mScoreActivityTestRule.getActivity().findViewById(R.id.timer);
        String startText = timerTV.getText().toString();
        long startTimeInMillis = sdf.parse(startText).getTime();

        // Now we wait 2 seconds
        SystemClock.sleep(2000);

        String endText = timerTV.getText().toString();
        long endTimeInMillis = sdf.parse(endText).getTime();

        assertTrue("'" + startText + "' > '" + endText + "'"
                , startTimeInMillis > endTimeInMillis);

        onView(withId(R.id.startPauseButton)).check(matches(withText(R.string.pause)));
    }

    @Test
    public void resetGame() {
        onView(withId(R.id.startPauseButton)).perform(click());
        // Now we wait 2 seconds
        SystemClock.sleep(2000);

        onView(withId(R.id.newProgram)).perform(click());

        onView(withId(R.id.startPauseButton)).check(matches(withText(R.string.start)));
        onView(withId(R.id.timer)).check(matches(withText(INITIAL_TIME)));
    }
}
