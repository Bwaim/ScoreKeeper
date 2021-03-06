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

import android.app.Application;
import android.arch.core.executor.testing.InstantTaskExecutorRule;

import com.bwaim.scorekeeper.R;
import com.bwaim.scorekeeper.data.Configuration;
import com.bwaim.scorekeeper.data.source.ConfigurationDataSource.LoadConfigurationCallback;
import com.bwaim.scorekeeper.data.source.ConfigurationRepository;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.invocation.InvocationOnMock;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Collection;
import java.util.concurrent.TimeUnit;

import static com.google.common.base.Preconditions.checkNotNull;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.when;

/**
 * Created by Fabien Boismoreau on 31/08/2018.
 * <p>
 */
public class ConfigurationViewModelTest {

    private final static String NAME_A = "Toto A";
    private final static String NAME_B = "Team B";
    private final static int INITIAL_SCORE = 30;
    private final static int SYNTAX_ERROR = 1;
    private final static int LOGIC_ERROR = 3;
    private final static int VIRUS_ATTACK = 5;
    private final static int INITIAL_TIME = 60000;

    private final static String EXPECTED_TIME = "01:00";
    private final static String TEAM_A_WIN = "TeamA wins!";

    private Configuration config;

    // Executes each task synchronously using Architecture Components.
    @Rule
    public InstantTaskExecutorRule instantExecutorRule = new InstantTaskExecutorRule();

    @Mock
    private ConfigurationRepository mConfigurationRepository;

    @Mock
    private Application mContext;

    @Mock
    private DefaultTimer mockTimer;

    @InjectMocks
    private ConfigurationViewModel mConfigurationViewModel;

    @Before
    public void setupConfigurationViewModel() {
        MockitoAnnotations.initMocks(this);

        setupContext();

        config = new Configuration(NAME_A, NAME_B, INITIAL_SCORE
                , SYNTAX_ERROR, LOGIC_ERROR, VIRUS_ATTACK, INITIAL_TIME);

        mConfigurationViewModel.setCountDownTimer(mockTimer);

        doAnswer(
                (final InvocationOnMock invocation) -> {
                        LoadConfigurationCallback callback =
                                (LoadConfigurationCallback)invocation.getArguments()[0];
                        callback.onConfigurationLoaded(config);
                        return null;
                    }

        ).when(mConfigurationRepository).getConfiguration(any(LoadConfigurationCallback.class));

        mConfigurationViewModel.init();
    }

    private void setupContext() {
        when(mContext.getApplicationContext()).thenReturn(mContext);
        when(mContext.getString(eq(R.string.resultWin), any(String.class)))
                .thenReturn(TEAM_A_WIN);
    }

    private void simulateOnTick() {
        if (mConfigurationViewModel.isStarted) {
            mConfigurationViewModel.setTime((mConfigurationViewModel.getTime())
                    - TimeUnit.SECONDS.toMillis(1));
        }
    }

    @After
    public void deleteInstance() {
        mConfigurationViewModel = null;
    }

    @Test
    public void loadConfigurationFromRepository_dataLoaded() {
        assertSame(config, mConfigurationViewModel.mConfigurationLiveData.getValue());
        checkNotNull(mConfigurationViewModel.mConfigurationLiveData.getValue());
        assertEquals(config.getNameA()
                , mConfigurationViewModel.mConfigurationLiveData.getValue().getNameA());
        assertEquals(config.getNameB()
                , mConfigurationViewModel.mConfigurationLiveData.getValue().getNameB());
        assertEquals(config.getInitialScore()
                , mConfigurationViewModel.mConfigurationLiveData.getValue().getInitialScore());
        assertEquals(config.getInitialTime()
                , mConfigurationViewModel.mConfigurationLiveData.getValue().getInitialTime());
        assertEquals(config.getSyntaxError()
                , mConfigurationViewModel.mConfigurationLiveData.getValue().getSyntaxError());
        assertEquals(config.getLogicError()
                , mConfigurationViewModel.mConfigurationLiveData.getValue().getLogicError());
        assertEquals(config.getVirusAttack()
                , mConfigurationViewModel.mConfigurationLiveData.getValue().getVirusAttack());
    }

    @Test
    public void updateTime_updated() {
        mConfigurationViewModel.updateTime();

        assertEquals(EXPECTED_TIME, mConfigurationViewModel.mTime.getValue());
    }

