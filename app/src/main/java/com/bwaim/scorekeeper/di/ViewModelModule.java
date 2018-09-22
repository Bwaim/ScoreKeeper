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

import android.app.Application;

import com.bwaim.scorekeeper.configuration.ConfigurationViewModel;
import com.bwaim.scorekeeper.configuration.ConfigurationViewModelModule;
import com.bwaim.scorekeeper.data.source.ConfigurationRepository;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Fabien Boismoreau on 22/09/2018.
 * <p>
 */
@Module(includes = ConfigurationViewModelModule.class)
public class ViewModelModule {
    @Provides
    @Singleton
    static ConfigurationViewModel configurationViewModel(Application app
            , ConfigurationRepository repository
            , ConfigurationViewModel.MyCountDownTimer countDownTimer) {
        return new ConfigurationViewModel(app, repository, countDownTimer);
    }
}
