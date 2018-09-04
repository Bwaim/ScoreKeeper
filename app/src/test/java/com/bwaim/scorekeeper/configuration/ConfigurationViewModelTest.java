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

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;
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

    private static Configuration CONFIG = new Configuration(NAME_A, NAME_B, INITIAL_SCORE
            , SYNTAX_ERROR, LOGIC_ERROR, VIRUS_ATTACK, INITIAL_TIME);

    // Executes each task synchronously using Architecture Components.
    @Rule
    public InstantTaskExecutorRule instantExecutorRule = new InstantTaskExecutorRule();

    @Mock
    private ConfigurationRepository mConfigurationRepository;

    @Captor
    private ArgumentCaptor<LoadConfigurationCallback> mLoadConfigurationCallbackCaptor;

    private ConfigurationViewModel mConfigurationViewModel;

    @Before
    public void setupConfigurationViewModel() {
        MockitoAnnotations.initMocks(this);

        mConfigurationViewModel = new ConfigurationViewModel(mConfigurationRepository);

        verify(mConfigurationRepository).getConfiguration(mLoadConfigurationCallbackCaptor.capture());

        mLoadConfigurationCallbackCaptor.getValue().onConfigurationLoaded(CONFIG);
    }

    @Test
    public void loadConfigurationFromRepository_dataLoaded() {
        assertSame(CONFIG, mConfigurationViewModel.mConfigurationLiveData.getValue());
    }

    @Test
    public void updateTime_updated() {
        mConfigurationViewModel.updateTime();

        assertEquals(EXPECTED_TIME, mConfigurationViewModel.mTime.getValue());
    }
}
