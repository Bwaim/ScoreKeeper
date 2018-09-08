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

package com.bwaim.scorekeeper.mock;

import com.bwaim.scorekeeper.configuration.ConfigurationViewModel;

/**
 * Created by Fabien Boismoreau on 08/09/2018.
 * <p>
 */
public class MockTimer implements ConfigurationViewModel.MyCountDownTimer {

    private ConfigurationViewModel mViewModel;

    private boolean isStarted;
    private long mTime;

    public MockTimer(long time) {
        isStarted = false;
        mTime = time;
    }

    @Override
    public void attach(ConfigurationViewModel configurationViewModel) {
        mViewModel = configurationViewModel;
    }

    @Override
    public void start() {
        isStarted = true;
    }

    public void simulateOnTick() {
        if (isStarted)
            mViewModel.setTime(mTime--);
    }
}
