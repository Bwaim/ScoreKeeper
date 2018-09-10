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
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.VisibleForTesting;

import com.bwaim.scorekeeper.R;
import com.bwaim.scorekeeper.data.Configuration;
import com.bwaim.scorekeeper.data.source.ConfigurationRepository;

import java.util.Locale;
import java.util.concurrent.TimeUnit;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by Fabien Boismoreau on 29/08/2018.
 * <p>
 */
public class ConfigurationViewModel extends AndroidViewModel {

    public final long TIMER_INTERVAL = 1000;

    private final ConfigurationRepository mConfigurationRepository;

    public final MutableLiveData<Configuration> mConfigurationLiveData = new MutableLiveData<>();

    public final MutableLiveData<String> mTime = new MutableLiveData<>();

    public final MutableLiveData<String> startPauseButtonLabel = new MutableLiveData<>();

    private MyCountDownTimer mCountDownTimer;

    private boolean isStarted;

    private long initialGameTime;

    public ConfigurationViewModel(Application app, ConfigurationRepository repository
            , MyCountDownTimer countDownTimer) {
        super(app);
        mConfigurationRepository = repository;
        mCountDownTimer = countDownTimer;
        loadConfiguration();
        init();
    }

    private void init() {
        isStarted = false;
        startPauseButtonLabel.setValue(getApplication().getString(R.string.start));
    }

    public void start() {
        loadConfiguration();
    }

    @VisibleForTesting
    public void loadConfiguration() {
        mConfigurationRepository.getConfiguration(mConfigurationLiveData::setValue);
        if (mConfigurationLiveData.getValue() != null) {
            updateTime();
            mCountDownTimer.attach(this);
            initialGameTime = mConfigurationLiveData.getValue().getInitialTime();
        }
    }

    @VisibleForTesting
    public void updateTime() {
        Long initialTime = getTime();
        mTime.setValue(String.format(Locale.getDefault(), "%02d:%02d",
                TimeUnit.MILLISECONDS.toMinutes(initialTime),
                TimeUnit.MILLISECONDS.toSeconds(initialTime) -
                        TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(initialTime))));
    }

    public void startTimer() {

        isStarted = !isStarted;
        if (isStarted) {
            mCountDownTimer.start();
        } else {
            mCountDownTimer.pause();
        }
        startPauseButtonLabel.setValue(isStarted ? getApplication().getString(R.string.pause)
                : getApplication().getString(R.string.start));
    }

    public long getTime() {
        checkNotNull(mConfigurationLiveData.getValue());
        return mConfigurationLiveData.getValue().getInitialTime();
    }

    public void setTime(long newTime) {
        checkNotNull(mConfigurationLiveData.getValue());
        mConfigurationLiveData.getValue().setInitialTime(newTime);
        updateTime();
    }

    public void resetGame() {
        setTime(initialGameTime);
        if (isStarted) {
            startTimer();
        }
    }

    public interface MyCountDownTimer {
        void attach(ConfigurationViewModel configurationViewModel);

        void start();

        void pause();
    }
}