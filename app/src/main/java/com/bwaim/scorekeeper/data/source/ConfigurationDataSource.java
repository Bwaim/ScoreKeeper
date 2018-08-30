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

/**
 * Created by Fabien Boismoreau on 30/08/2018.
 * <p>
 */
public interface ConfigurationDataSource {

    void getConfiguraton(@NonNull LoadConfigurationCallback callback);

    interface LoadConfigurationCallback {

        void onConfigurationLoaded(Configuration config);

    }
}
