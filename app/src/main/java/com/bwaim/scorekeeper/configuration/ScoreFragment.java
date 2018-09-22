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
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bwaim.scorekeeper.databinding.ScoreFragBinding;
import com.bwaim.scorekeeper.di.ActivityScoped;

import javax.inject.Inject;

import dagger.android.support.DaggerFragment;

@ActivityScoped
public class ScoreFragment extends DaggerFragment {

    @Inject
    ConfigurationViewModel mConfigurationViewModel;

    private ScoreFragBinding mScoreFragBinding;

    @Inject
    public ScoreFragment() {
        // Required empty public constructor
    }

    public static ScoreFragment newInstance() {
        return new ScoreFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mScoreFragBinding = ScoreFragBinding.inflate(inflater, container, false);
        mScoreFragBinding.setViewModel(mConfigurationViewModel);
        mScoreFragBinding.setLifecycleOwner(this);

        mScoreFragBinding.syntaxErrorA.setOnClickListener((View v) -> mConfigurationViewModel.syntaxError(1));

        mScoreFragBinding.syntaxErrorB.setOnClickListener((View v) -> mConfigurationViewModel.syntaxError(2));

        mScoreFragBinding.logicErrorA.setOnClickListener((View v) -> mConfigurationViewModel.logicError(1));

        mScoreFragBinding.logicErrorB.setOnClickListener((View v) -> mConfigurationViewModel.logicError(2));

        mScoreFragBinding.virusAttackA.setOnClickListener((View v) -> mConfigurationViewModel.virusAttack(1));

        mScoreFragBinding.virusAttackB.setOnClickListener((View v) -> mConfigurationViewModel.virusAttack(2));

        // Inflate the layout for this fragment
        return mScoreFragBinding.getRoot();
    }
}
