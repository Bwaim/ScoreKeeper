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

import android.os.CountDownTimer;

public class DefaultTimer implements ConfigurationViewModel.MyCountDownTimer {

    private CountDownTimer mCountDownTimer;
    private ConfigurationViewModel mViewModel;

    public DefaultTimer() {
    }

    @Override
    public void attach(ConfigurationViewModel viewModel) {
        mViewModel = viewModel;
        init();
    }

    @Override
    public void start() {
        mCountDownTimer.start();
    }

    @Override
    public void pause() {
        mCountDownTimer.cancel();
        init();
    }

    private void init() {
        mCountDownTimer = new CountDownTimer(mViewModel.getTime(), mViewModel.TIMER_INTERVAL) {

            public void onTick(long millisUntilFinished) {
                // Here the remaining time is saved
                mViewModel.setTime(millisUntilFinished);
            }

            public void onFinish() {
                // Nothing for the moment
            }
        };
    }
}
