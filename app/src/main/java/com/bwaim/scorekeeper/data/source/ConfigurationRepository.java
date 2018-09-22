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

import android.support.annotation.NonNull;

import com.bwaim.scorekeeper.data.Configuration;

import javax.inject.Inject;
import javax.inject.Singleton;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by Fabien Boismoreau on 30/08/2018.
 * <p>
 */
@Singleton
public class ConfigurationRepository implements ConfigurationDataSource {

    private volatile static ConfigurationRepository INSTANCE = null;

    private final Configuration mConfiguration;

    @Inject
    ConfigurationRepository(Configuration config) {
        mConfiguration = config;
    }

//    public static ConfigurationRepository getInstance(Configuration config) {
//        if (INSTANCE == null) {
//            synchronized (ConfigurationRepository.class) {
//                if (INSTANCE == null) {
//                    INSTANCE = new ConfigurationRepository(config);
//                }
//            }
//        }
//        return INSTANCE;
//    }

//    public static void destroyInstance() {
//        INSTANCE = null;
//    }

    @Override
    public void getConfiguration(@NonNull final LoadConfigurationCallback callback) {
        checkNotNull(callback);

        callback.onConfigurationLoaded(mConfiguration);
    }
}
