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

package com.bwaim.scorekeeper.viewmodel;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

import java.util.Map;

import javax.inject.Inject;
import javax.inject.Provider;
import javax.inject.Singleton;

/**
 * Created by Fabien Boismoreau on 02/09/2018.
 * <p>
 */
@Singleton
public class ScoreKeeperViewModelFactory implements ViewModelProvider.Factory {

    private Map<Class<? extends ViewModel>, Provider<ViewModel>> creators;

    @Inject
    ScoreKeeperViewModelFactory(Map<Class<? extends ViewModel>, Provider<ViewModel>> creators) {
        this.creators = creators;
    }

    @NonNull
    @Override
    @SuppressWarnings("UNCHECKED_CAST")
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {

        for (Map.Entry<Class<? extends ViewModel>, Provider<ViewModel>> it : creators.entrySet()) {
            Class<? extends ViewModel> key = it.getKey();
            Provider<ViewModel> creator = it.getValue();
            if (modelClass.isAssignableFrom(key)) {
                try {
                    return (T) creator.get();
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        }
//        if (modelClass.isAssignableFrom(ConfigurationViewModel.class)) {
//            //noinspection unchecked
//            return (T) new ConfigurationViewModel(mConfigurationRepository, new DefaultTimer());
//        }
        throw new IllegalArgumentException("Unknown ViewModel class: " + modelClass.getName());
    }
}
