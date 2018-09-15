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
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.bwaim.scorekeeper.R;
import com.bwaim.scorekeeper.databinding.ScoreFragBinding;

public class ScoreFragment extends Fragment {

    private ConfigurationViewModel mConfigurationViewModel;

    private ScoreFragBinding mScoreFragBinding;

    public ScoreFragment() {
        // Required empty public constructor
    }

    public static ScoreFragment newInstance() {
        return new ScoreFragment();
    }

    @Override
    public void onResume() {
        super.onResume();
        mConfigurationViewModel.start();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mScoreFragBinding = ScoreFragBinding.inflate(inflater, container, false);
        mConfigurationViewModel = ScoreActivity.obtainViewModel(getActivity());
        mScoreFragBinding.setViewModel(mConfigurationViewModel);
        mScoreFragBinding.setLifecycleOwner(this);

        Button syntaxErrorA = mScoreFragBinding.getRoot().findViewById(R.id.syntaxErrorA);
        syntaxErrorA.setOnClickListener((View v) -> mConfigurationViewModel.syntaxError(1));

        Button syntaxErrorB = mScoreFragBinding.getRoot().findViewById(R.id.syntaxErrorB);
        syntaxErrorB.setOnClickListener((View v) -> mConfigurationViewModel.syntaxError(2));

        Button logicErrorA = mScoreFragBinding.getRoot().findViewById(R.id.logicErrorA);
        logicErrorA.setOnClickListener((View v) -> mConfigurationViewModel.logicError(1));

        Button logicErrorB = mScoreFragBinding.getRoot().findViewById(R.id.logicErrorB);
        logicErrorB.setOnClickListener((View v) -> mConfigurationViewModel.logicError(2));

        Button virusAttackA = mScoreFragBinding.getRoot().findViewById(R.id.virusAttackA);
        virusAttackA.setOnClickListener((View v) -> mConfigurationViewModel.virusAttack(1));

        Button virusAttackB = mScoreFragBinding.getRoot().findViewById(R.id.virusAttackB);
        virusAttackB.setOnClickListener((View v) -> mConfigurationViewModel.virusAttack(2));

        // Inflate the layout for this fragment
        return mScoreFragBinding.getRoot();
    }
}
