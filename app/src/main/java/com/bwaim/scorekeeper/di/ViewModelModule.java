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

package com.bwaim.scorekeeper.di;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;

import com.bwaim.scorekeeper.configuration.ConfigurationViewModel;
import com.bwaim.scorekeeper.viewmodel.ScoreKeeperViewModelFactory;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;

/**
 * Created by Fabien Boismoreau on 22/09/2018.
 * <p>
 */
@Module()
abstract class ViewModelModule {
    @Binds
    @IntoMap
    @ViewModelKey(ConfigurationViewModel.class)
    abstract ViewModel bindConfigurationViewModel(
            ConfigurationViewModel configurationViewModel);

    @Binds
    abstract ViewModelProvider.Factory bindViewModelFactory(ScoreKeeperViewModelFactory factory);
}
