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

import android.arch.core.executor.testing.InstantTaskExecutorRule;

import com.bwaim.scorekeeper.data.Configuration;
import com.bwaim.scorekeeper.data.source.ConfigurationDataSource.LoadConfigurationCallback;
import com.bwaim.scorekeeper.data.source.ConfigurationRepository;
import com.bwaim.scorekeeper.mock.MockTimer;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.text.SimpleDateFormat;

import static com.google.common.base.Preconditions.checkNotNull;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.verify;

/**
 * Created by Fabien Boismoreau on 31/08/2018.
 * <p>
 */
public class ConfigurationViewModelTest {

    private final static String NAME_A = "Team A";
    private final static String NAME_B = "Team B";
    private final static int INITIAL_SCORE = 30;
    private final static int SYNTAX_ERROR = 1;
    private final static int LOGIC_ERROR = 3;
    private final static int VIRUS_ATTACK = 5;
    private final static int INITIAL_TIME = 60000;

    private final static String EXPECTED_TIME = "01:00";

    private Configuration config;

    // Executes each task synchronously using Architecture Components.
    @Rule
    public InstantTaskExecutorRule instantExecutorRule = new InstantTaskExecutorRule();

    @Mock
    private ConfigurationRepository mConfigurationRepository;

    @Captor
    private ArgumentCaptor<LoadConfigurationCallback> mLoadConfigurationCallbackCaptor;

    private ConfigurationViewModel mConfigurationViewModel;

    private MockTimer mockTimer;

    @Before
    public void setupConfigurationViewModel() {
        MockitoAnnotations.initMocks(this);

        config = new Configuration(NAME_A, NAME_B, INITIAL_SCORE
                , SYNTAX_ERROR, LOGIC_ERROR, VIRUS_ATTACK, INITIAL_TIME);
        mockTimer = new MockTimer(INITIAL_TIME);

        mConfigurationViewModel = new ConfigurationViewModel(mConfigurationRepository, mockTimer);

        verify(mConfigurationRepository).getConfiguration(mLoadConfigurationCallbackCaptor.capture());
        mLoadConfigurationCallbackCaptor.getValue().onConfigurationLoaded(config);
        mConfigurationViewModel.loadConfiguration();
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

        mockTimer.simulateOnTick();
        String time1 = mConfigurationViewModel.mTime.getValue();
        long time1inMillis = sdf.parse(time1).getTime();
        mockTimer.simulateOnTick();
        String time2 = mConfigurationViewModel.mTime.getValue();
        long time2inMillis = sdf.parse(time2).getTime();

        assertTrue(time1inMillis > time2inMillis);
    }
}
