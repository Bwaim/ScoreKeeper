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


import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bwaim.scorekeeper.databinding.TimerFragBinding;
import com.bwaim.scorekeeper.di.Injectable;

import javax.inject.Inject;


/**
 * A simple {@link Fragment} subclass.
 */
public class TimerFragment extends Fragment implements Injectable {

    @Inject
    ViewModelProvider.Factory viewModelFactory;

    ConfigurationViewModel mConfigurationViewModel;

    private TimerFragBinding mTimerFragBinding;

    public TimerFragment() {
        // Required empty public constructor
    }

    public static TimerFragment newInstance() {
        return new TimerFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mTimerFragBinding = TimerFragBinding.inflate(inflater, container, false);
//        mTimerFragBinding = DataBindingUtil.inflate(
//                inflater,
//                R.layout.timer_frag,
//                container,
//                false
//        );
//        mTimerFragBinding.setViewModelConfiguration(mConfigurationViewModel);
        mTimerFragBinding.setLifecycleOwner(this);

//        mTimerFragBinding.startPauseButton.setOnClickListener((View v) -> mConfigurationViewModel.startTimer());
//
//        mTimerFragBinding.newProgram.setOnClickListener((View v) -> mConfigurationViewModel.resetGame());

        // Inflate the layout for this fragment
        return mTimerFragBinding.getRoot();
//        return null;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mConfigurationViewModel = ViewModelProviders.of(this, viewModelFactory)
                .get(ConfigurationViewModel.class);
        mConfigurationViewModel.setCountDownTimer(new DefaultTimer());
        mConfigurationViewModel.init();

        mTimerFragBinding.setViewModelConfiguration(mConfigurationViewModel);
        mTimerFragBinding.startPauseButton.setOnClickListener((View v) -> mConfigurationViewModel.startTimer());
        mTimerFragBinding.newProgram.setOnClickListener((View v) -> mConfigurationViewModel.resetGame());
    }
}
