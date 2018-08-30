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

package com.bwaim.scorekeeper.data.source;

import com.bwaim.scorekeeper.data.Configuration;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Matchers.same;
import static org.mockito.Mockito.verify;

/**
 * Created by Fabien Boismoreau on 30/08/2018.
 * <p>
 */
public class ConfigurationRepositoryTest {

    private final static String NAME_A = "Team A";
    private final static String NAME_B = "Team B";
    private final static int INITIAL_SCORE = 30;
    private final static int SYNTAX_ERROR = 1;
    private final static int LOGIC_ERROR = 3;
    private final static int VIRUS_ATTACK = 5;
    private final static int INITIAL_TIME = 60;

    private static Configuration CONFIG = new Configuration(NAME_A, NAME_B, INITIAL_SCORE
            , SYNTAX_ERROR, LOGIC_ERROR, VIRUS_ATTACK, INITIAL_TIME);

    private ConfigurationRepository mConfigurationRepository;

    @Mock
    private ConfigurationDataSource.LoadConfigurationCallback mLoadConfigurationCallback;

    @Before
    public void setupConfigurationRepository() {
        MockitoAnnotations.initMocks(this);

        mConfigurationRepository = ConfigurationRepository.getInstance(CONFIG);
    }

    @After
    public void destroyRepositoryInstance() {
        ConfigurationRepository.destroyInstance();
    }

    @Test
    public void getConfiguration_check() {
        // When configuration is requested from repository
        mConfigurationRepository.getConfiguration(mLoadConfigurationCallback);

        verify(mLoadConfigurationCallback).onConfigurationLoaded(same(CONFIG));
    }
}