    @Test
    public void startTimer_TimeChange() throws Exception {
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");

        mConfigurationViewModel.updateTime();

        mConfigurationViewModel.startTimer();

        simulateOnTick();
        String time1 = mConfigurationViewModel.mTime.getValue();
        long time1inMillis = sdf.parse(time1).getTime();
        simulateOnTick();
        String time2 = mConfigurationViewModel.mTime.getValue();
        long time2inMillis = sdf.parse(time2).getTime();

        assertTrue(time1 + " > " + time2, time1inMillis > time2inMillis);
    }

    @Test
    public void startTimer_labelChange() {
        checkNotNull(mConfigurationViewModel.mStartPauseButtonLabelId.getValue());
        assertEquals(R.string.start
                , mConfigurationViewModel.mStartPauseButtonLabelId.getValue().intValue());

        mConfigurationViewModel.startTimer();
        assertEquals(R.string.pause
                , mConfigurationViewModel.mStartPauseButtonLabelId.getValue().intValue());

        mConfigurationViewModel.startTimer();
        assertEquals(R.string.start
                , mConfigurationViewModel.mStartPauseButtonLabelId.getValue().intValue());
    }

    @Test
    public void startTimer_pauseTime() throws Exception {
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");

        mConfigurationViewModel.updateTime();

        mConfigurationViewModel.startTimer();

        simulateOnTick();
        mConfigurationViewModel.startTimer();
        String time1 = mConfigurationViewModel.mTime.getValue();
        long time1inMillis = sdf.parse(time1).getTime();
        simulateOnTick();
        String time2 = mConfigurationViewModel.mTime.getValue();
        long time2inMillis = sdf.parse(time2).getTime();

        assertEquals(time1inMillis, time2inMillis);
    }

    @Test
    public void resetGame_press() {

        Integer buttonStringId = mConfigurationViewModel.mStartPauseButtonLabelId.getValue();
        String initialTime = mConfigurationViewModel.mTime.getValue();

        mConfigurationViewModel.startTimer();
        simulateOnTick();
        simulateOnTick();
        assertNotEquals(initialTime, mConfigurationViewModel.mTime.getValue());
        assertNotEquals(buttonStringId, mConfigurationViewModel.mStartPauseButtonLabelId.getValue());
        mConfigurationViewModel.resetGame();

        assertEquals(buttonStringId, mConfigurationViewModel.mStartPauseButtonLabelId.getValue());
        assertEquals(initialTime, mConfigurationViewModel.mTime.getValue());
    }

    @Test
    public void syntaxError_click_started() {
        checkNotNull(mConfigurationViewModel.mConfigurationLiveData.getValue());
        mConfigurationViewModel.startTimer();
        mConfigurationViewModel.syntaxError(1);
        mConfigurationViewModel.syntaxError(2);

        assertEquals(INITIAL_SCORE - SYNTAX_ERROR
                , mConfigurationViewModel.mConfigurationLiveData.getValue().getScoreA());
        assertEquals(INITIAL_SCORE - SYNTAX_ERROR
                , mConfigurationViewModel.mConfigurationLiveData.getValue().getScoreB());
    }

    @Test
    public void syntaxError_click_notStarted() {
        checkNotNull(mConfigurationViewModel.mConfigurationLiveData.getValue());
        mConfigurationViewModel.syntaxError(1);
        mConfigurationViewModel.syntaxError(1);

        assertEquals(INITIAL_SCORE
                , mConfigurationViewModel.mConfigurationLiveData.getValue().getScoreA());
        assertEquals(INITIAL_SCORE
                , mConfigurationViewModel.mConfigurationLiveData.getValue().getScoreB());
    }

    @Test
    public void modificationScore_reset() {
        checkNotNull(mConfigurationViewModel.mConfigurationLiveData.getValue());
        mConfigurationViewModel.startTimer();
        mConfigurationViewModel.syntaxError(1);
        assertNotEquals(INITIAL_SCORE
                , mConfigurationViewModel.mConfigurationLiveData.getValue().getScoreA());
        mConfigurationViewModel.resetGame();

        assertEquals(INITIAL_SCORE
                , mConfigurationViewModel.mConfigurationLiveData.getValue().getScoreA());
    }

