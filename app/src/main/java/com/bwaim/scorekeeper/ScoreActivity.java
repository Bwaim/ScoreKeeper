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

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;

import com.bwaim.scorekeeper.configuration.ScoreFragment;
import com.bwaim.scorekeeper.configuration.TimerFragment;
import com.bwaim.scorekeeper.util.ActivityUtils;

import javax.inject.Inject;

import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.support.HasSupportFragmentInjector;

public class ScoreActivity extends AppCompatActivity implements HasSupportFragmentInjector {

    @Inject
    DispatchingAndroidInjector<Fragment> dispatchingAndroidInjector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.score_act);

        setupViewFragment();
    }

    @Override
    public AndroidInjector<Fragment> supportFragmentInjector() {
        return dispatchingAndroidInjector;
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
}
