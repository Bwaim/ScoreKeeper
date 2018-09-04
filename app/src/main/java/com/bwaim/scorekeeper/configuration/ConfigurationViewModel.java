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

/**
 * Created by Fabien Boismoreau on 29/08/2018.
 * <p>
 */
public class ConfigurationViewModel extends ViewModel {

    private final ConfigurationRepository mConfigurationRepository;

    public final MutableLiveData<Configuration> mConfigurationLiveData = new MutableLiveData<>();

    public final MutableLiveData<String> mTime = new MutableLiveData<>();

    public ConfigurationViewModel(ConfigurationRepository repository) {
        mConfigurationRepository = repository;

        loadConfiguration();
    }

    public void start() {
        loadConfiguration();
    }

    private void loadConfiguration() {
        mConfigurationRepository.getConfiguration(mConfigurationLiveData::setValue);
        updateTime();
    }

    @VisibleForTesting
    public void updateTime() {
        if (mConfigurationLiveData.getValue() != null) {
            Long initialTime = mConfigurationLiveData.getValue().getInitialTime();
            mTime.setValue(String.format(Locale.getDefault(), "%02d:%02d",
                    TimeUnit.MILLISECONDS.toMinutes(initialTime),
                    TimeUnit.MILLISECONDS.toSeconds(initialTime) -
                            TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(initialTime))));
        }
    }
}