    @Test
    public void logicError_click_started() {
        checkNotNull(mConfigurationViewModel.mConfigurationLiveData.getValue());
        mConfigurationViewModel.startTimer();
        mConfigurationViewModel.logicError(1);
        mConfigurationViewModel.logicError(2);

        assertEquals(INITIAL_SCORE - LOGIC_ERROR
                , mConfigurationViewModel.mConfigurationLiveData.getValue().getScoreA());
        assertEquals(INITIAL_SCORE - LOGIC_ERROR
                , mConfigurationViewModel.mConfigurationLiveData.getValue().getScoreB());
    }

    @Test
    public void logicError_click_notStarted() {
        checkNotNull(mConfigurationViewModel.mConfigurationLiveData.getValue());
        mConfigurationViewModel.logicError(1);
        mConfigurationViewModel.logicError(2);

        assertEquals(INITIAL_SCORE
                , mConfigurationViewModel.mConfigurationLiveData.getValue().getScoreA());
        assertEquals(INITIAL_SCORE
                , mConfigurationViewModel.mConfigurationLiveData.getValue().getScoreB());
    }

    @Test
    public void virusAttack_click_started() {
        checkNotNull(mConfigurationViewModel.mConfigurationLiveData.getValue());
        mConfigurationViewModel.startTimer();
        mConfigurationViewModel.virusAttack(1);
        mConfigurationViewModel.virusAttack(1);
        mConfigurationViewModel.virusAttack(2);

        assertEquals(INITIAL_SCORE + VIRUS_ATTACK
                , mConfigurationViewModel.mConfigurationLiveData.getValue().getScoreA());
        assertEquals(INITIAL_SCORE + VIRUS_ATTACK * 2
                , mConfigurationViewModel.mConfigurationLiveData.getValue().getScoreB());
    }

    @Test
    public void virusAttack_click_notStarted() {
        checkNotNull(mConfigurationViewModel.mConfigurationLiveData.getValue());
        mConfigurationViewModel.virusAttack(1);
        mConfigurationViewModel.virusAttack(2);

        assertEquals(INITIAL_SCORE
                , mConfigurationViewModel.mConfigurationLiveData.getValue().getScoreA());
        assertEquals(INITIAL_SCORE
                , mConfigurationViewModel.mConfigurationLiveData.getValue().getScoreB());
    }

    @Test
    public void hasWinner_winner() {
        checkNotNull(mConfigurationViewModel.mConfigurationLiveData.getValue());
        mConfigurationViewModel.mConfigurationLiveData.getValue().setScoreA(0);
        assertTrue(mConfigurationViewModel.hasWinner());
    }

    @Test
    public void hasWinner_noWinner() {
        assertFalse(mConfigurationViewModel.hasWinner());
    }

    @Test
    public void displayResult_teamA() {
        checkNotNull(mConfigurationViewModel.mConfigurationLiveData.getValue());
        Configuration config = mConfigurationViewModel.mConfigurationLiveData.getValue();
        config.setScoreA(0);
        mConfigurationViewModel.displayWinner();
        assertEquals(TEAM_A_WIN, mConfigurationViewModel.mTime.getValue());
    }

    @Test
    public void updateTime_DisplayWinner() {
        checkNotNull(mConfigurationViewModel.mConfigurationLiveData.getValue());
        Configuration config = mConfigurationViewModel.mConfigurationLiveData.getValue();
        config.setScoreA(15);
        config.setScoreB(25);
        mConfigurationViewModel.setTime(0);

        assertEquals(TEAM_A_WIN, mConfigurationViewModel.mTime.getValue());
    }

    @RunWith(Parameterized.class)
    public static class ConfigurationViewModelParameterizedTest {

        @Parameterized.Parameter()
        public int score;

        @Parameterized.Parameter(value = 1)
        public int modifier;

        @Parameterized.Parameter(value = 2)
        public int result;

        @Parameterized.Parameters(name = "{index} : score({0}, {1}) => {2}")
        public static Collection<Object[]> initParameters() {
            return Arrays.asList(new Object[][]{
                    {25, -1, 24},
                    {0, -1, 0},
                    {1, -2, 0},
                    {0, 3, 3}
            });
        }

        @Test
        public void updateScore_zero() {
            int score = ConfigurationViewModel.updateScore(this.score, this.modifier);

            assertEquals(this.result, score);
        }
    }
}
