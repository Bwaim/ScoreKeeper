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

package com.bwaim.scorekeeper;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

import com.bwaim.scorekeeper.configuration.ConfigurationViewModel;
import com.bwaim.scorekeeper.configuration.DefaultTimer;
import com.bwaim.scorekeeper.data.source.ConfigurationRepository;

/**
 * Created by Fabien Boismoreau on 02/09/2018.
 * <p>
 */
public class ViewModelFactory extends ViewModelProvider.NewInstanceFactory {

    private static volatile ViewModelFactory INSTANCE;

    private final ConfigurationRepository mConfigurationRepository;

    private ViewModelFactory(ConfigurationRepository repository) {
        mConfigurationRepository = repository;
    }

    public static ViewModelFactory getInstance() {

        if (INSTANCE == null) {
            synchronized (ViewModelFactory.class) {
                if (INSTANCE == null) {
                    INSTANCE = new ViewModelFactory(Injection.provideConfigurationRepository());
                }
            }
        }

        return INSTANCE;
    }

    public static void destroyInstance() {
        INSTANCE = null;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(ConfigurationViewModel.class)) {
            //noinspection unchecked
            return (T) new ConfigurationViewModel(mConfigurationRepository, new DefaultTimer());
        }
        throw new IllegalArgumentException("Unknown ViewModel class: " + modelClass.getName());
    }
}
