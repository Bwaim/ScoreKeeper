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

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.support.annotation.VisibleForTesting;

import com.bwaim.scorekeeper.data.Configuration;
import com.bwaim.scorekeeper.data.source.ConfigurationRepository;

import java.util.Locale;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;
import javax.inject.Singleton;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by Fabien Boismoreau on 29/08/2018.
 * <p>
 */
@Singleton
public class ConfigurationViewModel extends ViewModel {

    final long TIMER_INTERVAL = 1000;

    private final String TIMER_FORMAT = "%02d:%02d";

    private final ConfigurationRepository mConfigurationRepository;

    public final MutableLiveData<Configuration> mConfigurationLiveData = new MutableLiveData<>();

    public final MutableLiveData<String> mTime = new MutableLiveData<>();

    public final MutableLiveData<String> startPauseButtonLabel = new MutableLiveData<>();

    private MyCountDownTimer mCountDownTimer;

    @VisibleForTesting
    public boolean isStarted;

    @Inject
    public ConfigurationViewModel(ConfigurationRepository repository) {
        mConfigurationRepository = repository;
    }

    public void setCountDownTimer(MyCountDownTimer timer) {
        mCountDownTimer = timer;
    }

    public void init() {
        isStarted = false;
        startPauseButtonLabel.setValue("start");
        loadConfiguration();
    }

    private void loadConfiguration() {
        mConfigurationRepository.getConfiguration(mConfigurationLiveData::setValue);
//        if (mConfigurationLiveData.getValue() != null) {
            updateTime();
            mCountDownTimer.attach(this);
//        }
    }

    @VisibleForTesting
    public void updateTime() {
        Long time = getTime();
        mTime.setValue(String.format(Locale.getDefault(), TIMER_FORMAT,
                TimeUnit.MILLISECONDS.toMinutes(time),
                TimeUnit.MILLISECONDS.toSeconds(time) -
                        TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(time))));
        if (time == 0) {
            displayWinner();
        }
    }

    public void startTimer() {

        isStarted = !isStarted;
        if (isStarted) {
            mCountDownTimer.start();
        } else {
            mCountDownTimer.pause();
        }
        updateStartPauseLabel();
    }

    public long getInitialTime() {
        checkNotNull(mConfigurationLiveData.getValue());
        return mConfigurationLiveData.getValue().getInitialTime();
    }

    public long getTime() {
        checkNotNull(mConfigurationLiveData.getValue());
        return mConfigurationLiveData.getValue().getTime();
    }

    public void setTime(long newTime) {
        checkNotNull(mConfigurationLiveData.getValue());
        mConfigurationLiveData.getValue().setTime(newTime);
        updateTime();
    }

    public void resetGame() {
        checkNotNull(mConfigurationLiveData.getValue());
        setTime(mConfigurationLiveData.getValue().getInitialTime());
        mCountDownTimer.reset();
        isStarted = false;
        updateStartPauseLabel();
        resetScore();
    }

    public void syntaxError(int teamId) {
        checkNotNull(mConfigurationLiveData.getValue());
        Configuration config = mConfigurationLiveData.getValue();
        modifyScore(teamId, -config.getSyntaxError());
    }

    public void logicError(int teamId) {
        checkNotNull(mConfigurationLiveData.getValue());
        Configuration config = mConfigurationLiveData.getValue();
        modifyScore(teamId, -config.getLogicError());
    }

    public void virusAttack(int teamId) {
        checkNotNull(mConfigurationLiveData.getValue());
        Configuration config = mConfigurationLiveData.getValue();
        // Inverse the team id as the effect is on other team
        teamId = teamId == 1 ? 2 : 1;
        modifyScore(teamId, config.getVirusAttack());
    }

    private void modifyScore(int teamId, int value) {
        if (isStarted) {
            checkNotNull(mConfigurationLiveData.getValue());
            Configuration config = mConfigurationLiveData.getValue();
            if (teamId == 1) {
                int newScore = updateScore(config.getScoreA(), value);
                config.setScoreA(newScore);
            } else if (teamId == 2) {
                int newScore = updateScore(config.getScoreB(), value);
                config.setScoreB(newScore);
            }
            mConfigurationLiveData.setValue(config);
            if (hasWinner()) {
                displayWinner();
            }
        }
    }

    @VisibleForTesting
    public static int updateScore(int score, int modifier) {
        score += modifier;
        if (score < 0) {
            score = 0;
        }
        return score;
    }

    @VisibleForTesting
    public boolean hasWinner() {
        checkNotNull(mConfigurationLiveData.getValue());
        Configuration config = mConfigurationLiveData.getValue();
        return (config.getScoreA() == 0 || config.getScoreB() == 0);
    }

    @VisibleForTesting
    public void displayWinner() {
        checkNotNull(mConfigurationLiveData.getValue());
        Configuration config = mConfigurationLiveData.getValue();
        int scoreA = config.getScoreA();
        int scoreB = config.getScoreB();
        mTime.setValue(scoreA < scoreB
                ? "WinA"
                : scoreB < scoreA ? "WinB"
                : "Draw");
    }

    private void resetScore() {
        checkNotNull(mConfigurationLiveData.getValue());
        Configuration config = mConfigurationLiveData.getValue();
        int initialScore = config.getInitialScore();
        config.setScoreA(initialScore);
        config.setScoreB(initialScore);
        mConfigurationLiveData.setValue(config);
    }

    private void updateStartPauseLabel() {
        startPauseButtonLabel.setValue(isStarted ? "pause"
                : "start");
    }

    public interface MyCountDownTimer {
        void attach(ConfigurationViewModel configurationViewModel);

        void start();

        void pause();

        void reset();
    }
}