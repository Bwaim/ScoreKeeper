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

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.bwaim.scorekeeper.R;
import com.bwaim.scorekeeper.ViewModelFactory;
import com.bwaim.scorekeeper.util.ActivityUtils;

public class ScoreActivity extends AppCompatActivity {

    private ConfigurationViewModel mConfigurationViewModel;

    public static ConfigurationViewModel obtainViewModel(FragmentActivity activity) {
        // Use a Factory to inject dependencies into the ViewModel
        ViewModelFactory factory = ViewModelFactory.getInstance();

        return ViewModelProviders.of(activity, factory).get(ConfigurationViewModel.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.score_act);

        setupViewFragment();

        mConfigurationViewModel = obtainViewModel(this);
    }

    private void setupViewFragment() {
        ScoreFragment scoreFragment =
                (ScoreFragment) getSupportFragmentManager().findFragmentById(R.id.contentScoreFrame);
        if (scoreFragment == null) {
            // Create the fragment
            scoreFragment = ScoreFragment.newInstance();
            ActivityUtils.replaceFragmentInActivity(
                    getSupportFragmentManager(), scoreFragment, R.id.contentScoreFrame);
        }

        TimerFragment timerFragment =
                (TimerFragment) getSupportFragmentManager().findFragmentById(R.id.contentTimerFrame);
        if (timerFragment == null) {
            // Create the fragment
            timerFragment = TimerFragment.newInstance();
            ActivityUtils.replaceFragmentInActivity(
                    getSupportFragmentManager(), timerFragment, R.id.contentTimerFrame);
        }
    }

    public void startPauseDebugging(View view) {
        mConfigurationViewModel.startTimer();
    }
}
