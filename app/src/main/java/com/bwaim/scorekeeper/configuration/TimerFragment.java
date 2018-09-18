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


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.bwaim.scorekeeper.R;
import com.bwaim.scorekeeper.databinding.TimerFragBinding;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;


/**
 * A simple {@link Fragment} subclass.
 */
public class TimerFragment extends Fragment {

    private ConfigurationViewModel mConfigurationViewModel;

    private TimerFragBinding mTimerFragBinding;

    @BindView(R.id.startPauseButton)
    Button startPauseB;
    @BindView(R.id.newProgram)
    Button resetGameB;

    private Unbinder unbinder;

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
        unbinder = ButterKnife.bind(this, mTimerFragBinding.getRoot());
        mConfigurationViewModel = ScoreActivity.obtainViewModel(getActivity());
        mTimerFragBinding.setViewModel(mConfigurationViewModel);
        mTimerFragBinding.setLifecycleOwner(this);

        startPauseB.setOnClickListener((View v) -> mConfigurationViewModel.startTimer());

        resetGameB.setOnClickListener((View v) -> mConfigurationViewModel.resetGame());

        // Inflate the layout for this fragment
        return mTimerFragBinding.getRoot();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
