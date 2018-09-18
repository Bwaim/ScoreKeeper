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

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class ScoreFragment extends Fragment {

    private ConfigurationViewModel mConfigurationViewModel;

    private ScoreFragBinding mScoreFragBinding;

    @BindView(R.id.syntaxErrorA)
    Button syntaxErrorA;
    @BindView(R.id.syntaxErrorB)
    Button syntaxErrorB;
    @BindView(R.id.logicErrorA)
    Button logicErrorA;
    @BindView(R.id.logicErrorB)
    Button logicErrorB;
    @BindView(R.id.virusAttackA)
    Button virusAttackA;
    @BindView(R.id.virusAttackB)
    Button virusAttackB;

    private Unbinder unbinder;

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
        unbinder = ButterKnife.bind(this, mScoreFragBinding.getRoot());
        mConfigurationViewModel = ScoreActivity.obtainViewModel(getActivity());
        mScoreFragBinding.setViewModel(mConfigurationViewModel);
        mScoreFragBinding.setLifecycleOwner(this);

        syntaxErrorA.setOnClickListener((View v) -> mConfigurationViewModel.syntaxError(1));

        syntaxErrorB.setOnClickListener((View v) -> mConfigurationViewModel.syntaxError(2));

        logicErrorA.setOnClickListener((View v) -> mConfigurationViewModel.logicError(1));

        logicErrorB.setOnClickListener((View v) -> mConfigurationViewModel.logicError(2));

        virusAttackA.setOnClickListener((View v) -> mConfigurationViewModel.virusAttack(1));

        virusAttackB.setOnClickListener((View v) -> mConfigurationViewModel.virusAttack(2));

        // Inflate the layout for this fragment
        return mScoreFragBinding.getRoot();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
